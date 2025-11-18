package models;

public class Doctor extends User {

    private int departmentId;
    private String speciality;

    public Doctor(int id, String username, String email, String password, int roleId,
                  boolean isActive, Gender gender, String phone,
                  int departmentId, String speciality) {

        super(id, username, email, password, roleId, isActive, gender, phone);
        this.departmentId = departmentId;
        this.speciality = speciality;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
