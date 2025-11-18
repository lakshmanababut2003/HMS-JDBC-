package controllers.ManageMenus;

import java.util.ArrayList;
import java.util.List;

import controllers.interfaces.AdminController;
import dto.request.RoleReq;
import dto.response.RoleRes;
import exceptions.AlreadyExistsException;
import exceptions.NomoreRecordsException;
import exceptions.ServiceException;
import services.RoleService;

public class RoleCRUD implements AdminController<RoleReq , RoleRes> {

    private final RoleService roleService;

    public RoleCRUD(){

        this.roleService = new RoleService();
    }

    @Override
    public RoleRes add(RoleReq req){

        RoleRes  res = null;
        try{

            res = roleService.add(req);
        }
        catch(AlreadyExistsException | ServiceException e){
            System.out.println(e.getMessage());
        }

        return res;
    }

    @Override
    public RoleRes update(RoleReq req){

        RoleRes res = null;

         try{

            res = roleService.update(req);
        }
        catch(ServiceException e){
            System.out.println(e.getMessage());
        }

        return res;

    }


    @Override
    public List<RoleRes> viewAll(){

        List<RoleRes> res = new ArrayList<>();

        try{
             res = roleService.getAll();
        }
        catch(NomoreRecordsException e){
            System.out.println(e.getMessage());
        }
        
        return res;
    }


    public List<RoleRes> adminRoles(){

        List<RoleRes> res = new ArrayList<>();

         try{
             res = roleService.adminRoles();
        }
        catch(NomoreRecordsException e){
            System.out.println(e.getMessage());
        }
        
        return res;
    }
   
    
}
