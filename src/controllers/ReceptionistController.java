package controllers;

import dto.request.AppointmentReq;
import dto.request.PatientReq;
import dto.response.AppointmentRes;
import dto.response.DoctorRes;
import dto.response.PatientRes;
import exceptions.AlreadyExistsException;
import exceptions.NomoreRecordsException;
import exceptions.NotFoundException;
import exceptions.ServiceException;
import services.AppointmentService;
import services.DoctorService;
import services.PatientService;

import java.util.ArrayList;
import java.util.List;

public class ReceptionistController {

    private final PatientService patientService;
    private final AppointmentService appointmentService;
    private final DoctorService doctorService;

    public ReceptionistController() {
        this.patientService = new PatientService();
        this.appointmentService = new AppointmentService();
        this.doctorService = new DoctorService();
    }

    public PatientRes register(PatientReq req) {
        PatientRes res = null;
        try {
            res = patientService.add(req);
        } catch (AlreadyExistsException | NotFoundException | ServiceException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    public List<PatientRes> viewAll() {
        List<PatientRes> res = new ArrayList<>();
        try {
            res = patientService.getAll();
        } catch (NomoreRecordsException e) {
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

    public PatientRes getByName(String userName) {
        PatientRes res = null;
        try {
            res = patientService.get(userName);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    public AppointmentRes makeAppointment(AppointmentReq req) {
        
        AppointmentRes res = null;

        try {
            res = appointmentService.createAppointment(req);
        } 
        catch (ServiceException | AlreadyExistsException | NotFoundException e) {
            System.out.println(e.getMessage());
        }

        return res;
    }

    public List<AppointmentRes> viewAllAppointments() {
        List<AppointmentRes> res = new ArrayList<>();
        try {
            res = appointmentService.getAllAppointments();
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }
}