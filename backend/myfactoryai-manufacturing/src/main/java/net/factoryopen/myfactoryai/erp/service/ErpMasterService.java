package net.factoryopen.myfactoryai.erp.service;

import net.factoryopen.myfactoryai.erp.support.ErpErrorCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.erp.mapper.ErpMasterMapper;
import net.factoryopen.myfactoryai.erp.mapper.ErpMasterSql;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ErpMasterService {
    private final ErpMasterMapper mapper;
    public ErpMasterService(ErpMasterMapper mapper) { this.mapper=mapper; }

    public List<Map<String,Object>> departments() { return mapper.departments(); }

    public Map<String,Object> detail(String resource,Long id) {
        checkSpec(resource);
        Map<String,Object> result = mapper.detail(Map.of("resource",resource,"id",id));
        if (result == null) throw new ServiceException("档案不存在", ErpErrorCode.INVALID_PARAM.code());
        return result;
    }

    @Transactional
    public Map<String,String> create(String resource,Map<String,Object> input) {
        if ("sku".equals(resource)) throw new ServiceException("SKU 请使用色码生成", ErpErrorCode.INVALID_PARAM.code());
        ErpMasterSql.Spec spec = checkSpec(resource);
        Map<String,Object> args = new HashMap<>();
        args.put("resource",resource); args.put("row",normalize(spec,input,null));
        args.put("username",SecurityUtils.getUsername());
        try { mapper.insert(args); }
        catch (DuplicateKeyException e) { throw new ServiceException("编码或名称已存在", ErpErrorCode.CONFLICT.code()); }
        return Map.of("id",((Number)args.get("id")).toString());
    }

    @Transactional
    public void update(String resource,Long id,Map<String,Object> input) {
        ErpMasterSql.Spec spec = checkSpec(resource);
        Map<String,Object> old = detail(resource,id);
        Map<String,Object> args = new HashMap<>();
        args.put("resource",resource); args.put("id",id);
        args.put("row",normalize(spec,input,old)); args.put("username",SecurityUtils.getUsername());
        try { if (mapper.update(args) != 1) throw new ServiceException("档案已变化", ErpErrorCode.CONFLICT.code()); }
        catch (DuplicateKeyException e) { throw new ServiceException("编码或名称已存在", ErpErrorCode.CONFLICT.code()); }
    }

    /**
     * 删除档案。主数据表没有 del_flag、也没有外键约束，所以必须先自己查引用，
     * 被引用过的档案不允许物理删除，否则单据里会出现空白的关联名称。
     */
    @Transactional
    public void delete(String resource,Long id) {
        if (id == null || id <= 0) throw new ServiceException("档案 ID 不合法", ErpErrorCode.INVALID_PARAM.code());
        checkSpec(resource);
        Map<String,Object> current = mapper.detail(Map.of("resource",resource,"id",id));
        if (current == null) throw new ServiceException("档案不存在或已删除", ErpErrorCode.INVALID_PARAM.code());
        Map<String,Object> refArgs = new HashMap<>();
        refArgs.put("resource",resource); refArgs.put("id",id);
        List<String> used = new java.util.ArrayList<>();
        for (Map<String,Object> row : mapper.refCounts(refArgs)) {
            long count = ((Number)row.get("cnt")).longValue();
            if (count > 0) used.add(row.get("label") + " " + count + " 条");
        }
        if (!used.isEmpty()) {
            throw new ServiceException("该档案已被" + String.join("、",used)
                    + "引用，不能删除；如需停用请编辑后把状态改为停用",2007);
        }
        Map<String,Object> args = new HashMap<>();
        args.put("resource",resource); args.put("id",id);
        if (mapper.delete(args) != 1) throw new ServiceException("档案已变化，请刷新后重试", ErpErrorCode.CONFLICT.code());
    }

    /**
     * 员工在职/离职切换。0 离职 1 在职。离职前不校验引用——历史上报过工、
     * 领过工资的员工本来就应该保留记录，只是不能再排产。
     */
    @Transactional
    public void changeStatus(String resource,Long id,Integer status) {
        if (status == null || (status != 0 && status != 1)) throw new ServiceException("状态只能是 0（停用/离职）或 1（启用/在职）", ErpErrorCode.INVALID_PARAM.code());
        Map<String,Object> old = detail(resource,id);
        Map<String,Object> input = new HashMap<>();
        input.put("status",status);
        Map<String,Object> args = new HashMap<>();
        args.put("resource",resource); args.put("id",id);
        args.put("row",normalize(checkSpec(resource),input,old)); args.put("username",SecurityUtils.getUsername());
        if (mapper.update(args) != 1) throw new ServiceException("档案已变化，请刷新后重试", ErpErrorCode.CONFLICT.code());
    }

    @Transactional
    public Map<String,String> generateSku(Long styleId,Long colorId,Long sizeId) {
        if (styleId == null || colorId == null || sizeId == null) throw new ServiceException("款式、颜色和尺码必填", ErpErrorCode.INVALID_PARAM.code());
        Long existing = mapper.existingSku(styleId,colorId,sizeId);
        if (existing != null) return Map.of("id",existing.toString());
        String code = mapper.skuCode(styleId,colorId,sizeId);
        if (code == null || code.length() > 50) throw new ServiceException("色码未启用或 SKU 编码过长", ErpErrorCode.INVALID_PARAM.code());
        Map<String,Object> args = new HashMap<>();
        args.put("code",code); args.put("styleId",styleId); args.put("colorId",colorId);
        args.put("sizeId",sizeId); args.put("username",SecurityUtils.getUsername());
        try { mapper.insertSku(args); }
        catch (DuplicateKeyException e) {
            Long raced = mapper.existingSku(styleId,colorId,sizeId);
            if (raced != null) return Map.of("id",raced.toString());
            throw new ServiceException("SKU 编码与其他色码冲突", ErpErrorCode.CONFLICT.code());
        }
        return Map.of("id",((Number)args.get("id")).toString());
    }

    private Map<String,Object> normalize(ErpMasterSql.Spec spec,Map<String,Object> input,Map<String,Object> old) {
        if (input == null) throw new ServiceException("档案内容必填", ErpErrorCode.INVALID_PARAM.code());
        for (String key : input.keySet()) {
            if (spec.fields().stream().noneMatch(field -> field.key().equals(key))) throw new ServiceException("未知档案字段: " + key, ErpErrorCode.INVALID_PARAM.code());
        }
        Map<String,Object> row = new HashMap<>();
        for (ErpMasterSql.Field field : spec.fields()) {
            Object raw = input.containsKey(field.key()) ? input.get(field.key()) : old == null ? field.defaultValue() : old.get(field.key());
            if (raw == null && field.required()) throw new ServiceException(field.key() + " 必填",1001);
            Object value = switch (field.kind()) {
                case TEXT -> normalizeText(field,raw);
                case INTEGER -> normalizeInteger(field,raw);
                case MONEY -> normalizeMoney(raw);
                case ID -> normalizeId(raw);
                case DATE -> normalizeDate(raw);
            };
            if ("hr_employee".equals(spec.table()) && "gender".equals(field.key()) && Integer.valueOf(2).equals(value)) {
                throw new ServiceException("gender 不合法", ErpErrorCode.INVALID_PARAM.code());
            }
            row.put(field.key(),value);
        }
        return row;
    }

    private Object normalizeText(ErpMasterSql.Field field,Object raw) {
        if (raw == null) return null;
        String value = raw.toString().trim();
        if (field.required() && value.isEmpty()) throw new ServiceException(field.key() + " 必填",1001);
        if (value.length() > field.maxLength()) throw new ServiceException(field.key() + " 过长",1001);
        return value.isEmpty() ? null : value;
    }

    private Object normalizeInteger(ErpMasterSql.Field field,Object raw) {
        if (raw == null) return null;
        try {
            int value = Integer.parseInt(raw.toString());
            if (value < 0 || ("status".equals(field.key()) && value > 1)
                    || ("gender".equals(field.key()) && value > 2)
                    || ("category".equals(field.key()) && value > 2)
                    || ("empType".equals(field.key()) && (value < 1 || value > 2))) throw new NumberFormatException();
            return value;
        } catch (NumberFormatException e) { throw new ServiceException(field.key() + " 不合法",1001); }
    }

    private Object normalizeMoney(Object raw) {
        try {
            BigDecimal value = new BigDecimal(raw.toString()).setScale(2);
            if (value.signum() < 0 || value.precision() > 10) throw new NumberFormatException();
            return value;
        } catch (RuntimeException e) { throw new ServiceException("金额不合法", ErpErrorCode.INVALID_PARAM.code()); }
    }

    private Object normalizeId(Object raw) {
        if (raw == null || raw.toString().isBlank()) return null;
        try { long value = Long.parseLong(raw.toString()); if (value <= 0) throw new NumberFormatException(); return value; }
        catch (NumberFormatException e) { throw new ServiceException("关联 ID 不合法", ErpErrorCode.INVALID_PARAM.code()); }
    }

    private Object normalizeDate(Object raw) {
        if (raw == null || raw.toString().isBlank()) return null;
        try { return LocalDate.parse(raw.toString()); }
        catch (DateTimeParseException e) { throw new ServiceException("日期不合法", ErpErrorCode.INVALID_PARAM.code()); }
    }

    private ErpMasterSql.Spec checkSpec(String resource) {
        ErpMasterSql.Spec spec = ErpMasterSql.spec(resource);
        if (spec == null) throw new ServiceException("未知主数据类型", ErpErrorCode.INVALID_PARAM.code());
        return spec;
    }
}
