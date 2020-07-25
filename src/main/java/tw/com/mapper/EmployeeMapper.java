package tw.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import io.swagger.annotations.ApiModelProperty;
import tw.com.entity.Department;
import tw.com.entity.Employee;
import tw.com.vo.EmployeeVo;
import tw.com.vo.QueryEmployeeConditionVo;

@Mapper
public interface EmployeeMapper
{
    @Select("SELECT * FROM Employee WHERE eid = #{eid} ")
    Employee findByEid(@Param("eid") int eid);

    @Select("SELECT * FROM Employee order by eid ")
    List<Employee> findAll(@Param("eid") int eid);

    @Select(
    {
            "<script>",
            "select e.*,d.dName ",
            "from Employee e ,Department d ",
            "where 1=1 ",
            "and e.isLeaveType = 'N' ",
            "and e.departmentId = d.did ",
            "<if test=\" queryEid != null and queryEid != '' \">",
            "   and  e.eid=#{queryEid} ",
            "</if>",
            "<if test=\" queryEname != null and queryEname != '' \">",
            "   and e.eName like CONCAT(CONCAT('%', #{queryEname}),'%') ", 
            "</if>",
            "<if test=\" queryDepartmentName != null and queryDepartmentName != '' \">",
            "   and  d.dName=#{queryDepartmentName} ",
            "</if>",
            "<if test=\" queryAge != null and queryAge != '' \">",
            "   and  e.age=#{queryAge} ",
            "</if>",
            "order by e.eid ",
      
            "OFFSET #{offsetSize} ROWS ",
            "FETCH NEXT #{eachPageSize} ROWS ONLY ",
            
            "</script>"
    })
    List<EmployeeVo> findByQueryEmployeeCondition(QueryEmployeeConditionVo conditionVo);

    @Insert("INSERT INTO Employee (eName, departmentId, gender, phone, address, age, createTime, lastModifyTime, isLeaveType ) "
        + "VALUES (#{eName}, #{departmentId}, #{gender}, #{phone}, #{address}, #{age}, #{createTime}, #{lastModifyTime}, #{isLeaveType} )")
    int addEmployee(Employee employee);

    @Update("Update Employee set eName = #{eName}, departmentId = #{departmentId}, gender = #{gender}, phone = #{phone}, address = #{address},"
        + " age = #{age}, lastModifyTime = #{lastModifyTime} where eid = #{eid} ")
    int updEmployeeByEid(Employee employee);

    @Update("Update Employee set isLeaveType = 'Y', lastModifyTime = #{lastModifyTime} where eid = #{eid} ")
    int updIsLeaveTypeByEid(@Param("lastModifyTime") String lastModifyTime, @Param("eid") int eid);

    @Delete("delete from Employee ")
    int deleteAllData(@Param("eid") int eid);
}
