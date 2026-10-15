package net.factoryopen.myfactoryai.erp.mapper;

import java.util.Map;
import java.util.List;

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

    @Select("SELECT CONCAT(s.style_no,'-',c.color_code,'-',z.size_code) FROM md_style s JOIN md_color c ON c.color_id=#{colorId} AND c.status=1 JOIN md_size z ON z.size_id=#{sizeId} AND z.status=1 WHERE s.style_id=#{styleId} AND s.status=1")
    String skuCode(@Param("styleId") Long styleId,@Param("colorId") Long colorId,@Param("sizeId") Long sizeId);

    @Select("SELECT sku_id FROM md_sku WHERE style_id=#{styleId} AND color_id=#{colorId} AND size_id=#{sizeId}")
    Long existingSku(@Param("styleId") Long styleId,@Param("colorId") Long colorId,@Param("sizeId") Long sizeId);

    @Insert("INSERT INTO md_sku(sku_code,style_id,color_id,size_id,status,create_by,create_time) VALUES(#{code},#{styleId},#{colorId},#{sizeId},1,#{username},NOW())")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSku(Map<String,Object> args);
}
