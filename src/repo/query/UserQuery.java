package repo.query;

public enum UserQuery {

    INSERT(
            "INSERT INTO users (username, email, password, role_id, is_active, gender, phone) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id"),

    UPDATE(
            "UPDATE users SET username=?, email=?, password=?, role_id=?, is_active=?, gender=?, phone=? " +
                    "WHERE id=?"),
    UPDATEPASSWORD(
            "UPDATE users SET  password=? " +
                    "WHERE id=?"),

    LOADALL("SELECT * FROM users");



    private final String query;

    UserQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return this.query;
    }
}
