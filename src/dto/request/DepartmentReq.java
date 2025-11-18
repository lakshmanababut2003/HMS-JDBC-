package dto.request;

public class DepartmentReq {

     private String departmentName;
     private String oldDptName;

    public DepartmentReq(String departmentName){
        this.departmentName = departmentName;
    }

    public DepartmentReq(String oldDeptName , String deptName){
        this(deptName);
        this.oldDptName = oldDeptName;
    }

    public String getOldDeptName(){
        return this.oldDptName;
    }

    public void setOldDptName(String oldDptName){
        this.oldDptName = oldDptName;
    }

    public String getdepartmentName(){
        return this.departmentName;
    }

    
}
