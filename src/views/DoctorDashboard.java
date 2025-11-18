package views;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

import controllers.DoctorController;
import dto.request.LabReportReq;
import dto.request.PrescriptionReq;
import dto.request.SlotReq;
import dto.response.AppointmentRes;
import dto.response.LabReportRes;
import dto.response.PrescriptionRes;
import dto.response.SlotRes;
import dto.response.UserRes;
import menus.Menus;
import models.AppointmentStatus;
import util.InputUtil;
import util.LogUtil;
import util.Validator;
import views.interfaces.RoleView;

public class DoctorDashboard implements RoleView {

    private final UserRes user;
    private final DoctorController doctorController;

    public DoctorDashboard(UserRes user) {
        this.user = user;
        this.doctorController = new DoctorController();
    }

    @Override
    public void showDashboard() {
        LogUtil.print("Welcome Back : " + user.getUsername());
        int choice;
        Scanner sc = InputUtil.getScanner();

        do {
            Menus.doctorMenu();
            choice = Validator.getValidChoice(sc);

            switch (choice) {
                case 1: {
                    generateSlots(sc);
                    break;
                }
                case 2: {
                    viewMySlots();
                    break;
                }
                case 3: {
                    viewMyAppointments();
                    break;
                }
                case 4: {
                    updateAppointmentStatus(sc);
                    break;
                }
                case 5: {
                    createPrescription(sc);
                    break;
                }
                case 6: {
                    viewPrescription(sc);
                    break;
                }
                case 7: {
                    requestLabTest(sc);
                    break;
                }
                case 8: {
                    viewLabTestResults(sc);
                    break;
                }
                case 9: {
                    LogUtil.print("Logout...");
                    break;
                }
                default: {
                    LogUtil.print("Wrong Choice... Try Again...");
                    break;
                }
            }
        } while (choice != 9);
    }

    private void generateSlots(Scanner sc) {
        
        int duration = Validator.getValidInt(sc, "Duration (in minutes)");
        if (duration == -1) {
            return;
        }

        LocalDate date = Validator.getValidDateFormat(sc);
        if (date == null) {
            return;
        }

        String start = Validator.getValidTime(sc);
        if (start == null) {
            return;
        }

        String end = Validator.getValidTime(sc);
        if (end == null) {
            return;
        }

        LocalTime startTime = Validator.convertToLocalTime(start);
        LocalTime endTime = Validator.convertToLocalTime(end);

        if (startTime == null || endTime == null) {
            LogUtil.print("Invalid time format.");
            return;
        }

        if (!endTime.isAfter(startTime)) {
            LogUtil.print("End time must be greater than start time.");
            return;
        }

        long totalMinutes = java.time.Duration.between(startTime, endTime).toMinutes();
        if (duration > totalMinutes) {
            LogUtil.print("Duration is larger than the available time window.");
            return;
        }

        SlotRes res = doctorController.generateSlots(new SlotReq(user.getUsername(), date, startTime, endTime, duration));

        if (res != null) {
            LogUtil.print( res.getDate() + " - Created " + res.getCount() + " slots successfully!");
        } else {
            LogUtil.print(" Failed to generate slots: ");
        }
    }

    private void viewMySlots() {
        List<SlotRes> mySlots = doctorController.viewMySlots(user.getUsername());
        
        LogUtil.slotsTable(mySlots);

    }


    private void viewMyAppointments() {
        
        getMyAppointments();
    }


    private void updateAppointmentStatus(Scanner sc) {

        if(!getMyAppointments()){
            return;
        }
        
        int appointmentId = Validator.getValidInt(sc, "Enter Appointment ID");
        if (appointmentId == -1) return;

        AppointmentStatus status = null;

         System.out.println("1.Completed");
            System.out.println("2.Cancelled");

            int choice = Validator.getValidChoice(sc);

            switch (choice) {
                case 1: {
                    status = AppointmentStatus.COMPLETED;
                    break;
                }
                case 2: {
                    status = AppointmentStatus.CANCELLED;
                    break;
                }
                default: {
                    LogUtil.print("wrong choice try again...");
                    break;
                }
            }

        if (status == null){
            return;
        } 

        AppointmentRes res = doctorController.updateAppointmentStatus(appointmentId, status);
       
        LogUtil.appointmentRecord(res);

    }


    private void createPrescription(Scanner sc) {

        if(!getMyAppointments()){
            return;
        }
        
        int appointmentId = Validator.getValidInt(sc, "Enter Appointment ID");
        if (appointmentId == -1) return;

        String diagnosis = Validator.getNonEmptyString(sc, "Enter Diagnosis");
        if (diagnosis == null) return;

        String medications = Validator.getNonEmptyString(sc, "Enter Medications");
        if (medications == null) return;

        String instructions = Validator.getNonEmptyString(sc, "Enter Instructions");
        if (instructions == null) return;

        PrescriptionRes res = doctorController.createPrescription(
            new PrescriptionReq(appointmentId, diagnosis, medications, instructions)
        );

        LogUtil.prescriptionRecord(res);

    }

    private void viewPrescription(Scanner sc) {

        if(!getMyAppointments()){
            return;
        }
        
        int appointmentId = Validator.getValidInt(sc, "Enter Appointment ID");
        if (appointmentId == -1) return;

        PrescriptionRes res = doctorController.viewPrescription(appointmentId);

        LogUtil.prescriptionRecord(res);
    }

    private void requestLabTest(Scanner sc) {
    if(!getMyAppointments()){
        return;
    }
    
    int appointmentId = Validator.getValidInt(sc, "Enter Appointment ID");
    if (appointmentId == -1) return;

    
    String testName = Validator.getNonEmptyString(sc, "Enter Test Name");
    if (testName == null) return;

    String labTechnicianUsername = Validator.getNonEmptyString(sc, "Enter Lab Technician Username");
    if (labTechnicianUsername == null) return;
    
    LabReportRes res = doctorController.requestLabTest(
        new LabReportReq(testName, appointmentId, labTechnicianUsername) , user
    );

    if (res != null) {
        LogUtil.print("Lab test requested successfully!");
        LogUtil.print("Test Name: " + testName);
        LogUtil.print("Appointment ID: " + res.getAppointmentId());
        LogUtil.print("Lab Technician: " + res.getLabTechnicianUserName());
        LogUtil.print("Status: " + res.getStatus());
    } else {
        LogUtil.print("Failed to request lab test");
    }
}

private void viewLabTestResults(Scanner sc) {
    
    List<LabReportRes> labTests = doctorController.viewMyLabTests(user.getUsername());
    if (labTests.isEmpty()) {
        LogUtil.print("No lab tests found.");
        return;
    }

   
    LogUtil.viewLabReportsTable(labTests);

    int testId = Validator.getValidInt(sc, "Enter Lab Test ID to view results");
    if (testId == -1) return;

    LabReportRes result = doctorController.viewLabTestResult(testId);
    if (result != null) {
        LogUtil.print(" Lab Test Results:");
        LogUtil.print("Test ID: " + result.getReportId());
        LogUtil.print("Lab Test : " + result.getLabTestName());
        LogUtil.print("Appointment ID: " + result.getAppointmentId());
        LogUtil.print("Test Result: " + result.getTestResult());
        LogUtil.print("Status: " + result.getStatus());
        LogUtil.print("Created Date: " + result.getCreatedAt());
    } else {
        LogUtil.print(" No results available for this test or test not found");
    }
}




    private boolean getMyAppointments(){

        List<AppointmentRes> appointments = doctorController.viewMyAppointments(user.getUsername());

        if(appointments.isEmpty()){
            return false;
        }

        LogUtil.doctorAppointmentsTable(appointments);
        return true;
    }   


}