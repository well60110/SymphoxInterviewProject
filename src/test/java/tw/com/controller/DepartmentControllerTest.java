package tw.com.controller;

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

import tw.com.entity.Department;
import tw.com.mapper.DepartmentMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DepartmentControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    DepartmentMapper departmentMapper;

    private HttpHeaders httpHeaders;

    @Before
    public void init()
    {
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");

        Department department = new Department();
        department.setDid("01");
        department.setdName("人資部門");
        departmentMapper.addDepartment(department);

    }

    @After
    public void clear()
    {
        departmentMapper.deleteAllData(0);
    }

    @Test
    public void testAddDepartment() throws Exception
    {

        JSONObject request = new JSONObject();
        request.put("did", "02");
        request.put("dName", "資訊科技部門");

        RequestBuilder requestBuilder =
            MockMvcRequestBuilders.post("/addDepartment").headers(httpHeaders).content(request.toString());

        ResultActions resultActions = this.mockMvc.perform(requestBuilder);

        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.ResponseCode").value("00000"))
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testUpdDepartment() throws Exception
    {

        JSONObject request = new JSONObject();
        request.put("dName", "資訊科技部門");

        String did = "01";

        RequestBuilder requestBuilder =
            MockMvcRequestBuilders.put("/updDepartment/" + did).headers(httpHeaders).content(request.toString());

        ResultActions resultActions = this.mockMvc.perform(requestBuilder);

        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.ResponseCode").value("00000"))
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testDelDepartment() throws Exception
    {

        String did = "01";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/delDepartment/" + did).headers(httpHeaders);

        ResultActions resultActions = this.mockMvc.perform(requestBuilder);

        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.ResponseCode").value("00000"))
            .andDo(MockMvcResultHandlers.print());
    }
}
