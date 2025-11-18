package services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import dto.request.DoctorReq;
import dto.request.LabReportReq;
import dto.request.PrescriptionReq;
import dto.request.SlotReq;
import dto.response.AppointmentRes;
import dto.response.DoctorRes;
import dto.response.LabReportRes;
import dto.response.PrescriptionRes;
import dto.response.SlotRes;
import dto.response.UserRes;
import exceptions.*;
import models.Appointment;
import models.AppointmentStatus;
import models.Doctor;
import models.LabReport;
import models.LabReportStatus;
import models.LabTest;
import models.Patient;
import models.Prescription;
import models.Role;
import models.Slot;
import models.SlotStatus;
import models.User;
import repo.AppointmentRepo;
import repo.DoctorRepo;
import repo.LabReportRepo;
import repo.LabTestRepo;
import repo.PatientRepo;
import repo.PrescriptionRepo;
import repo.SlotRepo;
import repo.UserRepo;
import services.interfaces.CRUDService;
import services.interfaces.DoctorActivity;
import util.HashUtil;

public class DoctorService implements CRUDService<DoctorRes, DoctorReq>, DoctorActivity {

    private final DoctorRepo doctorRepo;
    private final UserRepo userRepo;
    private final RoleService roleService;
    private final DepartmentService departmentService;
    private final SlotRepo slotRepo;
    private final AppointmentRepo appointmentRepo;
    private final PatientRepo patientRepo;
    private final PrescriptionRepo prescriptionRepo;
    private final LabReportRepo labReportRepo;
    private final LabTestRepo labTestRepo;

    public DoctorService() {
        this.doctorRepo = DoctorRepo.getDoctorRepo();
        this.userRepo = UserRepo.getUserRepo();
        this.roleService = new RoleService();
        this.departmentService = new DepartmentService();
        this.slotRepo = SlotRepo.getSlotRepo();
        this.appointmentRepo = AppointmentRepo.getAppointmentRepo();
        this.patientRepo = PatientRepo.getPatientRepo();
        this.prescriptionRepo = PrescriptionRepo.getPrescriptionRepo();
        this.labReportRepo = LabReportRepo.getLabReportRepo();
        this.labTestRepo = LabTestRepo.getLabTestRepo();
    }


    @Override
    public DoctorRes add(DoctorReq req) {
        if (checkAlreadyExisting(req)) {
            throw new AlreadyExistsException("Username or Email or Phone Number Already Exists...");
        }

        Role role = roleService.getRoleByName(req.getRole());
        if (role == null) {
            throw new NotFoundException("Invalid Role...");
        }

        try {
            User user = new User(
                    req.getUsername(),
                    req.getEmail(),
                    HashUtil.makeHashedPassword(req.getPassword()),
                    role.getId(),
                    true,
                    req.getGender(),
                    req.getPhone());

            userRepo.add(user);

            int departmentId = departmentService
                    .getDepartmentByName(req.getDeptName())
                    .getId();

            Doctor doctor = new Doctor(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getRole(),
                    user.isActive(),
                    user.getGender(),
                    user.getPhone(),
                    departmentId,
                    req.getSpecialization());

            doctorRepo.add(doctor);

            return new DoctorRes(
                    user.getUsername(),
                    user.getEmail(),
                    user.getPhone(),
                    user.getGender(),
                    doctor.getSpeciality(),
                    departmentService.getById(departmentId).getdepartmentName(),
                    user.isActive());

        } catch (CRUDFailedException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public DoctorRes update(DoctorReq req) {
        Doctor doctor = getDoctorByUserName(req.getOldUserName());
        if (doctor == null) {
            throw new NotFoundException("Doctor not found...");
        }

        if (req.hasValidSpeciality()) {
            doctor.setSpeciality(req.getSpecialization());
        }

        if (req.hasValidDepartment()) {
            int deptId = departmentService
                    .getDepartmentByName(req.getDeptName())
                    .getId();
            doctor.setDepartmentId(deptId);
        }

        User user = userRepo.getById(doctor.getId());
        if (user == null) {
            throw new NotFoundException("User not found for doctor id");
        }

        if (req.hasValidUsername())
            user.setUsername(req.getUsername());
        if (req.hasValidEmail())
            user.setEmail(req.getEmail());
        if (req.hasValidPhone())
            user.setPhone(req.getPhone());
        if (req.hasValidGender())
            user.setGender(req.getGender());
        if (req.hasValidActive())
            user.setActive(req.isActive());

        if (req.getPassword() != null && !req.getPassword().isEmpty()) {
            user.setPassword(HashUtil.makeHashedPassword(req.getPassword()));
        }

        if (req.hasValidRole()) {
            Role role = roleService.getRoleByName(req.getRole());
            if (role == null)
                throw new NotFoundException("Invalid Role...");
            user.setRole(role.getId());
        }

        try {
            userRepo.update(user);
            doctorRepo.update(doctor);

            return new DoctorRes(
                    user.getUsername(),
                    user.getEmail(),
                    user.getPhone(),
                    user.getGender(),
                    doctor.getSpeciality(),
                    departmentService.getById(doctor.getDepartmentId()).getdepartmentName(),
                    user.isActive());

        } catch (CRUDFailedException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<DoctorRes> getAll() {
        List<Doctor> doctors = doctorRepo.getAll();
        if (doctors.isEmpty()) {
            throw new NomoreRecordsException("No Doctor Records found");
        }

        List<User> users = userRepo.getAll();
        List<DoctorRes> list = new ArrayList<>();

        for (Doctor doc : doctors) {
            User user = users.stream()
                    .filter(u -> u.getId() == doc.getId())
                    .findFirst()
                    .orElse(null);

            if (user == null)
                continue;

            list.add(new DoctorRes(
                    user.getUsername(),
                    user.getEmail(),
                    user.getPhone(),
                    user.getGender(),
                    doc.getSpeciality(),
                    departmentService.getById(doc.getDepartmentId()).getdepartmentName(),
                    user.isActive()));
        }

        return list;
    }

    public DoctorRes get(String username) {
        return getDoctor(username);
    }

    public DoctorRes getDoctor(String username) {
        Doctor doc = getDoctorByUserName(username);
        User user = userRepo.getById(doc.getId());

        if (user == null) {
            throw new NotFoundException("User not found for doctor id");
        }

        return new DoctorRes(
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getGender(),
                doc.getSpeciality(),
                departmentService.getById(doc.getDepartmentId()).getdepartmentName(),
                user.isActive());
    }

    private Doctor getDoctorByUserName(String username) {
        List<Doctor> doctors = doctorRepo.getAll();

        if (doctors.isEmpty()) {
            throw new NomoreRecordsException("No Doctor Records found...");
        }

        for (Doctor doc : doctors) {
            User user = userRepo.getById(doc.getId());

            if (user != null && user.getUsername().equalsIgnoreCase(username)) {
                return doc;
            }
        }

        throw new NotFoundException("Doctor not found...");
    }

    private boolean checkAlreadyExisting(DoctorReq req) {
        for (User user : userRepo.getAll()) {
            if (req.getUsername().equalsIgnoreCase(user.getUsername())
                    || req.getEmail().equalsIgnoreCase(user.getEmail())
                    || req.getPhone().equalsIgnoreCase(user.getPhone())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public SlotRes generateSlots(SlotReq req) {
        try {
            long totalMinutes = java.time.Duration.between(req.getStartTime(), req.getEndTime()).toMinutes();
            if (req.getDuration() > totalMinutes) {
                throw new ServiceException("Duration is larger than the available time window");
            }

            Doctor doctor = getDoctorByUserName(req.getDoctor());
            if (doctor == null) {
                throw new NotFoundException("Doctor not found with username: " + req.getDoctor());
            }

            List<Slot> existingSlots = slotRepo.findByDoctorAndDate(doctor.getId(), req.getDate());
            if (!existingSlots.isEmpty()) {
                throw new AlreadyExistsException("Slots already exist for this doctor on " + req.getDate());
            }

            List<Slot> generatedSlots = createTimeSlots(doctor.getId(), req);

            if (generatedSlots.isEmpty()) {
                throw new ServiceException("No slots could be generated with the given parameters");
            }

            int savedCount = 0;
            for (Slot slot : generatedSlots) {
                Slot savedSlot = slotRepo.add(slot);
                if (savedSlot != null) {
                    savedCount++;
                }
            }

            return new SlotRes(req.getDoctor(), req.getDate(), req.getStartTime(), req.getEndTime(), SlotStatus.FREE, savedCount, "Slot generated Successfully...");

        } catch (CRUDFailedException e) {
            throw new ServiceException("Failed to generate slots: " );
        }
    }

    private List<Slot> createTimeSlots(int doctorId, SlotReq req) {
        List<Slot> slots = new ArrayList<>();
        LocalTime currentStart = req.getStartTime();

        while (currentStart.plusMinutes(req.getDuration()).isBefore(req.getEndTime()) ||
                currentStart.plusMinutes(req.getDuration()).equals(req.getEndTime())) {

            LocalTime currentEnd = currentStart.plusMinutes(req.getDuration());

            Slot slot = new Slot(doctorId, req.getDate(), currentStart, currentEnd);
            slots.add(slot);

            currentStart = currentEnd;
        }

        return slots;
    }

    public List<SlotRes> getSlotsByDoctor(String doctorUsername) {
        try {
            Doctor doctor = getDoctorByUserName(doctorUsername);
            if (doctor == null) {
                throw new NotFoundException("Doctor not found with username: " + doctorUsername);
            }

            List<Slot> doctorSlots = slotRepo.findByDoctor(doctor.getId());
            if (doctorSlots.isEmpty()) {
                throw new NomoreRecordsException("No slots found for doctor: " + doctorUsername);
            }

            List<SlotRes> slotResponses = new ArrayList<>();
            for (Slot slot : doctorSlots) {
                slotResponses.add(new SlotRes(
                    slot.getSlotId(),
                    doctor.getUsername(), 
                    slot.getDate(),
                    slot.getStartTime(),
                    slot.getEndTime(),
                    slot.getStatus()
                ));
            }

            return slotResponses;

        } catch (CRUDFailedException e) {
            throw new ServiceException("Failed to fetch slots " );
        }
    }

   
    public List<AppointmentRes> getAppointmentsByDoctor(String doctorUsername) {
        try {
            Doctor doctor = getDoctorByUserName(doctorUsername);
            if (doctor == null) {
                throw new NotFoundException("Doctor not found: " + doctorUsername);
            }

            List<Appointment> appointments = appointmentRepo.findByDoctor(doctor.getId());
            if (appointments.isEmpty()) {
                throw new NomoreRecordsException("No appointments found for doctor: " + doctorUsername);
            }

            List<AppointmentRes> result = new ArrayList<>();
            for (Appointment appointment : appointments) {
                Patient patient = patientRepo.getById(appointment.getPatientId());
                if (patient != null) {
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
            throw new ServiceException("Failed to fetch appointments: " );
        }
    }

    public AppointmentRes updateAppointmentStatus(int appointmentId, AppointmentStatus status) {
        try {
            Appointment appointment = appointmentRepo.getById(appointmentId);
            if (appointment == null || !(appointment.getStatus().equals(AppointmentStatus.BOOKED))) {
                throw new NotFoundException("Appointment not found with ID: " + appointmentId);
            }

            if(appointment.getStatus().equals(AppointmentStatus.CANCELLED)){
                throw new ServiceException("The Appointment Status Already Cancelled...");
            }
            
            appointment.setStatus(status);

            Appointment updatedAppointment = appointmentRepo.update(appointment);

            Doctor doctor = doctorRepo.getById(updatedAppointment.getDoctorId());
            Patient patient = patientRepo.getById(updatedAppointment.getPatientId());

            return new AppointmentRes(
                updatedAppointment.getId(),
                doctor.getUsername() ,
                 patient.getUsername() ,
                updatedAppointment.getDate(),
                updatedAppointment.getTime(),
                updatedAppointment.getStatus().name()
            );

        } catch (CRUDFailedException e) {
            throw new ServiceException("Failed to update appointment status: " + e.getMessage() );
        }
    }


    public PrescriptionRes createPrescription(PrescriptionReq req) {
        try {
            
            Appointment appointment = appointmentRepo.getById(req.getAppointmentId());
            if (appointment == null) {
                throw new NotFoundException("Appointment not found with ID: " + req.getAppointmentId());
            }

            Prescription existingPrescription = prescriptionRepo.findByAppointmentId(req.getAppointmentId());
            if (existingPrescription != null) {
                throw new AlreadyExistsException("Prescription already exists for this appointment");
            }

            Prescription prescription = new Prescription(
                req.getAppointmentId(),
                req.getDiagnosis(),
                req.getMedications(),
                req.getInstructions()
            );

            Prescription savedPrescription = prescriptionRepo.add(prescription);

            return new PrescriptionRes(
                savedPrescription.getId(),
                savedPrescription.getAppointmentId(),
                savedPrescription.getDiagnosis(),
                savedPrescription.getMedications(),
                savedPrescription.getInstructions(),
                savedPrescription.getPrescribedDate()
            );

        } catch (CRUDFailedException e) {
            throw new ServiceException("Failed to create prescription: " );
        }
    }

    public PrescriptionRes getPrescriptionByAppointment(int appointmentId) {
        try {

            Prescription prescription = prescriptionRepo.findByAppointmentId(appointmentId);
            if (prescription == null) {
                throw new NotFoundException("Prescription not found for appointment ID: " + appointmentId);
            }

            return new PrescriptionRes(
                prescription.getId(),
                prescription.getAppointmentId(),
                prescription.getDiagnosis(),
                prescription.getMedications(),
                prescription.getInstructions(),
                prescription.getPrescribedDate()
            );

        } catch (CRUDFailedException e) {
            throw new ServiceException("Failed to fetch prescription: " );
        }
    }

    public List<PrescriptionRes> getPrescriptionsByDoctor(String doctorUsername) {
        try {
            Doctor doctor = getDoctorByUserName(doctorUsername);
            if (doctor == null) {
                throw new NotFoundException("Doctor not found: " + doctorUsername);
            }

        
            List<Appointment> doctorAppointments = appointmentRepo.findByDoctor(doctor.getId());
            List<Integer> appointmentIds = new ArrayList<>();
            for (Appointment appointment : doctorAppointments) {
                appointmentIds.add(appointment.getId());
            }

    
            List<Prescription> prescriptions = new ArrayList<>();
            for (Integer appointmentId : appointmentIds) {
                Prescription prescription = prescriptionRepo.findByAppointmentId(appointmentId);
                if (prescription != null) {
                    prescriptions.add(prescription);
                }
            }

            if (prescriptions.isEmpty()) {
                throw new NomoreRecordsException("No prescriptions found for doctor: " + doctorUsername);
            }

            List<PrescriptionRes> result = new ArrayList<>();
            for (Prescription prescription : prescriptions) {
                result.add(new PrescriptionRes(
                    prescription.getId(),
                    prescription.getAppointmentId(),
                    prescription.getDiagnosis(),
                    prescription.getMedications(),
                    prescription.getInstructions(),
                    prescription.getPrescribedDate()
                ));
            }

            return result;

        } catch (CRUDFailedException e) {
            throw new ServiceException("Failed to fetch prescriptions: " );
        }
    }


     private int getUserIdFromUsername(String username) {
        for (User user : userRepo.getAll()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user.getId();
            }
        }
        return -1;
    }

     private int getTestIdFromTestname(String testname) {
        for (LabTest test : labTestRepo.getAll()) {
            if (test.getTestName().equalsIgnoreCase(testname)) {
                return test.getId();
            }
        }
        return -1;
    }

    private String getUsernameFromUserId(int userId) {
        User user = userRepo.getById(userId);
        return user != null ? user.getUsername() : "Unknown";
    }

      private String getTestnameFromTestId(int testId) {
        LabTest test = labTestRepo.getById(testId);
        return test != null ? test.getTestName() : "Unknown";
    }

   

 public LabReportRes requestLabTest(LabReportReq req, UserRes user) {
        try {
            Appointment appointment = appointmentRepo.getById(req.getAppointmentId());
            if (appointment == null) {
                throw new NotFoundException("Appointment not found with ID: " + req.getAppointmentId());
            }

            Doctor doctor = getDoctorByUserName(user.getUsername());
            if (appointment.getDoctorId() != doctor.getId()) {
                throw new ServiceException("This appointment does not belong to you");
            }

            int labTechnicianId = getUserIdFromUsername(req.getLabTechnicianUsername());
            if (labTechnicianId == -1) {
                throw new NotFoundException("Lab technician not found: " + req.getLabTechnicianUsername());
            }

            int labTestId = getTestIdFromTestname(req.getLabTestName());

            LabReport labReport = new LabReport(
                labTestId,
                req.getAppointmentId(),
                labTechnicianId,
                "Test Requested: " + req.getLabTestName(),
                LocalDate.now(),
                LabReportStatus.PENDING
            );

            LabReport savedLabReport = labReportRepo.add(labReport);

            return new LabReportRes(
                savedLabReport.getId(),
                req.getLabTestName(),
                savedLabReport.getAppointmentId(),
                req.getLabTechnicianUsername(),
                savedLabReport.getTestResult(),
                savedLabReport.getCreatedAt(),
                savedLabReport.getStatus().name()
            );

        } catch (CRUDFailedException e) {
            throw new ServiceException("Failed to request lab test: " + e.getMessage());
        }
    }

    public List<LabReportRes> getLabTestsByDoctor(String doctorUsername) {
        try {
            Doctor doctor = getDoctorByUserName(doctorUsername);
            if (doctor == null) {
                throw new NotFoundException("Doctor not found: " + doctorUsername);
            }

            List<Appointment> doctorAppointments = appointmentRepo.findByDoctor(doctor.getId());
            List<Integer> appointmentIds = new ArrayList<>();
            for (Appointment appointment : doctorAppointments) {
                appointmentIds.add(appointment.getId());
            }

            List<LabReport> allLabReports = labReportRepo.getAll();
            List<LabReport> doctorLabReports = new ArrayList<>();
            
            for (LabReport labReport : allLabReports) {
                if (appointmentIds.contains(labReport.getAppointmentId())) {
                    doctorLabReports.add(labReport);
                }
            }

            if (doctorLabReports.isEmpty()) {
                throw new NomoreRecordsException("No lab tests found for doctor: " + doctorUsername);
            }

            return convertLabReportsToResList(doctorLabReports);

        } catch (CRUDFailedException e) {
            throw new ServiceException("Failed to fetch lab tests: " + e.getMessage());
        }
    }

    public LabReportRes getLabTestResult(int testId) {
        try {
            LabReport labReport = labReportRepo.getById(testId);
            if (labReport == null) {
                throw new NotFoundException("Lab test not found with ID: " + testId);
            }

            if (labReport.getStatus() != LabReportStatus.COMPLETED) {
                throw new ServiceException("Lab test results are not available yet. Status: " + labReport.getStatus());
            }

            return new LabReportRes(
                labReport.getId(),
                getTestnameFromTestId(labReport.getLabTestId()),
                labReport.getAppointmentId(),
                getUsernameFromUserId(labReport.getLabTechicianId()),
                labReport.getTestResult(),
                labReport.getCreatedAt(),
                labReport.getStatus().name()
            );

        } catch (CRUDFailedException e) {
            throw new ServiceException("Failed to fetch lab test result: " + e.getMessage());
        }
    }

    private List<LabReportRes> convertLabReportsToResList(List<LabReport> labReports) {
        List<LabReportRes> result = new ArrayList<>();
        for (LabReport labReport : labReports) {
            result.add(new LabReportRes(
                labReport.getId(),                
                getTestnameFromTestId(labReport.getLabTestId()),
                labReport.getAppointmentId(),
                getUsernameFromUserId(labReport.getLabTechicianId()),
                labReport.getTestResult(),
                labReport.getCreatedAt(),
                labReport.getStatus().name()
            ));
        }
        return result;
    }
}