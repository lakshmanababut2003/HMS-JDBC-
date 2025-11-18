package dto.request;

import models.Gender;

public class PatientReq {

    private String oldUsername;
    private String username;
    private String email;
    private String password;
    private Gender gender;
    private String phone;
    private boolean isActive;
    private String bloodGroup;
    private String address;

    public PatientReq(String username, String email, String password, Gender gender,
            String phone, String bloodGroup, String address, boolean isActive) {

        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.phone = phone;
        this.bloodGroup = bloodGroup;
        this.address = address;
        this.isActive = isActive;
    }

    public PatientReq(String oldUsername, String username, String email, String password,
            Gender gender, String phone, String bloodGroup, String address,
            boolean isActive) {

        this.oldUsername = oldUsername;
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.phone = phone;
        this.bloodGroup = bloodGroup;
        this.address = address;
        this.isActive = isActive;
    }

    public String getOldUsername() {
        return oldUsername;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Gender getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getAddress() {
        return address;
    }

    public boolean hasValidUsername() {
        return username != null && !username.trim().isEmpty();
    }

    public boolean hasValidEmail() {
        return email != null && !email.trim().isEmpty();
    }

    public boolean hasValidPhone() {
        return phone != null && !phone.trim().isEmpty();
    }

    public boolean hasValidGender() {
        return gender != null;
    }

    public boolean hasValidActive() {
        return true;
    }

    public boolean hasValidBloodGroup() {
        return bloodGroup != null && !bloodGroup.trim().isEmpty();
    }

    public boolean hasValidAddress() {
        return address != null && !address.trim().isEmpty();
    }
}
