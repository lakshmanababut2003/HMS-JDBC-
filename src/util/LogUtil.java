package util;

import java.util.List;

import dto.response.AppointmentRes;
import dto.response.DepartmentRes;
import dto.response.DoctorRes;
import dto.response.LabReportRes;
import dto.response.LabTestRes;
import dto.response.PatientRes;
import dto.response.PrescriptionRes;
import dto.response.RoleRes;
import dto.response.SlotRes;
import dto.response.UserRes;
import models.LabTest;

public class LogUtil {

    public static void roleTable(List<RoleRes> roles) {

        if (roles.isEmpty()) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-15s%n", "Role Name"));
        sb.append("------------------\n");
        for (RoleRes role : roles) {
            sb.append(String.format(" %-15s%n", role.getRoleName()));
        }

        System.out.println(sb);
    }

    public static void roleRecord(RoleRes role) {

        StringBuilder sb = new StringBuilder();
        sb.append(String.format(" %-15s%n", "Role Name"));
        sb.append("-------------------\n");
        sb.append(String.format(" %-15s%n", role.getRoleName()));
        System.out.println(sb);

    }

    public static void deptTable(List<DepartmentRes> departments) {

        if (departments.isEmpty()) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-15s%n", "Department Name"));
        sb.append("------------------\n");
        for (DepartmentRes department : departments) {
            sb.append(String.format(" %-15s%n", department.getdepartmentName()));
        }

        System.out.println(sb);
    }

    public static void deptRecord(DepartmentRes department) {

        StringBuilder sb = new StringBuilder();
        sb.append(String.format(" %-15s%n", "Department Name"));
        sb.append("-------------------\n");
        sb.append(String.format(" %-15s%n", department.getdepartmentName()));
        System.out.println(sb);

    }

    public static void userTable(List<UserRes> users) {

        if (users.isEmpty()) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%-15s | %-20s | %-15s | %-30s | %-10s%n", "UserName", "Email", "Role", "Phone No",
                "Status"));
        sb.append(
                "--------------------------------------------------------------------------------------------------------------\n");

        for (UserRes user : users) {
            sb.append(String.format("%-15s | %-20s | %-15s | %-30s | %-10s%n", user.getUsername(), user.getEmail(),
                    user.getRole(), user.getPhone(), user.isActive() ? "Active" : "Inactive"));
        }

        System.out.println(sb);
    }

    public static void userRecord(UserRes user) {

        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%-15s | %-20s | %-15s | %-30s | %-10s%n", "UserName", "Email", "Role", "Phone No",
                "Status"));
        sb.append(
                "-------------------------------------------------------------------------------------------------------------\n");

        sb.append(String.format("%-15s | %-20s | %-15s | %-30s | %-10s%n", user.getUsername(), user.getEmail(),
                user.getRole(), user.getPhone(), user.isActive() ? "Active" : "Inactive"));

        System.out.println(sb);
    }

    public static void doctorTable(List<DoctorRes> doctors) {

        if (doctors.isEmpty()) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        sb.append(String.format(
                "%-15s | %-20s | %-15s | %-10s | %-20s | %-20s | %-10s%n",
                "Doctor Name", "Email", "Phone No", "Gender", "Department", "Specialization", "Status"));

        sb.append(
                "-----------------------------------------------------------------------------------------------------------------------------\n");

        for (DoctorRes doc : doctors) {

            sb.append(String.format(
                    "%-15s | %-20s | %-15s | %-10s | %-20s | %-20s | %-10s%n", doc.getUsername(), doc.getEmail(),
                    doc.getPhone(),
                    doc.getGender(), doc.getDepartmentName(), doc.getSpecialization(),
                    doc.isActive() ? "Active" : "Inactive"));
        }

        System.out.println(sb);
    }

    public static void doctorRecord(DoctorRes doctor) {

        StringBuilder sb = new StringBuilder();

        sb.append(String.format(
                "%-15s | %-20s | %-15s | %-10s | %-20s | %-20s | %-10s%n",
                "Doctor Name", "Email", "Phone No", "Gender", "Department", "Specialization", "Status"));

        sb.append(
                "-----------------------------------------------------------------------------------------------------------------------------\n");

        sb.append(String.format(
                "%-15s | %-20s | %-15s | %-10s | %-20s | %-20s | %-10s%n", doctor.getUsername(), doctor.getEmail(),
                doctor.getPhone(),
                doctor.getGender(), doctor.getDepartmentName(), doctor.getSpecialization(),
                doctor.isActive() ? "Active" : "Inactive"));

        System.out.println(sb);
    }

    public static void patientTable(List<PatientRes> patients) {

        if (patients.isEmpty()) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        sb.append(String.format(
                "%-15s | %-20s | %-15s | %-10s | %-20s | %-20s | %-10s%n",
                "Patinet Name", "Email", "Phone No", "Gender", "Blood Group", "Address", "Status"));

        sb.append(
                "-----------------------------------------------------------------------------------------------------------------------------\n");

        for (PatientRes pat : patients) {

            sb.append(String.format(
                    "%-15s | %-20s | %-15s | %-10s | %-20s | %-20s | %-10s%n", pat.getUsername(), pat.getEmail(),
                    pat.getPhone(),
                    pat.getGender(), pat.getBloodGroup(), pat.getAddress(), pat.isActive() ? "Active" : "Inactive"));
        }

        System.out.println(sb);
    }

    public static void viewPatientNames(List<PatientRes> patients){

        if(patients.isEmpty()){
            return;
        }

        System.out.println("Patinent Name");
        System.out.println("--------------------");
        for(PatientRes patient : patients){
            System.out.println(patient.getUsername());
        }
        System.out.println();
    }

        public static void viewDoctorNames(List<DoctorRes> doctors){

        if(doctors.isEmpty()){
            return;
        }

        System.out.println("Doctor Name");
        System.out.println("--------------------");
        for(DoctorRes doctor : doctors){
            System.out.println(doctor.getUsername());
        }
        System.out.println();

    }

    public static void viewLabTechNames(List<UserRes> labTechs){

        if(labTechs.isEmpty()){
            
            return;
        }

        System.out.println("Labechnician Name");
        System.out.println("--------------------");
        for(UserRes labTech : labTechs){
            System.out.println(labTech.getUsername());
        }
        System.out.println();

    }

     public static void viewLabTestNames(List<LabTest> labTests){

        if(labTests.isEmpty()){
            return;
        }

        System.out.println("Lab Test Name");
        System.out.println("--------------------");
        for(LabTest labTest : labTests){
            System.out.println(labTest.getTestName());
        }
        System.out.println();

    }


    public static void patientRecord(PatientRes pat) {

        StringBuilder sb = new StringBuilder();

        sb.append(String.format(
                "%-15s | %-20s | %-15s | %-10s | %-20s | %-20s | %-10s%n",
                "Patient Name", "Email", "Phone No", "Gender", "Blood Group", "Address", "Status"));

        sb.append(
                "-----------------------------------------------------------------------------------------------------------------------------\n");

        sb.append(String.format(
                "%-15s | %-20s | %-15s | %-10s | %-20s | %-20s | %-10s%n", pat.getUsername(), pat.getEmail(),
                pat.getPhone(),
                pat.getGender(), pat.getBloodGroup(), pat.getAddress(), pat.isActive() ? "Active" : "Inactive"));

        System.out.println(sb);
    }

    public static void labTestTable(List<LabTestRes> labTests) {

        if (labTests.isEmpty()) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        sb.append(String.format(" %-15s | %-15s%n", "Lab Test", "Fees"));
        sb.append("---------------------------------------\n");

        for (LabTestRes labTest : labTests) {
            sb.append(
                    String.format(" %-15s | %-15s%n", labTest.getTestName(), labTest.getFees()));
        }

        System.out.println(sb);
    }

    public static void labTestRecord(LabTestRes labTest) {

        StringBuilder sb = new StringBuilder();

        sb.append(String.format(" %-15s | %-15s%n", "Lab Test", "Fees"));
        sb.append("---------------------------------------\n");

        sb.append(
                String.format(" %-15s | %-15s%n", labTest.getTestName(), labTest.getFees()));

        System.out.println(sb);
    }

    public static void slotsTable(List<SlotRes> slots) {

        if (slots.isEmpty()) {
            System.out.println("No slots found.");
            return;
        }

        StringBuilder sb = new StringBuilder();

        sb.append(String.format(
                "%-12s | %-10s | %-10s | %-10s | %-10s%n",
                "Date", "Start Time", "End Time", "Duration", "Status"));

        sb.append("------------------------------------------------------------------------------------\n");

        for (SlotRes slot : slots) {

            long duration = java.time.Duration.between(slot.getStartTime(), slot.getEndTime()).toMinutes();

            sb.append(String.format(
                    " %-12s | %-10s | %-10s | %-10s | %-10s%n",
                    slot.getDate(),
                    slot.getStartTime(),
                    slot.getEndTime(),
                    duration + " min",
                    slot.getStatus()));
        }

        System.out.println(sb);
    }

    public static void viewDetailedSlotsTable(List<SlotRes> slots) {

        if (slots.isEmpty()) {
            System.out.println("No slots found.");
            return;
        }

        StringBuilder sb = new StringBuilder();

        sb.append(String.format(
                " %-15s | %-12s | %-10s | %-10s | %-10s | %-10s%n",
                "Doctor", "Date", "Start Time", "End Time", "Duration", "Status"));

        sb.append(
                "---------------------------------------------------------------------------------------------------------\n");

        for (SlotRes slot : slots) {

            long duration = java.time.Duration.between(slot.getStartTime(), slot.getEndTime()).toMinutes();

            sb.append(String.format(
                    " %-15s | %-12s | %-10s | %-10s | %-10s | %-10s%n",

                    slot.getDoctorName() != null ? slot.getDoctorName() : "N/A",
                    slot.getDate(),
                    slot.getStartTime(),
                    slot.getEndTime(),
                    duration + " min",
                    slot.getStatus()));
        }

        System.out.println(sb);
    }

    public static void appointmentsTable(List<AppointmentRes> appointments) {

        if (appointments.isEmpty()) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.format(
                "%-12s | %-15s | %-15s | %-12s | %-10s | %-12s%n",
                "Appointment ID", "Doctor", "Patient", "Date", "Time", "Status"));
        sb.append("-------------------------------------------------------------------------------------------\n");

        for (AppointmentRes appointment : appointments) {
            sb.append(String.format(
                    "%-12s | %-15s | %-15s | %-12s | %-10s | %-12s%n",
                    appointment.getAppointmentId(),
                    appointment.getDoctorName(),
                    appointment.getPatientName(),
                    appointment.getDate(),
                    appointment.getTime(),
                    appointment.getStatus()));
        }
        System.out.println(sb);
    }

    public static void appointmentRecord(AppointmentRes appointment) {

        if (appointment == null) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.format(
                "%-12s | %-15s | %-15s | %-12s | %-10s | %-12s%n",
                "Appointment ID", "Doctor", "Patient", "Date", "Time", "Status"));
        sb.append("-------------------------------------------------------------------------------------------\n");

        sb.append(String.format(
                "%-12s | %-15s | %-15s | %-12s | %-10s | %-12s%n",
                appointment.getAppointmentId(),
                appointment.getDoctorName(),
                appointment.getPatientName(),
                appointment.getDate(),
                appointment.getTime(),
                appointment.getStatus()));

        System.out.println(sb);
    }

    public static void doctorAppointmentsTable(List<AppointmentRes> appointments) {

        if (appointments.isEmpty()) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.format(
                "%-12s | %-15s | %-12s | %-10s | %-12s%n",
                "Appointment ID", "Patient", "Date", "Time", "Status"));
        sb.append("-------------------------------------------------------------------\n");

        for (AppointmentRes appointment : appointments) {
            sb.append(String.format(
                    "%-12s | %-15s | %-12s | %-10s | %-12s%n",
                    appointment.getAppointmentId(),
                    appointment.getPatientName(),
                    appointment.getDate(),
                    appointment.getTime(),
                    appointment.getStatus()));
        }
        System.out.println(sb);
    }

    public static void prescriptionRecord(PrescriptionRes pres) {

        if (pres == null) {
            System.out.println("No prescriptions found.");
            return;
        }

        StringBuilder sb = new StringBuilder();

        sb.append(String.format(
                "%-25s | %-35s | %-30s | %-12s%n",
                 "Appointment ID", "Diagnosis", "Medications", "Prescribed Date"));

        sb.append("------------------------------------------------------------------------------------------------------------------------------\n");

        String diagnosis = pres.getDiagnosis().length() > 30 ? pres.getDiagnosis().substring(0, 20) + "..."
                : pres.getDiagnosis();

        sb.append(String.format(
                "%-25s | %-35s | %-30s | %-12s%n",
                pres.getAppointmentId(),
                diagnosis,
                pres.getMedications(),
                pres.getPrescribedDate()));

        System.out.println(sb);
    }


    public static void viewLabReportsTable(List<LabReportRes> labReports, String type) {
    if (labReports.isEmpty()) {
        System.out.println("No lab reports found.");
        return;
    }

    StringBuilder sb = new StringBuilder();
    
    if ("PENDING".equals(type)) {
        sb.append(String.format(
            "%-10s | %-12s | %-15s | %-20s | %-12s%n",
            "Report ID", "Lab Test ID", "Appointment ID", "Created Date", "Status"
        ));
        sb.append("-----------------------------------------------------------------------\n");

        for (LabReportRes report : labReports) {
            sb.append(String.format(
                "%-10s | %-12s | %-15s | %-20s | %-12s%n",
                report.getReportId(),
                report.getLabTestName(),
                report.getAppointmentId(),
                report.getCreatedAt(),
                report.getStatus()
            ));
        }
    } else {
        sb.append(String.format(
            "%-10s | %-12s | %-15s | %-30s | %-12s | %-12s%n",
            "Report ID", "Lab Test ID", "Appointment ID", "Test Result", "Created Date", "Status"
        ));
        sb.append("---------------------------------------------------------------------------------------------------\n");

        for (LabReportRes report : labReports) {
            String testResult = report.getTestResult() != null && report.getTestResult().length() > 28 ? 
                report.getTestResult().substring(0, 25) + "..." : 
                (report.getTestResult() != null ? report.getTestResult() : "N/A");
                    
            sb.append(String.format(
                "%-10s | %-12s | %-15s | %-30s | %-12s | %-12s%n",
                report.getReportId(),
                report.getLabTestName(),
                report.getAppointmentId(),
                testResult,
                report.getCreatedAt(),
                report.getStatus()
            ));
        }
    }
    
    System.out.println(sb);
}

      public static void viewLabReportDetail(LabReportRes report) {
        StringBuilder sb = new StringBuilder();
    
        sb.append(String.format("  %-20s: %s%n", "Report ID", report.getReportId()));
        sb.append(String.format("  %-20s: %s%n", "Lab Test", report.getLabTestName()));
        sb.append(String.format("  %-20s: %s%n", "Appointment ID", report.getAppointmentId()));
        sb.append(String.format("  %-20s: %s%n", "Technician Name", report.getLabTechnicianUserName()));
        sb.append(String.format("  %-20s: %s%n", "Status", report.getStatus()));
        sb.append(String.format("  %-20s: %s%n", "Created Date", report.getCreatedAt()));
        sb.append("───────────────────────────────────────────────────────────────────\n");
        sb.append(String.format("  %-20s: %s%n", "Test Result", report.getTestResult()));
        sb.append("═══════════════════════════════════════════════════════════════════\n");

        System.out.println(sb);
    }

    public static void viewLabReportsTable(List<LabReportRes> labTests) {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format(
        "%-10s | %-12s | %-15s | %-20s | %-12s | %-10s%n",
        "Report ID", "Lab Test", "Appointment ID", "Created Date", "Status", "Has Result"
    ));
    sb.append("-------------------------------------------------------------------------------------------\n");

    for (LabReportRes test : labTests) {
        String hasResult = "COMPLETED".equals(test.getStatus()) && test.getTestResult() != null ? "Yes" : "No";
        
        sb.append(String.format(
            "%-10s | %-12s | %-15s | %-20s | %-12s | %-10s%n",
            test.getReportId(),
            test.getLabTestName(),
            test.getAppointmentId(),
            test.getCreatedAt(),
            test.getStatus(),
            hasResult
        ));
    }
    System.out.println(sb);
}

public static void prescriptionTable(List<PrescriptionRes> prescriptions) {
    if (prescriptions.isEmpty()) {
        return;
    }

    StringBuilder sb = new StringBuilder();
    sb.append(String.format(
            "%-15s | %-15s | %-20s | %-25s | %-25s | %-15s%n",
            "Prescription ID", "Appointment ID", "Diagnosis", "Medications", "Instructions", "Prescribed Date"));
    sb.append("-----------------------------------------------------------------------------------------------------------------------------------------------\n");

    for (PrescriptionRes prescription : prescriptions) {
        sb.append(String.format(
                "%-15s | %-15s | %-20s | %-25s | %-25s | %-15s%n",
                prescription.getPrescriptionId(),
                prescription.getAppointmentId(),
                truncateString(prescription.getDiagnosis(), 20),
                truncateString(prescription.getMedications(), 25),
                truncateString(prescription.getInstructions(), 25),
                prescription.getPrescribedDate()));
    }
    System.out.println(sb);
}

private static String truncateString(String str, int maxLength) {
    if (str == null) return "N/A";
    return str.length() > maxLength ? str.substring(0, maxLength - 3) + "..." : str;
}


    public static void print(String text) {
        System.out.println();
        System.out.println(text);
        System.out.println();
    }

}
