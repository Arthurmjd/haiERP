package net.factoryopen.myfactoryai.erp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ErpShipmentMapper {
    /**
     * 待出货订单（按「订单 × SKU」平铺）：只返回出货需要的信息，不含单价与金额。
     *
     * <p>仓管员没有 sd:order:query 权限，进不了销售订单详情页，所以出货这一步必须有自己的入口。
     * 这里刻意不查金额：仓管要知道的是「发什么、发多少、成品仓有没有」，不是这单卖了多少钱。</p>
     *
     * <p>过滤条件：订单在生产中(2)或已完工(3)，且该 SKU 还有未发数量。</p>
     */
    @Select("""
        SELECT CAST(o.order_id AS CHAR) AS orderId, o.order_no AS orderNo,
               c.customer_name AS customerName, o.status, o.delivery_date AS deliveryDate,
               CAST(sk.sku_id AS CHAR) AS skuId, sk.sku_code AS skuCode,
               oi.qty - COALESCE((
                   SELECT SUM(si.qty) FROM sh_shipment sh
                   JOIN sh_shipment_item si ON si.shipment_id = sh.shipment_id
                   WHERE sh.order_id = o.order_id AND si.sku_id = sk.sku_id
                     AND sh.status IN (1,2) AND sh.del_flag = '0'), 0) AS remaining,
               COALESCE((SELECT SUM(i.qty) FROM st_inventory i
                         WHERE i.item_type = 2 AND i.item_id = sk.sku_id), 0) AS available
        FROM sd_order o
        JOIN md_customer c ON c.customer_id = o.customer_id
        JOIN sd_order_item oi ON oi.order_id = o.order_id
        JOIN md_sku sk ON sk.style_id = oi.style_id AND sk.color_id = oi.color_id AND sk.size_id = oi.size_id
        WHERE o.del_flag = '0' AND o.status IN (2,3)
        HAVING remaining > 0
        ORDER BY o.order_id DESC, sk.sku_id
        """)
    List<Map<String,Object>> pendingOrders();

    @Select("SELECT order_id AS orderId,customer_id AS customerId,status FROM sd_order WHERE order_id=#{id} AND del_flag='0' FOR UPDATE")
    Map<String,Object> lockOrder(@Param("id") Long id);

    @Select("SELECT sku.sku_id AS skuId,SUM(oi.qty) AS qty FROM sd_order_item oi JOIN md_sku sku ON sku.style_id=oi.style_id AND sku.color_id=oi.color_id AND sku.size_id=oi.size_id WHERE oi.order_id=#{id} GROUP BY sku.sku_id")
    List<Map<String,Object>> ordered(@Param("id") Long id);

    @Select("SELECT COALESCE(SUM(i.qty),0) FROM sh_shipment s JOIN sh_shipment_item i ON i.shipment_id=s.shipment_id WHERE s.order_id=#{orderId} AND i.sku_id=#{skuId} AND s.status IN (1,2) AND s.del_flag='0'")
    int shipped(@Param("orderId") Long orderId,@Param("skuId") Long skuId);

    @Insert("INSERT INTO sh_shipment(shipment_no,order_id,customer_id,logistics_company,logistics_no,status,create_by,create_time) VALUES(#{shipmentNo},#{orderId},#{customerId},#{logisticsCompany},#{logisticsNo},0,#{username},NOW())")
    @Options(useGeneratedKeys=true,keyProperty="shipmentId")
    int insertShipment(Map<String,Object> row);

    @Update("UPDATE sh_shipment SET shipment_no=#{no} WHERE shipment_id=#{id}")
    int setShipmentNo(@Param("id") Long id,@Param("no") String no);

    @Insert("INSERT INTO sh_shipment_item(shipment_id,sku_id,batch_no,qty,create_by,create_time) VALUES(#{shipmentId},#{skuId},#{batch},#{qty},#{username},NOW())")
    int insertShipmentLine(@Param("shipmentId") Long shipmentId,@Param("skuId") Long skuId,@Param("batch") String batch,@Param("qty") Integer qty,@Param("username") String username);

    /** 出货明细批量插入参数。 */
    record ShipmentLineInsert(Long skuId, String batch, Integer qty) {
    }

    /** 批量插入出货明细，一次数据库往返完成全部行。 */
    @Insert("""
        <script>
        INSERT INTO sh_shipment_item(shipment_id,sku_id,batch_no,qty,create_by,create_time)
        VALUES
        <foreach collection="lines" item="l" separator=",">
          (#{shipmentId},#{l.skuId},#{l.batch},#{l.qty},#{username},NOW())
        </foreach>
        </script>
        """)
    int insertShipmentLines(@Param("shipmentId") Long shipmentId, @Param("lines") List<ShipmentLineInsert> lines,
                            @Param("username") String username);

    @Select("SELECT shipment_id AS shipmentId,order_id AS orderId,customer_id AS customerId,status FROM sh_shipment WHERE shipment_id=#{id} AND del_flag='0' FOR UPDATE")
    Map<String,Object> lockShipment(@Param("id") Long id);

    @Select("SELECT item_id AS itemId,sku_id AS skuId,batch_no AS batchNo,qty FROM sh_shipment_item WHERE shipment_id=#{id} ORDER BY item_id")
    List<Map<String,Object>> shipmentLines(@Param("id") Long id);

    @Update("UPDATE sh_shipment SET status=1,ship_date=CURDATE(),update_by=#{username},update_time=NOW() WHERE shipment_id=#{id} AND status=0")
    int ship(@Param("id") Long id,@Param("username") String username);

    @Update("UPDATE sd_order SET status=4,full_ship_date=CURDATE(),update_by=#{username},update_time=NOW() WHERE order_id=#{id} AND status IN (2,3)")
    int markOrderShipped(@Param("id") Long id,@Param("username") String username);

    @Update("UPDATE sh_shipment SET status=2,update_by=#{username},update_time=NOW() WHERE shipment_id=#{id} AND status=1")
    int sign(@Param("id") Long id,@Param("username") String username);

    @Select("SELECT COUNT(*) FROM sh_shipment WHERE order_id=#{id} AND status=1 AND del_flag='0'")
    int unsignedShipments(@Param("id") Long id);

    @Update("UPDATE sd_order SET status=5,update_by=#{username},update_time=NOW() WHERE order_id=#{id} AND status=4 AND full_ship_date IS NOT NULL")
    int completeOrder(@Param("id") Long id,@Param("username") String username);

    @Select("""
        SELECT CAST(s.shipment_id AS CHAR) AS shipmentId,s.shipment_no AS shipmentNo,
          CAST(s.order_id AS CHAR) AS orderId,c.customer_name AS customerName,
          s.ship_date AS shipDate,s.logistics_company AS logisticsCompany,
          s.logistics_no AS logisticsNo,s.status
        FROM sh_shipment s JOIN md_customer c ON c.customer_id=s.customer_id
        WHERE s.shipment_id=#{id} AND s.del_flag='0'
        """)
    Map<String,Object> shipmentDetail(@Param("id") Long id);

    @Select("SELECT CAST(i.item_id AS CHAR) AS itemId,CAST(i.sku_id AS CHAR) AS skuId,sku.sku_code AS skuCode,i.batch_no AS batchNo,i.qty FROM sh_shipment_item i JOIN md_sku sku ON sku.sku_id=i.sku_id WHERE i.shipment_id=#{id} ORDER BY i.item_id")
    List<Map<String,Object>> shipmentDetailLines(@Param("id") Long id);

    @Insert("INSERT INTO sh_return(return_no,order_id,shipment_id,customer_id,reason,return_type,is_restock,refund_amount,status,create_by,create_time) VALUES(#{returnNo},#{orderId},#{shipmentId},#{customerId},#{reason},#{returnType},#{isRestock},#{refundAmount},0,#{username},NOW())")
    @Options(useGeneratedKeys=true,keyProperty="returnId")
    int insertReturn(Map<String,Object> row);

    @Update("UPDATE sh_return SET return_no=#{no} WHERE return_id=#{id}")
    int setReturnNo(@Param("id") Long id,@Param("no") String no);

    @Insert("INSERT INTO sh_return_item(return_id,sku_id,batch_no,qty,create_by,create_time) VALUES(#{returnId},#{skuId},#{batch},#{qty},#{username},NOW())")
    int insertReturnLine(@Param("returnId") Long returnId,@Param("skuId") Long skuId,@Param("batch") String batch,@Param("qty") Integer qty,@Param("username") String username);

    /** 退货明细批量插入参数。 */
    record ReturnLineInsert(Long skuId, String batch, Integer qty) {
    }

    /** 批量插入退货明细，一次数据库往返完成全部行。 */
    @Insert("""
        <script>
        INSERT INTO sh_return_item(return_id,sku_id,batch_no,qty,create_by,create_time)
        VALUES
        <foreach collection="lines" item="l" separator=",">
          (#{returnId},#{l.skuId},#{l.batch},#{l.qty},#{username},NOW())
        </foreach>
        </script>
        """)
    int insertReturnLines(@Param("returnId") Long returnId, @Param("lines") List<ReturnLineInsert> lines,
                          @Param("username") String username);

    @Select("SELECT return_id AS returnId,shipment_id AS shipmentId,order_id AS orderId,is_restock AS isRestock,status FROM sh_return WHERE return_id=#{id} AND del_flag='0' FOR UPDATE")
    Map<String,Object> lockReturn(@Param("id") Long id);

    @Select("SELECT item_id AS itemId,sku_id AS skuId,batch_no AS batchNo,qty FROM sh_return_item WHERE return_id=#{id} ORDER BY item_id")
    List<Map<String,Object>> returnLines(@Param("id") Long id);

    @Select("SELECT COALESCE(SUM(i.qty),0) FROM sh_return r JOIN sh_return_item i ON i.return_id=r.return_id WHERE r.shipment_id=#{shipmentId} AND i.sku_id=#{skuId} AND i.batch_no=#{batch} AND r.status=1 AND r.del_flag='0'")
    int returned(@Param("shipmentId") Long shipmentId,@Param("skuId") Long skuId,@Param("batch") String batch);

    @Update("UPDATE sh_return SET status=1,return_date=CURDATE(),update_by=#{username},update_time=NOW() WHERE return_id=#{id} AND status=0")
    int handleReturn(@Param("id") Long id,@Param("username") String username);

    @Select("SELECT CAST(r.return_id AS CHAR) AS returnId,r.return_no AS returnNo,CAST(r.shipment_id AS CHAR) AS shipmentId,CAST(r.order_id AS CHAR) AS orderId,r.reason,r.return_type AS returnType,r.is_restock AS isRestock,r.refund_amount AS refundAmount,r.return_date AS returnDate,r.status FROM sh_return r WHERE r.return_id=#{id} AND r.del_flag='0'")
    Map<String,Object> returnDetail(@Param("id") Long id);

    @Select("SELECT CAST(i.item_id AS CHAR) AS itemId,CAST(i.sku_id AS CHAR) AS skuId,sku.sku_code AS skuCode,i.batch_no AS batchNo,i.qty FROM sh_return_item i JOIN md_sku sku ON sku.sku_id=i.sku_id WHERE i.return_id=#{id} ORDER BY i.item_id")
    List<Map<String,Object>> returnDetailLines(@Param("id") Long id);
}
