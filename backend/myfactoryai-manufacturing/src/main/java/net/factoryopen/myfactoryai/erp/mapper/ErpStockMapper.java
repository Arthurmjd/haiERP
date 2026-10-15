package net.factoryopen.myfactoryai.erp.mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ErpStockMapper {
    @Insert("""
        INSERT INTO biz_action_request(request_key,action_type,target_type,target_id,payload_hash,status,create_by)
        VALUES(#{key},#{action},#{targetType},#{targetId},#{hash},0,#{username})
        ON DUPLICATE KEY UPDATE request_key=request_key
        """)
    int reserveAction(@Param("key") String key,@Param("action") String action,
                      @Param("targetType") String targetType,@Param("targetId") Long targetId,
                      @Param("hash") String hash,@Param("username") String username);

    @Select("SELECT action_type AS actionType,target_type AS targetType,target_id AS targetId,payload_hash AS payloadHash,status,result_json AS resultJson " +
            "FROM biz_action_request WHERE request_key=#{key} FOR UPDATE")
    Map<String,Object> action(@Param("key") String key);

    @Update("""
        UPDATE biz_action_request SET status=1,result_json=#{result},update_time=NOW()
        WHERE request_key=#{key} AND status=0
        """)
    int completeAction(@Param("key") String key,@Param("result") String result);

    @Insert("""
        INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
        VALUES(#{type},#{itemId},#{batch},1,#{qty},#{username},NOW(),NOW())
        ON DUPLICATE KEY UPDATE qty=qty+VALUES(qty),update_time=NOW()
        """)
    int addStock(@Param("type") int type,@Param("itemId") Long itemId,
                 @Param("batch") String batch,@Param("qty") BigDecimal qty,
                 @Param("username") String username);

    @Update("""
        UPDATE st_inventory SET qty=qty-#{qty},update_time=NOW()
        WHERE item_type=#{type} AND item_id=#{itemId} AND batch_no=#{batch}
          AND warehouse_id=1 AND qty>=#{qty}
        """)
    int subtractStock(@Param("type") int type,@Param("itemId") Long itemId,
                      @Param("batch") String batch,@Param("qty") BigDecimal qty);

    @Insert("""
        INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,
          source_type,source_id,source_line_id,request_key,txn_time,remark,create_by,create_time)
        VALUES(#{txnNo},#{direction},#{bizType},#{itemType},#{itemId},#{batch},#{qty},
          #{sourceType},#{sourceId},#{sourceLineId},#{requestKey},#{txnDate},#{remark},#{username},NOW())
        """)
    int insertTxn(@Param("txnNo") String txnNo,@Param("direction") int direction,
                  @Param("bizType") int bizType,@Param("itemType") int itemType,
                  @Param("itemId") Long itemId,@Param("batch") String batch,
                  @Param("qty") BigDecimal qty,@Param("sourceType") String sourceType,
                  @Param("sourceId") Long sourceId,@Param("sourceLineId") Long sourceLineId,
                  @Param("requestKey") String requestKey,@Param("txnDate") LocalDate txnDate,
                  @Param("remark") String remark,
                  @Param("username") String username);

    @Select("SELECT qty FROM st_inventory WHERE item_type=#{type} AND item_id=#{itemId} AND batch_no=#{batch} AND warehouse_id=1 FOR UPDATE")
    BigDecimal lockQuantity(@Param("type") int type,@Param("itemId") Long itemId,@Param("batch") String batch);

    @Select("SELECT COUNT(*) FROM md_material WHERE material_id=#{id}")
    int materialExists(@Param("id") Long id);

    @Select("SELECT COUNT(*) FROM md_sku WHERE sku_id=#{id}")
    int skuExists(@Param("id") Long id);

    @Select("""
        SELECT CAST(t.txn_id AS CHAR) AS txnId,t.txn_no AS txnNo,t.txn_type AS txnType,
               t.biz_type AS bizType,t.item_type AS itemType,CAST(t.item_id AS CHAR) AS itemId,
               COALESCE(m.material_code,s.sku_code) AS itemCode,
               COALESCE(m.material_name,st.style_name) AS itemName,
               t.batch_no AS batchNo,t.qty,t.source_type AS sourceType,
               CAST(t.source_id AS CHAR) AS sourceId,t.txn_time AS txnTime
        FROM st_stock_txn t
        LEFT JOIN md_material m ON t.item_type=1 AND m.material_id=t.item_id
        LEFT JOIN md_sku s ON t.item_type=2 AND s.sku_id=t.item_id
        LEFT JOIN md_style st ON st.style_id=s.style_id
        ORDER BY t.txn_id DESC
        """)
    List<Map<String,Object>> txns();
}
