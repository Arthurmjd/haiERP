package net.factoryopen.myfactoryai.erp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ErpReadMapper {
    @Select("SELECT CAST(supplier_id AS CHAR) AS id,supplier_name AS name FROM md_supplier WHERE status=1 ORDER BY supplier_name")
    List<Map<String,Object>> suppliers();

    @Select("SELECT CAST(material_id AS CHAR) AS id,material_code AS code,material_name AS name,unit,ref_price AS refPrice FROM md_material WHERE status=1 ORDER BY material_code")
    List<Map<String,Object>> materials();

    @Select("SELECT CAST(emp_id AS CHAR) AS id,CONCAT(emp_no,' · ',name) AS name FROM hr_employee WHERE status=1 AND emp_type=1 ORDER BY emp_no")
    List<Map<String,Object>> workers();
    @Select("SELECT CAST(customer_id AS CHAR) AS id, customer_name AS name FROM md_customer WHERE status=1 ORDER BY customer_name")
    List<Map<String, Object>> customers();

    @Select("SELECT CAST(style_id AS CHAR) AS id, style_no AS code, style_name AS name FROM md_style WHERE status=1 ORDER BY style_no")
    List<Map<String, Object>> styles();

    @Select("SELECT CAST(color_id AS CHAR) AS id, color_name AS name FROM md_color WHERE status=1 ORDER BY sort,color_id")
    List<Map<String, Object>> colors();

    @Select("SELECT CAST(size_id AS CHAR) AS id, size_name AS name FROM md_size WHERE status=1 ORDER BY sort,size_id")
    List<Map<String, Object>> sizes();

    @Select("""
        SELECT CAST(sku_id AS CHAR) AS id, sku_code AS code,
               CAST(style_id AS CHAR) AS styleId,
               CAST(color_id AS CHAR) AS colorId,
               CAST(size_id AS CHAR) AS sizeId
        FROM md_sku WHERE status=1 ORDER BY sku_code
        """)
    List<Map<String, Object>> skus();

    @Select("""
        SELECT CAST(material_id AS CHAR) AS materialId, material_code AS materialCode,
               material_name AS materialName, unit, demand_qty AS demandQty,
               stock_qty AS stockQty, on_way_qty AS onWayQty,
               gap_qty AS gapQty, suggest_qty AS suggestQty,
               est_amount AS estAmount, shortage_flag AS shortageFlag
        FROM v_mrp_demand ORDER BY shortage_flag DESC, est_amount DESC, material_id
        """)
    List<Map<String, Object>> mrp();

    @Select("""
        SELECT CAST(i.inv_id AS CHAR) AS inventoryId, i.item_type AS itemType,
               CAST(i.item_id AS CHAR) AS itemId,
               COALESCE(m.material_code, sku.sku_code) AS itemCode,
               COALESCE(m.material_name, st.style_name) AS itemName,
               i.batch_no AS batchNo, i.qty, i.safe_stock AS safeStock,
               COALESCE(m.unit, st.unit) AS unit
        FROM st_inventory i
        LEFT JOIN md_material m ON i.item_type=1 AND m.material_id=i.item_id
        LEFT JOIN md_sku sku ON i.item_type=2 AND sku.sku_id=i.item_id
        LEFT JOIN md_style st ON st.style_id=sku.style_id
        ORDER BY i.item_type,i.item_id,i.batch_no
        """)
    List<Map<String, Object>> inventory();

    @Select("""
        SELECT CONCAT('mrp:',material_id) AS id,'shortage' AS alertType,
          '物料缺口' AS title,material_code AS subject,gap_qty AS triggerValue,
          unit,'md_material' AS sourceType,CAST(material_id AS CHAR) AS sourceId
        FROM v_mrp_demand WHERE shortage_flag=1
        UNION ALL
        SELECT CONCAT('order:',order_id) AS id,'overdue' AS alertType,
          '订单逾期未发齐' AS title,order_no AS subject,delay_days AS triggerValue,
          '天' AS unit,'sd_order' AS sourceType,CAST(order_id AS CHAR) AS sourceId
        FROM v_order_ontime WHERE ontime_flag=-1 AND delay_days>0
        ORDER BY triggerValue DESC LIMIT 50
        """)
    List<Map<String,Object>> alerts();

    @Select("""
        SELECT (SELECT COUNT(*) FROM sd_order WHERE del_flag='0' AND status<>9) AS orderCount,
               (SELECT COUNT(*) FROM v_order_ontime WHERE ontime_flag=-1 AND delay_days>0) AS overdueCount,
               (SELECT COUNT(*) FROM v_mrp_demand WHERE shortage_flag=1) AS shortageCount,
               (SELECT COALESCE(SUM(total_gross_profit),0) FROM v_style_cost) AS standardGrossProfit,
               (SELECT ROUND(100*SUM(ontime_flag=1)/NULLIF(SUM(ontime_flag<>-1),0),1)
                  FROM v_order_ontime) AS ontimeRate
        """)
    Map<String, Object> overview();
}
