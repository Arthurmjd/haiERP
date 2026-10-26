package net.factoryopen.myfactoryai.erp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import net.factoryopen.myfactoryai.erp.mapper.ErpDocMapper;
import net.factoryopen.myfactoryai.erp.mapper.ErpDocSql;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 业务单据的删除草稿与作废。
 *
 * <p>删除只对草稿开放并物理删除（含子表）；已经推进过状态的单据只能作废（status=9），
 * 以免把已经产生后续业务的数据抹掉。两者都会检查下游引用。</p>
 */
@Service
public class ErpDocService {

    private final ErpDocMapper mapper;

    public ErpDocService(ErpDocMapper mapper) { this.mapper = mapper; }

    @Transactional
    public void deleteDraft(String doc, Long id) {
        Map<String, Object> row = load(doc, id);
        ErpDocSql.DocSpec spec = spec(doc);
        int status = ((Number) row.get("status")).intValue();
        if (!spec.deletable().contains(status)) {
            throw new ServiceException("只有草稿可以删除，当前状态不允许；如需终止业务请使用作废", 2001);
        }
        ensureNoRefs(doc, id, "删除");
        for (ErpDocSql.Child child : spec.children()) {
            Map<String, Object> args = new HashMap<>();
            args.put("table", child.table());
            args.put("column", child.column());
            args.put("id", id);
            mapper.deleteChild(args);
        }
        Map<String, Object> args = new HashMap<>();
        args.put("doc", doc);
        args.put("id", id);
        if (mapper.deleteHeader(args) != 1) throw new ServiceException("单据已变化，请刷新后重试", 2006);
    }

    @Transactional
    public void cancel(String doc, Long id) {
        Map<String, Object> row = load(doc, id);
        ErpDocSql.DocSpec spec = spec(doc);
        if (spec.cancelable().isEmpty()) throw new ServiceException("该单据不支持作废", 2001);
        int status = ((Number) row.get("status")).intValue();
        if (status == 9) throw new ServiceException("单据已作废", 2001);
        if (!spec.cancelable().contains(status)) {
            throw new ServiceException("当前状态不允许作废", 2001);
        }
        ensureNoRefs(doc, id, "作废");
        Map<String, Object> args = new HashMap<>();
        args.put("doc", doc);
        args.put("id", id);
        args.put("username", SecurityUtils.getUsername());
        if (mapper.cancel(args) != 1) throw new ServiceException("单据已变化，请刷新后重试", 2006);
    }

    private Map<String, Object> load(String doc, Long id) {
        spec(doc);
        if (id == null || id <= 0) throw new ServiceException("单据 ID 不合法", 1001);
        Map<String, Object> args = new HashMap<>();
        args.put("doc", doc);
        args.put("id", id);
        Map<String, Object> row = mapper.selectStatus(args);
        if (row == null) throw new ServiceException("单据不存在", 1001);
        return row;
    }

    /** 有下游单据时不允许删除或作废，并告诉用户具体是哪些 */
    private void ensureNoRefs(String doc, Long id, String action) {
        Map<String, Object> args = new HashMap<>();
        args.put("doc", doc);
        args.put("id", id);
        List<String> used = new ArrayList<>();
        for (Map<String, Object> row : mapper.refCounts(args)) {
            long count = ((Number) row.get("cnt")).longValue();
            if (count > 0) used.add(row.get("label") + " " + count + " 条");
        }
        if (!used.isEmpty()) {
            throw new ServiceException("该单据已被" + String.join("、", used) + "引用，不能" + action, 2007);
        }
    }

    private ErpDocSql.DocSpec spec(String doc) {
        ErpDocSql.DocSpec spec = ErpDocSql.spec(doc);
        if (spec == null) throw new ServiceException("未知单据类型", 1001);
        return spec;
    }
}
