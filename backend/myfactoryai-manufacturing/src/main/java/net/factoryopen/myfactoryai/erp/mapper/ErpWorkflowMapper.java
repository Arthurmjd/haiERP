package net.factoryopen.myfactoryai.erp.mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ErpWorkflowMapper {
    @Insert("""
        INSERT IGNORE INTO biz_todo(todo_type,target_type,target_id,assignee_role,title,path,due_date,status,create_time)
        VALUES(#{todoType},#{targetType},#{targetId},#{role},#{title},#{path},#{dueDate},0,NOW())
        """)
    int insertTodo(@Param("todoType") String todoType,@Param("targetType") String targetType,
                   @Param("targetId") Long targetId,@Param("role") String role,
                   @Param("title") String title,@Param("path") String path,
                   @Param("dueDate") LocalDate dueDate);

    @Select("""
        SELECT todo_id AS todoId,todo_type AS todoType,target_type AS targetType,
               target_id AS targetId,assignee_role AS assigneeRole,title,path,
               due_date AS dueDate,create_time AS createTime
        FROM biz_todo WHERE status=0 ORDER BY todo_id DESC
        """)
    List<Map<String,Object>> openTodos();

    @Update("""
        UPDATE biz_todo SET status=1,done_by=#{username},done_time=NOW()
        WHERE todo_type=#{todoType} AND target_type=#{targetType} AND target_id=#{targetId} AND status=0
        """)
    int completeTodo(@Param("todoType") String todoType,@Param("targetType") String targetType,
                     @Param("targetId") Long targetId,@Param("username") String username);

    @Insert("""
        INSERT INTO biz_approval(doc_type,doc_id,approval_type,amount,applicant,status,create_time)
        VALUES(#{docType},#{docId},#{approvalType},#{amount},#{applicant},0,NOW())
        """)
    int insertApproval(@Param("docType") String docType,@Param("docId") Long docId,
                       @Param("approvalType") String approvalType,@Param("amount") java.math.BigDecimal amount,
                       @Param("applicant") String applicant);

    @Select("""
        SELECT approval_id AS approvalId,doc_type AS docType,doc_id AS docId,
               approval_type AS approvalType,amount,applicant,approver,status,comment,
               create_time AS createTime,approve_time AS approveTime
        FROM biz_approval WHERE status=0 ORDER BY approval_id DESC
        """)
    List<Map<String,Object>> pendingApprovals();

    @Select("""
        SELECT approval_id AS approvalId,status FROM biz_approval
        WHERE doc_type=#{docType} AND doc_id=#{docId} AND status=0
        ORDER BY approval_id DESC LIMIT 1 FOR UPDATE
        """)
    Map<String,Object> lockApproval(@Param("docType") String docType,@Param("docId") Long docId);

    @Update("""
        UPDATE biz_approval SET status=#{status},approver=#{approver},comment=#{comment},approve_time=NOW()
        WHERE approval_id=#{id} AND status=0
        """)
    int decideApproval(@Param("id") Long id,@Param("status") int status,
                       @Param("approver") String approver,@Param("comment") String comment);

    @Select("SELECT status FROM biz_period WHERE period=#{period}")
    Integer periodStatus(@Param("period") String period);

    @Insert("""
        INSERT INTO biz_period(period,status,closed_by,closed_time)
        VALUES(#{period},1,#{username},NOW())
        ON DUPLICATE KEY UPDATE status=1,closed_by=VALUES(closed_by),closed_time=NOW(),
          reopened_by='',reopened_time=NULL
        """)
    int closePeriod(@Param("period") String period,@Param("username") String username);

    @Update("""
        UPDATE biz_period SET status=0,reopened_by=#{username},reopened_time=NOW()
        WHERE period=#{period} AND status=1
        """)
    int reopenPeriod(@Param("period") String period,@Param("username") String username);

    @Select("""
        SELECT period,status,closed_by AS closedBy,closed_time AS closedTime,
               reopened_by AS reopenedBy,reopened_time AS reopenedTime
        FROM biz_period ORDER BY period DESC
        """)
    List<Map<String,Object>> periods();

    @Select("SELECT config_value FROM sys_config WHERE config_key=#{key} LIMIT 1")
    String configValue(@Param("key") String key);
}
