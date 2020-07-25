package tw.com.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tw.com.entity.Department;
import tw.com.mapper.DepartmentMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartmentServiceTest
{

    @Autowired
    DepartmentService departmentService;

    @Autowired
    DepartmentMapper departmentMapper;

    @Before
    public void init()
    {
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
        Department department = new Department();
        department.setDid("02");
        department.setdName("資訊科技部門");
        int addCount = departmentService.addDepartment(department);

        Department dbDepartment = departmentMapper.findByDid(department.getDid());
        Assert.assertEquals(addCount, 1);
        Assert.assertEquals(dbDepartment.getDid(), department.getDid());
        Assert.assertEquals(dbDepartment.getdName(), department.getdName());
    }

    @Test
    public void testUpdDepartmentByDid() throws Exception
    {
        Department department = new Department();
        department.setDid("01");
        department.setdName("資訊科技部門");
        int updCount = departmentService.updDepartmentByDid(department);

        Department dbDepartment = departmentMapper.findByDid(department.getDid());
        Assert.assertEquals(updCount, 1);
        Assert.assertEquals(dbDepartment.getDid(), department.getDid());
        Assert.assertEquals(dbDepartment.getdName(), department.getdName());
    }

    @Test
    public void testDelDepartmentByDid() throws Exception
    {
        String did = "01";
        int updCount = departmentService.delDepartmentByDid(did);

        Department dbDepartment = departmentMapper.findByDid(did);
        Assert.assertEquals(updCount, 1);
        Assert.assertEquals(dbDepartment.getIsDelType(), "Y");
    }
}
