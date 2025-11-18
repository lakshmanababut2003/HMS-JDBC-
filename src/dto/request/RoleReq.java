package dto.request;

public class RoleReq {

    private String roleName;
    private String oldRoleName;

    public RoleReq(String roleName){
        this.roleName = roleName;
    }

   public RoleReq(String  oldRoleName , String roleName){
        this(roleName);
        this.oldRoleName = oldRoleName;
   }

    public String getOldRoleName(){
        return this.oldRoleName;
    }

    public String getRoleName(){
        return this.roleName;
    }
    
}
