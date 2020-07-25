package tw.com.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.entity.Department;
import tw.com.mapper.DepartmentMapper;
import tw.com.service.DepartmentService;

@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService
{
    static final Logger log = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Autowired
    DepartmentMapper departmentMapper;

    @Override
    public Department findByDid(String did)
    {
        return departmentMapper.findByDid(did);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int addDepartment(Department department)
    {
        int addCount = departmentMapper.addDepartment(department);
        log.info("addCount : {}", addCount);
        return addCount;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updDepartmentByDid(Department department)
    {
        int updCount = departmentMapper.updDepartmentByDid(department);
        log.info("updCount : {} ", updCount);
        return updCount;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int delDepartmentByDid(String did)
    {
        int updCount = departmentMapper.updIsDelTypeByDid(did);
        log.info("updCount : {} ", updCount);
        return updCount;
    }

    @Override
    public List<Department> findAll()
    {
        List<Department> departmentList = departmentMapper.findAll("");
        log.info(departmentList.toString());
        return departmentList;
    }

}
