package dto.response;

import models.Gender;

public class UserRes {

    private String username;
    private String email;
    private String role;
    private Gender gender;
    private String phone;
    private boolean isActive;

    public UserRes(String email, String username, String role, Gender gender, String phone, boolean isActive) {
        this.username = username;
        this.role = role;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public Gender getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }
}
