package repo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dbConfig.DBConnection;
import exceptions.CRUDFailedException;
import models.Appointment;
import models.AppointmentStatus;
import repo.interfaces.Repo;
import repo.query.AppointmentQuery;

public class AppointmentRepo implements Repo<Appointment> {

    private static AppointmentRepo instance;
    private final Map<Integer, Appointment> appointments = new HashMap<>();

    private AppointmentRepo() {}

    public static AppointmentRepo getAppointmentRepo() {
        if (instance == null) {
            instance = new AppointmentRepo();
        }
        return instance;
    }

    private Appointment setAppointmentDetails(ResultSet rs) throws SQLException {

        LocalDate date = rs.getDate("appointment_date").toLocalDate();
        AppointmentStatus status = AppointmentStatus.valueOf(rs.getString("status"));

        if(date.isBefore(LocalDate.now()) && status.equals(AppointmentStatus.BOOKED)){
            status = AppointmentStatus.EXPIRED;
        }
        return new Appointment(
                rs.getInt("id"),
                rs.getInt("doctor_id"),
                rs.getInt("patient_id"),
                rs.getInt("slot_id"),
                date,
                rs.getTime("appointment_time").toLocalTime(),
                status
                );
    }

    private void makePrepareStatementDetails(PreparedStatement ps, Appointment appointment) throws SQLException {
        ps.setInt(1, appointment.getDoctorId());
        ps.setInt(2, appointment.getPatientId());
        ps.setInt(3, appointment.getSlotId());
        ps.setDate(4, Date.valueOf(appointment.getDate()));
        ps.setTime(5, Time.valueOf(appointment.getTime()));
        ps.setString(6, appointment.getStatus().name());
    }

    @Override
    public void loadAll() {
        try (PreparedStatement ps = DBConnection.getInstance()
                .getConnection()
                .prepareStatement(AppointmentQuery.LOADALL.getQuery())) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Appointment appointment = setAppointmentDetails(rs);
                appointments.put(appointment.getId(), appointment);
            }
        } catch (SQLException e) {
            System.out.println("Unable to load all appointment data: " );
        }
    }

    @Override
    public Appointment add(Appointment appointment) {
        int id = addToDB(appointment);
        appointment.setId(id);
        appointments.put(id, appointment);
        return appointment;
    }

    @Override
    public Appointment update(Appointment appointment) {
        updateToDB(appointment);
        appointments.put(appointment.getId(), appointment);
        return appointment;
    }

    @Override
    public List<Appointment> getAll() {
        return new ArrayList<>(appointments.values());
    }

    @Override
    public Appointment getById(int id) {
        return appointments.get(id);
    }

    public List<Appointment> findByDoctorAndDate(int doctorId, LocalDate date) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment appointment : appointments.values()) {
            if (appointment.getDoctorId() == doctorId && appointment.getDate().equals(date)) {
                result.add(appointment);
            }
        }
        return result;
    }

    public List<Appointment> findByPatient(int patientId) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment appointment : appointments.values()) {
            if (appointment.getPatientId() == patientId) {
                result.add(appointment);
            }
        }
        return result;
    }

    public List<Appointment> findByDoctor(int doctorId) {
        List<Appointment> result = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (Appointment appointment : appointments.values()) {

            if (appointment.getDoctorId() == doctorId && appointment.getStatus().equals(AppointmentStatus.BOOKED) 
            && !appointment.getDate().isBefore(today)) {

                result.add(appointment);
            }
        }
        return result;
    }

    private int addToDB(Appointment appointment) {
        try (PreparedStatement ps = DBConnection.getInstance()
                .getConnection()
                .prepareStatement(AppointmentQuery.INSERT.getQuery(), Statement.RETURN_GENERATED_KEYS)) {

            makePrepareStatementDetails(ps, appointment);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new CRUDFailedException("No ID returned after appointment insertion");
            }
        } catch (SQLException e) {
            throw new CRUDFailedException("Appointment insertion failed: " );
        }
    }

    private void updateToDB(Appointment appointment) {

        try (PreparedStatement ps = DBConnection.getInstance()
                .getConnection()
                .prepareStatement(AppointmentQuery.UPDATE.getQuery())) {

            makePrepareStatementDetails(ps, appointment);
            ps.setInt(7, appointment.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new CRUDFailedException("Appointment update failed: " + e.getMessage());
        }
    }
}