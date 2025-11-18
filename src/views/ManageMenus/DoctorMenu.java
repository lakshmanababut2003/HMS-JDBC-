package views.ManageMenus;

import java.util.Scanner;

import controllers.ManageMenus.DoctorCRUD;
import controllers.ManageMenus.DepartmentCRUD;
import dto.request.DoctorReq;
import dto.response.DoctorRes;
import menus.Menus;
import models.Gender;
import util.InputUtil;
import util.LogUtil;
import util.Validator;
import views.interfaces.ManageMenuView;

public class DoctorMenu implements ManageMenuView {

    private final DoctorCRUD doctorController = new DoctorCRUD();
    private final DepartmentCRUD departmentController = new DepartmentCRUD();

    @Override
    public void showMenus() {

        int choice;
        Scanner sc = InputUtil.getScanner();

        do {
            Menus.ManageMenus("Doctors");
            choice = Validator.getValidChoice(sc);

            switch (choice) {
                case 1: {
                    addOrUpdateDoctor(sc, "add");
                    break;
                }
                case 2: {
                    addOrUpdateDoctor(sc, "update");
                    break;
                }
                case 3: {
                    viewAllDoctors();
                    break;
                }
                case 4: {
                    viewByUserName(sc);
                    break;
                }
                case 5: {
                    LogUtil.print("Going Back...");
                    break;
                }
                default: {
                    LogUtil.print("Wrong Choice... Try Again...");
                    break;
                }
            }

        } while (choice != 5);
    }

    private void addOrUpdateDoctor(Scanner sc, String operation) {

        int operationId = operation.equals("update") ? 1 : 0;

        String oldUserName = "";

        if (operationId == 1) {

            oldUserName = Validator.getNonEmptyString(sc, "Existing Doctor Username");

            if (oldUserName == null) {
                return;
            }

            LogUtil.doctorRecord(doctorController.getByName(oldUserName));
        }

        String userName = Validator.getNonEmptyString(sc, "Doctor Name");
        if (userName == null && operationId != 1)
            return;

        String email = Validator.getNonEmptyString(sc, "Email");
        if (email == null && operationId != 1)
            return;

        String password = Validator.getValidPassword(sc);
        if (password == null && operationId != 1)
            return;

        String phone = Validator.getNonEmptyString(sc, "Phone Number");
        if (phone == null && operationId != 1)
            return;

        Gender gender = Validator.getValidGender(sc);
        if (gender == null && operationId != 1)
            return;

        LogUtil.deptTable(departmentController.viewAll());

        String deptName = Validator.getNonEmptyString(sc, "Department Name");
        if (deptName == null && operationId != 1)
            return;

        String specialization = Validator.getNonEmptyString(sc, "Specialization");
        if (specialization == null && operationId != 1)
            return;

        DoctorRes res;

        if (operationId == 0) {
            
            res = doctorController.add(
                    new DoctorReq(userName, email, password, phone, gender, specialization, deptName));
            checkNullResponse(res, "Added");
        } else {
        
            res = doctorController.update(
                    new DoctorReq(oldUserName, userName, email, password, phone, gender, specialization, deptName));
            checkNullResponse(res, "Updated");
        }
    }

    private void viewByUserName(Scanner sc) {

        String userName = Validator.getNonEmptyString(sc, "Doctor Username");

        if (userName == null)
            return;

        DoctorRes res = doctorController.getByName(userName);
        checkNullResponse(res, "Fetched");
    }

    private void viewAllDoctors() {
        LogUtil.doctorTable(doctorController.viewAll());
    }

    private void checkNullResponse(DoctorRes res, String operation) {

        if (res != null) {
            System.out.println("Successfully " + operation + " Doctor...");
            LogUtil.doctorRecord(res);
            return;
        }

        System.out.println("Failed to " + operation + " Doctor...");
    }
}
