package net.factoryopen.myfactoryai.web.controller.system;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.security.access.prepost.PreAuthorize;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 系统管理接口的权限护栏。
 *
 * <p>背景：RuoYi 底座带来的 system 控制器原本把 {@code @PreAuthorize} 全部注释掉了，
 * 相当于任何登录用户都能新增/删除用户与角色、修改菜单与组织架构。系统管理页面前端是
 * 靠静态路由守卫拦的，但前端守卫可以被绕过，后端必须自己拦住。</p>
 *
 * <p>这个测试同时把「后端注解里的权限字符串」和「sql/system-permissions.sql 里写入
 * sys_menu.perms 的字符串」钉在一起：两边不一致时，前端菜单能看见但接口 403，
 * 或者反过来接口放行但按钮不显示。</p>
 */
class SystemPermissionGuardTest {

    /** system 控制器所在包 */
    private static final Path CONTROLLER_DIR = Path.of(
            "src/main/java/net/factoryopen/myfactoryai/web/controller/system");

    /** 权限种子脚本（测试运行目录是 backend/myfactoryai-admin） */
    private static final Path PERMISSION_SQL = Path.of("../../sql/system-permissions.sql");

    private static final Pattern PERMS_IN_SQL =
            Pattern.compile("'((?:system|sys):[a-zA-Z:]+)'");

    /**
     * 这些控制器天然不需要权限：登录、注册、个人信息、首页。
     */
    private static final Set<String> EXEMPT_CLASSES = Set.of(
            "net.factoryopen.myfactoryai.web.controller.system.SysLoginController",
            "net.factoryopen.myfactoryai.web.controller.system.SysRegisterController",
            "net.factoryopen.myfactoryai.web.controller.system.SysProfileController",
            "net.factoryopen.myfactoryai.web.controller.system.SysIndexController");

    /**
     * 这些方法本身只服务于「已授权后的下拉/树选择」，统一挂到所属模块的 list 权限上，
     * 由各自的 @PreAuthorize 单独覆盖 —— 记录下来是为了让「谁归谁管」一目了然。
     */
    private static final Map<String, String> KNOWN_ENDPOINTS = Map.of(
            "SysConfigController#getConfigKey", "system:config:query",
            "SysDeptController#updateDeptXY", "system:dept:edit",
            "SysDictDataController#dictType", "system:dict:list",
            "SysDictTypeController#optionselect", "system:dict:list",
            "SysMenuController#roleMenuTreeselect", "system:menu:list",
            "SysMenuController#treeselect", "system:menu:list",
            "SysPostController#optionselect", "system:post:list",
            "SysUserController#importTemplate", "system:user:import");

    @Test
    void systemControllersAreGuardedByPreAuthorize() throws Exception {
        List<String> unguarded = new ArrayList<>();
        for (Class<?> type : guardedControllerClasses()) {
            if (EXEMPT_CLASSES.contains(type.getName())) continue;
            for (Method method : type.getDeclaredMethods()) {
                if (method.isSynthetic() || method.getName().startsWith("lambda$")) continue;
                if (method.getAnnotation(PreAuthorize.class) != null) continue;
                String key = type.getSimpleName() + "#" + method.getName();
                if (KNOWN_ENDPOINTS.containsKey(key)) continue;
                unguarded.add(key);
            }
        }
        assertTrue(unguarded.isEmpty(),
                "以下系统管理接口缺少 @PreAuthorize，任何登录用户都能调用：" + unguarded);
    }

    @Test
    void knownEndpointsCarryTheirDeclaredPermission() throws Exception {
        // 记录下来的端点必须真的挂了对应权限，避免清单与代码脱节
        for (Class<?> type : guardedControllerClasses()) {
            for (Method method : type.getDeclaredMethods()) {
                String key = type.getSimpleName() + "#" + method.getName();
                String expected = KNOWN_ENDPOINTS.get(key);
                if (expected == null) continue;
                PreAuthorize annotation = method.getAnnotation(PreAuthorize.class);
                assertTrue(annotation != null, key + " 应当带 @PreAuthorize");
                assertTrue(annotation.value().contains(expected),
                        key + " 的权限应为 " + expected + "，实际 " + annotation.value());
            }
        }
    }

    @Test
    void permissionStringsExistInSeedSql() throws Exception {
        Set<String> declared = declaredPermissions();
        assertFalse(declared.isEmpty(), "未能从控制器解析出任何权限字符串");

        Set<String> seeded = seededPermissions();
        assertFalse(seeded.isEmpty(), "未能从 sql/system-permissions.sql 解析出任何权限字符串");

        List<String> missing = new ArrayList<>();
        for (String perm : declared) {
            if (!seeded.contains(perm)) {
                missing.add(perm);
            }
        }
        assertTrue(missing.isEmpty(),
                "控制器使用了这些权限，但 sql/system-permissions.sql 没有写入 sys_menu.perms：" + missing);
    }

    @Test
    void seedSqlDeclaresMenusForEveryModule() throws Exception {
        // 系统管理至少要有用户/角色/菜单/部门/岗位五个入口菜单，
        // 否则前端左侧「系统管理」分组会是空的
        String sql = Files.readString(PERMISSION_SQL, StandardCharsets.UTF_8);
        for (String module : new String[]{"user", "role", "menu", "dept", "post"}) {
            assertTrue(sql.contains("'system:" + module + ":list'"),
                    "种子脚本缺少 system:" + module + ":list 菜单项");
        }
    }

    // ------------------------------------------------------------------

    private List<Class<?>> guardedControllerClasses() throws Exception {
        if (!Files.isDirectory(CONTROLLER_DIR)) {
            throw new IllegalStateException("找不到控制器目录：" + CONTROLLER_DIR.toAbsolutePath());
        }
        List<Class<?>> classes = new ArrayList<>();
        try (var stream = Files.list(CONTROLLER_DIR)) {
            for (Path file : stream.filter(p -> p.getFileName().toString().endsWith(".java")).toList()) {
                String simpleName = file.getFileName().toString().replace(".java", "");
                classes.add(Class.forName(
                        "net.factoryopen.myfactoryai.web.controller.system." + simpleName));
            }
        }
        return classes;
    }

    /** 收集控制器注解里用到的全部权限字符串 */
    private Set<String> declaredPermissions() throws Exception {
        Set<String> perms = new LinkedHashSet<>();
        for (Class<?> type : guardedControllerClasses()) {
            for (Method method : type.getDeclaredMethods()) {
                PreAuthorize annotation = method.getAnnotation(PreAuthorize.class);
                if (annotation == null) continue;
                Matcher matcher = Pattern.compile("hasPermi\\('([^']+)'\\)").matcher(annotation.value());
                if (matcher.find()) {
                    perms.add(matcher.group(1));
                }
            }
        }
        return perms;
    }

    /** 收集种子脚本里写入 sys_menu.perms 的权限字符串 */
    private Set<String> seededPermissions() throws Exception {
        String sql = Files.readString(PERMISSION_SQL, StandardCharsets.UTF_8);
        Set<String> perms = new LinkedHashSet<>();
        Matcher matcher = PERMS_IN_SQL.matcher(sql);
        while (matcher.find()) {
            perms.add(matcher.group(1));
        }
        return perms;
    }
}
