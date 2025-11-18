package repo.query;

public enum SlotQuery {

    INSERT(
        "INSERT INTO slots (doctor_id, slot_date, start_time, end_time, status) " +
        "VALUES (?, ?, ?, ?, ?) RETURNING id"),

    UPDATE(
        "UPDATE slots SET doctor_id=?, slot_date=?, start_time=?, end_time=?, status=? " +
        "WHERE id=?"),

    LOADALL("SELECT * FROM slots");

    private final String query;

    SlotQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return this.query;
    }
}