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
public interface ErpSalaryMapper {
    @Select("SELECT emp_id AS empId,base_salary AS baseSalary,status FROM hr_employee WHERE emp_id=#{id} FOR UPDATE")
    Map<String,Object> employee(@Param("id") Long id);

    @Select("SELECT COALESCE(SUM(amount),0) FROM hr_piece_wage WHERE emp_id=#{empId} AND wage_period=#{period}")
    BigDecimal pieceTotal(@Param("empId") Long empId,@Param("period") String period);

    @Select("SELECT id,status,allowance AS allowance,overtime_pay AS overtimePay,si_personal AS siPersonal,fund_personal AS fundPersonal,tax,other_deduction AS otherDeduction,si_company AS siCompany,fund_company AS fundCompany FROM hr_salary_slip WHERE emp_id=#{empId} AND wage_period=#{period} FOR UPDATE")
    Map<String,Object> existing(@Param("empId") Long empId,@Param("period") String period);

    @Insert("INSERT INTO hr_salary_slip(emp_id,wage_period,base_salary,piece_amount,gross_pay,net_pay,company_cost,status,create_by,create_time) VALUES(#{empId},#{period},#{base},#{piece},#{gross},#{gross},#{gross},0,#{username},NOW())")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(Map<String,Object> row);

    @Update("UPDATE hr_salary_slip SET base_salary=#{base},piece_amount=#{piece},gross_pay=#{gross},net_pay=#{net},company_cost=#{companyCost} WHERE id=#{id} AND status=0")
    int refresh(Map<String,Object> row);

    @Select("SELECT id,emp_id AS empId,wage_period AS wagePeriod,piece_amount AS pieceAmount,status FROM hr_salary_slip WHERE id=#{id} FOR UPDATE")
    Map<String,Object> lockSlip(@Param("id") Long id);

    @Select("SELECT emp_id AS empId FROM hr_salary_slip WHERE id=#{id}")
    Long slipEmployee(@Param("id") Long id);

    @Update("UPDATE hr_salary_slip SET status=1 WHERE id=#{id} AND status=0")
    int confirm(@Param("id") Long id);

    @Update("UPDATE hr_salary_slip SET status=0 WHERE id=#{id} AND status=1")
    int voidSlip(@Param("id") Long id);

    @Select("SELECT id,emp_id AS empId,piece_amount AS pieceAmount,status FROM hr_salary_slip WHERE wage_period=#{period} AND status=0 ORDER BY id FOR UPDATE")
    List<Map<String,Object>> draftSlips(@Param("period") String period);

    @Select("SELECT e.name AS empName FROM hr_employee e WHERE e.emp_id=#{id}")
    String employeeName(@Param("id") Long id);

    @Update("UPDATE hr_piece_wage SET status=1 WHERE emp_id=#{empId} AND wage_period=#{period} AND status=0")
    int markWagesPaid(@Param("empId") Long empId,@Param("period") String period);
}
