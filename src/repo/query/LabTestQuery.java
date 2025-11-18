package repo.query;

public enum LabTestQuery {

     INSERT("insert into labtests (test_name , fees) values  ( ? , ?) RETURNING id" ),
    LOADALL("select * from labtests"),
    UPDATE(
            "UPDATE labtests SET test_name=? , fees = ? " +
                    "WHERE id=?");

    private final String query;

    LabTestQuery(String query){
        this.query = query;
    }

    public String getQuery(){
        return this.query;
    }
    
}
