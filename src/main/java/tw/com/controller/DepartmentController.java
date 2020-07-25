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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import tw.com.entity.Department;
import tw.com.service.DepartmentService;

@RestController
public class DepartmentController
{
    static final Logger log = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    DepartmentService departmentService;

    @ApiOperation(value = "新增部門資料", httpMethod = "POST")
    @PostMapping(value = "addDepartment")
    ResponseEntity<Map<String, String>> addDepartment(@Validated @RequestBody Department department,
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

            Department chkDepartment = departmentService.findByDid(department.getDid());
            if (chkDepartment != null)
            {
                resMap.put("ResponseCode", "10030");
                resMap.put("ActionTime", sdf.format(date));
                resMap.put("ResponseMessage", "部門ID重複");
                return new ResponseEntity<Map<String, String>>(resMap, HttpStatus.OK);
            }

            int addCount = departmentService.addDepartment(department);

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

    @ApiOperation(value = "更新部門資料", httpMethod = "PUT")
    @PutMapping(value = "updDepartment/{did}")
    ResponseEntity<Map<String, String>> updDepartment(@PathVariable("did") String did,
        @Validated @RequestBody Department department, BindingResult bindingResult)
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

            Department chkDepartment = departmentService.findByDid(did);
            if (chkDepartment == null)
            {
                resMap.put("ResponseCode", "10040");
                resMap.put("ActionTime", sdf.format(date));
                resMap.put("ResponseMessage", "無此部門ID");
                return new ResponseEntity<Map<String, String>>(resMap, HttpStatus.OK);
            } else if ("Y".equals(chkDepartment.getIsDelType()))
            {
                resMap.put("ResponseCode", "10050");
                resMap.put("ActionTime", sdf.format(date));
                resMap.put("ResponseMessage", "部門撤銷");
                return new ResponseEntity<Map<String, String>>(resMap, HttpStatus.OK);
            }

            department.setDid(did);
            int updCount = departmentService.updDepartmentByDid(department);

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

    @ApiOperation(value = "刪除部門資料", httpMethod = "DELETE")
    @DeleteMapping(value = "delDepartment/{did}")
    ResponseEntity<Map<String, String>> delDepartment(@PathVariable("did") String did)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Map<String, String> resMap = new HashMap<>();
        Date date = new Date();
        try
        {
            Department chkDepartment = departmentService.findByDid(did);
            if (chkDepartment == null)
            {
                resMap.put("ResponseCode", "10040");
                resMap.put("ActionTime", sdf.format(date));
                resMap.put("ResponseMessage", "無此部門ID");
                return new ResponseEntity<Map<String, String>>(resMap, HttpStatus.OK);
            } else if ("Y".equals(chkDepartment.getIsDelType()))
            {
                resMap.put("ResponseCode", "10050");
                resMap.put("ActionTime", sdf.format(date));
                resMap.put("ResponseMessage", "部門撤銷");
                return new ResponseEntity<Map<String, String>>(resMap, HttpStatus.OK);
            }

            int updCount = departmentService.delDepartmentByDid(did);

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

    @ApiOperation(value = "查詢部門資料", httpMethod = "GET")
    @GetMapping(value = "department")
    ResponseEntity<List<Department>> departments()
    {

        List<Department> departmentList = departmentService.findAll();

        return new ResponseEntity<List<Department>>(departmentList, HttpStatus.OK);

    }

}
