package net.factoryopen.myfactoryai.ai.nl2sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SQL 安全校验器（包含基于角色的字段权限拦截）
 * <p>
 * 功能：
 * <ul>
 *   <li>只允许 SELECT 查询语句</li>
 *   <li>防止 SQL 注入 / 危险操作（INSERT/UPDATE/DELETE/DROP等）</li>
 *   <li>防止多条语句执行（通过分号分隔）</li>
 *   <li>根据当前登录用户的角色，限制可查询的字段（敏感字段拦截）</li>
 *   <li>禁止使用 SELECT *</li>
 * </ul>
 * </p>
 */
@Component
public class SqlSecurityValidator {

    private static final Logger log = LoggerFactory.getLogger(SqlSecurityValidator.class);

    // ==================== 基础安全校验规则 ====================
    protected static final Pattern SELECT_PATTERN = Pattern.compile(
            "^\\s*SELECT\\s+.*$", Pattern.CASE_INSENSITIVE | Pattern.DOTALL
    );
    protected static final Pattern DANGEROUS_KEYWORDS_PATTERN = Pattern.compile(
            "\\b(INSERT|UPDATE|DELETE|DROP|TRUNCATE|ALTER|CREATE|REPLACE|RENAME|GRANT|REVOKE|"
                    + "EXEC|EXECUTE|INTO\\s+OUTFILE|INTO\\s+DUMPFILE|LOAD_FILE|BENCHMARK|SLEEP|"
                    + "UNION\\s+SELECT|--|#|/\\*|\\*/)\\b",
            Pattern.CASE_INSENSITIVE
    );
    protected static final Pattern MULTIPLE_STATEMENTS_PATTERN = Pattern.compile(
            ";\\s*\\S+", Pattern.CASE_INSENSITIVE
    );

    // ==================== 字段权限配置（角色 -> 表 -> 允许字段） ====================
    // 注意：角色名称应为 Spring Security 中的角色名（如 ROLE_admin, ROLE_manager, ROLE_common）
    // 若 Ruoyi 中角色存储为 "admin", "manager", "common"，则直接使用
    private static final Map<String, Map<String, Set<String>>> ROLE_ALLOWED_FIELDS = new HashMap<>();

    static {
        // 普通员工角色（common）只能访问基础业务字段
        Map<String, Set<String>> commonAllowed = new HashMap<>();
        commonAllowed.put("pln_ml", new HashSet<>(Arrays.asList(
                "id", "bill_no", "product_name", "status", "quantity_dispatched",
                "quantity_accepted", "quantity_output", "start_time", "finish_time"
        )));
        commonAllowed.put("pln_wo", new HashSet<>(Arrays.asList(
                "id", "bill_no", "product_name", "status", "quantity_planned", "need_day"
        )));
        ROLE_ALLOWED_FIELDS.put("common", commonAllowed);

        // 管理者角色（manager）
        Map<String, Set<String>> managerAllowed = new HashMap<>();
        managerAllowed.put("pln_ml", new HashSet<>(Arrays.asList(
                "id", "bill_no", "product_code", "product_name", "status", "quantity_dispatched",
                "quantity_accepted", "quantity_scrap", "quantity_redisp", "quantity_rework",
                "quantity_pending", "quantity_output", "start_time", "finish_time", "dispatching_time",
                "accepting_time", "stockreq_time", "stockin_time", "line_id", "unit_id"
        )));
        managerAllowed.put("pln_wo", new HashSet<>(Arrays.asList(
                "id", "bill_no", "product_code", "product_name", "status", "quantity_planned",
                "quantity_assigned", "need_day", "priority", "line_id"
        )));
        ROLE_ALLOWED_FIELDS.put("manager", managerAllowed);

        // 管理员（admin）不配置，表示全字段允许（在代码中特殊处理）
    }

    // ==================== 核心验证入口 ====================

    /**
     * 验证 SQL 并执行字段权限过滤（若违反则抛出 SecurityException）
     * 注：本方法仅验证，不修改 SQL（但会拒绝 SELECT *）
     *
     * @param sql 原始 SQL
     * @return 原 SQL（若通过验证）
     * @throws SecurityException 当 SQL 不安全或字段越权时
     */
    public String validateAndRewriteSql(String sql) throws SecurityException {
        log.info("【SQL安全】开始校验 SQL 并执行字段权限检查");

        // 1. 基础安全检查（不变）
        performBasicSecurityCheck(sql);

        // 2. 获取当前用户的角色（从 Spring Security 上下文中获取）
        List<String> roles = getCurrentUserRoles();
        log.debug("【SQL安全】当前用户角色: {}", roles);

        // 3. 如果是 admin 角色，跳过字段权限检查
        if (roles.contains("admin") || roles.contains("ROLE_admin")) {
            log.info("【SQL安全】用户拥有管理员角色，跳过字段权限拦截");
            return sql;
        }

        // 4. 解析 SQL 的表名和请求字段
        String tableName = extractTableName(sql);
        if (tableName == null) {
            throw new SecurityException("无法解析 SQL 中的表名，请确保 FROM 子句明确");
        }
        log.debug("【SQL安全】表名: {}", tableName);

        Set<String> requestedFields = extractSelectFields(sql);
        if (requestedFields.isEmpty()) {
            throw new SecurityException("无法解析 SELECT 字段列表，请明确列出字段");
        }
        log.debug("【SQL安全】请求字段: {}", requestedFields);

        // 5. 禁止 SELECT *
        if (requestedFields.contains("*")) {
            throw new SecurityException("出于安全考虑，不允许使用 SELECT *，请明确列出需要查询的字段");
        }

        // 6. 获取角色允许的字段集合
        Set<String> allowedFields = getAllowedFields(tableName, roles);
        if (allowedFields == null || allowedFields.isEmpty()) {
            throw new SecurityException(String.format("当前角色 %s 无权访问表 %s 的任何字段", roles, tableName));
        }

        // 7. 检查每个字段是否都在允许集合内
        for (String field : requestedFields) {
            if (!allowedFields.contains(field)) {
                throw new SecurityException(String.format("角色 %s 无权访问表 %s 的字段: %s", roles, tableName, field));
            }
        }

        log.info("【SQL安全】所有安全检查通过，字段权限满足");
        return sql;
    }

    // ==================== 基础安全检查（原 validateSql 方法） ====================

    public void validateSql(String sql) throws SecurityException {
        performBasicSecurityCheck(sql);
    }

    private void performBasicSecurityCheck(String sql) throws SecurityException {
        log.info("【SQL安全】开始基础安全检查");
        if (sql == null || sql.trim().isEmpty()) {
            throw new SecurityException("SQL 语句不能为空");
        }

        String sqlForLog = truncateSql(sql, 500);
        log.debug("【SQL安全】待校验 SQL: {}", sqlForLog);

        // 1. 只允许 SELECT
        if (!isSelectOnly(sql)) {
            throw new SecurityException("只允许 SELECT 查询语句，当前 SQL 开头非 SELECT：" + truncateSql(sql, 200));
        }
        log.debug("【SQL安全】✓ 仅 SELECT 语句");

        // 2. 危险关键字检查
        if (containsDangerousPattern(sql)) {
            throw new SecurityException("检测到潜在危险的 SQL 关键字或注入特征：" + truncateSql(sql, 200));
        }
        log.debug("【SQL安全】✓ 无危险关键字");

        // 3. 多条语句检查
        if (hasMultipleStatements(sql)) {
            throw new SecurityException("不允许执行多条 SQL 语句：" + truncateSql(sql, 200));
        }
        log.debug("【SQL安全】✓ 无多条语句");

        log.info("【SQL安全】基础安全检查通过");
    }

    protected boolean isSelectOnly(String sql) {
        return SELECT_PATTERN.matcher(sql).matches();
    }

    protected boolean containsDangerousPattern(String sql) {
        return DANGEROUS_KEYWORDS_PATTERN.matcher(sql).find();
    }

    protected boolean hasMultipleStatements(String sql) {
        return MULTIPLE_STATEMENTS_PATTERN.matcher(sql).find();
    }

    // ==================== 角色获取（完全基于 Spring Security） ====================

    private List<String> getCurrentUserRoles() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            log.warn("未获取到 Spring Security Authentication，默认赋予 common 角色");
            return Collections.singletonList("common");
        }

        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : auth.getAuthorities()) {
            String role = authority.getAuthority();
            // 如果角色名以 "ROLE_" 开头，则去掉前缀（也可以保留，但需与配置中的角色名匹配）
            if (role.startsWith("ROLE_")) {
                role = role.substring(5);
            }
            roles.add(role);
        }
        if (roles.isEmpty()) {
            log.warn("当前用户没有任何 GrantedAuthority，默认赋予 common 角色");
            roles.add("common");
        }
        return roles;
    }

    // ==================== 字段权限辅助方法 ====================

    private Set<String> getAllowedFields(String tableName, List<String> roles) {
        Set<String> merged = null;
        for (String role : roles) {
            Map<String, Set<String>> roleMapping = ROLE_ALLOWED_FIELDS.get(role);
            if (roleMapping != null && roleMapping.containsKey(tableName)) {
                Set<String> fields = roleMapping.get(tableName);
                if (merged == null) {
                    merged = new HashSet<>(fields);
                } else {
                    merged.addAll(fields);
                }
            }
        }
        // 若角色对表没有任何配置，则返回空集合（表示无权限）
        return merged;
    }

    private String extractTableName(String sql) {
        // 匹配 FROM 后的第一个表名（支持反引号、数据库前缀）
        Pattern p = Pattern.compile("FROM\\s+([`]?[a-zA-Z0-9_]+[`]?)(?:\\s|$)", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(sql);
        if (m.find()) {
            return m.group(1).replace("`", "");
        }
        return null;
    }

    private Set<String> extractSelectFields(String sql) {
        Set<String> fields = new LinkedHashSet<>();
        // 匹配 SELECT 和 FROM 之间的内容
        Pattern selectPattern = Pattern.compile("SELECT\\s+(.+?)\\s+FROM", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = selectPattern.matcher(sql);
        if (!m.find()) {
            return fields;
        }
        String fieldPart = m.group(1).trim();
        // 简单按逗号分割，忽略 AS 别名和表名前缀
        String[] parts = fieldPart.split(",");
        for (String part : parts) {
            part = part.trim();
            // 去除 AS 别名（取最后一个单词之前的部分）
            int asIdx = part.lastIndexOf(' ');
            if (asIdx > 0) {
                part = part.substring(0, asIdx);
            }
            // 去除表前缀（如 t.column -> column）
            int dotIdx = part.lastIndexOf('.');
            if (dotIdx >= 0) {
                part = part.substring(dotIdx + 1);
            }
            if (!part.isEmpty()) {
                fields.add(part);
            }
        }
        return fields;
    }

    private String truncateSql(String sql) {
        return truncateSql(sql, 300);
    }

    private String truncateSql(String sql, int maxLen) {
        if (sql == null) return null;
        if (sql.length() <= maxLen) return sql;
        return sql.substring(0, maxLen) + "...";
    }
}