package repo.query;

public enum AppointmentQuery {
    INSERT(
        "INSERT INTO appointments (doctor_id, patient_id, slot_id, appointment_date, appointment_time, status) " +
        "VALUES (?, ?, ?, ?, ?, ?) RETURNING id"),

    UPDATE(
        "UPDATE appointments SET doctor_id=?, patient_id=?, slot_id=?, appointment_date=?, appointment_time=?, status=? " +
        "WHERE id=?"),

    LOADALL("SELECT * FROM appointments");

    private final String query;

    AppointmentQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return this.query;
    }
}