package repo;

import dbConfig.DBConnection;
import exceptions.CRUDFailedException;
import models.Gender;
import models.Patient;
import repo.interfaces.Repo;
import repo.query.PatientQuery;

import java.sql.*;
import java.util.*;

public class PatientRepo implements Repo<Patient> {

    private static PatientRepo instance;

    private PatientRepo() {}

    public static PatientRepo getPatientRepo() {
        if (instance == null) instance = new PatientRepo();
        return instance;
    }

    private final Map<Integer, Patient> patients = new HashMap<>();


    private Patient setPatientDetails(ResultSet rs) throws SQLException {
        return new Patient(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getInt("role_id"),
                rs.getBoolean("is_active"),
                Gender.valueOf(rs.getString("gender")),
                rs.getString("phone"),
                rs.getString("blood_group"),
                rs.getString("address")
        );
    }


    @Override
    public void loadAll() {
        try (PreparedStatement ps = DBConnection.getInstance()
                .getConnection()
                .prepareStatement(PatientQuery.LOADALL.getQuery())) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Patient p = setPatientDetails(rs);
                patients.put(p.getId(), p);
            }

        } catch (SQLException e) {
            System.out.println("Unable to Load all the Patient data..." + e.getMessage());
        }
    }


    private void addToPatientDetails(Patient patient) {
        try (PreparedStatement ps = DBConnection.getInstance()
                .getConnection()
                .prepareStatement(PatientQuery.INSERT.getQuery())) {

            ps.setInt(1, patient.getId());
            ps.setString(2, patient.getBloodGroup());
            ps.setString(3, patient.getAddress());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new CRUDFailedException("Insertion Failed..." + e.getMessage());
        }
    }


    @Override
    public Patient add(Patient patient) {

        addToPatientDetails(patient);
        patients.put(patient.getId(), patient);
        return patient;
    }


    private void updatePatientDetails(Patient patient) {
        try (PreparedStatement ps = DBConnection.getInstance()
                .getConnection()
                .prepareStatement(PatientQuery.UPDATE.getQuery())) {

            ps.setString(1, patient.getBloodGroup());
            ps.setString(2, patient.getAddress());
            ps.setInt(3, patient.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new CRUDFailedException("Updatation Failed...");
        }
    }


    @Override
    public Patient update(Patient patient) {

        updatePatientDetails(patient);
        patients.put(patient.getId(), patient);
        return patient;
    }


    @Override
    public List<Patient> getAll() {
        return new ArrayList<>(patients.values());
    }

    @Override
    public Patient getById(int id){
        return patients.get(id);
    }
}
