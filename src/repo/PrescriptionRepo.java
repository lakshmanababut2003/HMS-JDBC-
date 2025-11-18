package repo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dbConfig.DBConnection;
import exceptions.CRUDFailedException;
import models.Prescription;
import repo.interfaces.Repo;
import repo.query.PrescriptionQuery;

public class PrescriptionRepo implements Repo<Prescription> {

    private static PrescriptionRepo instance;
    private final Map<Integer, Prescription> prescriptions = new HashMap<>();

    private PrescriptionRepo() {
       
    }

    public static PrescriptionRepo getPrescriptionRepo() {
        if (instance == null) {
            instance = new PrescriptionRepo();
        }
        return instance;
    }

    private Prescription setPrescriptionDetails(ResultSet rs) throws SQLException {
        return new Prescription(
            rs.getInt("id"),
            rs.getInt("appointment_id"),
            rs.getString("diagnosis"),
            rs.getString("medications"),
            rs.getString("instructions"),
            rs.getDate("prescribed_date").toLocalDate()
        );
    }

    private void makePrepareStatementDetails(PreparedStatement ps, Prescription prescription) throws SQLException {
        ps.setInt(1, prescription.getAppointmentId());
        ps.setString(2, prescription.getDiagnosis());
        ps.setString(3, prescription.getMedications());
        ps.setString(4, prescription.getInstructions());
        ps.setDate(5, Date.valueOf(prescription.getPrescribedDate()));
    }

    @Override
    public void loadAll() {
        try (PreparedStatement ps = DBConnection.getInstance()
                .getConnection()
                .prepareStatement(PrescriptionQuery.LOADALL.getQuery())) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Prescription prescription = setPrescriptionDetails(rs);
                prescriptions.put(prescription.getId(), prescription);
            }
        } catch (SQLException e) {
            System.out.println("Unable to load prescription data: " + e.getMessage());
        }
    }

    @Override
    public Prescription add(Prescription prescription) {
        int id = addToDB(prescription);
        prescription.setId(id);
        prescriptions.put(id, prescription);
        return prescription;
    }

    @Override
    public Prescription update(Prescription prescription) {
        updateToDB(prescription);
        prescriptions.put(prescription.getId(), prescription);
        return prescription;
    }

    @Override
    public List<Prescription> getAll() {
        return new ArrayList<>(prescriptions.values());
    }

    @Override
    public Prescription getById(int id) {
        return prescriptions.get(id);
    }

    public Prescription findByAppointmentId(int appointmentId) {
        for (Prescription prescription : prescriptions.values()) {
            if (prescription.getAppointmentId() == appointmentId) {
                return prescription;
            }
        }
        return null;
    }

    private int addToDB(Prescription prescription) {
        try (PreparedStatement ps = DBConnection.getInstance()
                .getConnection()
                .prepareStatement(PrescriptionQuery.INSERT.getQuery(), Statement.RETURN_GENERATED_KEYS)) {

            makePrepareStatementDetails(ps, prescription);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new CRUDFailedException("No ID returned after prescription insertion");
            }
        } catch (SQLException e) {
            throw new CRUDFailedException("Prescription insertion failed: " + e.getMessage());
        }
    }

    private void updateToDB(Prescription prescription) {
        try (PreparedStatement ps = DBConnection.getInstance()
                .getConnection()
                .prepareStatement(PrescriptionQuery.UPDATE.getQuery())) {

            makePrepareStatementDetails(ps, prescription);
            ps.setInt(6, prescription.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new CRUDFailedException("Prescription update failed: " + e.getMessage());
        }
    }
}