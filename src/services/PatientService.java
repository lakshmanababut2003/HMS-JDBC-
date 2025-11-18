package services;

import dto.request.PatientReq;
import dto.request.AppointmentReq;
import dto.response.PatientRes;
import dto.response.AppointmentRes;
import dto.response.LabReportRes;
import dto.response.PrescriptionRes;
import exceptions.*;
import models.Patient;
import models.Role;
import models.User;
import models.Appointment;
import models.Doctor;
import models.Slot;
import models.SlotStatus;
import models.LabReport;
import models.LabReportStatus;
import models.LabTest;
import models.Prescription;
import repo.PatientRepo;
import repo.UserRepo;
import repo.AppointmentRepo;
import repo.DoctorRepo;
import repo.SlotRepo;
import repo.LabReportRepo;
import repo.LabTestRepo;
import repo.PrescriptionRepo;
import services.interfaces.CRUDService;
import util.HashUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PatientService implements CRUDService<PatientRes, PatientReq> {

    private final PatientRepo patientRepo;
    private final UserRepo userRepo;
    private final RoleService roleService;
    private final AppointmentRepo appointmentRepo;
    private final DoctorRepo doctorRepo;
    private final SlotRepo slotRepo;
    private final LabReportRepo labReportRepo;
    private final PrescriptionRepo prescriptionRepo;
    private final LabTestRepo labTestRepo;

    public PatientService() {
        this.patientRepo = PatientRepo.getPatientRepo();
        this.userRepo = UserRepo.getUserRepo();
        this.roleService = new RoleService();
        this.appointmentRepo = AppointmentRepo.getAppointmentRepo();
        this.doctorRepo = DoctorRepo.getDoctorRepo();
        this.slotRepo = SlotRepo.getSlotRepo();
        this.labReportRepo = LabReportRepo.getLabReportRepo();
        this.prescriptionRepo = PrescriptionRepo.getPrescriptionRepo();
        this.labTestRepo = LabTestRepo.getLabTestRepo();
    }

    @Override
    public PatientRes add(PatientReq req) {
        if (isAlreadyExisting(req)) {
            throw new AlreadyExistsException("Username or Email or Phone Already Exists...");
        }

        Role role = roleService.getRoleByName("PATIENT");
        if (role == null) throw new NotFoundException("Invalid Patient Role...");

        try {
            User user = new User(
                    req.getUsername(),
                    req.getEmail(),
                    HashUtil.makeHashedPassword(req.getPassword()),
                    role.getId(),
                    true,
                    req.getGender(),
                    req.getPhone()
            );

            userRepo.add(user);

            Patient p = new Patient(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getRole(),
                    user.isActive(),
                    user.getGender(),
                    user.getPhone(),
                    req.getBloodGroup(),
                    req.getAddress()
            );

            patientRepo.add(p);

            return toRes(p);

        } catch (CRUDFailedException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public PatientRes update(PatientReq req) {
        Patient p = getPatientByName(req.getOldUsername());
        if (p == null) throw new NotFoundException("Patient not found...");

        User user = userRepo.getById(p.getId());
        if (user == null) throw new NotFoundException("User not found for patient id");

        if (req.hasValidUsername()) {
            user.setUsername(req.getUsername());
        }

        if (req.hasValidEmail()) {
            user.setEmail(req.getEmail());
        }
        if (req.hasValidPhone()) {
            user.setPhone(req.getPhone());
        }
        if (req.hasValidGender()) {
            user.setGender(req.getGender());
        }
        if (req.hasValidActive()) {
            user.setActive(req.isActive());
        }

        if (req.getPassword() != null && !req.getPassword().isEmpty()) {
            user.setPassword(HashUtil.makeHashedPassword(req.getPassword()));
        }

        if (req.hasValidBloodGroup()) {
            p.setBloodGroup(req.getBloodGroup());
        }
        if (req.hasValidAddress()) {
            p.setAddress(req.getAddress());
        }

        try {
            userRepo.update(user);
            patientRepo.update(p);

            return toRes(p);

        } catch (CRUDFailedException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<PatientRes> getAll() {
        List<Patient> patients = patientRepo.getAll();
        if (patients.isEmpty()) throw new NomoreRecordsException("No Patient Records found");

        List<PatientRes> list = new ArrayList<>();

        for (Patient p : patients) {
            list.add(toRes(p));
        }

        return list;
    }

    public PatientRes get(String username) {
        return toRes(getPatientByName(username));
    }
   
    public List<AppointmentRes> getAppointmentsByPatient(String patientUsername) {
        try {
            Patient patient = getPatientByName(patientUsername);
            if (patient == null) {
                throw new NotFoundException("Patient not found: " + patientUsername);
            }

            List<Appointment> appointments = appointmentRepo.findByPatient(patient.getId());
            if (appointments.isEmpty()) {
                throw new NotFoundException("No appointments found for patient: " + patientUsername);
            }

            List<AppointmentRes> result = new ArrayList<>();
            for (Appointment appointment : appointments) {
                Doctor doctor = doctorRepo.getById(appointment.getDoctorId());
                if (doctor != null) {
                    result.add(new AppointmentRes(
                        appointment.getId(),
                        doctor.getUsername(),
                        patient.getUsername(),
                        appointment.getDate(),
                        appointment.getTime(),
                        appointment.getStatus().name()
                    ));
                }
            }

            return result;

        } catch (CRUDFailedException e) {
            throw new ServiceException("Failed to fetch appointments: " + e.getMessage());
        }
    }

    private Doctor geDoctor(String docName){

        for(Doctor doc : doctorRepo.getAll()){

            if(doc.getUsername().equalsIgnoreCase(docName)){
                return doc;
            }
        }

        throw new NotFoundException("Not Found Doctor");
    }

    public AppointmentRes createAppointment(AppointmentReq req) {
        try {
            Doctor doctor = geDoctor(req.getDocName());
            if (doctor == null) {
                throw new NotFoundException("Doctor not found: " + req.getDocName());
            }

            Patient patient = getPatientByUsername(req.getPatName());
            if (patient == null) {
                throw new NotFoundException("Patient not found: " + req.getPatName());
            }

            Slot availableSlot = findAvailableSlot(doctor.getId(), req.getDate(), req.getTime());
            if (availableSlot == null) {
                throw new ServiceException("No available slot found for the selected time");
            }

            if (hasExistingAppointment(doctor.getId(), patient.getId(), req.getDate(), req.getTime())) {
                throw new AlreadyExistsException("Appointment already exists with this doctor at the selected time");
            }

            Appointment appointment = new Appointment(
                doctor.getId(),
                patient.getId(),
                availableSlot.getSlotId(),
                req.getDate(),
                req.getTime()
            );

            availableSlot.setStatus(SlotStatus.BOOKED);
            slotRepo.update(availableSlot);

            Appointment savedAppointment = appointmentRepo.add(appointment);

            return new AppointmentRes(
                savedAppointment.getId(),
                doctor.getUsername(),
                patient.getUsername(),
                savedAppointment.getDate(),
                savedAppointment.getTime(),
                savedAppointment.getStatus().name()
            );

        } catch (CRUDFailedException e) {
            throw new ServiceException("Failed to create appointment: " + e.getMessage());
        }
    }

    public List<LabReportRes> getLabReportsByPatient(String patientUsername) {
        try {
            Patient patient = getPatientByName(patientUsername);
            if (patient == null) {
                throw new NotFoundException("Patient not found: " + patientUsername);
            }

            List<Appointment> patientAppointments = appointmentRepo.findByPatient(patient.getId());
            if (patientAppointments.isEmpty()) {
                throw new NotFoundException("No appointments found for patient: " + patientUsername);
            }

            List<Integer> appointmentIds = new ArrayList<>();
            for (Appointment appointment : patientAppointments) {
                appointmentIds.add(appointment.getId());
            }

            List<LabReport> allLabReports = labReportRepo.getAll();
            List<LabReportRes> result = new ArrayList<>();

            for (LabReport labReport : allLabReports) {
                if (appointmentIds.contains(labReport.getAppointmentId()) && 
                    labReport.getStatus() == LabReportStatus.COMPLETED) {
                    
                    String labTestName = getTestnameFromTestId(labReport.getLabTestId());
                    String labTechnicianName = getUsernameFromUserId(labReport.getLabTechicianId());
                    
                    result.add(new LabReportRes(
                        labReport.getId(),
                        labTestName,
                        labReport.getAppointmentId(),
                        labTechnicianName,
                        labReport.getTestResult(),
                        labReport.getCreatedAt(),
                        labReport.getStatus().name()
                    ));
                }
            }

            if (result.isEmpty()) {
                throw new NotFoundException("No completed lab reports found for patient: " + patientUsername);
            }

            return result;

        } catch (CRUDFailedException e) {
            throw new ServiceException("Failed to fetch lab reports: " + e.getMessage());
        }
    }

    public List<PrescriptionRes> getPrescriptionsByPatient(String patientUsername) {
        try {
            Patient patient = getPatientByName(patientUsername);
            if (patient == null) {
                throw new NotFoundException("Patient not found: " + patientUsername);
            }

            List<Appointment> patientAppointments = appointmentRepo.findByPatient(patient.getId());
            if (patientAppointments.isEmpty()) {
                throw new NotFoundException("No appointments found for patient: " + patientUsername);
            }

            List<PrescriptionRes> result = new ArrayList<>();
            for (Appointment appointment : patientAppointments) {
                Prescription prescription = prescriptionRepo.findByAppointmentId(appointment.getId());
                if (prescription != null) {
                    result.add(new PrescriptionRes(
                        prescription.getId(),
                        prescription.getAppointmentId(),
                        prescription.getDiagnosis(),
                        prescription.getMedications(),
                        prescription.getInstructions(),
                        prescription.getPrescribedDate()
                    ));
                }
            }

            if (result.isEmpty()) {
                throw new NotFoundException("No prescriptions found for patient: " + patientUsername);
            }

            return result;

        } catch (CRUDFailedException e) {
            throw new ServiceException("Failed to fetch prescriptions: " + e.getMessage());
        }
    }

    private Patient getPatientByName(String username) {
        for (Patient p : patientRepo.getAll()) {
            if (p.getUsername().equalsIgnoreCase(username)) {
                return p;
            }
        }
        throw new NotFoundException("Patient not found...");
    }

    private Patient getPatientByUsername(String username) {
        for (Patient patient : patientRepo.getAll()) {
            if (patient.getUsername().equalsIgnoreCase(username)) {
                return patient;
            }
        }
        return null;
    }



    private String getUsernameFromUserId(int userId) {
        User user = userRepo.getById(userId);
        return user != null ? user.getUsername() : "Unknown";
    }

      private String getTestnameFromTestId(int testId) {
        LabTest test = labTestRepo.getById(testId);
        return test != null ? test.getTestName() : "Unknown";
    }

    private Slot findAvailableSlot(int doctorId, LocalDate date, LocalTime time) {
        List<Slot> availableSlots = slotRepo.findAvailableSlotsByDoctorAndDate(doctorId, date);
        for (Slot slot : availableSlots) {
            if (slot.getStartTime().equals(time)) {
                return slot;
            }
        }
        return null;
    }

    private boolean hasExistingAppointment(int doctorId, int patientId, LocalDate date, LocalTime time) {
        List<Appointment> doctorAppointments = appointmentRepo.findByDoctorAndDate(doctorId, date);
        for (Appointment appointment : doctorAppointments) {
            if (appointment.getPatientId() == patientId && appointment.getTime().equals(time)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAlreadyExisting(PatientReq req) {
        for (User user : userRepo.getAll()) {
            if (user.getUsername().equalsIgnoreCase(req.getUsername())
                    || user.getEmail().equalsIgnoreCase(req.getEmail())
                    || user.getPhone().equalsIgnoreCase(req.getPhone())) {
                return true;
            }
        }
        return false;
    }

    private PatientRes toRes(Patient p) {
        return new PatientRes(
                p.getUsername(),
                p.getEmail(),
                p.getPhone(),
                p.getGender(),
                p.getBloodGroup(),
                p.getAddress(),
                p.isActive()
        );
    }
}