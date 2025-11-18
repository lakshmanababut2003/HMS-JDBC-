package repo;

import dbConfig.DBConnection;
import exceptions.CRUDFailedException;
import models.Doctor;
import models.Gender;
import repo.interfaces.Repo;
import repo.query.DoctorQuery;

import java.sql.*;
import java.util.*;

public class DoctorRepo implements Repo<Doctor> {

    private static DoctorRepo instance;

    private DoctorRepo() {}

    public static DoctorRepo getDoctorRepo() {
        if (instance == null) instance = new DoctorRepo();
        return instance;
    }

    private final Map<Integer, Doctor> doctors = new HashMap<>();

    private Doctor setDoctorDetails(ResultSet rs) throws SQLException {
        return new Doctor(
            rs.getInt("id"),
            rs.getString("username"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getInt("role_id"),
            rs.getBoolean("is_active"),
            Gender.valueOf(rs.getString("gender")),
            rs.getString("phone"),
            rs.getInt("department_id"),
            rs.getString("speciality")
        );
    }


    @Override
    public void loadAll() {
        try (PreparedStatement ps = DBConnection.getInstance()
                .getConnection()
                .prepareStatement(DoctorQuery.LOADALL.getQuery())) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Doctor doctor = setDoctorDetails(rs);
                doctors.put(doctor.getId(), doctor);
            }

        } catch (SQLException e) {
            System.out.println("Unable to Load all the Doctor data...");

        }
    }

    private void addToDoctorDetails(int userId, Doctor doctor) {
        try (PreparedStatement ps = DBConnection.getInstance()
                .getConnection()
                .prepareStatement(DoctorQuery.INSERT.getQuery())) {

            ps.setInt(1, userId);
            ps.setInt(2, doctor.getDepartmentId());
            ps.setString(3, doctor.getSpeciality());

            ps.executeUpdate();
        }
        catch(SQLException e){
            throw new CRUDFailedException("Inseration Failed...");
        }
    }

    @Override
    public Doctor add(Doctor doctor) {

            addToDoctorDetails(doctor.getId(), doctor);
            doctors.put(doctor.getId(), doctor);
            return doctor;

    }


    private void updateDoctorDetails(Doctor doctor) {
        try (PreparedStatement ps = DBConnection.getInstance()
                .getConnection()
                .prepareStatement(DoctorQuery.UPDATE.getQuery())) {

            ps.setInt(1, doctor.getDepartmentId());
            ps.setString(2, doctor.getSpeciality());
            ps.setInt(3, doctor.getId());

            ps.executeUpdate();
        }
        catch(SQLException e){
            throw new CRUDFailedException("Updatation Failed...");
        }
    }

    @Override
    public Doctor update(Doctor doctor) {

            updateDoctorDetails(doctor);
            doctors.put(doctor.getId(), doctor);
            return doctor;

    }

    @Override
    public List<Doctor> getAll() {
        return new ArrayList<>(doctors.values());
    }

    @Override 
    public Doctor getById(int id){
        return doctors.get(id);
    }
}
