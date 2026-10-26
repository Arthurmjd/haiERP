package net.factoryopen.myfactoryai.erp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

@Mapper
public interface ErpDocMapper {

    @SelectProvider(type = ErpDocSql.class, method = "selectStatus")
    Map<String, Object> selectStatus(Map<String, Object> args);

    @SelectProvider(type = ErpDocSql.class, method = "refCounts")
    List<Map<String, Object>> refCounts(Map<String, Object> args);

    @DeleteProvider(type = ErpDocSql.class, method = "deleteChild")
    int deleteChild(Map<String, Object> args);

    @DeleteProvider(type = ErpDocSql.class, method = "deleteHeader")
    int deleteHeader(Map<String, Object> args);

    @UpdateProvider(type = ErpDocSql.class, method = "cancel")
    int cancel(Map<String, Object> args);
}
