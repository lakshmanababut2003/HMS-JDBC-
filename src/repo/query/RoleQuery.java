package repo.query;

public enum RoleQuery {

    INSERT("insert into roles (rolename) values  ( ?) RETURNING id" ),
    LOADALL("select * from roles"),
    UPDATE(
            "UPDATE roles SET rolename=? " +
                    "WHERE id=?");

    private final String query;

    RoleQuery(String query){
        this.query = query;
    }

    public String getQuery(){
        return this.query;
    }
    
}
