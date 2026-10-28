package net.factoryopen.myfactoryai.erp.mapper;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ErpModuleReadMapper {
    @Select("SELECT CAST(category_id AS CHAR) AS id,category_name AS name,sort,status FROM md_category ORDER BY sort,category_id")
    List<Map<String,Object>> categories();

    @Select("SELECT CAST(color_id AS CHAR) AS id,color_code AS code,color_name AS name,sort,status FROM md_color ORDER BY sort,color_id")
    List<Map<String,Object>> colors();

    @Select("SELECT CAST(size_id AS CHAR) AS id,size_code AS code,size_name AS name,size_group AS sizeGroup,sort,status FROM md_size ORDER BY sort,size_id")
    List<Map<String,Object>> sizes();

    @Select("SELECT CAST(style_id AS CHAR) AS id,style_no AS code,style_name AS name,season,unit,base_cost AS baseCost,status FROM md_style ORDER BY style_id DESC")
    List<Map<String,Object>> styles();

    @Select("SELECT CAST(s.sku_id AS CHAR) AS id,s.sku_code AS code,st.style_name AS styleName,c.color_name AS colorName,z.size_name AS sizeName,s.status FROM md_sku s JOIN md_style st ON st.style_id=s.style_id JOIN md_color c ON c.color_id=s.color_id JOIN md_size z ON z.size_id=s.size_id ORDER BY s.sku_id DESC")
    List<Map<String,Object>> skus();

    @Select("SELECT CAST(material_id AS CHAR) AS id,material_code AS code,material_name AS name,category,spec,unit,ref_price AS refPrice,status FROM md_material ORDER BY material_id DESC")
    List<Map<String,Object>> materials();

    @Select("SELECT CAST(process_id AS CHAR) AS id,process_code AS code,process_name AS name,sort,status FROM md_process ORDER BY sort,process_id")
    List<Map<String,Object>> processes();

    @Select("SELECT CAST(customer_id AS CHAR) AS id,customer_code AS code,customer_name AS name,contact,phone,credit_days AS creditDays,status FROM md_customer ORDER BY customer_id DESC")
    List<Map<String,Object>> customers();

    @Select("SELECT CAST(supplier_id AS CHAR) AS id,supplier_code AS code,supplier_name AS name,contact,phone,status FROM md_supplier ORDER BY supplier_id DESC")
    List<Map<String,Object>> suppliers();

    @Select("SELECT CAST(emp_id AS CHAR) AS id,emp_no AS code,name,position,emp_type AS empType,hire_date AS hireDate,base_salary AS baseSalary,si_base AS siBase,status FROM hr_employee ORDER BY emp_id DESC")
    List<Map<String,Object>> employees();

    @Select("""
        <script>
        SELECT CAST(w.id AS CHAR) AS id,CAST(w.emp_id AS CHAR) AS empId,e.name AS employeeName,w.wage_period AS wagePeriod,CAST(w.work_order_id AS CHAR) AS workOrderId,w.qty,w.unit_price AS unitPrice,w.amount,w.status
        FROM hr_piece_wage w JOIN hr_employee e ON e.emp_id=w.emp_id
        <where><if test="month != null and month != ''">w.wage_period = #{month}</if></where>
        ORDER BY w.id DESC
        </script>
        """)
    List<Map<String,Object>> pieceWages(@Param("month") String month);

    @Select("""
        <script>
        SELECT CAST(s.id AS CHAR) AS id,CAST(s.emp_id AS CHAR) AS empId,e.name AS employeeName,s.wage_period AS wagePeriod,s.base_salary AS baseSalary,s.piece_amount AS pieceAmount,s.si_personal AS siPersonal,s.fund_personal AS fundPersonal,s.tax,s.gross_pay AS grossPay,s.net_pay AS netPay,s.company_cost AS companyCost,s.status
        FROM hr_salary_slip s JOIN hr_employee e ON e.emp_id=s.emp_id
        <where><if test="month != null and month != ''">s.wage_period = #{month}</if></where>
        ORDER BY s.wage_period DESC,s.id DESC
        </script>
        """)
    List<Map<String,Object>> salarySlips(@Param("month") String month);

    @Select("SELECT CAST(q.quote_id AS CHAR) AS id,q.quote_no AS code,c.customer_name AS customerName,q.quote_date AS quoteDate,q.total_amount AS totalAmount,q.status FROM sd_quotation q JOIN md_customer c ON c.customer_id=q.customer_id WHERE q.del_flag='0' ORDER BY q.quote_id DESC")
    List<Map<String,Object>> quotations();

    @Select("""
        <script>
        SELECT CAST(w.wo_id AS CHAR) AS id,w.wo_no AS code,CAST(w.order_id AS CHAR) AS orderId,
               o.order_no AS orderNo,s.style_name AS styleName,w.qty,
               w.plan_start_date AS planStartDate,w.plan_end_date AS planEndDate,
               w.actual_end_date AS actualEndDate,w.status
        FROM pr_work_order w JOIN md_style s ON s.style_id=w.style_id
        LEFT JOIN sd_order o ON o.order_id=w.order_id
        WHERE w.del_flag='0'
        <if test="month != null and month != ''">AND DATE_FORMAT(w.plan_end_date,'%Y-%m') = #{month}</if>
        ORDER BY w.wo_id DESC
        </script>
        """)
    List<Map<String,Object>> workOrders(@Param("month") String month);

    @Select("""
        <script>
        SELECT CAST(s.shipment_id AS CHAR) AS id,s.shipment_no AS code,CAST(s.order_id AS CHAR) AS orderId,
               o.order_no AS orderNo,c.customer_name AS customerName,s.ship_date AS shipDate,
               s.logistics_company AS logisticsCompany,s.logistics_no AS logisticsNo,s.status
        FROM sh_shipment s JOIN md_customer c ON c.customer_id=s.customer_id
        LEFT JOIN sd_order o ON o.order_id=s.order_id
        WHERE s.del_flag='0'
        <if test="month != null and month != ''">AND DATE_FORMAT(s.ship_date,'%Y-%m') = #{month}</if>
        ORDER BY s.shipment_id DESC
        </script>
        """)
    List<Map<String,Object>> shipments(@Param("month") String month);

    @Select("SELECT CAST(r.return_id AS CHAR) AS id,r.return_no AS code,CAST(r.shipment_id AS CHAR) AS shipmentId,c.customer_name AS customerName,r.return_date AS returnDate,r.reason,r.refund_amount AS refundAmount,r.is_restock AS isRestock,r.status FROM sh_return r JOIN md_customer c ON c.customer_id=r.customer_id WHERE r.del_flag='0' ORDER BY r.return_id DESC")
    List<Map<String,Object>> returns();

    @Select("SELECT CAST(s.settle_id AS CHAR) AS id,s.settle_no AS code,CAST(s.order_id AS CHAR) AS orderId,st.style_name AS styleName,s.qty,s.material_cost AS materialCost,s.labor_cost AS laborCost,s.total_cost AS totalCost,s.revenue,s.gross_profit AS grossProfit,s.gross_margin AS grossMargin,s.status FROM fn_cost_settlement s JOIN md_style st ON st.style_id=s.style_id WHERE s.del_flag='0' ORDER BY s.settle_id DESC")
    List<Map<String,Object>> settlements();

    @Select("SELECT CAST(order_id AS CHAR) AS orderId,order_no AS orderNo,customer_name AS customerName,delivery_date AS deliveryDate,full_ship_date AS fullShipDate,delivery_state AS deliveryState,delay_days AS delayDays FROM v_order_ontime ORDER BY order_id DESC")
    List<Map<String,Object>> ontime();

    @Select("""
        SELECT CAST(o.order_id AS CHAR) AS orderId,o.order_no AS orderNo,c.customer_name AS customerName,
               o.delivery_date AS deliveryDate,complete.full_ship_date AS fullShipDate,
               DATEDIFF(COALESCE(complete.full_ship_date,#{asOfDate}),o.delivery_date) AS delayDays,
               CASE WHEN complete.full_ship_date IS NULL AND o.delivery_date < #{asOfDate} THEN '逾期未发齐'
                    WHEN complete.full_ship_date IS NULL THEN '未发齐'
                    WHEN complete.full_ship_date <= o.delivery_date THEN '准时发齐' ELSE '延期发齐' END AS deliveryState
        FROM sd_order o JOIN md_customer c ON c.customer_id=o.customer_id
        LEFT JOIN (
          SELECT order_lines.order_id,
                 IF(COUNT(*)=COUNT(order_lines.sku_full_date),MAX(order_lines.sku_full_date),NULL) AS full_ship_date
          FROM (
            SELECT oi.order_id,oi.item_id,
                   MIN(IF(sent.running_qty>=oi.qty,sent.ship_date,NULL)) AS sku_full_date
            FROM sd_order_item oi
            JOIN md_sku sku ON sku.style_id=oi.style_id AND sku.color_id=oi.color_id AND sku.size_id=oi.size_id
            LEFT JOIN (
              SELECT sh.order_id,si.sku_id,sh.ship_date,
                     SUM(si.qty) OVER (PARTITION BY sh.order_id,si.sku_id
                                       ORDER BY sh.ship_date,sh.shipment_id,si.item_id) AS running_qty
              FROM sh_shipment sh JOIN sh_shipment_item si ON si.shipment_id=sh.shipment_id
              WHERE sh.del_flag='0' AND sh.status IN (1,2) AND sh.ship_date<=#{asOfDate}
            ) sent ON sent.order_id=oi.order_id AND sent.sku_id=sku.sku_id
            GROUP BY oi.order_id,oi.item_id
          ) order_lines GROUP BY order_lines.order_id
        ) complete ON complete.order_id=o.order_id
        WHERE o.del_flag='0' AND o.status<>9 AND o.order_date<=#{asOfDate}
        ORDER BY o.order_id DESC
        """)
    List<Map<String,Object>> ontimeAt(@Param("asOfDate") LocalDate asOfDate);

    @Select("SELECT CAST(style_id AS CHAR) AS styleId,style_no AS styleNo,style_name AS styleName,total_qty AS totalQty,avg_unit_cost AS avgUnitCost,total_revenue AS totalRevenue,total_gross_profit AS totalGrossProfit,gross_margin AS grossMargin FROM v_style_cost ORDER BY total_revenue DESC")
    List<Map<String,Object>> styleCost();
}
