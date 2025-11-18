package repo.query;

public enum PatientQuery {

    INSERT("INSERT INTO patient_details (user_id, blood_group, address) VALUES (?, ?, ?)"),
    
    LOADALL(
        "SELECT u.id, u.username, u.email, u.password, u.role_id, u.is_active, " +
        "u.gender, u.phone, p.blood_group, p.address " +
        "FROM users u JOIN patient_details p ON u.id = p.user_id"
    ),

    UPDATE("UPDATE patient_details SET blood_group = ?, address = ? WHERE id = ?");

    private final String query;

    PatientQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
