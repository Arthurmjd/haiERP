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
public interface ErpFinanceMapper {
    @Select("""
        SELECT CAST(account_id AS CHAR) AS id,account_no AS code,account_name AS name,
               account_type AS accountType,opening_balance AS openingBalance,
               current_balance AS currentBalance,status,remark
        FROM fn_account ORDER BY account_id
        """)
    List<Map<String,Object>> accounts();

    /**
     * 挑选一个「余额足以支付本次金额」的启用账户。
     * 旧实现只取第一个启用账户，若该账户余额不足、而其他账户有钱，会误报「资金不足」。
     * 这里按余额从大到小选第一个够付的账户。
     */
    @Select("""
        SELECT account_id FROM fn_account
        WHERE status=1 AND current_balance >= #{amount}
        ORDER BY current_balance DESC, account_id LIMIT 1
        """)
    Long paymentAccountFor(@Param("amount") BigDecimal amount);

    /** 所有启用账户的可用余额合计，用于「资金不足」时给出可读提示。 */
    @Select("SELECT COALESCE(SUM(current_balance),0) FROM fn_account WHERE status=1")
    BigDecimal availableCashBalance();

    @Select("SELECT account_id AS accountId,current_balance AS currentBalance,status FROM fn_account WHERE account_id=#{id} FOR UPDATE")
    Map<String,Object> lockAccount(@Param("id") Long id);

    @Insert("""
        INSERT INTO fn_account(account_no,account_name,account_type,opening_balance,current_balance,status,remark,create_by,create_time)
        VALUES(#{accountNo},#{accountName},#{accountType},#{openingBalance},#{openingBalance},#{status},#{remark},#{username},NOW())
        """)
    @Options(useGeneratedKeys=true,keyProperty="accountId")
    int insertAccount(Map<String,Object> row);

    @Update("""
        UPDATE fn_account SET account_no=#{accountNo},account_name=#{accountName},account_type=#{accountType},
          status=#{status},remark=#{remark},update_by=#{username},update_time=NOW()
        WHERE account_id=#{accountId}
        """)
    int updateAccount(Map<String,Object> row);

    @Update("UPDATE fn_account SET current_balance=current_balance+#{amount},update_time=NOW() WHERE account_id=#{id}")
    int addBalance(@Param("id") Long id,@Param("amount") BigDecimal amount);

    @Update("UPDATE fn_account SET current_balance=current_balance-#{amount},update_time=NOW() WHERE account_id=#{id} AND current_balance>=#{amount}")
    int subtractBalance(@Param("id") Long id,@Param("amount") BigDecimal amount);

    @Select("""
        SELECT CAST(t.txn_id AS CHAR) AS id,t.txn_no AS txnNo,a.account_name AS accountName,
               t.direction,t.amount,t.biz_type AS bizType,t.counterparty_type AS counterpartyType,
               t.counterparty_id AS counterpartyId,t.txn_date AS txnDate,t.source_type AS sourceType,
               t.source_id AS sourceId,t.remark
        FROM fn_cash_txn t JOIN fn_account a ON a.account_id=t.account_id
        ORDER BY t.txn_id DESC
        """)
    List<Map<String,Object>> cashTxns();

    @Insert("""
        INSERT INTO fn_cash_txn(txn_no,account_id,direction,amount,biz_type,counterparty_type,
          counterparty_id,source_type,source_id,txn_date,request_key,remark,create_by,create_time)
        VALUES(#{txnNo},#{accountId},#{direction},#{amount},#{bizType},#{counterpartyType},
          #{counterpartyId},#{sourceType},#{sourceId},#{txnDate},#{requestKey},#{remark},#{username},NOW())
        ON DUPLICATE KEY UPDATE txn_no=txn_no
        """)
    int insertCashTxn(Map<String,Object> row);

    @Select("""
        SELECT CAST(ar_id AS CHAR) AS id,ar_no AS code,customer_name AS customerName,
               CAST(order_id AS CHAR) AS orderId,CAST(shipment_id AS CHAR) AS shipmentId,
               amount,received_amount AS receivedAmount,balance,due_date AS dueDate,
               status,overdue_days AS overdueDays,aging_bucket AS agingBucket
        FROM v_ar_aging ORDER BY ar_id DESC
        """)
    List<Map<String,Object>> receivables();

    @Select("""
        SELECT CAST(ap_id AS CHAR) AS id,ap_no AS code,supplier_name AS supplierName,
               CAST(po_id AS CHAR) AS poId,amount,paid_amount AS paidAmount,balance,
               due_date AS dueDate,status,overdue_days AS overdueDays,aging_bucket AS agingBucket
        FROM v_ap_aging ORDER BY ap_id DESC
        """)
    List<Map<String,Object>> payables();

    @Insert("""
        INSERT IGNORE INTO fn_receivable(ar_no,customer_id,order_id,shipment_id,amount,received_amount,
          balance,due_date,status,request_key,create_by,create_time)
        VALUES(#{arNo},#{customerId},#{orderId},#{shipmentId},#{amount},0,#{amount},#{dueDate},0,#{requestKey},#{username},NOW())
        """)
    @Options(useGeneratedKeys=true,keyProperty="arId")
    int insertReceivable(Map<String,Object> row);

    @Update("UPDATE fn_receivable SET ar_no=#{no} WHERE ar_id=#{id}")
    int setArNo(@Param("id") Long id,@Param("no") String no);

    @Select("SELECT ar_id AS arId FROM fn_receivable WHERE request_key=#{key}")
    Long receivableByRequest(@Param("key") String key);

    @Select("SELECT ar_id AS arId,balance FROM fn_receivable WHERE ar_id=#{id} FOR UPDATE")
    Map<String,Object> lockReceivable(@Param("id") Long id);

    @Select("""
        SELECT ar_id AS arId,balance FROM fn_receivable
        WHERE order_id=#{orderId} AND status IN (0,1) ORDER BY ar_id DESC LIMIT 1 FOR UPDATE
        """)
    Map<String,Object> latestReceivable(@Param("orderId") Long orderId);

    @Update("""
        UPDATE fn_receivable SET status=CASE WHEN balance-#{amount}<=0 THEN 2 ELSE 1 END,
          received_amount=received_amount+#{amount},balance=balance-#{amount},update_time=NOW()
        WHERE ar_id=#{id} AND balance>=#{amount}
        """)
    int settleReceivable(@Param("id") Long id,@Param("amount") BigDecimal amount);

    @Insert("""
        INSERT IGNORE INTO fn_payable(ap_no,supplier_id,po_id,amount,paid_amount,balance,
          due_date,status,request_key,create_by,create_time)
        VALUES(#{apNo},#{supplierId},#{poId},#{amount},0,#{amount},#{dueDate},0,#{requestKey},#{username},NOW())
        """)
    @Options(useGeneratedKeys=true,keyProperty="apId")
    int insertPayable(Map<String,Object> row);

    @Update("UPDATE fn_payable SET ap_no=#{no} WHERE ap_id=#{id}")
    int setApNo(@Param("id") Long id,@Param("no") String no);

    @Select("SELECT ap_id AS apId FROM fn_payable WHERE request_key=#{key}")
    Long payableByRequest(@Param("key") String key);

    @Select("SELECT ap_id AS apId FROM fn_payable WHERE po_id=#{poId} ORDER BY ap_id LIMIT 1")
    Long payableByPo(@Param("poId") Long poId);

    @Select("SELECT ap_id AS apId,balance FROM fn_payable WHERE ap_id=#{id} FOR UPDATE")
    Map<String,Object> lockPayable(@Param("id") Long id);

    @Update("""
        UPDATE fn_payable SET status=CASE WHEN balance-#{amount}<=0 THEN 2 ELSE 1 END,
          paid_amount=paid_amount+#{amount},balance=balance-#{amount},update_time=NOW()
        WHERE ap_id=#{id} AND balance>=#{amount}
        """)
    int settlePayable(@Param("id") Long id,@Param("amount") BigDecimal amount);

    @Select("""
        SELECT CAST(p.payment_id AS CHAR) AS id,p.payment_no AS code,p.direction,p.biz_type AS bizType,
               a.account_name AS accountName,p.amount,p.pay_date AS payDate,p.status,p.remark
        FROM fn_payment p JOIN fn_account a ON a.account_id=p.account_id
        ORDER BY p.payment_id DESC
        """)
    List<Map<String,Object>> payments();

    @Select("""
        SELECT line_id AS lineId,target_type AS targetType,CAST(target_id AS CHAR) AS targetId,amount
        FROM fn_payment_line WHERE payment_id=#{id} ORDER BY line_id
        """)
    List<Map<String,Object>> paymentLines(@Param("id") Long id);

    @Insert("""
        INSERT INTO fn_payment(payment_no,direction,biz_type,account_id,counterparty_type,counterparty_id,
          amount,pay_date,status,request_key,remark,create_by,create_time)
        VALUES(#{paymentNo},#{direction},#{bizType},#{accountId},#{counterpartyType},#{counterpartyId},
          #{amount},#{payDate},0,#{requestKey},#{remark},#{username},NOW())
        """)
    @Options(useGeneratedKeys=true,keyProperty="paymentId")
    int insertPayment(Map<String,Object> row);

    @Update("UPDATE fn_payment SET payment_no=#{no} WHERE payment_id=#{id}")
    int setPaymentNo(@Param("id") Long id,@Param("no") String no);

    @Insert("""
        INSERT INTO fn_payment_line(payment_id,target_type,target_id,amount)
        VALUES(#{paymentId},#{targetType},#{targetId},#{amount})
        """)
    int insertPaymentLine(@Param("paymentId") Long paymentId,@Param("targetType") Integer targetType,
                          @Param("targetId") Long targetId,@Param("amount") BigDecimal amount);

    @Select("""
        SELECT payment_id AS paymentId,direction,biz_type AS bizType,account_id AS accountId,
               counterparty_type AS counterpartyType,counterparty_id AS counterpartyId,
               amount,pay_date AS payDate,status,remark
        FROM fn_payment WHERE payment_id=#{id} FOR UPDATE
        """)
    Map<String,Object> lockPayment(@Param("id") Long id);

    @Update("UPDATE fn_payment SET status=1,confirm_by=#{username},confirm_time=NOW() WHERE payment_id=#{id} AND status=0")
    int confirmPayment(@Param("id") Long id,@Param("username") String username);

    @Select("""
        SELECT COALESCE(SUM(si.qty*oi.unit_price),0)
        FROM sh_shipment_item si
        JOIN sh_shipment sh ON sh.shipment_id=si.shipment_id
        JOIN md_sku sku ON sku.sku_id=si.sku_id
        JOIN sd_order_item oi ON oi.order_id=sh.order_id AND oi.style_id=sku.style_id
          AND oi.color_id=sku.color_id AND oi.size_id=sku.size_id
        WHERE si.shipment_id=#{shipmentId}
        """)
    BigDecimal shipmentAmount(@Param("shipmentId") Long shipmentId);

    @Select("SELECT credit_days FROM md_customer WHERE customer_id=#{id}")
    Integer customerCreditDays(@Param("id") Long id);

    @Select("SELECT credit_days FROM md_supplier WHERE supplier_id=#{id}")
    Integer supplierCreditDays(@Param("id") Long id);

    @Select("SELECT COALESCE(SUM(balance),0) FROM fn_receivable WHERE customer_id=#{id} AND status IN (0,1)")
    BigDecimal outstandingReceivable(@Param("id") Long customerId);

    @Select("""
        SELECT
          (SELECT COALESCE(SUM(current_balance),0) FROM fn_account WHERE status=1) AS cashBalance,
          (SELECT COALESCE(SUM(amount),0) FROM fn_cash_txn WHERE direction=1 AND txn_date>=DATE_FORMAT(CURDATE(),'%Y-%m-01')) AS monthIn,
          (SELECT COALESCE(SUM(amount),0) FROM fn_cash_txn WHERE direction=2 AND txn_date>=DATE_FORMAT(CURDATE(),'%Y-%m-01')) AS monthOut,
          (SELECT COALESCE(SUM(balance),0) FROM fn_receivable WHERE status IN (0,1)) AS receivableBalance,
          (SELECT COALESCE(SUM(balance),0) FROM fn_payable WHERE status IN (0,1)) AS payableBalance,
          (SELECT COUNT(*) FROM fn_receivable WHERE status IN (0,1) AND due_date<CURDATE()) AS receivableOverdue,
          (SELECT COUNT(*) FROM fn_payable WHERE status IN (0,1) AND due_date<CURDATE()) AS payableOverdue
        """)
    Map<String,Object> summary();

    /**
     * 账龄汇总：应收 / 应付各自的「未到期、1-30、31-60、61-90、90 以上」分段余额。
     * 只统计未结清单据（status IN (0,1)），未设到期日的按未到期处理。
     */
    @Select("""
        SELECT 'receivable' AS category, '应收' AS categoryName,
               COALESCE(SUM(CASE WHEN d <= 0 THEN bal ELSE 0 END), 0) AS notDue,
               COALESCE(SUM(CASE WHEN d >= 1 AND d <= 30 THEN bal ELSE 0 END), 0) AS days1to30,
               COALESCE(SUM(CASE WHEN d >= 31 AND d <= 60 THEN bal ELSE 0 END), 0) AS days31to60,
               COALESCE(SUM(CASE WHEN d >= 61 AND d <= 90 THEN bal ELSE 0 END), 0) AS days61to90,
               COALESCE(SUM(CASE WHEN d > 90 THEN bal ELSE 0 END), 0) AS over90,
               COALESCE(SUM(bal), 0) AS total
        FROM (
          SELECT balance AS bal, COALESCE(DATEDIFF(CURDATE(), due_date), 0) AS d
          FROM fn_receivable WHERE status IN (0, 1)
        ) x
        UNION ALL
        SELECT 'payable' AS category, '应付' AS categoryName,
               COALESCE(SUM(CASE WHEN d <= 0 THEN bal ELSE 0 END), 0) AS notDue,
               COALESCE(SUM(CASE WHEN d >= 1 AND d <= 30 THEN bal ELSE 0 END), 0) AS days1to30,
               COALESCE(SUM(CASE WHEN d >= 31 AND d <= 60 THEN bal ELSE 0 END), 0) AS days31to60,
               COALESCE(SUM(CASE WHEN d >= 61 AND d <= 90 THEN bal ELSE 0 END), 0) AS days61to90,
               COALESCE(SUM(CASE WHEN d > 90 THEN bal ELSE 0 END), 0) AS over90,
               COALESCE(SUM(bal), 0) AS total
        FROM (
          SELECT balance AS bal, COALESCE(DATEDIFF(CURDATE(), due_date), 0) AS d
          FROM fn_payable WHERE status IN (0, 1)
        ) y
        """)
    List<Map<String,Object>> agingSummary();

    /** 月度现金流：按月的流入 / 流出 / 净流量。 */
    @Select("""
        SELECT period, inflow, outflow, net_flow AS netFlow
        FROM v_cash_flow_monthly
        ORDER BY period DESC
        """)
    List<Map<String,Object>> cashFlowMonthly();
}
