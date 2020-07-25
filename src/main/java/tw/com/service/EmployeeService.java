package tw.com.service;

import java.util.List;

import tw.com.entity.Employee;
import tw.com.vo.EmployeeVo;
import tw.com.vo.QueryEmployeeConditionVo;

public interface EmployeeService
{
    int addEmployee(Employee employee);

    Employee findByEid(int eid);

    int updEmployeeByEid(Employee employee);

    int delEmployeeByEid(int eid);

    List<EmployeeVo> findByQueryEmployeeCondition(QueryEmployeeConditionVo conditionVo);
}
