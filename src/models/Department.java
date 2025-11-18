package models;

public class Department {

    private int id;
    private String departmentName;

    public Department(int id , String departmentName){
        this(departmentName);
        this.id = id;
    }

    public Department(String departmentName){
        this.departmentName = departmentName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }   

    
    
}
