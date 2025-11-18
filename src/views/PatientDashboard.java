package views;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

import controllers.PatientController;
import dto.request.AppointmentReq;
import dto.response.AppointmentRes;
import dto.response.LabReportRes;
import dto.response.PrescriptionRes;
import dto.response.UserRes;
import menus.Menus;
import util.InputUtil;
import util.LogUtil;
import util.Validator;
import views.interfaces.RoleView;

public class PatientDashboard implements RoleView {

    private final UserRes user;
    private final PatientController patientController;

    public PatientDashboard(UserRes user) {
        this.user = user;
        this.patientController = new PatientController();
    }

    @Override
    public void showDashboard() {
        LogUtil.print("Welcome " + user.getUsername() + "!");
        int choice;
        Scanner sc = InputUtil.getScanner();

        do {
            Menus.patientMenu();
            choice = Validator.getValidChoice(sc);

            switch (choice) {
                case 1: {
                    viewMyAppointments();
                    break;
                }
                case 2: {
                    bookNewAppointment(sc);
                    break;
                }
                case 3: {
                    viewTestReports();
                    break;
                }
                case 4: {
                    viewMyPrescriptions();
                    break;
                }
                case 5: {
                    LogUtil.print("Logout...");
                    break;
                }
                default: {
                    LogUtil.print("Wrong Choice... Try Again...");
                    break;
                }
            }
        } while (choice != 5);
    }

    private void viewMyAppointments() {
        List<AppointmentRes> appointments = patientController.viewMyAppointments(user.getUsername());

        if (appointments.isEmpty()) {
            return;
        }
        LogUtil.appointmentsTable(appointments);
    }

    private void bookNewAppointment(Scanner sc) {

        LogUtil.viewDoctorNames(patientController.viewAllDoctors());

        String doctorName = Validator.getNonEmptyString(sc, "Enter Doctor Name");
        if (doctorName == null) {
            return;
        }

        LocalDate date = Validator.getValidDateFormat(sc);
        if (date == null) {
            return;
        }

        String timeInput = Validator.getValidTime(sc);
        if (timeInput == null) {
            return;
        }

        LocalTime time = Validator.convertToLocalTime(timeInput);
        if (time == null) {
            return;
        }

        AppointmentRes res = patientController.bookAppointment(
                new AppointmentReq(doctorName, user.getUsername(), date, time));

        if (res != null) {
            LogUtil.appointmentRecord(res);
        }
    }

    private void viewTestReports() {

        List<LabReportRes> labReports = patientController.viewMyLabReports(user.getUsername());

        if (labReports.isEmpty()) {
            LogUtil.print("No lab reports found.");
            return;
        }

        LogUtil.viewLabReportsTable(labReports);
    }

private void viewMyPrescriptions() {
    
    List<PrescriptionRes> prescriptions = patientController.viewMyPrescriptions(user.getUsername());

    if (prescriptions.isEmpty()) {
        LogUtil.print("No prescriptions found.");
        return;
    }

    LogUtil.prescriptionTable(prescriptions);
}

}