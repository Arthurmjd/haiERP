package net.factoryopen.myfactoryai.erp.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ErpWorkOrderMapper {
    @Select("SELECT COUNT(*) FROM pr_work_order WHERE order_id=#{orderId} AND style_id=#{styleId} AND del_flag='0'")
    int existingWorkOrder(@Param("orderId") Long orderId,@Param("styleId") Long styleId);
    @Select("SELECT status FROM sd_order WHERE order_id=#{id} AND del_flag='0' FOR UPDATE")
    Integer lockOrder(@Param("id") Long id);

    @Select("SELECT delivery_date AS deliveryDate FROM sd_order WHERE order_id=#{id} AND del_flag='0'")
    java.time.LocalDate orderDelivery(@Param("id") Long id);

    @Select("SELECT DISTINCT style_id AS styleId FROM sd_order_item WHERE order_id=#{orderId}")
    List<Long> orderStyles(@Param("orderId") Long orderId);

    @Select("""
        SELECT sku.sku_id AS skuId,SUM(oi.qty) AS qty
        FROM sd_order_item oi JOIN md_sku sku ON sku.style_id=oi.style_id
          AND sku.color_id=oi.color_id AND sku.size_id=oi.size_id
        WHERE oi.order_id=#{orderId} AND oi.style_id=#{styleId}
        GROUP BY sku.sku_id ORDER BY sku.sku_id
        """)
    List<Map<String,Object>> orderSkus(@Param("orderId") Long orderId,@Param("styleId") Long styleId);

    @Select("""
        SELECT b.material_id AS materialId,b.qty AS unitQty,b.loss_rate AS lossRate,
               b.unit,m.unit AS materialUnit
        FROM md_bom b JOIN md_material m ON m.material_id=b.material_id AND m.status=1
        WHERE b.style_id=#{styleId} ORDER BY b.material_id
        """)
    List<Map<String,Object>> bom(@Param("styleId") Long styleId);

    @Select("""
        SELECT p.process_id AS processId,p.seq,p.unit_price AS unitPrice
        FROM md_style_process p JOIN md_process m ON m.process_id=p.process_id AND m.status=1
        WHERE p.style_id=#{styleId} ORDER BY p.seq,p.process_id
        """)
    List<Map<String,Object>> processes(@Param("styleId") Long styleId);

    @Insert("""
        INSERT INTO pr_work_order(wo_no,order_id,style_id,qty,plan_start_date,plan_end_date,status,create_by,create_time)
        VALUES(#{woNo},#{orderId},#{styleId},#{qty},#{planStartDate},#{planEndDate},0,#{createdBy},NOW())
        """)
    @Options(useGeneratedKeys=true,keyProperty="woId")
    int insert(WorkOrderHeader header);

    @Update("UPDATE pr_work_order SET wo_no=#{no} WHERE wo_id=#{id}")
    int setNo(@Param("id") Long id,@Param("no") String no);

    @Insert("INSERT INTO pr_work_order_sku(wo_id,sku_id,plan_qty,create_by,create_time) VALUES(#{woId},#{skuId},#{qty},#{username},NOW())")
    int insertSku(@Param("woId") Long woId,@Param("skuId") Long skuId,@Param("qty") Integer qty,@Param("username") String username);

    /** 工单 SKU 批量插入参数。 */
    record SkuInsert(Long skuId, Integer qty) {
    }

    /** 批量插入工单 SKU，一次数据库往返完成全部行。 */
    @Insert("""
        <script>
        INSERT INTO pr_work_order_sku(wo_id,sku_id,plan_qty,create_by,create_time)
        VALUES
        <foreach collection="lines" item="l" separator=",">
          (#{woId},#{l.skuId},#{l.qty},#{username},NOW())
        </foreach>
        </script>
        """)
    int insertSkus(@Param("woId") Long woId, @Param("lines") List<SkuInsert> lines,
                   @Param("username") String username);

    @Insert("""
        INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,create_by,create_time)
        VALUES(#{woId},#{materialId},#{unit},#{unitQty},#{lossRate},#{planQty},#{username},NOW())
        """)
    int insertMaterial(@Param("woId") Long woId,@Param("materialId") Long materialId,
                       @Param("unit") String unit,@Param("unitQty") BigDecimal unitQty,
                       @Param("lossRate") BigDecimal lossRate,@Param("planQty") BigDecimal planQty,
                       @Param("username") String username);

    /** 工单用料批量插入参数。 */
    record MaterialInsert(Long materialId, String unit, BigDecimal unitQty, BigDecimal lossRate, BigDecimal planQty) {
    }

    /** 批量插入工单用料，一次数据库往返完成全部行。 */
    @Insert("""
        <script>
        INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,create_by,create_time)
        VALUES
        <foreach collection="lines" item="l" separator=",">
          (#{woId},#{l.materialId},#{l.unit},#{l.unitQty},#{l.lossRate},#{l.planQty},#{username},NOW())
        </foreach>
        </script>
        """)
    int insertMaterials(@Param("woId") Long woId, @Param("lines") List<MaterialInsert> lines,
                        @Param("username") String username);

    @Insert("""
        INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,unit_price,create_by,create_time)
        VALUES(#{woId},#{processId},#{seq},#{qty},#{unitPrice},#{username},NOW())
        """)
    int insertProcess(@Param("woId") Long woId,@Param("processId") Long processId,
                      @Param("seq") Integer seq,@Param("qty") Integer qty,
                      @Param("unitPrice") BigDecimal unitPrice,@Param("username") String username);

    /** 工单工序批量插入参数。 */
    record ProcessInsert(Long processId, Integer seq, Integer qty, BigDecimal unitPrice) {
    }

    /** 批量插入工单工序，一次数据库往返完成全部行。 */
    @Insert("""
        <script>
        INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,unit_price,create_by,create_time)
        VALUES
        <foreach collection="lines" item="l" separator=",">
          (#{woId},#{l.processId},#{l.seq},#{l.qty},#{l.unitPrice},#{username},NOW())
        </foreach>
        </script>
        """)
    int insertProcesses(@Param("woId") Long woId, @Param("lines") List<ProcessInsert> lines,
                        @Param("username") String username);

    @Select("SELECT status FROM pr_work_order WHERE wo_id=#{id} AND del_flag='0' FOR UPDATE")
    Integer lockStatus(@Param("id") Long id);

    @Select("SELECT order_id AS orderId,style_id AS styleId,qty FROM pr_work_order WHERE wo_id=#{id} AND del_flag='0'")
    Map<String,Object> header(@Param("id") Long id);

    @Select("""
        SELECT CAST(w.wo_id AS CHAR) AS woId,w.wo_no AS woNo,
               CAST(w.order_id AS CHAR) AS orderId,CAST(w.style_id AS CHAR) AS styleId,
               s.style_name AS styleName,w.qty,w.status,w.plan_end_date AS planEndDate,
               w.actual_start_date AS actualStartDate,w.actual_end_date AS actualEndDate
        FROM pr_work_order w JOIN md_style s ON s.style_id=w.style_id
        WHERE w.wo_id=#{id} AND w.del_flag='0'
        """)
    Map<String,Object> detail(@Param("id") Long id);

    @Select("""
        SELECT CAST(w.sku_id AS CHAR) AS skuId,s.sku_code AS skuCode,
               w.plan_qty AS planQty,w.completed_qty AS completedQty
        FROM pr_work_order_sku w JOIN md_sku s ON s.sku_id=w.sku_id
        WHERE w.wo_id=#{id} ORDER BY w.sku_id
        """)
    List<Map<String,Object>> detailSkus(@Param("id") Long id);

    @Select("""
        SELECT CAST(w.id AS CHAR) AS id,CAST(w.material_id AS CHAR) AS materialId,
               m.material_code AS materialCode,m.material_name AS materialName,
               w.unit,w.plan_qty AS planQty,w.issued_qty AS issuedQty,w.returned_qty AS returnedQty
        FROM pr_work_order_material w JOIN md_material m ON m.material_id=w.material_id
        WHERE w.wo_id=#{id} ORDER BY w.material_id
        """)
    List<Map<String,Object>> detailMaterials(@Param("id") Long id);

    @Select("""
        SELECT CAST(w.id AS CHAR) AS id,p.process_name AS processName,
               w.seq,w.plan_qty AS planQty,w.done_qty AS doneQty,w.bad_qty AS badQty,
               w.is_outsourced AS isOutsourced,w.status
        FROM pr_work_order_process w JOIN md_process p ON p.process_id=w.process_id
        WHERE w.wo_id=#{id} ORDER BY w.seq,w.id
        """)
    List<Map<String,Object>> detailProcesses(@Param("id") Long id);

    @Select("SELECT id AS id,material_id AS materialId,plan_qty AS planQty,issued_qty AS issuedQty,returned_qty AS returnedQty FROM pr_work_order_material WHERE wo_id=#{woId} ORDER BY material_id")
    List<Map<String,Object>> materials(@Param("woId") Long woId);

    @Insert("""
        INSERT INTO st_reservation(item_type,item_id,source_type,source_id,reserved_qty,status,create_by,create_time)
        VALUES(1,#{materialId},'pr_wo',#{woId},#{qty},0,#{username},NOW())
        ON DUPLICATE KEY UPDATE reserved_qty=VALUES(reserved_qty),status=0,update_time=NOW()
        """)
    int insertReservation(@Param("woId") Long woId,@Param("materialId") Long materialId,
                          @Param("qty") BigDecimal qty,@Param("username") String username);

    @Select("""
        SELECT GREATEST(COALESCE((SELECT SUM(qty) FROM st_inventory
                    WHERE item_type=1 AND item_id=#{materialId} AND warehouse_id=1),0)
               - COALESCE((SELECT SUM(reserved_qty) FROM st_reservation
                    WHERE item_type=1 AND item_id=#{materialId} AND status=0),0),0)
        """)
    BigDecimal availableToReserve(@Param("materialId") Long materialId);

    @Select("""
        SELECT GREATEST(COALESCE((SELECT SUM(qty) FROM st_inventory
                    WHERE item_type=1 AND item_id=#{materialId} AND warehouse_id=1),0)
               - COALESCE((SELECT SUM(reserved_qty) FROM st_reservation
                    WHERE item_type=1 AND item_id=#{materialId} AND status=0
                      AND NOT(source_type='pr_wo' AND source_id=#{woId})),0),0)
        """)
    BigDecimal availableForIssue(@Param("materialId") Long materialId,@Param("woId") Long woId);

    @Update("""
        UPDATE st_reservation SET reserved_qty=GREATEST(reserved_qty-#{qty},0),update_time=NOW()
        WHERE source_type='pr_wo' AND source_id=#{woId} AND item_type=1 AND item_id=#{materialId} AND status=0
        """)
    int reduceReservation(@Param("woId") Long woId,@Param("materialId") Long materialId,
                          @Param("qty") BigDecimal qty);

    @Update("""
        UPDATE st_reservation SET status=1,reserved_qty=0,update_time=NOW()
        WHERE source_type='pr_wo' AND source_id=#{woId} AND status=0
        """)
    int releaseReservations(@Param("woId") Long woId);

    @Update("""
        UPDATE pr_work_order_material SET issued_qty=issued_qty-#{qty},returned_qty=returned_qty+#{qty}
        WHERE id=#{id} AND issued_qty-returned_qty>=#{qty}
        """)
    int returnMaterial(@Param("id") Long id,@Param("qty") BigDecimal qty);

    @Select("SELECT inv_id AS invId,batch_no AS batchNo,qty FROM st_inventory WHERE item_type=1 AND item_id=#{materialId} AND warehouse_id=1 AND qty>0 ORDER BY inv_id FOR UPDATE")
    List<Map<String,Object>> stockBatches(@Param("materialId") Long materialId);

    @Update("UPDATE pr_work_order_material SET issued_qty=issued_qty+#{qty} WHERE id=#{id} AND issued_qty+#{qty}<=plan_qty")
    int markIssued(@Param("id") Long id,@Param("qty") BigDecimal qty);

    @Update("UPDATE pr_work_order SET status=1,actual_start_date=CURDATE(),update_by=#{username},update_time=NOW() WHERE wo_id=#{id} AND status=0")
    int start(@Param("id") Long id,@Param("username") String username);

    @Update("UPDATE sd_order SET status=2,update_by=#{username},update_time=NOW() WHERE order_id=#{orderId} AND status=1")
    int startOrder(@Param("orderId") Long orderId,@Param("username") String username);

    @Select("""
        SELECT p.id AS processId,p.wo_id AS woId,p.process_id AS baseProcessId,
               p.seq,p.plan_qty AS planQty,p.done_qty AS doneQty,p.bad_qty AS badQty,
               p.is_outsourced AS isOutsourced,p.unit_price AS unitPrice,p.status
        FROM pr_work_order_process p WHERE p.id=#{id} FOR UPDATE
        """)
    Map<String,Object> lockProcess(@Param("id") Long id);

    @Select("SELECT plan_qty AS planQty,completed_qty AS completedQty FROM pr_work_order_sku WHERE wo_id=#{woId} AND sku_id=#{skuId}")
    Map<String,Object> skuPlan(@Param("woId") Long woId,@Param("skuId") Long skuId);

    @Select("SELECT COALESCE(SUM(good_qty),0) FROM pr_process_report WHERE wo_process_id=#{processId} AND sku_id=#{skuId}")
    int reportedGood(@Param("processId") Long processId,@Param("skuId") Long skuId);

    @Select("SELECT id FROM pr_work_order_process WHERE wo_id=#{woId} AND seq<#{seq} ORDER BY seq DESC,id DESC LIMIT 1")
    Long priorProcess(@Param("woId") Long woId,@Param("seq") Integer seq);

    @Select("SELECT id FROM pr_work_order_process WHERE wo_id=#{woId} ORDER BY seq DESC,id DESC LIMIT 1")
    Long finalProcess(@Param("woId") Long woId);

    @Insert("""
        INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,
          good_qty,bad_qty,report_time,create_by,create_time)
        VALUES(#{key},#{processId},#{woId},#{skuId},#{empId},#{outsourced},
          #{good},#{bad},#{date},#{username},NOW())
        """)
    @Options(useGeneratedKeys=true,keyProperty="reportId")
    int insertReport(ProcessReportRow row);

    @Update("""
        UPDATE pr_work_order_process SET done_qty=done_qty+#{good},bad_qty=bad_qty+#{bad},
          status=IF(done_qty+#{good}>=plan_qty,2,1)
        WHERE id=#{id} AND done_qty+#{good}<=plan_qty
        """)
    int progress(@Param("id") Long id,@Param("good") Integer good,@Param("bad") Integer bad);

    @Insert("""
        INSERT INTO hr_piece_wage(emp_id,work_order_id,style_id,process_id,qty,unit_price,
          amount,wage_period,status,report_id,create_by,create_time)
        VALUES(#{empId},#{woId},#{styleId},#{processId},#{qty},#{price},#{amount},
          #{period},0,#{reportId},#{username},NOW())
        """)
    int insertWage(@Param("empId") Long empId,@Param("woId") Long woId,
                   @Param("styleId") Long styleId,@Param("processId") Long processId,
                   @Param("qty") Integer qty,@Param("price") BigDecimal price,
                   @Param("amount") BigDecimal amount,@Param("period") String period,
                   @Param("reportId") Long reportId,@Param("username") String username);

    @Select("SELECT status FROM hr_employee WHERE emp_id=#{id} AND emp_type=1 FOR UPDATE")
    Integer activeWorker(@Param("id") Long id);

    @Select("SELECT name FROM hr_employee WHERE emp_id=#{id}")
    String workerName(@Param("id") Long id);

    @Select("SELECT COUNT(*) FROM md_process_worker WHERE process_id=#{processId}")
    int processWorkerCount(@Param("processId") Long processId);

    @Select("SELECT COUNT(*) FROM md_process_worker WHERE process_id=#{processId} AND emp_id=#{empId}")
    int workerInProcess(@Param("empId") Long empId,@Param("processId") Long processId);

    @Select("SELECT COUNT(*) FROM hr_salary_slip WHERE emp_id=#{empId} AND wage_period=#{period} AND status IN (1,2)")
    int closedWagePeriod(@Param("empId") Long empId,@Param("period") String period);

    @Update("UPDATE pr_work_order_sku SET completed_qty=completed_qty+#{qty} WHERE wo_id=#{woId} AND sku_id=#{skuId} AND completed_qty+#{qty}<=plan_qty")
    int finishSku(@Param("woId") Long woId,@Param("skuId") Long skuId,@Param("qty") Integer qty);

    @Select("SELECT COUNT(*) FROM pr_work_order_sku WHERE wo_id=#{woId} AND completed_qty<plan_qty")
    int unfinishedSkuCount(@Param("woId") Long woId);

    @Update("UPDATE pr_work_order SET status=2,actual_end_date=CURDATE(),update_by=#{username},update_time=NOW() WHERE wo_id=#{woId} AND status=1")
    int finish(@Param("woId") Long woId,@Param("username") String username);

    @Update("UPDATE sd_order SET status=3,update_by=#{username},update_time=NOW() WHERE order_id=#{orderId} AND status=2 AND NOT EXISTS(SELECT 1 FROM pr_work_order WHERE order_id=#{orderId} AND status<>2 AND del_flag='0')")
    int finishOrder(@Param("orderId") Long orderId,@Param("username") String username);
}
