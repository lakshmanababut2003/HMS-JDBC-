package dto.response;

import models.Gender;

public class PatientRes {


    private String username;
    private String email;
    private String phone;
    private Gender gender;
    private boolean isActive;
    private String bloodGroup;
    private String address;

    public PatientRes( String username, String email, String phone, Gender gender,String bloodGroup,
                       String address,  boolean isActive) {

        this.username = username;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.isActive = isActive;
        this.bloodGroup = bloodGroup;
        this.address = address;
    }


    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Gender getGender() {
        return gender;
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
}
