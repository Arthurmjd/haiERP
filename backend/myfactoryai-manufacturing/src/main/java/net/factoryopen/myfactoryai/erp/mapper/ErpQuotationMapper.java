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
public interface ErpQuotationMapper {
    @Select("SELECT s.settle_id AS settleId,s.style_id AS styleId,s.qty,s.unit_cost AS unitCost,s.revenue,o.customer_id AS customerId FROM fn_cost_settlement s JOIN sd_order o ON o.order_id=s.order_id WHERE s.settle_id=#{id} AND s.status=1 AND s.del_flag='0' FOR UPDATE")
    Map<String,Object> confirmedSettlement(@Param("id") Long id);

    @Select("SELECT quote_id FROM sd_quotation WHERE request_key=#{key}")
    Long byRequestKey(@Param("key") String key);

    @Insert("INSERT INTO sd_quotation(quote_no,customer_id,quote_date,status,total_amount,request_key,create_by,create_time) VALUES(#{quoteNo},#{customerId},CURDATE(),0,#{totalAmount},#{requestKey},#{username},NOW())")
    @Options(useGeneratedKeys=true,keyProperty="quoteId")
    int insert(Map<String,Object> row);

    @Update("UPDATE sd_quotation SET quote_no=#{no} WHERE quote_id=#{id}")
    int setNo(@Param("id") Long id,@Param("no") String no);

    @Insert("INSERT INTO sd_quotation_item(quote_id,style_id,qty,unit_price,cost_ref,cost_src,source_settle_id,amount,create_by,create_time) VALUES(#{quoteId},#{styleId},#{qty},#{unitPrice},#{costRef},1,#{settleId},#{amount},#{username},NOW())")
    int insertLine(@Param("quoteId") Long quoteId,@Param("styleId") Long styleId,@Param("qty") Integer qty,
                   @Param("unitPrice") BigDecimal unitPrice,@Param("costRef") BigDecimal costRef,
                   @Param("settleId") Long settleId,@Param("amount") BigDecimal amount,@Param("username") String username);

    @Select("SELECT CAST(q.quote_id AS CHAR) AS quoteId,q.quote_no AS quoteNo,c.customer_name AS customerName,q.quote_date AS quoteDate,q.status,q.total_amount AS totalAmount FROM sd_quotation q JOIN md_customer c ON c.customer_id=q.customer_id WHERE q.quote_id=#{id} AND q.del_flag='0'")
    Map<String,Object> detail(@Param("id") Long id);

    @Select("SELECT CAST(i.quote_item_id AS CHAR) AS itemId,CAST(i.style_id AS CHAR) AS styleId,s.style_name AS styleName,i.qty,i.unit_price AS unitPrice,i.cost_ref AS costRef,i.cost_src AS costSrc,CAST(i.source_settle_id AS CHAR) AS sourceSettleId,i.amount FROM sd_quotation_item i JOIN md_style s ON s.style_id=i.style_id WHERE i.quote_id=#{id} ORDER BY i.quote_item_id")
    List<Map<String,Object>> lines(@Param("id") Long id);

    @Update("UPDATE sd_quotation SET status=#{status},update_by=#{username},update_time=NOW() WHERE quote_id=#{id} AND status=0 AND del_flag='0'")
    int transition(@Param("id") Long id,@Param("status") int status,@Param("username") String username);
}
