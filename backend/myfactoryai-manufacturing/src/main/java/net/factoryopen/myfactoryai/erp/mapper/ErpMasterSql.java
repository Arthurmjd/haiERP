package net.factoryopen.myfactoryai.erp.mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class ErpMasterSql {
    private ErpMasterSql() {}
    public record Field(String key,String column,Kind kind,boolean required,int maxLength,Object defaultValue) {}
    public enum Kind { TEXT, INTEGER, MONEY, ID, DATE }
    public record Spec(String table,String idColumn,List<Field> fields) {}

    private static Field text(String key,String column,boolean required,int max,Object fallback) { return new Field(key,column,Kind.TEXT,required,max,fallback); }
    private static Field integer(String key,String column,Object fallback) { return new Field(key,column,Kind.INTEGER,false,0,fallback); }
    private static Field money(String key,String column) { return new Field(key,column,Kind.MONEY,false,0,0); }
    private static Field id(String key,String column) { return new Field(key,column,Kind.ID,false,0,null); }
    private static Field date(String key,String column) { return new Field(key,column,Kind.DATE,false,0,null); }

    private static final Map<String,Spec> SPECS = Map.of(
      "category",new Spec("md_category","category_id",List.of(text("name","category_name",true,50,null),integer("sort","sort",0),integer("status","status",1))),
      "color",new Spec("md_color","color_id",List.of(text("code","color_code",true,20,null),text("name","color_name",true,50,null),integer("sort","sort",0),integer("status","status",1))),
      "size",new Spec("md_size","size_id",List.of(text("code","size_code",true,20,null),text("name","size_name",true,50,null),text("sizeGroup","size_group",true,20,"服装码"),integer("sort","sort",0),integer("status","status",1))),
      "style",new Spec("md_style","style_id",List.of(text("code","style_no",true,30,null),text("name","style_name",true,100,null),id("categoryId","category_id"),integer("gender","gender",2),text("season","season",false,20,null),text("unit","unit",true,10,"件"),money("baseCost","base_cost"),integer("status","status",1))),
      "material",new Spec("md_material","material_id",List.of(text("code","material_code",true,30,null),text("name","material_name",true,100,null),integer("category","category",1),text("spec","spec",false,100,null),text("unit","unit",true,10,"米"),money("refPrice","ref_price"),integer("status","status",1))),
      "process",new Spec("md_process","process_id",List.of(text("code","process_code",true,20,null),text("name","process_name",true,50,null),integer("sort","sort",0),integer("status","status",1))),
      "customer",new Spec("md_customer","customer_id",List.of(text("code","customer_code",true,30,null),text("name","customer_name",true,100,null),text("contact","contact",false,50,null),text("phone","phone",false,20,null),text("address","address",false,200,null),integer("creditDays","credit_days",0),integer("status","status",1))),
      "supplier",new Spec("md_supplier","supplier_id",List.of(text("code","supplier_code",true,30,null),text("name","supplier_name",true,100,null),text("contact","contact",false,50,null),text("phone","phone",false,20,null),text("address","address",false,200,null),integer("creditDays","credit_days",0),integer("status","status",1))),
      "sku",new Spec("md_sku","sku_id",List.of(text("barcode","barcode",false,50,null),integer("status","status",1))),
      "employee",new Spec("hr_employee","emp_id",List.of(text("code","emp_no",true,20,null),text("name","name",true,50,null),integer("gender","gender",0),text("phone","phone",false,20,null),id("deptId","dept_id"),text("position","position",false,50,null),integer("empType","emp_type",1),date("hireDate","hire_date"),money("baseSalary","base_salary"),money("siBase","si_base"),integer("status","status",1)))
    );

    public static Spec spec(String resource) { return SPECS.get(resource); }

    /**
     * 一条引用关系：哪张表的哪个字段指向当前档案，label 是给用户看的业务名。
     * extra 用于带额外条件的引用（例如 st_inventory 要区分 item_type）。
     */
    public record Ref(String table,String column,String label,String extra) {
        public Ref(String table,String column,String label) { this(table,column,label,null); }
    }

    /**
     * 主数据表既没有 del_flag 也没有外键约束，删除前必须在这里登记所有引用点，
     * 否则会留下指向已删除档案的孤儿数据（列表查询不校验关联，会显示成空白）。
     */
    private static final Map<String,List<Ref>> REFS = Map.of(
      "category",List.of(new Ref("md_style","category_id","款式")),
      "color",List.of(new Ref("md_sku","color_id","SKU")),
      "size",List.of(new Ref("md_sku","size_id","SKU")),
      "style",List.of(
          new Ref("md_sku","style_id","SKU"),
          new Ref("md_bom","style_id","BOM"),
          new Ref("md_style_process","style_id","工艺路线"),
          new Ref("sd_order_item","style_id","销售订单"),
          new Ref("sd_quotation_item","style_id","报价单")),
      "material",List.of(
          new Ref("md_bom","material_id","BOM"),
          new Ref("pu_purchase_order_item","material_id","采购单"),
          new Ref("pr_work_order_material","material_id","工单用料"),
          new Ref("st_inventory","item_id","库存","item_type=1")),
      "process",List.of(
          new Ref("md_style_process","process_id","工艺路线"),
          new Ref("pr_work_order_process","process_id","工单工序")),
      "customer",List.of(
          new Ref("sd_order","customer_id","销售订单"),
          new Ref("sd_quotation","customer_id","报价单"),
          new Ref("sh_shipment","customer_id","出货单")),
      "supplier",List.of(new Ref("pu_purchase_order","supplier_id","采购单")),
      "sku",List.of(
          new Ref("sh_shipment_item","sku_id","出货单"),
          new Ref("sh_return_item","sku_id","退货单"),
          new Ref("pr_work_order_sku","sku_id","工单"),
          new Ref("st_inventory","item_id","库存","item_type=2")),
      "employee",List.of(
          new Ref("pr_process_report","emp_id","报工记录"),
          new Ref("hr_piece_wage","emp_id","计件工资"),
          new Ref("hr_salary_slip","emp_id","工资单"))
    );

    public static List<Ref> refs(String resource) { return REFS.getOrDefault(resource,List.of()); }

    public static String insert(Map<String,Object> args) {
        Spec spec = spec((String)args.get("resource"));
        String columns = spec.fields().stream().map(Field::column).collect(Collectors.joining(","));
        String values = spec.fields().stream().map(field -> "#{row."+field.key()+"}").collect(Collectors.joining(","));
        return "INSERT INTO " + spec.table() + "(" + columns + ",create_by,create_time) VALUES(" + values + ",#{username},NOW())";
    }

    public static String update(Map<String,Object> args) {
        Spec spec = spec((String)args.get("resource"));
        String assignments = spec.fields().stream().map(field -> field.column()+"=#{row."+field.key()+"}").collect(Collectors.joining(","));
        return "UPDATE " + spec.table() + " SET " + assignments + ",update_by=#{username},update_time=NOW() WHERE " + spec.idColumn() + "=#{id}";
    }

    public static String detail(Map<String,Object> args) {
        Spec spec = spec((String)args.get("resource"));
        String columns = spec.fields().stream().map(field -> field.column()+" AS " + field.key()).collect(Collectors.joining(","));
        return "SELECT CAST(" + spec.idColumn() + " AS CHAR) AS id," + columns + " FROM " + spec.table() + " WHERE " + spec.idColumn() + "=#{id}";
    }

    /** 各引用点的命中条数，供删除前校验 */
    public static String refCounts(Map<String,Object> args) {
        List<Ref> refs = refs((String)args.get("resource"));
        StringBuilder sql = new StringBuilder();
        for (int i = 0; i < refs.size(); i++) {
            Ref ref = refs.get(i);
            if (i > 0) sql.append(" UNION ALL ");
            sql.append("SELECT '").append(ref.label()).append("' AS label,COUNT(*) AS cnt FROM ")
               .append(ref.table()).append(" WHERE ").append(ref.column()).append("=#{id}");
            if (ref.extra() != null) sql.append(" AND ").append(ref.extra());
        }
        if (sql.length() == 0) sql.append("SELECT '引用' AS label,0 AS cnt");
        return sql.toString();
    }

    public static String delete(Map<String,Object> args) {
        Spec spec = spec((String)args.get("resource"));
        return "DELETE FROM " + spec.table() + " WHERE " + spec.idColumn() + "=#{id}";
    }
}
