package models;

public class LabTechnician extends User {

    public LabTechnician( int id , String username , String email , String password , int roleId , boolean isActive , Gender gender , String phone){
        super(id, username, email , password, roleId, isActive, gender, phone);
    }
    
}
