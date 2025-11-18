package dto.response;

public class RoleRes {

    private String roleName;

    public RoleRes(String roleName){
        this.roleName = roleName;
    }

    public String getRoleName(){
        return this.roleName;
    }
    
}
