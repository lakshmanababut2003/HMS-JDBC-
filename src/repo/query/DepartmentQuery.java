package repo.query;

public enum DepartmentQuery {

     INSERT("insert into departments (department_name) values  ( ?) RETURNING id" ),
    LOADALL("select * from departments"),
    UPDATE(
            "UPDATE departments SET department_name=? " +
                    "WHERE id=?");

    private final String query;

    DepartmentQuery(String query){
        this.query = query;
    }

    public String getQuery(){
        return this.query;
    }
    
}
