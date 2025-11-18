package models;

public class User {

    private int id;
    private String username;
    private String email;
    private String password;
    private int  roleId;
    private boolean isActive;
    private Gender gender;
    private String phone;


    public User(int id , String username , String email , String password , int  roleId , boolean isActive , Gender gender , String phone){

        this(username, email, password, roleId, isActive, gender, phone);
        this.id = id;
    }

    public User( String username , String email , String password , int  roleId , boolean isActive , Gender gender , String phone){

        this.username = username;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
        this.isActive = isActive;
        this.phone = phone;
        this.gender = gender;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public int getRole() {
        return roleId;
    }


    public void setRole(int role) {
        this.roleId = role;
    }


    public boolean isActive() {
        return isActive;
    }


    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }


    public Gender getGender() {
        return gender;
    }


    public void setGender(Gender gender) {
        this.gender = gender;
    }


    public String getPhone() {
        return phone;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId(){
        
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
    
}
