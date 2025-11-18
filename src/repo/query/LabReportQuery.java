package repo.query;

public enum LabReportQuery {
    INSERT(
        "INSERT INTO lab_reports (lab_test_id, appointment_id, lab_technician_id, test_result, created_at, status) " +
        "VALUES (?, ?, ?, ?, ?, ?) RETURNING id"),

    UPDATE(
        "UPDATE lab_reports SET lab_test_id=?, appointment_id=?, lab_technician_id=?, test_result=?, created_at=?, status=? " +
        "WHERE id=?"),

    LOADALL("SELECT * FROM lab_reports");

    private final String query;

    LabReportQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return this.query;
    }
}