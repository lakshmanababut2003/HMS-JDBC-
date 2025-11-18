package services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dto.request.LabReportReq;
import dto.response.LabReportRes;
import exceptions.CRUDFailedException;
import exceptions.NotFoundException;
import exceptions.ServiceException;
import models.LabReport;
import models.LabReportStatus;
import models.LabTest;
import models.User;
import repo.LabReportRepo;
import repo.LabTestRepo;
import repo.UserRepo;

public class LabReportService {

    private final LabReportRepo labReportRepo;
    private final UserRepo userRepo;
    private final LabTestRepo labTestRepo;

    public LabReportService() {
        this.labReportRepo = LabReportRepo.getLabReportRepo();
        this.userRepo = UserRepo.getUserRepo();
        this.labTestRepo = LabTestRepo.getLabTestRepo();
    }

    private int getUserIdFromUsername(String username) {
        for (User user : userRepo.getAll()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user.getId();
            }
        }
        throw new NotFoundException("User not found: " + username);
    }

    private int getTestIdFromTestname(String testname) {
        for (LabTest test : labTestRepo.getAll()) {
            if (test.getTestName().equalsIgnoreCase(testname)) {
                return test.getId();
            }
        }
        throw new NotFoundException("User not found: " + testname);
    }

    private String getUsernameFromUserId(int userId) {
        User user = userRepo.getById(userId);
        return user != null ? user.getUsername() : "Unknown";
    }

    private String getTestnameFromTestId(int userId) {
        LabTest test = labTestRepo.getById(userId);
        return test != null ? test.getTestName() : "Unknown";
    }

    public LabReportRes createLabReport(LabReportReq req) {
        try {

            LabReport labReport = new LabReport(
                    getTestIdFromTestname(req.getLabTestName()),
                    req.getAppointmentId(),
                    getUserIdFromUsername(req.getLabTechnicianUsername()),
                    req.getResult(),
                    LocalDate.now(),
                    LabReportStatus.COMPLETED);

            LabReport savedLabReport = labReportRepo.add(labReport);

            return new LabReportRes(
                    savedLabReport.getId(),
                    getTestnameFromTestId(savedLabReport.getLabTestId()),
                    savedLabReport.getAppointmentId(),
                    getUsernameFromUserId(savedLabReport.getLabTechicianId()),
                    savedLabReport.getTestResult(),
                    savedLabReport.getCreatedAt(),
                    savedLabReport.getStatus().name());

        } catch (CRUDFailedException e) {
            throw new ServiceException("Failed to create lab report: " + e.getMessage());
        }
    }

    public LabReportRes updateLabReportResult(int reportId, String testResult) {
        try {
            LabReport labReport = labReportRepo.getById(reportId);
            if (labReport == null) {
                throw new NotFoundException("Lab report not found with ID: " + reportId);
            }

            if (testResult == null || testResult.trim().isEmpty()) {
                throw new ServiceException("Test result cannot be empty");
            }

            labReport.setTestResult(testResult);
            labReport.setStatus(LabReportStatus.COMPLETED);

            LabReport updatedLabReport = labReportRepo.update(labReport);

            return new LabReportRes(
                    updatedLabReport.getId(),
                    getTestnameFromTestId(updatedLabReport.getLabTestId()),
                    updatedLabReport.getAppointmentId(),
                    getUsernameFromUserId(updatedLabReport.getLabTechicianId()),
                    updatedLabReport.getTestResult(),
                    updatedLabReport.getCreatedAt(),
                    updatedLabReport.getStatus().name());

        } catch (CRUDFailedException e) {
            throw new ServiceException("Failed to update lab report: " + e.getMessage());
        }
    }

    public List<LabReportRes> getAssignedLabTests(String username) {
        try {
            List<LabReport> labReports = labReportRepo.findPendingByLabTechnician(username);
            if (labReports.isEmpty()) {
                throw new NotFoundException("No assigned lab tests found");
            }

            return convertToResList(labReports);

        } catch (CRUDFailedException e) {
            throw new ServiceException("Failed to fetch assigned lab tests: " + e.getMessage());
        }
    }

    public List<LabReportRes> getCompletedLabResults(String username) {
        try {
            List<LabReport> labReports = labReportRepo.findCompletedByLabTechnician(username);
            if (labReports.isEmpty()) {
                throw new NotFoundException("No completed lab results found");
            }

            return convertToResList(labReports);

        } catch (CRUDFailedException e) {
            throw new ServiceException("Failed to fetch completed lab results: " + e.getMessage());
        }
    }

    public List<LabReportRes> getAllLabReportsByTechnician(String username) {
        try {
            List<LabReport> labReports = labReportRepo.findByLabTechnician(username);
            if (labReports.isEmpty()) {
                throw new NotFoundException("No lab reports found for technician");
            }

            return convertToResList(labReports);

        } catch (CRUDFailedException e) {
            throw new ServiceException("Failed to fetch lab reports: " + e.getMessage());
        }
    }

    private List<LabReportRes> convertToResList(List<LabReport> labReports) {
        List<LabReportRes> result = new ArrayList<>();
        for (LabReport labReport : labReports) {

            result.add(new LabReportRes(
                   labReport.getId(),
                    getTestnameFromTestId(labReport.getLabTestId()),
                    labReport.getAppointmentId(),
                    getUsernameFromUserId(labReport.getLabTechicianId()),
                    labReport.getTestResult(),
                    labReport.getCreatedAt(),
                    labReport.getStatus().name()));
        }
        return result;
    }
}