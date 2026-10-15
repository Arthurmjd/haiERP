package net.factoryopen.myfactoryai.erp.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ErpCostMapper {
    @Select("SELECT status FROM sd_order WHERE order_id=#{id} AND del_flag='0' FOR UPDATE")
    Integer lockOrder(@Param("id") Long id);

    @Select("SELECT SUM(qty) AS qty,SUM(amount) AS revenue FROM sd_order_item WHERE order_id=#{orderId} AND style_id=#{styleId}")
    Map<String,Object> orderStyle(@Param("orderId") Long orderId,@Param("styleId") Long styleId);

    @Select("SELECT wo_id AS woId,status FROM pr_work_order WHERE order_id=#{orderId} AND style_id=#{styleId} AND del_flag='0' LIMIT 1")
    Map<String,Object> workOrder(@Param("orderId") Long orderId,@Param("styleId") Long styleId);

    @Select("""
        SELECT m.material_id AS sourceId,m.plan_qty AS qty,p.unit_price AS unitPrice,
          IF(p.price_src=1,'purchase_snapshot','reference_snapshot') AS priceSource
        FROM pr_work_order_material m JOIN v_material_last_price p ON p.material_id=m.material_id
        WHERE m.wo_id=#{woId} ORDER BY m.material_id
        """)
    List<Map<String,Object>> frozenMaterials(@Param("woId") Long woId);

    @Select("""
        SELECT b.material_id AS sourceId,ROUND(#{qty}*b.qty*(1+b.loss_rate/100),3) AS qty,
          p.unit_price AS unitPrice,IF(p.price_src=1,'purchase_snapshot','reference_snapshot') AS priceSource
        FROM md_bom b JOIN v_material_last_price p ON p.material_id=b.material_id
        WHERE b.style_id=#{styleId} ORDER BY b.material_id
        """)
    List<Map<String,Object>> estimatedMaterials(@Param("styleId") Long styleId,@Param("qty") Integer qty);

    @Select("""
        SELECT id AS sourceId,IF(#{finished}=1,done_qty,plan_qty) AS qty,
          IF(is_outsourced=1 AND outsource_price>0,outsource_price,unit_price) AS unitPrice,
          'work_order_snapshot' AS priceSource
        FROM pr_work_order_process WHERE wo_id=#{woId} ORDER BY seq,id
        """)
    List<Map<String,Object>> frozenProcesses(@Param("woId") Long woId,@Param("finished") int finished);

    @Select("SELECT id AS sourceId,#{qty} AS qty,unit_price AS unitPrice,'route_estimate' AS priceSource FROM md_style_process WHERE style_id=#{styleId} ORDER BY seq,id")
    List<Map<String,Object>> estimatedProcesses(@Param("styleId") Long styleId,@Param("qty") Integer qty);

    @Select("SELECT settle_id AS settleId,status,overhead_rate AS overheadRate FROM fn_cost_settlement WHERE order_id=#{orderId} AND style_id=#{styleId} AND del_flag='0' FOR UPDATE")
    Map<String,Object> lockSettlement(@Param("orderId") Long orderId,@Param("styleId") Long styleId);

    @Insert("INSERT INTO fn_cost_settlement(settle_no,order_id,style_id,qty,status,create_by,create_time) VALUES(#{settleNo},#{orderId},#{styleId},#{qty},0,#{username},NOW())")
    @Options(useGeneratedKeys=true,keyProperty="settleId")
    int insert(Map<String,Object> row);

    @Update("UPDATE fn_cost_settlement SET settle_no=#{no} WHERE settle_id=#{id}")
    int setNo(@Param("id") Long id,@Param("no") String no);

    @Delete("DELETE FROM fn_cost_detail WHERE settle_id=#{id}")
    int deleteDetails(@Param("id") Long id);

    @Insert("INSERT INTO fn_cost_detail(settle_id,line_type,source_id,qty,unit_price,amount,price_source,create_by,create_time) VALUES(#{settleId},#{type},#{sourceId},#{qty},#{price},#{amount},#{source},#{username},NOW())")
    int insertDetail(@Param("settleId") Long settleId,@Param("type") int type,@Param("sourceId") Long sourceId,
                     @Param("qty") BigDecimal qty,@Param("price") BigDecimal price,@Param("amount") BigDecimal amount,
                     @Param("source") String source,@Param("username") String username);

    @Update("""
        UPDATE fn_cost_settlement SET qty=#{qty},material_cost=#{materialCost},labor_cost=#{laborCost},
          overhead_rate=#{overheadRate},overhead_cost=#{overheadCost},total_cost=#{totalCost},
          unit_cost=#{unitCost},revenue=#{revenue},gross_profit=#{grossProfit},gross_margin=#{grossMargin},
          calc_time=NOW(),update_by=#{username},update_time=NOW()
        WHERE settle_id=#{settleId} AND status=0 AND del_flag='0'
        """)
    int updateTotals(Map<String,Object> row);

    @Select("""
        SELECT CAST(s.settle_id AS CHAR) AS settleId,s.settle_no AS settleNo,
          CAST(s.order_id AS CHAR) AS orderId,CAST(s.style_id AS CHAR) AS styleId,
          st.style_name AS styleName,s.qty,s.material_cost AS materialCost,s.labor_cost AS laborCost,
          s.overhead_rate AS overheadRate,s.overhead_cost AS overheadCost,s.total_cost AS totalCost,
          s.unit_cost AS unitCost,s.revenue,s.gross_profit AS grossProfit,s.gross_margin AS grossMargin,
          s.status,s.calc_time AS calcTime
        FROM fn_cost_settlement s JOIN md_style st ON st.style_id=s.style_id
        WHERE s.settle_id=#{id} AND s.del_flag='0'
        """)
    Map<String,Object> detail(@Param("id") Long id);

    @Select("SELECT line_type AS lineType,CAST(source_id AS CHAR) AS sourceId,qty,unit_price AS unitPrice,amount,price_source AS priceSource FROM fn_cost_detail WHERE settle_id=#{id} ORDER BY line_type,source_id")
    List<Map<String,Object>> details(@Param("id") Long id);

    @Select("SELECT order_id AS orderId,style_id AS styleId,status,total_cost AS totalCost FROM fn_cost_settlement WHERE settle_id=#{id} AND del_flag='0' FOR UPDATE")
    Map<String,Object> lockById(@Param("id") Long id);

    @Select("SELECT COALESCE(SUM(amount),0) FROM fn_cost_detail WHERE settle_id=#{id}")
    BigDecimal detailTotal(@Param("id") Long id);

    @Select("""
        SELECT COUNT(*) FROM sd_order_item oi
        JOIN md_sku sku ON sku.style_id=oi.style_id AND sku.color_id=oi.color_id AND sku.size_id=oi.size_id
        LEFT JOIN (SELECT sh.order_id,si.sku_id,SUM(si.qty) AS qty FROM sh_shipment sh
          JOIN sh_shipment_item si ON si.shipment_id=sh.shipment_id
          WHERE sh.status IN (1,2) AND sh.del_flag='0' GROUP BY sh.order_id,si.sku_id) sent
          ON sent.order_id=oi.order_id AND sent.sku_id=sku.sku_id
        WHERE oi.order_id=#{orderId} AND oi.style_id=#{styleId} AND COALESCE(sent.qty,0)<oi.qty
        """)
    int unshippedStyle(@Param("orderId") Long orderId,@Param("styleId") Long styleId);

    @Update("UPDATE fn_cost_settlement SET status=1,update_by=#{username},update_time=NOW() WHERE settle_id=#{id} AND status=0")
    int confirm(@Param("id") Long id,@Param("username") String username);
}
