package dto.response;

import models.Gender;

public class DoctorRes extends UserRes {

    private String specialization;
    private String departmentName;

    public DoctorRes(String username, String email, String phone, Gender gender, String specialization,
            String departmentName,
            boolean isActive) {

        super(email, username, "DOCTOR", gender, phone, isActive);

        this.specialization = specialization;
        this.departmentName = departmentName;
    }
    



    public String getSpecialization() {
        return specialization;
    }

    public String getDepartmentName() {
        return departmentName;
    }
}
