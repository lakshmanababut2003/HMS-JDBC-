package models;

public class Role {

    private int id;
    private String roleName;

    public Role(int id ,String roleName){
        this(roleName);
        this.id = id;
    }

    public Role(String roleName ){
        this.roleName = roleName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    
    
}
