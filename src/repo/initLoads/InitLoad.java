package repo.initLoads;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dbConfig.DBConnection;
import repo.AppointmentRepo;
import repo.DepartmentRepo;
import repo.DoctorRepo;
import repo.LabReportRepo;
import repo.LabTestRepo;
import repo.PatientRepo;
import repo.PrescriptionRepo;
import repo.RoleRepo;
import repo.SlotRepo;
import repo.UserRepo;

public class InitLoad {

    private static InitLoad loadAll;

    private InitLoad() {

        try {
            checkAndCreateTables();
        } catch (SQLException e) {
            System.out.println("Something Wrong..." + e.getMessage());
        }

        loadAllRepos();

    }

    public static InitLoad loadAllRepo() {

        if (loadAll == null) {
            loadAll = new InitLoad();
        }

        return loadAll;
    }

    private void loadAllRepos() {

        RoleRepo.getRoleRepo().loadAll();
        DepartmentRepo.getDepartmentRepo().loadAll();
        UserRepo.getUserRepo().loadAll();
        DoctorRepo.getDoctorRepo().loadAll();
        PatientRepo.getPatientRepo().loadAll();
        LabTestRepo.getLabTestRepo().loadAll();
        SlotRepo.getSlotRepo().loadAll();
        AppointmentRepo.getAppointmentRepo().loadAll();
        PrescriptionRepo.getPrescriptionRepo().loadAll();
        LabReportRepo.getLabReportRepo().loadAll();

    }

    private void checkAndCreateTables() throws SQLException {

        Connection conn = DBConnection.getInstance().getConnection();
        Statement st = conn.createStatement();

        createIfNotExists(st, "roles", DBTablesQuery.CREATE_ROLES);
        createIfNotExists(st, "users", DBTablesQuery.CREATE_USERS);
        createIfNotExists(st, "departments", DBTablesQuery.CREATE_DEPARTMENTS);
        createIfNotExists(st, "doctor_details", DBTablesQuery.CREATE_DOCTOR_DETAILS);
        createIfNotExists(st, "patient_details", DBTablesQuery.CREATE_PATIENT_DETAILS);
        createIfNotExists(st, "labtests", DBTablesQuery.CREATE_LABTESTS);
        createIfNotExists(st, "slots", DBTablesQuery.CREATE_SLOTS);
        createIfNotExists(st, "appointments", DBTablesQuery.CREATE_APPOINTMENTS);
        createIfNotExists(st, "prescriptions", DBTablesQuery.CREATE_PRESCRIPTIONS);
        createIfNotExists(st, "lab_reports", DBTablesQuery.CREATE_LAB_REPORTS);

        insertIfTableEmpty("roles", DBTablesQuery.INSERT_ROLES.getQuery());
        insertIfTableEmpty("users", DBTablesQuery.INSERT_USER.getQuery());

    }

    private void createIfNotExists(Statement st, String tableName, DBTablesQuery query) throws SQLException {

        String checkQuery = String.format(DBTablesQuery.CHECK_TABLE.getQuery(), tableName);

        ResultSet rs = st.executeQuery(checkQuery);

        rs.next();
        boolean exists = rs.getBoolean(1);

        if (!exists) {
            st.executeUpdate(query.getQuery());
        }
    }

    private void insertIfTableEmpty(String tableName, String insertQuery) throws SQLException {

        if (isTableEmpty(tableName)) {

            Connection conn = DBConnection.getInstance().getConnection();
            Statement st = conn.createStatement();
            st.executeUpdate(insertQuery);
        }

    }

    private boolean isTableEmpty(String tableName) throws SQLException {

        Connection conn = DBConnection.getInstance().getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM " + tableName);
        rs.next();
        return rs.getInt(1) == 0;
    }

}
