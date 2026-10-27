package net.factoryopen.myfactoryai.erp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ErpPayrollMapper {
    @Select("SELECT config_key AS configKey,config_value AS configValue FROM hr_payroll_config")
    List<Map<String,Object>> configs();

    @Select("SELECT emp_id AS empId,base_salary AS baseSalary,si_base AS siBase,status FROM hr_employee WHERE emp_id=#{id} FOR UPDATE")
    Map<String,Object> employeeForPay(@Param("id") Long id);

    @Select("SELECT emp_id FROM hr_employee WHERE status=1 ORDER BY emp_id")
    List<Long> activeEmployeeIds();

    @Select("SELECT id,emp_id AS empId,wage_period AS wagePeriod,net_pay AS netPay,status FROM hr_salary_slip WHERE id=#{id} FOR UPDATE")
    Map<String,Object> lockForPay(@Param("id") Long id);

    @Update("UPDATE hr_salary_slip SET status=2,paid_by=#{user},paid_time=NOW() WHERE id=#{id} AND status=1")
    int markPaid(@Param("id") Long id,@Param("user") String user);

    @Update("UPDATE hr_piece_wage SET status=1 WHERE emp_id=#{empId} AND wage_period=#{period} AND status=0")
    int markWagesPaid(@Param("empId") Long empId,@Param("period") String period);

    @Insert("""
        INSERT INTO hr_salary_slip(emp_id,wage_period,base_salary,allowance,piece_amount,overtime_pay,
          gross_pay,si_personal,fund_personal,tax,other_deduction,net_pay,si_company,fund_company,company_cost,
          status,create_by,create_time)
        VALUES(#{empId},#{period},#{base},#{allowance},#{piece},#{overtime},#{gross},#{siPersonal},
          #{fundPersonal},#{tax},#{otherDeduction},#{net},#{siCompany},#{fundCompany},#{companyCost},
          0,#{username},NOW())
        """)
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertFull(Map<String,Object> row);

    @Update("""
        UPDATE hr_salary_slip SET base_salary=#{base},allowance=#{allowance},piece_amount=#{piece},
          overtime_pay=#{overtime},gross_pay=#{gross},si_personal=#{siPersonal},fund_personal=#{fundPersonal},
          tax=#{tax},other_deduction=#{otherDeduction},net_pay=#{net},si_company=#{siCompany},
          fund_company=#{fundCompany},company_cost=#{companyCost}
        WHERE id=#{id} AND status=0
        """)
    int refreshFull(Map<String,Object> row);
}
