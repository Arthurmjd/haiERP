package net.factoryopen.myfactoryai.erp.mapper;

import java.util.Map;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

@Mapper
public interface ErpMasterMapper {
    @Select("SELECT CAST(dept_id AS CHAR) AS id,dept_name AS name FROM hr_department WHERE status=1 ORDER BY sort,dept_id")
    List<Map<String,Object>> departments();
    @InsertProvider(type=ErpMasterSql.class,method="insert")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(Map<String,Object> args);

    @UpdateProvider(type=ErpMasterSql.class,method="update")
    int update(Map<String,Object> args);

    @SelectProvider(type=ErpMasterSql.class,method="detail")
    Map<String,Object> detail(Map<String,Object> args);

    @SelectProvider(type=ErpMasterSql.class,method="refCounts")
    List<Map<String,Object>> refCounts(Map<String,Object> args);

    @DeleteProvider(type=ErpMasterSql.class,method="delete")
    int delete(Map<String,Object> args);

    @Select("SELECT CONCAT(s.style_no,'-',c.color_code,'-',z.size_code) FROM md_style s JOIN md_color c ON c.color_id=#{colorId} AND c.status=1 JOIN md_size z ON z.size_id=#{sizeId} AND z.status=1 WHERE s.style_id=#{styleId} AND s.status=1")
    String skuCode(@Param("styleId") Long styleId,@Param("colorId") Long colorId,@Param("sizeId") Long sizeId);

    @Select("SELECT sku_id FROM md_sku WHERE style_id=#{styleId} AND color_id=#{colorId} AND size_id=#{sizeId}")
    Long existingSku(@Param("styleId") Long styleId,@Param("colorId") Long colorId,@Param("sizeId") Long sizeId);

    @Insert("INSERT INTO md_sku(sku_code,style_id,color_id,size_id,status,create_by,create_time) VALUES(#{code},#{styleId},#{colorId},#{sizeId},1,#{username},NOW())")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSku(Map<String,Object> args);

    // ── 工序可报工员工 ────────────────────────────────────────────────
    @Select("SELECT COUNT(*) FROM md_process WHERE process_id=#{id} AND status=1")
    int processActive(@Param("id") Long id);

    @Select("SELECT COUNT(*) FROM hr_employee WHERE emp_id=#{id} AND status=1 AND emp_type=1")
    int workerActive(@Param("id") Long id);

    @Select("""
        SELECT CAST(e.emp_id AS CHAR) AS id, CONCAT(e.emp_no,' · ',e.name) AS name, e.position
        FROM md_process_worker w JOIN hr_employee e ON e.emp_id=w.emp_id
        WHERE w.process_id=#{processId} AND e.status=1 AND e.emp_type=1
        ORDER BY e.emp_no
        """)
    List<Map<String,Object>> processWorkers(@Param("processId") Long processId);

    @Select("""
        SELECT CAST(e.emp_id AS CHAR) AS id, CONCAT(e.emp_no,' · ',e.name) AS name, e.position
        FROM hr_employee e
        WHERE e.status=1 AND e.emp_type=1
          AND NOT EXISTS (SELECT 1 FROM md_process_worker w
                          WHERE w.process_id=#{processId} AND w.emp_id=e.emp_id)
        ORDER BY e.emp_no
        """)
    List<Map<String,Object>> processWorkerCandidates(@Param("processId") Long processId);

    @Insert("INSERT IGNORE INTO md_process_worker(process_id,emp_id,create_by,create_time) VALUES(#{processId},#{empId},#{username},NOW())")
    int insertProcessWorker(@Param("processId") Long processId,@Param("empId") Long empId,
                            @Param("username") String username);

    @Delete("DELETE FROM md_process_worker WHERE process_id=#{processId} AND emp_id=#{empId}")
    int deleteProcessWorker(@Param("processId") Long processId,@Param("empId") Long empId);
}
