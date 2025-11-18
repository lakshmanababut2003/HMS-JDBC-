package models;

public class Patient extends User {

    private String bloodGroup;
    private String address;
    

    public Patient(int id , String username , String email , String password , int roleId , boolean isActive , Gender gender , String phone , String bloodGroup , String address){
        super(id, username, email , password, roleId, isActive, gender, phone);
        this.bloodGroup = bloodGroup;
        this.address = address;
    }


    public String getBloodGroup() {
        return bloodGroup;
    }


    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }


    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    
}
