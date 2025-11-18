package repo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dbConfig.DBConnection;
import exceptions.CRUDFailedException;
import models.LabReport;
import models.LabReportStatus;
import models.User;
import repo.interfaces.Repo;
import repo.query.LabReportQuery;

public class LabReportRepo implements Repo<LabReport> {

    private static LabReportRepo instance;
    private final Map<Integer, LabReport> labReports = new HashMap<>();
    private final UserRepo userRepo;

    private LabReportRepo() {
        this.userRepo = UserRepo.getUserRepo();
        loadAll();
    }

    public static LabReportRepo getLabReportRepo() {
        if (instance == null) {
            instance = new LabReportRepo();
        }
        return instance;
    }

    private LabReport setLabReportDetails(ResultSet rs) throws SQLException {
        return new LabReport(
            rs.getInt("id"),
            rs.getInt("lab_test_id"),
            rs.getInt("appointment_id"),
            rs.getInt("lab_technician_id"),
            rs.getString("test_result"),
            rs.getDate("created_at").toLocalDate(),
            LabReportStatus.valueOf(rs.getString("status"))
        );
    }

    private void makePrepareStatementDetails(PreparedStatement ps, LabReport labReport) throws SQLException {
        ps.setInt(1, labReport.getLabTestId());
        ps.setInt(2, labReport.getAppointmentId());
        ps.setInt(3, labReport.getLabTechicianId());
        ps.setString(4, labReport.getTestResult());
        ps.setDate(5, Date.valueOf(labReport.getCreatedAt()));
        ps.setString(6, labReport.getStatus().name());
    }

    @Override
    public void loadAll() {
        try (PreparedStatement ps = DBConnection.getInstance()
                .getConnection()
                .prepareStatement(LabReportQuery.LOADALL.getQuery())) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LabReport labReport = setLabReportDetails(rs);
                labReports.put(labReport.getId(), labReport);
            }
        } catch (SQLException e) {
            System.out.println("Unable to load lab report data: " + e.getMessage());
        }
    }

    @Override
    public LabReport add(LabReport labReport) {
        int id = addToDB(labReport);
        labReport.setId(id);
        labReports.put(id, labReport);
        return labReport;
    }

    @Override
    public LabReport update(LabReport labReport) {
        updateToDB(labReport);
        labReports.put(labReport.getId(), labReport);
        return labReport;
    }

    @Override
    public List<LabReport> getAll() {
        return new ArrayList<>(labReports.values());
    }

    @Override
    public LabReport getById(int id) {
        return labReports.get(id);
    }

    private int getUserIdFromUsername(String username) {
        for (User user : userRepo.getAll()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user.getId();
            }
        }
        return -1; 
    }

    public List<LabReport> findByLabTechnician(String username) {
        int labTechnicianId = getUserIdFromUsername(username);
        if (labTechnicianId == -1) return new ArrayList<>();
        
        List<LabReport> result = new ArrayList<>();
        for (LabReport labReport : labReports.values()) {
            if (labReport.getLabTechicianId() == labTechnicianId) {
                result.add(labReport);
            }
        }
        return result;
    }

    public List<LabReport> findByStatus(LabReportStatus status) {
        List<LabReport> result = new ArrayList<>();
        for (LabReport labReport : labReports.values()) {
            if (labReport.getStatus() == status) {
                result.add(labReport);
            }
        }
        return result;
    }

    public List<LabReport> findPendingByLabTechnician(String username) {
        int labTechnicianId = getUserIdFromUsername(username);
        if (labTechnicianId == -1) return new ArrayList<>();
        
        List<LabReport> result = new ArrayList<>();
        for (LabReport labReport : labReports.values()) {
            if (labReport.getLabTechicianId() == labTechnicianId && 
                labReport.getStatus() == LabReportStatus.PENDING) {
                result.add(labReport);
            }
        }
        return result;
    }

    public List<LabReport> findCompletedByLabTechnician(String username) {
        int labTechnicianId = getUserIdFromUsername(username);
        if (labTechnicianId == -1) return new ArrayList<>();
        
        List<LabReport> result = new ArrayList<>();
        for (LabReport labReport : labReports.values()) {
            if (labReport.getLabTechicianId() == labTechnicianId && 
                labReport.getStatus() == LabReportStatus.COMPLETED) {
                result.add(labReport);
            }
        }
        return result;
    }

    private int addToDB(LabReport labReport) {
        try (PreparedStatement ps = DBConnection.getInstance()
                .getConnection()
                .prepareStatement(LabReportQuery.INSERT.getQuery(), Statement.RETURN_GENERATED_KEYS)) {

            makePrepareStatementDetails(ps, labReport);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new CRUDFailedException("No ID returned after lab report insertion");
            }
        } catch (SQLException e) {
            throw new CRUDFailedException("Lab report insertion failed: " + e.getMessage());
        }
    }

    private void updateToDB(LabReport labReport) {
        try (PreparedStatement ps = DBConnection.getInstance()
                .getConnection()
                .prepareStatement(LabReportQuery.UPDATE.getQuery())) {

            makePrepareStatementDetails(ps, labReport);
            ps.setInt(7, labReport.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new CRUDFailedException("Lab report update failed: " + e.getMessage());
        }
    }
}