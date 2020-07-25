package tw.com.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tw.com.entity.Department;
import tw.com.entity.Employee;
import tw.com.mapper.DepartmentMapper;
import tw.com.mapper.EmployeeMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTest
{

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    DepartmentMapper departmentMapper;

    @Before
    public void init()
    {
        departmentMapper.addDepartment(new Department("01", "人資部門"));
        departmentMapper.addDepartment(new Department("02", "資訊科技部門"));
        departmentMapper.addDepartment(new Department("03", "研發部"));
        departmentMapper.addDepartment(new Department("04", "公關部門"));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date();
        Employee employee = new Employee();
        employee.seteName("測試姓名A");
        employee.setDepartmentId("01");
        employee.setGender("男");
        employee.setPhone("0912345678");
        employee.setAddress("XX市XX區XX路XX巷XX號");
        employee.setAge("30");
        employee.setCreateTime(sdf.format(date));
        employee.setLastModifyTime(sdf.format(date));
        employee.setIsLeaveType("N");
        employeeMapper.addEmployee(employee);
    }

    @After
    public void clear()
    {
        employeeMapper.deleteAllData(0);
        departmentMapper.deleteAllData(0);
    }

    @Test
    public void testAddEmployee() throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date();
        Employee employee = new Employee();
        employee.seteName("測試篩選姓名");
        employee.setDepartmentId("02");
        employee.setGender("女");
        employee.setPhone("0912345678");
        employee.setAddress("XX市XX區XX路XX巷XX號");
        employee.setAge("30");
        employee.setCreateTime(sdf.format(date));
        employee.setLastModifyTime(sdf.format(date));
        employee.setIsLeaveType("N");

        int addCount = employeeService.addEmployee(employee);

        List<Employee> employeeList = employeeMapper.findAll(0);
        Employee dbEmployee = employeeList.get(employeeList.size() - 1);
        Assert.assertEquals(addCount, 1);
        Assert.assertEquals(dbEmployee.geteName(), employee.geteName());
        Assert.assertEquals(dbEmployee.getDepartmentId(), employee.getDepartmentId());
        Assert.assertEquals(dbEmployee.getGender(), employee.getGender());
        Assert.assertEquals(dbEmployee.getPhone(), employee.getPhone());
        Assert.assertEquals(dbEmployee.getAddress(), employee.getAddress());
        Assert.assertEquals(dbEmployee.getAge(), employee.getAge());

    }

    @Test
    public void testUpdEmployeeByEid() throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date();

        List<Employee> employeeList = employeeMapper.findAll(0);
        Employee tempEmployee = employeeList.get(employeeList.size() - 1);
        String eid = tempEmployee.getEid();
        Employee employee = new Employee();
        employee.setEid(eid);
        employee.seteName("測試篩選姓名");
        employee.setDepartmentId("02");
        employee.setGender("女");
        employee.setPhone("0912345678");
        employee.setAddress("XX市XX區XX路XX巷XX號");
        employee.setAge("30");
        employee.setCreateTime(sdf.format(date));
        employee.setLastModifyTime(sdf.format(date));
        employee.setIsLeaveType("N");
        int updCount = employeeService.updEmployeeByEid(employee);

        Employee dbEmployee = employeeMapper.findByEid(Integer.parseInt(eid));

        Assert.assertEquals(updCount, 1);
        Assert.assertEquals(dbEmployee.geteName(), employee.geteName());
        Assert.assertEquals(dbEmployee.getDepartmentId(), employee.getDepartmentId());
        Assert.assertEquals(dbEmployee.getGender(), employee.getGender());
        Assert.assertEquals(dbEmployee.getPhone(), employee.getPhone());
        Assert.assertEquals(dbEmployee.getAddress(), employee.getAddress());
        Assert.assertEquals(dbEmployee.getAge(), employee.getAge());

    }

    @Test
    public void testDelEmployeeByEid() throws Exception
    {
        List<Employee> employeeList = employeeMapper.findAll(0);
        Employee tempEmployee = employeeList.get(employeeList.size() - 1);
        String eid = tempEmployee.getEid();
        int updCount = employeeService.delEmployeeByEid(Integer.parseInt(eid));

        Employee dbEmployee = employeeMapper.findByEid(Integer.parseInt(eid));
        Assert.assertEquals(updCount, 1);
        Assert.assertEquals(dbEmployee.getIsLeaveType(), "Y");
    }
}
