package dto.request;

import models.Gender;

public class UserReq {

    private String username;
    private String oldUserName;
    private String email;
    private String password;
    private String role;
    private Gender gender;
    private String phone;
    private Boolean isActive;

    public UserReq(String username, String email, String password, String role, Gender gender, String phone,
            boolean isActive) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.gender = gender;
        this.phone = phone;
        this.isActive = isActive;
    }

    public UserReq(String oldUserName, String username, String email, String password, String role, Gender gender,
            String phone, boolean isActive) {
        this(username, email, password, role, gender, phone, isActive);
        this.oldUserName = oldUserName;
    }

    public String getOldUserName() {
        return oldUserName;
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

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public Boolean isActive() {
        return isActive;
    }

    public boolean hasValidUsername() {
        return username != null && !username.trim().isEmpty();
    }

    public boolean hasValidEmail() {
        return email != null && !email.trim().isEmpty();
    }

    public boolean hasValidRole() {
        return role != null;
    }

    public boolean hasValidActive() {
        return isActive != null;
    }

    public boolean hasValidGender() {
        return gender != null;
    }

    public boolean hasValidPhone() {
        return phone != null && !phone.trim().isEmpty();
    }
}
