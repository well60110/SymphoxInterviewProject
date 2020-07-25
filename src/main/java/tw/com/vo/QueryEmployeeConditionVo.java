package tw.com.vo;

import io.swagger.annotations.ApiModelProperty;

public class QueryEmployeeConditionVo
{
    @ApiModelProperty(value = "員工編號", required = true)
    private String queryEid;

    @ApiModelProperty(value = "姓名", required = true)
    private String queryEname;

    @ApiModelProperty(value = "部門名稱", required = true)
    private String queryDepartmentName;

    @ApiModelProperty(value = "年齡", required = true)
    private String queryAge;

    @ApiModelProperty(value = "分頁數", required = true)
    private int queryPage;

    private int offsetSize;

    private int eachPageSize;

    public String getQueryEid()
    {
        return queryEid;
    }

    public void setQueryEid(String queryEid)
    {
        this.queryEid = queryEid;
    }

    public String getQueryEname()
    {
        return queryEname;
    }

    public void setQueryEname(String queryEname)
    {
        this.queryEname = queryEname;
    }

    public String getQueryDepartmentName()
    {
        return queryDepartmentName;
    }

    public void setQueryDepartmentName(String queryDepartmentName)
    {
        this.queryDepartmentName = queryDepartmentName;
    }

    public String getQueryAge()
    {
        return queryAge;
    }

    public void setQueryAge(String queryAge)
    {
        this.queryAge = queryAge;
    }

    public int getQueryPage()
    {
        return queryPage;
    }

    public void setQueryPage(int queryPage)
    {
        this.queryPage = queryPage;
    }

    public int getOffsetSize()
    {
        return offsetSize;
    }

    public void setOffsetSize(int offsetSize)
    {
        this.offsetSize = offsetSize;
    }

    public int getEachPageSize()
    {
        return eachPageSize;
    }

    public void setEachPageSize(int eachPageSize)
    {
        this.eachPageSize = eachPageSize;
    }

    @Override
    public String toString()
    {
        return "QueryEmployeeConditionVo [queryEid=" + queryEid + ", queryEname=" + queryEname
            + ", queryDepartmentName=" + queryDepartmentName + ", queryAge=" + queryAge + ", queryPage=" + queryPage
            + ", offsetSize=" + offsetSize + ", eachPageSize=" + eachPageSize + "]";
    }

}
