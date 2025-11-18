package controllers;

import java.util.ArrayList;
import java.util.List;

import dto.request.AppointmentReq;
import dto.response.AppointmentRes;
import dto.response.DoctorRes;
import dto.response.LabReportRes;
import dto.response.PatientRes;
import dto.response.PrescriptionRes;
import exceptions.AlreadyExistsException;
import exceptions.NomoreRecordsException;
import exceptions.NotFoundException;
import exceptions.ServiceException;
import services.DoctorService;
import services.PatientService;

public class PatientController {

    private final PatientService patientService;
    private final DoctorService doctorService;

    public PatientController() {
        this.patientService = new PatientService();
        this.doctorService = new DoctorService();
    }

    public List<AppointmentRes> viewMyAppointments(String patientUsername) {
        List<AppointmentRes> res = new ArrayList<>();
        try {
            res = patientService.getAppointmentsByPatient(patientUsername);
        } catch (ServiceException | NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    public AppointmentRes bookAppointment(AppointmentReq req) {
        AppointmentRes res = null;
        try {
            res = patientService.createAppointment(req);
        } catch (ServiceException | AlreadyExistsException | NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }
    
    public PatientRes getPatientInfo(String username) {
        PatientRes res = null;
        try {
            res = patientService.get(username);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    public List<LabReportRes> viewMyLabReports(String patientUsername) {
        List<LabReportRes> res = new ArrayList<>();
        try {
            res = patientService.getLabReportsByPatient(patientUsername);
        } catch (ServiceException | NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    public List<PrescriptionRes> viewMyPrescriptions(String patientUsername) {
        List<PrescriptionRes> res = new ArrayList<>();
        try {
            res = patientService.getPrescriptionsByPatient(patientUsername);
        } catch (ServiceException | NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

     public List<DoctorRes> viewAllDoctors() {
        List<DoctorRes> res = new ArrayList<>();
        try {
            res = doctorService.getAll();
        } catch (NomoreRecordsException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }
}