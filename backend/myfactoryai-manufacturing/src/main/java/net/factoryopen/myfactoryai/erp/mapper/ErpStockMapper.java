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

    /** 默认入库仓：单仓 MVP，新入库统一落在 1 号仓。 */
    int DEFAULT_WAREHOUSE = 1;

    @Insert("""
        INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
        VALUES(#{type},#{itemId},#{batch},#{warehouseId},#{qty},#{username},NOW(),NOW())
        ON DUPLICATE KEY UPDATE qty=qty+VALUES(qty),update_time=NOW()
        """)
    int addStock(@Param("type") int type,@Param("itemId") Long itemId,
                 @Param("batch") String batch,@Param("qty") BigDecimal qty,
                 @Param("warehouseId") Long warehouseId,@Param("username") String username);

    /**
     * 按「批次 + 仓库」扣减结存。warehouse_id 不再写死：
     * 同一批号可能分散在多个仓库（历史数据存在 1 号仓与 2 号仓各有一份），
     * 由调用方先查出实际有货的仓库再逐行扣，qty&gt;=#{qty} 保证并发下不会扣成负数。
     */
    @Update("""
        UPDATE st_inventory SET qty=qty-#{qty},update_time=NOW()
        WHERE item_type=#{type} AND item_id=#{itemId} AND batch_no=#{batch}
          AND warehouse_id=#{warehouseId} AND qty>=#{qty}
        """)
    int subtractStock(@Param("type") int type,@Param("itemId") Long itemId,
                      @Param("batch") String batch,@Param("qty") BigDecimal qty,
                      @Param("warehouseId") Long warehouseId);

    /** 某批号在各仓库的结存（按仓库升序，优先扣 1 号仓）。 */
    @Select("""
        SELECT warehouse_id AS warehouseId,qty FROM st_inventory
        WHERE item_type=#{type} AND item_id=#{itemId} AND batch_no=#{batch} AND qty>0
        ORDER BY warehouse_id FOR UPDATE
        """)
    List<Map<String,Object>> batchStockRows(@Param("type") int type,@Param("itemId") Long itemId,
                                            @Param("batch") String batch);

    /** 某对象当前有哪些可用批次，用于报错提示与出货下拉。 */
    @Select("""
        SELECT batch_no AS batchNo,SUM(qty) AS qty FROM st_inventory
        WHERE item_type=#{type} AND item_id=#{itemId} AND qty>0
        GROUP BY batch_no ORDER BY batch_no
        """)
    List<Map<String,Object>> availableBatches(@Param("type") int type,@Param("itemId") Long itemId);

    /** 某批号的全仓合计结存，用于扣减前的可读校验。 */
    @Select("""
        SELECT COALESCE(SUM(qty),0) FROM st_inventory
        WHERE item_type=#{type} AND item_id=#{itemId} AND batch_no=#{batch}
        """)
    BigDecimal batchTotalQty(@Param("type") int type,@Param("itemId") Long itemId,
                             @Param("batch") String batch);

    @Insert("""
        INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,
          source_type,source_id,source_line_id,request_key,txn_time,remark,create_by,create_time)
        VALUES(#{txnNo},#{direction},#{bizType},#{itemType},#{itemId},#{batch},#{qty},#{warehouseId},
          #{sourceType},#{sourceId},#{sourceLineId},#{requestKey},#{txnDate},#{remark},#{username},NOW())
        """)
    int insertTxn(@Param("txnNo") String txnNo,@Param("direction") int direction,
                  @Param("bizType") int bizType,@Param("itemType") int itemType,
                  @Param("itemId") Long itemId,@Param("batch") String batch,
                  @Param("qty") BigDecimal qty,@Param("warehouseId") Long warehouseId,
                  @Param("sourceType") String sourceType,
                  @Param("sourceId") Long sourceId,@Param("sourceLineId") Long sourceLineId,
                  @Param("requestKey") String requestKey,@Param("txnDate") LocalDate txnDate,
                  @Param("remark") String remark,
                  @Param("username") String username);

    @Select("SELECT COUNT(*) FROM md_material WHERE material_id=#{id}")
    int materialExists(@Param("id") Long id);

    @Select("SELECT material_code FROM md_material WHERE material_id=#{id}")
    String materialCode(@Param("id") Long id);

    @Select("SELECT sku_code FROM md_sku WHERE sku_id=#{id}")
    String skuCode(@Param("id") Long id);

    @Select("SELECT COUNT(*) FROM md_sku WHERE sku_id=#{id}")
    int skuExists(@Param("id") Long id);

    @Select("""
        <script>
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
        <where><if test="month != null and month != ''">DATE_FORMAT(t.txn_time,'%Y-%m') = #{month}</if></where>
        ORDER BY t.txn_id DESC
        </script>
        """)
    List<Map<String,Object>> txns(@Param("month") String month);
}
