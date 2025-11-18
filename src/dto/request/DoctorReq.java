package dto.request;

import models.Gender;

public class DoctorReq extends UserReq {

    private String specialization;
    private String departmentName;

    public DoctorReq(String username, String email, String password,
                     String phone, Gender gender, String specialization,
                     String deptName) {

        super(username, email, password, "DOCTOR", gender, phone, true);

        this.specialization = specialization;
        this.departmentName = deptName;
    }

     public DoctorReq(String oldUserName ,String username, String email, String password,
                     String phone, Gender gender, String specialization,
                     String deptName) {

        super(oldUserName , username, email, password, "DOCTOR", gender, phone, true);

        this.specialization = specialization;
        this.departmentName = deptName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getDeptName() {
        return departmentName;
    }

    public boolean hasValidSpeciality() {
        return specialization != null && !specialization.trim().isEmpty();
    }

    public boolean hasValidDepartment() {
        return departmentName != null && !departmentName.trim().isEmpty();
    }
}
