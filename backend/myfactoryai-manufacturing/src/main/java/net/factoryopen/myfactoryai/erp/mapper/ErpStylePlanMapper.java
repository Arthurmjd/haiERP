package net.factoryopen.myfactoryai.erp.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ErpStylePlanMapper {
    @Select("SELECT status FROM md_style WHERE style_id=#{id} FOR UPDATE")
    Integer lockStyle(@Param("id") Long id);

    @Select("SELECT CAST(b.material_id AS CHAR) AS materialId,m.material_code AS materialCode,m.material_name AS materialName,b.qty,b.loss_rate AS lossRate,b.unit FROM md_bom b JOIN md_material m ON m.material_id=b.material_id WHERE b.style_id=#{id} ORDER BY b.bom_id")
    List<Map<String,Object>> bom(@Param("id") Long id);

    @Select("SELECT CAST(s.process_id AS CHAR) AS processId,p.process_name AS processName,s.seq,s.unit_price AS unitPrice FROM md_style_process s JOIN md_process p ON p.process_id=s.process_id WHERE s.style_id=#{id} ORDER BY s.seq,s.id")
    List<Map<String,Object>> processes(@Param("id") Long id);

    @Select("SELECT unit FROM md_material WHERE material_id=#{id} AND status=1")
    String materialUnit(@Param("id") Long id);

    @Select("SELECT COUNT(*) FROM md_process WHERE process_id=#{id} AND status=1")
    int activeProcess(@Param("id") Long id);

    @Select("SELECT CAST(process_id AS CHAR) AS id,process_name AS name FROM md_process WHERE status=1 ORDER BY sort,process_id")
    List<Map<String,Object>> processOptions();

    @Delete("DELETE FROM md_bom WHERE style_id=#{id}")
    int clearBom(@Param("id") Long id);

    @Delete("DELETE FROM md_style_process WHERE style_id=#{id}")
    int clearProcesses(@Param("id") Long id);

    @Insert("INSERT INTO md_bom(style_id,material_id,qty,loss_rate,unit,create_by,create_time) VALUES(#{styleId},#{materialId},#{qty},#{lossRate},#{unit},#{username},NOW())")
    int addBom(@Param("styleId") Long styleId,@Param("materialId") Long materialId,@Param("qty") BigDecimal qty,
               @Param("lossRate") BigDecimal lossRate,@Param("unit") String unit,@Param("username") String username);

    @Insert("INSERT INTO md_style_process(style_id,process_id,seq,unit_price,create_by,create_time) VALUES(#{styleId},#{processId},#{seq},#{unitPrice},#{username},NOW())")
    int addProcess(@Param("styleId") Long styleId,@Param("processId") Long processId,@Param("seq") Integer seq,
                   @Param("unitPrice") BigDecimal unitPrice,@Param("username") String username);
}
