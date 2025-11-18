package models;

public class Receptionist extends User{
    
    public Receptionist(int id , String username , String email , String password , int roleId , boolean isActive , Gender gender , String phone){

        super(id, username, email , password, roleId, isActive, gender, phone);
    }
}
