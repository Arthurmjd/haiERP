package net.factoryopen.myfactoryai.erp.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 业务单据的删除草稿 / 作废规格。
 *
 * <p>单据删除只允许在「草稿」状态做物理删除（连同子表），一旦推进过状态就应走作废，
 * 保留痕迹。作废把 status 置 9；没有 9 状态的单据（报价单、成本结算）不提供作废。</p>
 *
 * <p>下游引用（例如订单已被工单引用）由 refs 拦截，避免删掉一张已经产生后续业务链的草稿。</p>
 */
public final class ErpDocSql {

    private ErpDocSql() {}

    /** 子表：删除表头前先清空 */
    public record Child(String table, String column) {}

    /**
     * 引用方：有数据则不允许删除/作废。
     * extra 用于加额外条件，例如排除已经作废（status=9）的下游单据——
     * 作废掉的工单不应该继续挡住订单作废。
     */
    public record Ref(String table, String column, String label, String extra) {
        public Ref(String table, String column, String label) { this(table, column, label, null); }
    }

    public record DocSpec(String table, String idColumn, String statusColumn,
                          List<Child> children, Set<Integer> deletable, Set<Integer> cancelable,
                          List<Ref> refs) {}

    private static final String ORDER = "sd:order";
    private static final String PURCHASE = "pu:purchaseOrder";
    private static final String WORK_ORDER = "pr:workOrder";
    private static final String SHIPMENT = "sh:shipment";
    private static final String RETURN = "sh:return";
    private static final String QUOTATION = "sd:quotation";
    private static final String SETTLEMENT = "fn:settlement";

    private static final Map<String, DocSpec> SPECS = Map.of(
      ORDER, new DocSpec("sd_order", "order_id", "status",
          List.of(new Child("sd_order_item", "order_id")),
          Set.of(0), Set.of(0, 1),
          List.of(new Ref("pr_work_order", "order_id", "生产工单", "status<>9"),
                  new Ref("sh_shipment", "order_id", "出货单", "status<>9"))),
      PURCHASE, new DocSpec("pu_purchase_order", "po_id", "status",
          List.of(new Child("pu_purchase_order_item", "po_id")),
          Set.of(0), Set.of(0, 1),
          List.of()),
      WORK_ORDER, new DocSpec("pr_work_order", "wo_id", "status",
          List.of(new Child("pr_work_order_sku", "wo_id"),
                  new Child("pr_work_order_material", "wo_id"),
                  new Child("pr_work_order_process", "wo_id")),
          Set.of(0), Set.of(0),
          List.of(new Ref("pr_process_report", "wo_id", "报工记录"))),
      SHIPMENT, new DocSpec("sh_shipment", "shipment_id", "status",
          List.of(new Child("sh_shipment_item", "shipment_id")),
          Set.of(0), Set.of(0),
          List.of(new Ref("sh_return", "shipment_id", "退货单", "status<>9"))),
      RETURN, new DocSpec("sh_return", "return_id", "status",
          List.of(new Child("sh_return_item", "return_id")),
          Set.of(0), Set.of(0),
          List.of()),
      QUOTATION, new DocSpec("sd_quotation", "quote_id", "status",
          List.of(new Child("sd_quotation_item", "quote_id")),
          Set.of(0), Set.of(),
          List.of()),
      SETTLEMENT, new DocSpec("fn_cost_settlement", "settle_id", "status",
          List.of(new Child("fn_cost_detail", "settle_id")),
          Set.of(0), Set.of(),
          List.of(new Ref("sd_quotation_item", "source_settle_id", "报价单")))
    );

    public static DocSpec spec(String doc) { return SPECS.get(doc); }

    public static String selectStatus(Map<String, Object> args) {
        DocSpec spec = spec((String) args.get("doc"));
        return "SELECT CAST(" + spec.idColumn() + " AS CHAR) AS id," + spec.statusColumn()
                + " AS status FROM " + spec.table()
                + " WHERE " + spec.idColumn() + "=#{id}";
    }

    /**
     * 删除单个子表。表名与字段名都取自本类的常量规格，不来自请求参数，
     * 因此这里的字符串拼接不存在注入风险。
     */
    public static String deleteChild(Map<String, Object> args) {
        return "DELETE FROM " + args.get("table") + " WHERE " + args.get("column") + "=#{id}";
    }

    public static String deleteHeader(Map<String, Object> args) {
        DocSpec spec = spec((String) args.get("doc"));
        return "DELETE FROM " + spec.table() + " WHERE " + spec.idColumn() + "=#{id}";
    }

    public static String cancel(Map<String, Object> args) {
        DocSpec spec = spec((String) args.get("doc"));
        Set<Integer> allowed = spec.cancelable();
        if (allowed.isEmpty()) allowed = Set.of(Integer.MIN_VALUE);
        String statuses = allowed.stream().map(String::valueOf).collect(Collectors.joining(","));
        return "UPDATE " + spec.table() + " SET status=9,update_by=#{username},update_time=NOW()"
                + " WHERE " + spec.idColumn() + "=#{id} AND status IN (" + statuses + ")";
    }

    public static String refCounts(Map<String, Object> args) {
        DocSpec spec = spec((String) args.get("doc"));
        if (spec.refs().isEmpty()) {
            return "SELECT '引用' AS label,0 AS cnt";
        }
        StringBuilder sql = new StringBuilder();
        for (int i = 0; i < spec.refs().size(); i++) {
            Ref ref = spec.refs().get(i);
            if (i > 0) sql.append(" UNION ALL ");
            sql.append("SELECT '").append(ref.label()).append("' AS label,COUNT(*) AS cnt FROM ")
               .append(ref.table()).append(" WHERE ").append(ref.column()).append("=#{id}");
            if (ref.extra() != null) sql.append(" AND ").append(ref.extra());
        }
        return sql.toString();
    }
}
