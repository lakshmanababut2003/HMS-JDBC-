package repo;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dbConfig.DBConnection;
import exceptions.CRUDFailedException;
import models.Gender;
import models.User;
import repo.interfaces.Repo;
import repo.query.UserQuery;

public class UserRepo implements Repo<User> {

    private static UserRepo instance;

    private UserRepo() {}

    public static UserRepo getUserRepo() {
        if (instance == null) instance = new UserRepo();
        return instance;
    }

    private final Map<Integer, User> users = new HashMap<>();

    private User setUserDetails(ResultSet rs) throws SQLException {
        return new User(
            rs.getInt("id"),
            rs.getString("username"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getInt("role_id"),
            rs.getBoolean("is_active"),
            Gender.valueOf(rs.getString("gender")),
            rs.getString("phone")
        );
    }

   
    private void makePrepareStatementDetails(PreparedStatement ps, User user)
            throws SQLException {

        ps.setString(1, user.getUsername());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPassword());
        ps.setInt(4, user.getRole());
        ps.setBoolean(5, user.isActive());
        ps.setString(6, user.getGender().name());
        ps.setString(7, user.getPhone());

    }

    @Override
    public void loadAll() {

        try (PreparedStatement ps = DBConnection.getInstance()
                .getConnection()
                .prepareStatement(UserQuery.LOADALL.getQuery())) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = setUserDetails(rs);
                users.put(user.getId(), user);
            }
        }
        catch (SQLException e) {
            System.out.println("Unable to Load all the User data...");

        }
    }


    @Override
    public User add(User user) {

        int id = addToDB(user);
        user.setId(id);
        users.put(id, user);
        return user;

    }


    @Override
    public User update(User user) {
        updateToDB(user);
        users.put(user.getId(), user);
        return user;
    }


    private int addToDB(User user) {

        try (PreparedStatement ps = DBConnection.getInstance()
                .getConnection()
                .prepareStatement(UserQuery.INSERT.getQuery())) {

            makePrepareStatementDetails(ps, user);

            ResultSet rs = ps.executeQuery(); 

            if (rs.next()) {
                return rs.getInt("id");
            }
            else {
                throw new CRUDFailedException("No ID returned...");
            }
        }
        catch (SQLException e) {
            throw new CRUDFailedException("User Insertation was Failed...");
        }

    }


    private void updateToDB(User user) {

        try (PreparedStatement ps = DBConnection.getInstance()
                .getConnection()
                .prepareStatement(UserQuery.UPDATE.getQuery())) {

            makePrepareStatementDetails(ps, user);
            ps.setInt(8, user.getId());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new CRUDFailedException("User Updation was Failed..." );
        }
    }


    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override 
    public User getById(int id){

        return users.get(id);
    }

   
}
