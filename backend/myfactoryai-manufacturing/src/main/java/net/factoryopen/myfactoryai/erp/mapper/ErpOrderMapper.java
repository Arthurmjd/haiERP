package net.factoryopen.myfactoryai.erp.mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import net.factoryopen.myfactoryai.erp.domain.OrderHeader;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ErpOrderMapper {
    @Select("""
        SELECT CAST(o.order_id AS CHAR) AS orderId, o.order_no AS orderNo,
               CAST(o.customer_id AS CHAR) AS customerId, c.customer_name AS customerName,
               o.order_date AS orderDate, o.delivery_date AS deliveryDate,
               o.status, o.total_amount AS totalAmount,
               v.full_ship_date AS fullShipDate, v.delivery_state AS fulfillmentState,
               COALESCE(item_totals.line_count, 0) AS lineCount,
               COALESCE(item_totals.order_qty, 0) AS orderQty
        FROM sd_order o
        JOIN md_customer c ON c.customer_id = o.customer_id
        LEFT JOIN v_order_ontime v ON v.order_id = o.order_id
        LEFT JOIN (SELECT order_id, COUNT(*) AS line_count, SUM(qty) AS order_qty
                   FROM sd_order_item GROUP BY order_id) item_totals
          ON item_totals.order_id = o.order_id
        WHERE o.del_flag = '0'
          AND (#{keyword} IS NULL OR #{keyword} = '' OR o.order_no LIKE CONCAT('%', #{keyword}, '%')
               OR c.customer_name LIKE CONCAT('%', #{keyword}, '%'))
        ORDER BY o.order_id DESC
        """)
    List<Map<String, Object>> list(@Param("keyword") String keyword);

    @Select("""
        SELECT CAST(o.order_id AS CHAR) AS orderId, o.order_no AS orderNo,
               CAST(o.customer_id AS CHAR) AS customerId, c.customer_name AS customerName,
               o.order_date AS orderDate, o.delivery_date AS deliveryDate, o.status,
               o.total_amount AS totalAmount, v.full_ship_date AS fullShipDate,
               v.delivery_state AS fulfillmentState
        FROM sd_order o JOIN md_customer c ON c.customer_id = o.customer_id
        LEFT JOIN v_order_ontime v ON v.order_id = o.order_id
        WHERE o.order_id = #{orderId} AND o.del_flag = '0'
        """)
    Map<String, Object> detail(@Param("orderId") Long orderId);

    /**
     * 订单关联的生产工单。
     *
     * <p>订单确认后会自动生成工单，订单详情页要把它们列出来，否则业务员根本没法从订单跳到工单，
     * 只能去工单列表里按订单号一个个翻。</p>
     */
    @Select("""
        SELECT CAST(w.wo_id AS CHAR) AS woId, w.wo_no AS woNo, w.qty, w.status,
               CAST(w.style_id AS CHAR) AS styleId, s.style_name AS styleName,
               w.plan_end_date AS planEndDate, w.actual_end_date AS actualEndDate
        FROM pr_work_order w JOIN md_style s ON s.style_id = w.style_id
        WHERE w.order_id = #{orderId} AND w.del_flag = '0'
        ORDER BY w.wo_id
        """)
    List<Map<String, Object>> workOrders(@Param("orderId") Long orderId);

    @Select("""
        SELECT CAST(oi.item_id AS CHAR) AS itemId, CAST(oi.style_id AS CHAR) AS styleId,
               st.style_name AS styleName, CAST(sku.sku_id AS CHAR) AS skuId,
               sku.sku_code AS skuCode, CAST(oi.color_id AS CHAR) AS colorId,
               col.color_name AS colorName, CAST(oi.size_id AS CHAR) AS sizeId,
               sz.size_name AS sizeName, oi.qty, oi.unit_price AS unitPrice,
               oi.amount, COALESCE(sent.qty, 0) AS shippedQty,
               oi.qty - COALESCE(sent.qty, 0) AS remainingQty
        FROM sd_order_item oi
        JOIN md_style st ON st.style_id = oi.style_id
        JOIN md_color col ON col.color_id = oi.color_id
        JOIN md_size sz ON sz.size_id = oi.size_id
        JOIN md_sku sku ON sku.style_id = oi.style_id AND sku.color_id = oi.color_id AND sku.size_id = oi.size_id
        LEFT JOIN (
          SELECT sh.order_id, si.sku_id, SUM(si.qty) AS qty
          FROM sh_shipment sh JOIN sh_shipment_item si ON si.shipment_id = sh.shipment_id
          WHERE sh.del_flag = '0' AND sh.status IN (1,2) AND sh.ship_date <= CURDATE()
          GROUP BY sh.order_id, si.sku_id
        ) sent ON sent.order_id = oi.order_id AND sent.sku_id = sku.sku_id
        WHERE oi.order_id = #{orderId}
        ORDER BY oi.item_id
        """)
    List<Map<String, Object>> lines(@Param("orderId") Long orderId);

    @Select("SELECT COUNT(*) FROM md_customer WHERE customer_id=#{customerId} AND status=1")
    int activeCustomer(@Param("customerId") Long customerId);

    @Select("""
        SELECT sku.sku_id FROM md_sku sku
        JOIN md_style st ON st.style_id=sku.style_id AND st.status=1
        WHERE sku.style_id=#{styleId} AND sku.color_id=#{colorId}
          AND sku.size_id=#{sizeId} AND sku.status=1
        """)
    Long activeSku(@Param("styleId") Long styleId, @Param("colorId") Long colorId,
                   @Param("sizeId") Long sizeId);

    @Insert("""
        INSERT INTO sd_order
          (order_no,customer_id,order_date,delivery_date,status,total_amount,quote_id,create_by,create_time)
        VALUES (#{orderNo},#{customerId},CURDATE(),#{deliveryDate},0,
                #{totalAmount},#{quoteId},#{createdBy},NOW())
        """)
    @Options(useGeneratedKeys = true, keyProperty = "orderId")
    int insert(OrderHeader header);

    @Select("SELECT status,customer_id AS customerId FROM sd_quotation WHERE quote_id=#{id} AND del_flag='0'")
    Map<String, Object> quoteForOrder(@Param("id") Long id);

    /** 该报价已经生成的有效订单数（不含作废），用于防止一张报价重复下单。 */
    @Select("SELECT COUNT(*) FROM sd_order WHERE quote_id=#{id} AND status<>9")
    int countOrdersByQuote(@Param("id") Long id);

    /**
     * 报价转订单成功：把「已接受(1)」置为「已转单(3)」。
     * 条件更新兜住并发——两个请求同时转同一张报价时，只有一个能把 1 改成 3，另一个更新 0 行并回滚。
     */
    @Update("UPDATE sd_quotation SET status=3,update_by=#{username},update_time=NOW() WHERE quote_id=#{id} AND status=1 AND del_flag='0'")
    int markQuoteConverted(@Param("id") Long id,@Param("username") String username);

    @Update("UPDATE sd_order SET order_no=#{orderNo} WHERE order_id=#{orderId}")
    int setOrderNo(@Param("orderId") Long orderId, @Param("orderNo") String orderNo);

    @Insert("""
        INSERT INTO sd_order_item
          (order_id,style_id,color_id,size_id,qty,unit_price,amount,create_by,create_time)
        VALUES (#{orderId},#{styleId},#{colorId},#{sizeId},#{qty},#{unitPrice},
                #{amount},#{createdBy},NOW())
        """)
    int insertLine(@Param("orderId") Long orderId, @Param("styleId") Long styleId,
                   @Param("colorId") Long colorId, @Param("sizeId") Long sizeId,
                   @Param("qty") Integer qty, @Param("unitPrice") BigDecimal unitPrice,
                   @Param("amount") BigDecimal amount, @Param("createdBy") String createdBy);

    /** 明细行批量插入参数。 */
    record OrderLineInsert(Long styleId, Long colorId, Long sizeId,
                           Integer qty, BigDecimal unitPrice, BigDecimal amount) {
    }

    /** 批量插入订单明细，一次数据库往返完成全部行。 */
    @Insert("""
        <script>
        INSERT INTO sd_order_item
          (order_id,style_id,color_id,size_id,qty,unit_price,amount,create_by,create_time)
        VALUES
        <foreach collection="lines" item="l" separator=",">
          (#{orderId},#{l.styleId},#{l.colorId},#{l.sizeId},#{l.qty},#{l.unitPrice},
           #{l.amount},#{createdBy},NOW())
        </foreach>
        </script>
        """)
    int insertLines(@Param("orderId") Long orderId, @Param("lines") List<OrderLineInsert> lines,
                    @Param("createdBy") String createdBy);

    @Update("""
        UPDATE sd_order SET status=1,update_by=#{updatedBy},update_time=NOW()
        WHERE order_id=#{orderId} AND status=0 AND del_flag='0'
          AND EXISTS (SELECT 1 FROM sd_order_item WHERE order_id=#{orderId})
        """)
    int confirm(@Param("orderId") Long orderId, @Param("updatedBy") String updatedBy);

    @Select("""
        SELECT status, order_no AS orderNo
        FROM sd_order WHERE order_id = #{orderId} AND del_flag = '0' FOR UPDATE
        """)
    Map<String, Object> lockDraft(@Param("orderId") Long orderId);

    @Update("""
        UPDATE sd_order SET customer_id = #{customerId}, delivery_date = #{deliveryDate},
          total_amount = #{totalAmount}, update_by = #{updatedBy}, update_time = NOW()
        WHERE order_id = #{orderId} AND status = 0 AND del_flag = '0'
        """)
    int update(@Param("orderId") Long orderId, @Param("customerId") Long customerId,
               @Param("deliveryDate") LocalDate deliveryDate,
               @Param("totalAmount") BigDecimal totalAmount,
               @Param("updatedBy") String updatedBy);

    @Delete("DELETE FROM sd_order_item WHERE order_id = #{orderId}")
    int deleteLines(@Param("orderId") Long orderId);
}
