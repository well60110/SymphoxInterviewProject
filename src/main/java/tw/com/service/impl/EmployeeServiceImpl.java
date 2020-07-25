package tw.com.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.entity.Employee;
import tw.com.mapper.EmployeeMapper;
import tw.com.service.EmployeeService;
import tw.com.vo.EmployeeVo;
import tw.com.vo.QueryEmployeeConditionVo;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService
{
    static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Value("${eachPageSize}")
    int eachPageSize;

    @Autowired
    EmployeeMapper employeeMapper;

    @Override
    public Employee findByEid(int eid)
    {
        return employeeMapper.findByEid(eid);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int addEmployee(Employee employee)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date();
        employee.setCreateTime(sdf.format(date));
        employee.setLastModifyTime(sdf.format(date));
        employee.setIsLeaveType("N");
        log.info(employee.toString());

        int addCount = employeeMapper.addEmployee(employee);
        log.info("addCount : {}", addCount);
        return addCount;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updEmployeeByEid(Employee employee)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date();
        employee.setLastModifyTime(sdf.format(date));
        int updCount = employeeMapper.updEmployeeByEid(employee);
        log.info("updCount : {} ", updCount);
        return updCount;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int delEmployeeByEid(int eid)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date();
        String lastModifyTime = sdf.format(date);
        int updCount = employeeMapper.updIsLeaveTypeByEid(lastModifyTime, eid);
        log.info("updCount : {} ", updCount);
        return updCount;
    }

    @Override
    public List<EmployeeVo> findByQueryEmployeeCondition(QueryEmployeeConditionVo conditionVo)
    {
        int offsetSize = 0;
        if (conditionVo.getQueryPage() > 1)
            offsetSize = ((conditionVo.getQueryPage() - 1) * eachPageSize);

        conditionVo.setOffsetSize(offsetSize);
        conditionVo.setEachPageSize(eachPageSize);

        List<EmployeeVo> employeeVoList = employeeMapper.findByQueryEmployeeCondition(conditionVo);
        log.info(employeeVoList.toString());
        return employeeVoList;
    }

}
