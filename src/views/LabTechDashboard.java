package views;

import java.util.List;
import java.util.Scanner;

import controllers.LabTechnicianController;
import dto.response.LabReportRes;
import dto.response.UserRes;
import menus.Menus;
import util.InputUtil;
import util.LogUtil;
import util.Validator;
import views.interfaces.RoleView;

public class LabTechDashboard implements RoleView {

    private final UserRes user;
    private final LabTechnicianController labTechController;

    public LabTechDashboard(UserRes user) {
        this.user = user;
        this.labTechController = new LabTechnicianController();
    }

    @Override
    public void showDashboard() {
        LogUtil.print("Welcome Lab Technician: " + user.getUsername());
        int choice;
        Scanner sc = InputUtil.getScanner();

        do {
            Menus.labTechMenu();
            choice = Validator.getValidChoice(sc);

            switch (choice) {
                case 1: {
                    viewAssignedLabTests();
                    break;
                }
                case 2: {
                    updateLabTestResult(sc);
                    break;
                }
                case 3: {
                    viewCompleteLabResults();
                    break;
                }
                case 4: {
                    LogUtil.print("Logout...");
                    break;
                }
                default: {
                    LogUtil.print("Wrong Choice... Try Again...");
                    break;
                }
            }
        } while (choice != 4);
    }

    private void viewAssignedLabTests() {
        List<LabReportRes> assignedTests = labTechController.viewAssignedLabTests(user.getUsername());
        
        if (assignedTests.isEmpty()) {
            LogUtil.print("No assigned lab tests found.");
        } else {
            LogUtil.viewLabReportsTable(assignedTests, "PENDING");
        }
    }

    private void updateLabTestResult(Scanner sc) {
        
        List<LabReportRes> assignedTests = labTechController.viewAssignedLabTests(user.getUsername());
        if (assignedTests.isEmpty()) {
            LogUtil.print("No assigned lab tests to update.");
            return;
        }

        LogUtil.viewLabReportsTable(assignedTests, "PENDING");


        int reportId = Validator.getValidInt(sc, "Enter Lab Report ID to update");
        if (reportId == -1) return;

        String testResult = Validator.getNonEmptyString(sc, "Enter Test Result");
        if (testResult == null) return;

        LabReportRes res = labTechController.updateLabTestResult(reportId, user.getUsername(), testResult);
        if (res != null) {
            LogUtil.print("Lab test result updated successfully!");
            LogUtil.print("Report ID: " + res.getReportId());
            LogUtil.print("Test Result: " + res.getTestResult());
            LogUtil.print("Status: " + res.getStatus());
        } else {
            LogUtil.print("Failed to update lab test result");
        }
    }

    private void viewCompleteLabResults() {
        
        List<LabReportRes> completedResults = labTechController.viewCompleteLabResults(user.getUsername());
        
        if (completedResults.isEmpty()) {
            LogUtil.print("No completed lab results found.");
        } else {
            LogUtil.viewLabReportsTable(completedResults, "COMPLETED");
        }
    }


  
}