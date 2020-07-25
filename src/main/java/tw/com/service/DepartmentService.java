package tw.com.service;

import java.util.List;

import tw.com.entity.Department;

public interface DepartmentService
{
    Department findByDid(String did);

    int addDepartment(Department department);

    int updDepartmentByDid(Department department);

    int delDepartmentByDid(String did);

    List<Department> findAll();
}
