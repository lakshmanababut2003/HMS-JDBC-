package views;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

import controllers.ReceptionistController;
import dto.request.AppointmentReq;
import dto.request.PatientReq;
import dto.response.AppointmentRes;
import dto.response.PatientRes;
import dto.response.UserRes;
import menus.Menus;
import models.Gender;
import util.InputUtil;
import util.LogUtil;
import util.Validator;
import views.interfaces.RoleView;

public class ReceptionistDashboard implements RoleView {

    private final UserRes user;
    private final ReceptionistController recepController;

    public ReceptionistDashboard(UserRes res) {
        this.user = res;
        this.recepController = new ReceptionistController();
    }

    @Override
    public void showDashboard() {

        LogUtil.print("Welcome Back" + user.getUsername());
        Scanner sc = InputUtil.getScanner();
        int choice;

        do {
            Menus.receptionsitMenu();
            choice = Validator.getValidChoice(sc);

            switch (choice) {
                case 1: {
                    registerPatient(sc);
                    break;
                }
                case 2: {
                    viewAllPatients();
                    break;
                }
                case 3: {
                    viewPatientByUsername(sc);
                    break;
                }
                case 4: {
                    makeAppointment(sc);
                    break;
                }
                case 5: {
                    viewAllappointments();
                    break;
                }
                case 6: {
                    LogUtil.print("Logging Out...");
                    break;
                }
                default:
                    LogUtil.print("Wrong Choice... Try Again...");
            }

        } while (choice != 6);
    }

    private void registerPatient(Scanner sc) {

        String username = Validator.getNonEmptyString(sc, "Enter Patient Name");

        if (username == null) {
            return;
        }

        String email = Validator.getNonEmptyString(sc, "Enter Email");
        if (email == null) {
            return;
        }

        String password = Validator.getValidPassword(sc);
        if (password == null) {
            return;
        }

        Gender gender = Validator.getValidGender(sc);
        if (gender == null) {
            return;
        }

        String phone = Validator.getNonEmptyString(sc, "Enter Phone Number");
        if (phone == null) {
            return;
        }

        String bloodGroup = Validator.getNonEmptyString(sc, "Enter Blood Group");
        if (bloodGroup == null) {
            return;
        }

        String address = Validator.getNonEmptyString(sc, "Enter Address");
        if (address == null) {
            return;
        }

        boolean isActive = true;

        PatientRes res = recepController
                .register(new PatientReq(username, email, password, gender, phone, bloodGroup, address, isActive));

        checkNullResponse(res, "Added");

    }

    private void viewAllPatients() {
        LogUtil.patientTable(recepController.viewAll());
    }

    private void viewPatientByUsername(Scanner sc) {

        String username = Validator.getNonEmptyString(sc, "Enter Patient Username");

        if (username == null) {
            return;
        }

        PatientRes res = recepController.getByName(username);

        checkNullResponse(res, "Fetched");

    }

    private void makeAppointment(Scanner sc) {

        LogUtil.viewDoctorNames(recepController.viewAllDoctors());
        String doctorName = Validator.getNonEmptyString(sc, "Doctor Name");
        if (doctorName == null) {
            return;
        }

        LogUtil.viewPatientNames(recepController.viewAll());
        String patientName = Validator.getNonEmptyString(sc, "Patient Name");
        if (patientName == null) {
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

        AppointmentRes res = recepController.makeAppointment(
                new AppointmentReq(doctorName, patientName, date, time));

        if(res != null){
            LogUtil.appointmentRecord(res);
        }


    }

    private void viewAllappointments() {

        LogUtil.appointmentsTable(recepController.viewAllAppointments());
    }

    private void checkNullResponse(PatientRes res, String operation) {

        if (res != null) {
            LogUtil.patientRecord(res);
            return;
        }

        System.out.println("No Patient Found...");

    }
}
