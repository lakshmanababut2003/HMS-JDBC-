package controllers.ManageMenus;

import java.util.ArrayList;
import java.util.List;

import controllers.interfaces.AdminController;
import dto.request.UserReq;
import dto.response.UserRes;
import exceptions.AlreadyExistsException;
import exceptions.NomoreRecordsException;
import exceptions.NotFoundException;
import exceptions.ServiceException;
import services.UserService;

public class UserCRUD implements AdminController<UserReq , UserRes> {

    private final UserService userService;

    public UserCRUD(){
        this.userService = new UserService();
    }

    @Override
    public UserRes add(UserReq req){

        UserRes res = null;

        try{

            res = userService.add(req);
        }
        catch(NotFoundException | AlreadyExistsException | ServiceException e){
            System.out.println(e.getMessage());
        }
        
        return res;
    }

    @Override
    public UserRes update(UserReq req){

        UserRes res = null;

        try{
            res = userService.update(req);
        }
        catch(NotFoundException | ServiceException e){
            System.out.println(e.getMessage());
        }

        return res;

    }

    @Override
    public List<UserRes> viewAll(){

        List<UserRes> res = new ArrayList<>();

        try{
            res = userService.getAll();
        }
        catch(NomoreRecordsException e){
            System.out.println(e.getMessage());
        }

        return res;
    }

    @Override
    public UserRes getByName(String userName){


        UserRes res = null;

        try{

            res = userService.getUser(userName);
        }
        catch(ServiceException e){
            System.out.println(e.getMessage());
        }

        return res;
    }


    
}
