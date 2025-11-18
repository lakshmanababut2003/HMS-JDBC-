package repo.query;

public enum DoctorQuery {

     LOADALL(
        "SELECT u.id, u.username, u.email, u.password, u.role_id, u.is_active, " +
        "u.gender, u.phone, d.department_id, d.speciality " +
        "FROM users u JOIN doctor_details d ON u.id = d.user_id"
    ),

    INSERT(
        "INSERT INTO doctor_details (user_id, department_id, speciality) VALUES (?, ?, ?)"
    ),

    UPDATE(
        "UPDATE doctor_details SET department_id = ?, speciality = ? WHERE user_id = ?"
    );

    private final String query;

    DoctorQuery(String query){
        this.query = query;
    }

    public String getQuery(){
        return this.query;
    }
    
}
