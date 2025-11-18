package controllers;

import java.util.ArrayList;
import java.util.List;

import dto.request.LabReportReq;
import dto.request.PrescriptionReq;
import dto.request.SlotReq;
import dto.response.AppointmentRes;
import dto.response.LabReportRes;
import dto.response.LabTestRes;
import dto.response.PrescriptionRes;
import dto.response.SlotRes;
import dto.response.UserRes;
import exceptions.AlreadyExistsException;
import exceptions.NomoreRecordsException;
import exceptions.NotFoundException;
import exceptions.ServiceException;
import models.AppointmentStatus;
import services.DoctorService;
import services.LabTestService;
import services.UserService;

public class DoctorController {

    private final DoctorService doctorService;
    private final LabTestService labTestService;
    private final UserService userService;

    public DoctorController() {
        this.doctorService = new DoctorService();
        this.labTestService = new LabTestService();
        this.userService = new UserService();
    }

    public SlotRes generateSlots(SlotReq req) {
        SlotRes res = null;
        try {
            res = doctorService.generateSlots(req);
        } catch (ServiceException | AlreadyExistsException | NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    public List<SlotRes> viewMySlots(String doctorUsername) {

        List<SlotRes> res = new ArrayList<>();
        try {
            res = doctorService.getSlotsByDoctor(doctorUsername);
        } catch (ServiceException | NotFoundException | NomoreRecordsException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    
    public List<AppointmentRes> viewMyAppointments(String doctorUsername) {
        List<AppointmentRes> res = new ArrayList<>();
        try {
            res = doctorService.getAppointmentsByDoctor(doctorUsername);

        } catch (ServiceException | NotFoundException | NomoreRecordsException  e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    public List<LabTestRes> viewAllLabTest() {
        List<LabTestRes> res = new ArrayList<>();
        try {
            res = labTestService.getAll();

        } catch (ServiceException | NotFoundException | NomoreRecordsException  e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

     public List<UserRes> viewAllLabTechicans() {
        List<UserRes> res = new ArrayList<>();
        try {
            res = userService.getAllLabTechnicans();

        } catch (ServiceException | NotFoundException | NomoreRecordsException  e) {
            System.out.println(e.getMessage());
        }
        return res;
    }



    public AppointmentRes updateAppointmentStatus(int appointmentId, AppointmentStatus status) {
        AppointmentRes res = null;
        try {
            res = doctorService.updateAppointmentStatus(appointmentId, status);
        } catch (ServiceException | NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }


    public PrescriptionRes createPrescription(PrescriptionReq req) {
        PrescriptionRes res = null;
        try {
            res = doctorService.createPrescription(req);
        } catch (ServiceException | NotFoundException | AlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    public PrescriptionRes viewPrescription(int appointmentId) {
        PrescriptionRes res = null;
        try {
            res = doctorService.getPrescriptionByAppointment(appointmentId);
        } catch (ServiceException | NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    public List<PrescriptionRes> viewAllPrescriptions(String doctorUsername) {
        List<PrescriptionRes> res = new ArrayList<>();
        try {
            res = doctorService.getPrescriptionsByDoctor(doctorUsername);
        } catch (ServiceException | NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

   


public LabReportRes requestLabTest(LabReportReq req , UserRes user) {
    LabReportRes res = null;
    try {
        res = doctorService.requestLabTest(req , user);
    } catch (ServiceException | NotFoundException e) {
        System.out.println(e.getMessage());
    }
    return res;
}

public List<LabReportRes> viewMyLabTests(String doctorUsername) {
    List<LabReportRes> res = new ArrayList<>();
    try {
        res = doctorService.getLabTestsByDoctor(doctorUsername);
    } catch (ServiceException | NotFoundException e) {
        System.out.println(e.getMessage());
    }
    return res;
}

public LabReportRes viewLabTestResult(int testId) {
    LabReportRes res = null;
    try {
        res = doctorService.getLabTestResult(testId);
    } catch (ServiceException | NotFoundException e) {
        System.out.println(e.getMessage());
    }
    return res;
}
}