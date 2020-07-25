package tw.com.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import io.swagger.annotations.ApiModelProperty;
import tw.com.entity.Department;
import tw.com.entity.Employee;
import tw.com.mapper.DepartmentMapper;
import tw.com.mapper.EmployeeMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    DepartmentMapper departmentMapper;

    private HttpHeaders httpHeaders;

    @Before
    public void init()
    {
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");

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
        JSONObject request = new JSONObject();
        request.put("eName", "測試姓名A");
        request.put("departmentId", "01");
        request.put("gender", "男");
        request.put("phone", "0912345678");
        request.put("address", "XX市XX區XX路XX巷XX號");
        request.put("age", "30");

        RequestBuilder requestBuilder =
            MockMvcRequestBuilders.post("/addEmployee").headers(httpHeaders).content(request.toString());

        ResultActions resultActions = this.mockMvc.perform(requestBuilder);

        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.ResponseCode").value("00000"))
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testUpdEmployee() throws Exception
    {
        JSONObject request = new JSONObject();
        request.put("eName", "測試姓名A");
        request.put("departmentId", "03");
        request.put("gender", "男");
        request.put("phone", "0912345678");
        request.put("address", "XX市XX區XX路XX巷XX號");
        request.put("age", "50");

        String eid = employeeMapper.findAll(0).get(0).getEid();

        RequestBuilder requestBuilder =
            MockMvcRequestBuilders.put("/updEmployee/" + eid).headers(httpHeaders).content(request.toString());

        ResultActions resultActions = this.mockMvc.perform(requestBuilder);

        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.ResponseCode").value("00000"))
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testDelEmployee() throws Exception
    {
        String eid = employeeMapper.findAll(0).get(0).getEid();

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/delEmployee/" + eid).headers(httpHeaders);

        ResultActions resultActions = this.mockMvc.perform(requestBuilder);

        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.ResponseCode").value("00000"))
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testEmployees() throws Exception
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
        employeeMapper.addEmployee(employee);

        JSONObject request = new JSONObject();
        request.put("queryEid", "");
        request.put("queryEname", "測試篩選姓名");
        request.put("queryDepartmentName", "");
        request.put("queryAge", "");
        request.put("queryPage", "1");

        RequestBuilder requestBuilder =
            MockMvcRequestBuilders.post("/employees/").headers(httpHeaders).content(request.toString());

        ResultActions resultActions = this.mockMvc.perform(requestBuilder);

        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].eName").value(employee.geteName()))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].departmentId").value(employee.getDepartmentId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value(employee.getGender()))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].phone").value(employee.getPhone()))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].address").value(employee.getAddress()))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(employee.getAge()))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].isLeaveType").value(employee.getIsLeaveType()))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].dName").value("資訊科技部門"))
            .andDo(MockMvcResultHandlers.print());
    }
}
