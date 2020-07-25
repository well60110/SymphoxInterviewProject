package tw.com.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "員工資料表")
public class Employee
{
    @ApiModelProperty(value = "員工編號", required = true)
    @Pattern(regexp = "^[0-9]*$", message = "格式錯誤")
    @Length(min = 0, max = 4, message = "內容過長")
    private String eid;

    @ApiModelProperty(value = "姓名", required = true)
    @NotBlank(message = "姓名不得為空")
    @Length(min = 0, max = 50, message = "內容過長")
    private String eName;

    @ApiModelProperty(value = "部門ID", required = true)
    @NotBlank(message = "部門ID不得為空")
    @Length(min = 0, max = 4, message = "內容過長")
    private String departmentId;

    @ApiModelProperty(value = "性別", required = true)
    @NotBlank(message = "性別不得為空")
    @Pattern(regexp = "^[男|女]*$", message = "格式錯誤")
    @Length(min = 0, max = 1, message = "內容過長")
    private String gender;

    @ApiModelProperty(value = "電話", required = true)
    @Pattern(regexp = "^[0-9]*$", message = "格式錯誤")
    @Length(min = 0, max = 20, message = "內容過長")
    private String phone;

    @ApiModelProperty(value = "地址", required = true)
    @Length(min = 0, max = 200, message = "內容過長")
    private String address;

    @ApiModelProperty(value = "年齡", required = true)
    @Pattern(regexp = "^[0-9]*$", message = "格式錯誤")
    @Length(min = 0, max = 3, message = "內容過長")
    private String age;

    @ApiModelProperty(value = "建立時間", required = true)
    private String createTime;

    @ApiModelProperty(value = "最後一次修改時間", required = true)
    private String lastModifyTime;

    @ApiModelProperty(value = "離職狀態", required = true)
    private String isLeaveType;

    public String getEid()
    {
        return eid;
    }

    public void setEid(String eid)
    {
        this.eid = eid;
    }

    public String geteName()
    {
        return eName;
    }

    public void seteName(String eName)
    {
        this.eName = eName;
    }

    public String getDepartmentId()
    {
        return departmentId;
    }

    public void setDepartmentId(String departmentId)
    {
        this.departmentId = departmentId;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAge()
    {
        return age;
    }

    public void setAge(String age)
    {
        this.age = age;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }

    public String getLastModifyTime()
    {
        return lastModifyTime;
    }

    public void setLastModifyTime(String lastModifyTime)
    {
        this.lastModifyTime = lastModifyTime;
    }

    public String getIsLeaveType()
    {
        return isLeaveType;
    }

    public void setIsLeaveType(String isLeaveType)
    {
        this.isLeaveType = isLeaveType;
    }

    @Override
    public String toString()
    {
        return "Employee [eid=" + eid + ", eName=" + eName + ", departmentId=" + departmentId + ", gender=" + gender
            + ", phone=" + phone + ", address=" + address + ", age=" + age + ", createTime=" + createTime
            + ", lastModifyTime=" + lastModifyTime + ", isLeaveType=" + isLeaveType + "]";
    }

}
