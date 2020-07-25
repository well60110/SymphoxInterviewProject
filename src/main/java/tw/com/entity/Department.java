package tw.com.entity;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "部門資料表")
public class Department
{
    @ApiModelProperty(value = "部門ID", required = true)
    @Length(min = 0, max = 2, message = "內容過長")
    private String did;

    @ApiModelProperty(value = "部門名稱", required = true)
    @NotBlank(message = "部門名稱不得為空")
    @Length(min = 0, max = 50, message = "內容過長")
    private String dName;

    @ApiModelProperty(value = "刪除狀態", required = true)
    private String isDelType;

    public Department()
    {

    }

    public Department(String did, String dName)
    {
        this.did = did;
        this.dName = dName;
    }

    public String getDid()
    {
        return did;
    }

    public void setDid(String did)
    {
        this.did = did;
    }

    public String getdName()
    {
        return dName;
    }

    public void setdName(String dName)
    {
        this.dName = dName;
    }

    public String getIsDelType()
    {
        return isDelType;
    }

    public void setIsDelType(String isDelType)
    {
        this.isDelType = isDelType;
    }

    @Override
    public String toString()
    {
        return "Department [did=" + did + ", dName=" + dName + ", isDelType=" + isDelType + "]";
    }

}
