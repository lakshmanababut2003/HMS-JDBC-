package controllers;

import java.util.ArrayList;
import java.util.List;

import dto.response.LabReportRes;
import exceptions.NotFoundException;
import exceptions.ServiceException;
import services.LabReportService;

public class LabTechnicianController {

    private final LabReportService labReportService;

    public LabTechnicianController() {
        this.labReportService = new LabReportService();
    }

    public List<LabReportRes> viewAssignedLabTests(String username) {
        List<LabReportRes> res = new ArrayList<>();
        try {
            res = labReportService.getAssignedLabTests(username);
        } catch (ServiceException | NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    public LabReportRes updateLabTestResult(int reportId, String username, String testResult) {
        LabReportRes res = null;
        try {
            
            List<LabReportRes> assignedTests = labReportService.getAssignedLabTests(username);
            boolean isAuthorized = assignedTests.stream()
                .anyMatch(report -> report.getReportId() == reportId);
            
            if (!isAuthorized) {
                System.out.println("Unauthorized: This lab test is not assigned to you.");
                return null;
            }

            res = labReportService.updateLabReportResult(reportId, testResult);
        } catch (ServiceException | NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    public List<LabReportRes> viewCompleteLabResults(String username) {
        List<LabReportRes> res = new ArrayList<>();
        try {
            res = labReportService.getCompletedLabResults(username);
        } catch (ServiceException | NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    public List<LabReportRes> viewAllMyLabReports(String username) {
        List<LabReportRes> res = new ArrayList<>();
        try {
            res = labReportService.getAllLabReportsByTechnician(username);
        } catch (ServiceException | NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }
}