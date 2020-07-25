package tw.com.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.annotations.ApiOperation;
import tw.com.entity.Department;
import tw.com.entity.Employee;
import tw.com.service.DepartmentService;
import tw.com.service.EmployeeService;
import tw.com.vo.EmployeeVo;
import tw.com.vo.QueryEmployeeConditionVo;

@RestController
public class EmployeeController
{
    static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    EmployeeService employeeService;

    @Autowired
    DepartmentService departmentService;

    @ApiOperation(value = "新增員工資料", httpMethod = "POST")
    @PostMapping(value = "addEmployee")
    ResponseEntity<Map<String, String>> addEmployee(@Validated @RequestBody Employee employee,
        BindingResult bindingResult)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Map<String, String> resMap = new HashMap<>();
        Date date = new Date();
        try
        {
            if (bindingResult.hasErrors())
            {
                Map<String, String> errDataMap = new HashMap<>();
                for (FieldError fieldError : bindingResult.getFieldErrors())
                {
                    errDataMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                }

                log.info("json data error : {}", errDataMap.toString());

                resMap.put("ResponseCode", "10020");
                resMap.put("ActionTime", sdf.format(date));
                resMap.put("ResponseMessage", "JSON格式錯誤 - " + errDataMap.toString());
                return new ResponseEntity<Map<String, String>>(resMap, HttpStatus.OK);
            }

            Department department = departmentService.findByDid(employee.getDepartmentId());
            if (department == null)
            {
                resMap.put("ResponseCode", "10040");
                resMap.put("ActionTime", sdf.format(date));
                resMap.put("ResponseMessage", "無此部門ID");
                return new ResponseEntity<Map<String, String>>(resMap, HttpStatus.OK);
            }

            int addCount = employeeService.addEmployee(employee);
            if (addCount == 0)
            {
                resMap.put("ResponseCode", "00010");
                resMap.put("ActionTime", sdf.format(date));
                resMap.put("ResponseMessage", "操作失敗");
                return new ResponseEntity<Map<String, String>>(resMap, HttpStatus.OK);
            }

            resMap.put("ResponseCode", "00000");
            resMap.put("ActionTime", sdf.format(date));
            resMap.put("ResponseMessage", "執行成功");

            return new ResponseEntity<Map<String, String>>(resMap, HttpStatus.OK);

        } catch (Exception e)
        {
            log.error("########## controller had Exception:" + e.getMessage());

            resMap.put("ResponseCode", "99999");
            resMap.put("ActionTime", sdf.format(date));
            resMap.put("ResponseMessage", "其他錯誤");
            return new ResponseEntity<Map<String, String>>(resMap, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "更新員工資料", httpMethod = "PUT")
    @PutMapping(value = "updEmployee/{eid}")
    ResponseEntity<Map<String, String>> updEmployee(@PathVariable("eid") int eid,
        @Validated @RequestBody Employee employee, BindingResult bindingResult)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Map<String, String> resMap = new HashMap<>();
        Date date = new Date();
        try
        {
            if (bindingResult.hasErrors())
            {
                Map<String, String> errDataMap = new HashMap<>();
                for (FieldError fieldError : bindingResult.getFieldErrors())
                {
                    errDataMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                }

                log.info("json data error : {}", errDataMap.toString());

                resMap.put("ResponseCode", "10020");
                resMap.put("ActionTime", sdf.format(date));
                resMap.put("ResponseMessage", "JSON格式錯誤 - " + errDataMap.toString());
                return new ResponseEntity<Map<String, String>>(resMap, HttpStatus.OK);
            }

            Department department = departmentService.findByDid(employee.getDepartmentId());
            if (department == null)
            {
                resMap.put("ResponseCode", "10040");
                resMap.put("ActionTime", sdf.format(date));
                resMap.put("ResponseMessage", "無此部門ID");
                return new ResponseEntity<Map<String, String>>(resMap, HttpStatus.OK);
            }

            Employee chkEmployee = employeeService.findByEid(eid);
            if (chkEmployee == null)
            {
                resMap.put("ResponseCode", "10060");
                resMap.put("ActionTime", sdf.format(date));
                resMap.put("ResponseMessage", "無此員工編號");
                return new ResponseEntity<Map<String, String>>(resMap, HttpStatus.OK);
            } else if ("Y".equals(chkEmployee.getIsLeaveType()))
            {
                resMap.put("ResponseCode", "10070");
                resMap.put("ActionTime", sdf.format(date));
                resMap.put("ResponseMessage", "員工已離職");
                return new ResponseEntity<Map<String, String>>(resMap, HttpStatus.OK);
            }

            employee.setEid(String.valueOf(eid));
            int updCount = employeeService.updEmployeeByEid(employee);
            if (updCount == 0)
            {
                resMap.put("ResponseCode", "00010");
                resMap.put("ActionTime", sdf.format(date));
                resMap.put("ResponseMessage", "操作失敗");
                return new ResponseEntity<Map<String, String>>(resMap, HttpStatus.OK);
            }
            resMap.put("ResponseCode", "00000");
            resMap.put("ActionTime", sdf.format(date));
            resMap.put("ResponseMessage", "執行成功");

            return new ResponseEntity<Map<String, String>>(resMap, HttpStatus.OK);

        } catch (Exception e)
        {
            log.error("########## controller had Exception:" + e.getMessage());

            resMap.put("ResponseCode", "99999");
            resMap.put("ActionTime", sdf.format(date));
            resMap.put("ResponseMessage", "其他錯誤");
            return new ResponseEntity<Map<String, String>>(resMap, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "刪除員工資料", httpMethod = "DELETE")
    @DeleteMapping(value = "delEmployee/{eid}")
    ResponseEntity<Map<String, String>> delEmployee(@PathVariable("eid") int eid)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Map<String, String> resMap = new HashMap<>();
        Date date = new Date();
        try
        {
            Employee chkEmployee = employeeService.findByEid(eid);
            if (chkEmployee == null)
            {
                resMap.put("ResponseCode", "10060");
                resMap.put("ActionTime", sdf.format(date));
                resMap.put("ResponseMessage", "無此員工編號");
                return new ResponseEntity<Map<String, String>>(resMap, HttpStatus.OK);
            } else if ("Y".equals(chkEmployee.getIsLeaveType()))
            {
                resMap.put("ResponseCode", "10070");
                resMap.put("ActionTime", sdf.format(date));
                resMap.put("ResponseMessage", "員工已離職");
                return new ResponseEntity<Map<String, String>>(resMap, HttpStatus.OK);
            }

            int updCount = employeeService.delEmployeeByEid(eid);

            if (updCount == 0)
            {
                resMap.put("ResponseCode", "00010");
                resMap.put("ActionTime", sdf.format(date));
                resMap.put("ResponseMessage", "操作失敗");
                return new ResponseEntity<Map<String, String>>(resMap, HttpStatus.OK);
            }

            resMap.put("ResponseCode", "00000");
            resMap.put("ActionTime", sdf.format(date));
            resMap.put("ResponseMessage", "執行成功");

            return new ResponseEntity<Map<String, String>>(resMap, HttpStatus.OK);

        } catch (Exception e)
        {
            log.error("########## controller had Exception:" + e.getMessage());

            resMap.put("ResponseCode", "99999");
            resMap.put("ActionTime", sdf.format(date));
            resMap.put("ResponseMessage", "其他錯誤");
            return new ResponseEntity<Map<String, String>>(resMap, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "查詢員工資料", httpMethod = "POST")
    @PostMapping(value = "employees")
    ResponseEntity<List<EmployeeVo>> employees(@RequestBody QueryEmployeeConditionVo conditionVo,
        UriComponentsBuilder ucBuilder)
    {

        log.info(conditionVo.toString());

        List<EmployeeVo> employeeList = employeeService.findByQueryEmployeeCondition(conditionVo);

        return new ResponseEntity<List<EmployeeVo>>(employeeList, HttpStatus.OK);

    }

}
