package repo.query;

public enum PrescriptionQuery {
    INSERT(
        "INSERT INTO prescriptions (appointment_id, diagnosis, medications, instructions, prescribed_date) " +
        "VALUES (?, ?, ?, ?, ?) RETURNING id"),

    UPDATE(
        "UPDATE prescriptions SET appointment_id=?, diagnosis=?, medications=?, instructions=?, prescribed_date=? " +
        "WHERE id=?"),

    LOADALL("SELECT * FROM prescriptions");

    private final String query;

    PrescriptionQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return this.query;
    }
}