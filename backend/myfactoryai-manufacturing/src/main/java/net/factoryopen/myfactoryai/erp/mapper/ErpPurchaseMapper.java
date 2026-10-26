package net.factoryopen.myfactoryai.erp.mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
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
public interface ErpPurchaseMapper {
    @Select("SELECT v.suggest_qty AS suggestQty,p.unit_price AS unitPrice FROM v_mrp_demand v JOIN v_material_last_price p ON p.material_id=v.material_id WHERE v.material_id=#{id} AND v.shortage_flag=1")
    Map<String,Object> suggestedMaterial(@Param("id") Long id);

    @Select("SELECT po_id FROM pu_purchase_order WHERE suggestion_request_key=#{key}")
    Long bySuggestionKey(@Param("key") String key);

    @Update("UPDATE pu_purchase_order SET suggestion_request_key=#{key} WHERE po_id=#{id} AND suggestion_request_key IS NULL")
    int markSuggestion(@Param("id") Long id,@Param("key") String key);
    @Select("""
        SELECT CAST(p.po_id AS CHAR) AS poId, p.po_no AS poNo,
               CAST(p.supplier_id AS CHAR) AS supplierId, s.supplier_name AS supplierName,
               p.order_date AS orderDate, p.expect_date AS expectDate,
               p.actual_arrival_date AS actualArrivalDate, p.status,
               p.total_amount AS totalAmount, COUNT(i.item_id) AS lineCount
        FROM pu_purchase_order p JOIN md_supplier s ON s.supplier_id=p.supplier_id
        LEFT JOIN pu_purchase_order_item i ON i.po_id=p.po_id
        WHERE p.del_flag='0' AND (#{keyword} IS NULL OR #{keyword}=''
          OR p.po_no LIKE CONCAT('%',#{keyword},'%') OR s.supplier_name LIKE CONCAT('%',#{keyword},'%'))
        GROUP BY p.po_id,p.po_no,p.supplier_id,s.supplier_name,p.order_date,p.expect_date,
                 p.actual_arrival_date,p.status,p.total_amount
        ORDER BY p.po_id DESC
        """)
    List<Map<String, Object>> list(@Param("keyword") String keyword);

    @Select("""
        SELECT CAST(p.po_id AS CHAR) AS poId, p.po_no AS poNo,
               CAST(p.supplier_id AS CHAR) AS supplierId, s.supplier_name AS supplierName,
               p.order_date AS orderDate,p.expect_date AS expectDate,
               p.actual_arrival_date AS actualArrivalDate,p.status,p.total_amount AS totalAmount
        FROM pu_purchase_order p JOIN md_supplier s ON s.supplier_id=p.supplier_id
        WHERE p.po_id=#{poId} AND p.del_flag='0'
        """)
    Map<String, Object> detail(@Param("poId") Long poId);

    @Select("""
        SELECT CAST(i.item_id AS CHAR) AS itemId,CAST(i.material_id AS CHAR) AS materialId,
               m.material_code AS materialCode,m.material_name AS materialName,m.unit,
               i.qty,i.unit_price AS unitPrice,i.amount,
               COALESCE(r.received_qty,0) AS receivedQty,
               i.qty-COALESCE(r.received_qty,0) AS remainingQty
        FROM pu_purchase_order_item i JOIN md_material m ON m.material_id=i.material_id
        LEFT JOIN (SELECT source_line_id,SUM(qty) AS received_qty FROM st_stock_txn
                   WHERE txn_type=1 AND biz_type=1 AND source_type='pu_order'
                   GROUP BY source_line_id) r ON r.source_line_id=i.item_id
        WHERE i.po_id=#{poId} ORDER BY i.item_id
        """)
    List<Map<String, Object>> lines(@Param("poId") Long poId);

    @Select("SELECT COUNT(*) FROM md_supplier WHERE supplier_id=#{id} AND status=1")
    int activeSupplier(@Param("id") Long id);

    @Select("SELECT COUNT(*) FROM md_material WHERE material_id=#{id} AND status=1")
    int activeMaterial(@Param("id") Long id);

    @Insert("""
        INSERT INTO pu_purchase_order(po_no,supplier_id,order_date,expect_date,status,
          total_amount,create_by,create_time)
        VALUES(#{poNo},#{supplierId},CURDATE(),#{expectDate},0,#{totalAmount},#{createdBy},NOW())
        """)
    @Options(useGeneratedKeys = true, keyProperty = "poId")
    int insert(PurchaseHeader header);

    @Update("UPDATE pu_purchase_order SET po_no=#{poNo} WHERE po_id=#{poId}")
    int setPoNo(@Param("poId") Long poId, @Param("poNo") String poNo);

    @Insert("""
        INSERT INTO pu_purchase_order_item(po_id,material_id,qty,unit_price,amount,create_by,create_time)
        VALUES(#{poId},#{materialId},#{qty},#{unitPrice},#{amount},#{createdBy},NOW())
        """)
    int insertLine(@Param("poId") Long poId,@Param("materialId") Long materialId,
                   @Param("qty") BigDecimal qty,@Param("unitPrice") BigDecimal unitPrice,
                   @Param("amount") BigDecimal amount,@Param("createdBy") String createdBy);

    @Update("""
        UPDATE pu_purchase_order SET status=1,update_by=#{username},update_time=NOW()
        WHERE po_id=#{poId} AND status=0 AND del_flag='0'
          AND EXISTS(SELECT 1 FROM pu_purchase_order_item WHERE po_id=#{poId})
        """)
    int confirm(@Param("poId") Long poId,@Param("username") String username);

    @Select("SELECT status FROM pu_purchase_order WHERE po_id=#{poId} AND del_flag='0' FOR UPDATE")
    Integer lockStatus(@Param("poId") Long poId);

    @Select("SELECT supplier_id AS supplierId,status,total_amount AS totalAmount,approval_status AS approvalStatus FROM pu_purchase_order WHERE po_id=#{id} AND del_flag='0' FOR UPDATE")
    Map<String,Object> lockHeader(@Param("id") Long id);

    @Update("UPDATE pu_purchase_order SET approval_status=1,update_by=#{user},update_time=NOW() WHERE po_id=#{id} AND status=0 AND approval_status IN (0,3)")
    int markPendingApproval(@Param("id") Long id,@Param("user") String user);

    @Update("UPDATE pu_purchase_order SET status=1,approval_status=2,approved_by=#{user},approved_time=NOW(),update_by=#{user},update_time=NOW() WHERE po_id=#{id} AND status=0 AND approval_status=1")
    int approve(@Param("id") Long id,@Param("user") String user);

    @Update("UPDATE pu_purchase_order SET approval_status=3,approval_comment=#{comment},update_by=#{user},update_time=NOW() WHERE po_id=#{id} AND status=0 AND approval_status=1")
    int reject(@Param("id") Long id,@Param("comment") String comment,@Param("user") String user);

    @Select("""
        SELECT i.item_id AS itemId,i.material_id AS materialId,i.qty,i.unit_price AS unitPrice,
               COALESCE((SELECT SUM(t.qty) FROM st_stock_txn t WHERE t.source_type='pu_order'
                 AND t.source_line_id=i.item_id AND t.biz_type=1 AND t.txn_type=1),0) AS receivedQty
        FROM pu_purchase_order_item i WHERE i.item_id=#{itemId} AND i.po_id=#{poId}
        """)
    Map<String,Object> receiptLine(@Param("poId") Long poId,@Param("itemId") Long itemId);

    @Select("""
        SELECT COUNT(*) FROM pu_purchase_order_item i
        WHERE i.po_id=#{poId} AND i.qty > COALESCE((SELECT SUM(t.qty) FROM st_stock_txn t
          WHERE t.source_type='pu_order' AND t.source_line_id=i.item_id
            AND t.biz_type=1 AND t.txn_type=1),0)
        """)
    int remainingLineCount(@Param("poId") Long poId);

    @Update("""
        UPDATE pu_purchase_order SET status=#{status},
          actual_arrival_date=IF(#{status}=3,#{arrivalDate},NULL),
          update_by=#{username},update_time=NOW() WHERE po_id=#{poId}
        """)
    int finishReceipt(@Param("poId") Long poId,@Param("status") int status,
                      @Param("arrivalDate") LocalDate arrivalDate,@Param("username") String username);

    @Select("""
        SELECT status, po_no AS poNo
        FROM pu_purchase_order WHERE po_id = #{poId} AND del_flag = '0' FOR UPDATE
        """)
    Map<String, Object> lockDraft(@Param("poId") Long poId);

    @Update("""
        UPDATE pu_purchase_order SET supplier_id = #{supplierId}, expect_date = #{expectDate},
          total_amount = #{totalAmount}, update_by = #{updatedBy}, update_time = NOW()
        WHERE po_id = #{poId} AND status = 0 AND del_flag = '0'
        """)
    int update(@Param("poId") Long poId, @Param("supplierId") Long supplierId,
               @Param("expectDate") LocalDate expectDate,
               @Param("totalAmount") BigDecimal totalAmount,
               @Param("updatedBy") String updatedBy);

    @Delete("DELETE FROM pu_purchase_order_item WHERE po_id = #{poId}")
    int deleteLines(@Param("poId") Long poId);
}
