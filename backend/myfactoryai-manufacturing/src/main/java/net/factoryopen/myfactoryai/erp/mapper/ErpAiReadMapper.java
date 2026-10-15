package net.factoryopen.myfactoryai.erp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ErpAiReadMapper {
    @Select("SELECT order_no AS orderNo,delivery_date AS deliveryDate,status,total_amount AS totalAmount FROM sd_order WHERE del_flag='0' ORDER BY order_id DESC LIMIT 20")
    List<Map<String,Object>> orders();

    @Select("SELECT COALESCE(m.material_code,s.sku_code) AS itemCode,i.batch_no AS batchNo,i.qty,i.safe_stock AS safeStock FROM st_inventory i LEFT JOIN md_material m ON i.item_type=1 AND m.material_id=i.item_id LEFT JOIN md_sku s ON i.item_type=2 AND s.sku_id=i.item_id ORDER BY i.item_type,i.item_id,i.batch_no LIMIT 20")
    List<Map<String,Object>> inventory();

    @Select("SELECT material_code AS materialCode,unit,demand_qty AS demandQty,stock_qty AS stockQty,on_way_qty AS onWayQty,gap_qty AS gapQty FROM v_mrp_demand WHERE shortage_flag=1 ORDER BY gap_qty DESC LIMIT 20")
    List<Map<String,Object>> shortage();

    @Select("SELECT style_no AS styleNo,total_qty AS totalQty,avg_unit_cost AS avgUnitCost,total_revenue AS totalRevenue,total_gross_profit AS totalGrossProfit,gross_margin AS grossMargin FROM v_style_cost WHERE settle_cnt>0 ORDER BY total_revenue DESC LIMIT 20")
    List<Map<String,Object>> cost();

    @Select("SELECT order_no AS orderNo,delivery_date AS deliveryDate,full_ship_date AS fullShipDate,delivery_state AS deliveryState,delay_days AS delayDays FROM v_order_ontime ORDER BY order_id DESC LIMIT 20")
    List<Map<String,Object>> ontime();
}
