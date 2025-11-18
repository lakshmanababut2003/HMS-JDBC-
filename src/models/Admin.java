package models;

public class Admin extends User{

    public Admin(int id , String username , String email , String password , int roleId , boolean isActive , Gender gender , String phone){
        super(id, username, email ,  password, roleId, isActive, gender, phone);
    }
    
}
