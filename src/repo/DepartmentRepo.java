package repo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dbConfig.DBConnection;
import exceptions.CRUDFailedException;
import models.Department;
import repo.interfaces.Repo;
import repo.query.DepartmentQuery;

public class DepartmentRepo implements Repo<Department> {

    private static DepartmentRepo instance;

    private DepartmentRepo() {
    }

    public static DepartmentRepo getDepartmentRepo() {

        if (instance == null) {
            instance = new DepartmentRepo();
        }

        return instance;
    }

    private final Map<Integer, Department> departments = new HashMap<>();

    @Override
    public Department add(Department department) {

        int id = addToDB(department);
        department.setId(id);
        departments.put(id, department);
        return department;
    }

    @Override
    public Department update(Department department) {

        updateToDB(department);
        departments.put(department.getId(), department);
        return department;
    }

    @Override
    public List<Department> getAll() {
        return new ArrayList<>(departments.values());
    }

    @Override
    public void loadAll() {

        try (PreparedStatement ps = DBConnection.getInstance().getConnection()
                .prepareStatement(DepartmentQuery.LOADALL.getQuery())) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Department department = setDepartmentDetails(rs);
                departments.put(department.getId(), department);
            }
        } catch (SQLException e) {
            System.out.println("Unable to Load all the Department data...");
        }
    }

    @Override
    public Department getById(int id) {
        return departments.get(id);
    }

    private int addToDB(Department department) {

        try (PreparedStatement ps = DBConnection.getInstance().getConnection()
                .prepareStatement(DepartmentQuery.INSERT.getQuery())) {

            makePrepareStatementDetails(ps, department);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            } else {
                throw new CRUDFailedException("No ID was return....");
            }
        } catch (SQLException e) {
            throw new CRUDFailedException("Inseration was failed...");
        }

    }

    private void updateToDB(Department department) {

        try (PreparedStatement ps = DBConnection.getInstance().getConnection()
                .prepareStatement(DepartmentQuery.UPDATE.getQuery())) {

            makePrepareStatementDetails(ps, department);
            ps.setInt(2, department.getId());
            ps.executeUpdate();
        } 
        catch (SQLException e) {
            throw new CRUDFailedException("Updation was failed..." );
        }

    }

    private Department setDepartmentDetails(ResultSet rs) throws SQLException {

        return new Department(rs.getInt("id"), rs.getString("department_name"));
    }

    private void makePrepareStatementDetails(PreparedStatement ps, Department department) throws SQLException {

        ps.setString(1, department.getDepartmentName());
    }

}
