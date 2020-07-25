package tw.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import tw.com.entity.Department;

@Mapper
public interface DepartmentMapper
{
    @Select("SELECT * FROM Department WHERE did = #{did} ")
    Department findByDid(@Param("did") String did);

    @Select("SELECT * FROM Department ")
    List<Department> findAll(@Param("did") String did);

    @Insert("INSERT INTO Department (did, dName) VALUES (#{did}, #{dName} )")
    int addDepartment(Department department);

    @Update("Update Department set dName = #{dName} where did = #{did} ")
    int updDepartmentByDid(Department department);

    @Update("Update Department set isDelType = 'Y' where did = #{did} ")
    int updIsDelTypeByDid(@Param("did") String did);

    @Delete("delete from Department ")
    int deleteAllData(@Param("did") int did);
}
