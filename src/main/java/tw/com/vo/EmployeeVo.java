package tw.com.vo;

import io.swagger.annotations.ApiModelProperty;
import tw.com.entity.Employee;

public class EmployeeVo extends Employee
{
    @ApiModelProperty(value = "部門名稱", required = true)
    private String dName;

    public String getdName()
    {
        return dName;
    }

    public void setdName(String dName)
    {
        this.dName = dName;
    }

    @Override
    public String toString()
    {
        return "EmployeeVo [dName=" + dName + ", toString()=" + super.toString() + "]";
    }

}
