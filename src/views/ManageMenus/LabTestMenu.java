package views.ManageMenus;

import java.util.Scanner;

import controllers.ManageMenus.LabTestCRUD;
import dto.request.LabTestReq;
import dto.response.LabTestRes;
import menus.Menus;
import util.InputUtil;
import util.LogUtil;
import util.Validator;
import views.interfaces.ManageMenuView;

public class LabTestMenu implements ManageMenuView {

    private final LabTestCRUD testController = new LabTestCRUD();

    @Override
    public void showMenus() {

        int choice;
        Scanner sc = InputUtil.getScanner();

        do {

            Menus.ManageMenus("Lab Tests");
            choice = Validator.getValidChoice(sc);

            switch (choice) {

                case 1: {
                    addOrUpdateTest(sc, "add");
                    break;
                }

                case 2: {
                    addOrUpdateTest(sc, "update");
                    break;
                }

                case 3: {
                    viewAllTests();
                    break;
                }

                case 4: {
                    viewByTestName(sc);
                    break;
                }

                case 5: {
                    LogUtil.print("Go to Back...");
                    break;
                }

                default: {
                    LogUtil.print("Wrong Choice... Try Again...");
                    break;
                }
            }

        } while (choice != 5);
    }

    private void addOrUpdateTest(Scanner sc, String operation) {

        int operationId = operation.equals("update") ? 1 : 0;

        String oldTestName = "";

        if (operationId == 1) {

            oldTestName = Validator.getNonEmptyString(sc, "Existing Test Name");

            if (oldTestName == null) {
                return;
            }

            LogUtil.labTestRecord(testController.getByName(oldTestName));
        }

        String testName = Validator.getNonEmptyString(sc, "Test Name");

        if (testName == null && operationId != 1) {
            return;
        }

        Double fees = Validator.getValidDouble(sc, "Fees");

        if (fees == null && operationId != 1) {
            return ;
        }

        LabTestRes res;

        if (operationId == 0) {

            res = testController.add(new LabTestReq(testName, fees));

            checkNullResponse(res, "Added");
        } else {

            res = testController.update(new LabTestReq(oldTestName, testName, fees));

            checkNullResponse(res, "Updated");
        }
    }

    private void viewByTestName(Scanner sc) {

        String testName = Validator.getNonEmptyString(sc, "Test Name");

        if (testName == null) {
            return;
        }

        LabTestRes res = testController.getByName(testName);

        checkNullResponse(res, "Fetched");
    }

    private void viewAllTests() {

        LogUtil.labTestTable(testController.viewAll());
    }

    private void checkNullResponse(LabTestRes res, String operation) {

        if (res != null) {
            System.out.println("Successfully " + operation + " Lab Test...");
            LogUtil.labTestRecord(res);
            return;
        }

        System.out.println("Failed " + operation + " Lab Test...");
    }
}
