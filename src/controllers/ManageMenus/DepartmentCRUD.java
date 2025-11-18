package controllers.ManageMenus;

import java.util.ArrayList;
import java.util.List;

import controllers.interfaces.AdminController;
import dto.request.DepartmentReq;
import dto.response.DepartmentRes;
import exceptions.AlreadyExistsException;
import exceptions.NomoreRecordsException;
import exceptions.NotFoundException;
import exceptions.ServiceException;
import services.DepartmentService;

public class DepartmentCRUD implements AdminController<DepartmentReq , DepartmentRes> {

    private final DepartmentService departmentService;

    public DepartmentCRUD(){
        this.departmentService = new DepartmentService();
    }

    @Override
    public DepartmentRes add(DepartmentReq req){

        DepartmentRes res = null;

        try{

            res = departmentService.add(req);
        }
        catch(AlreadyExistsException | ServiceException e){
            System.out.println(e.getMessage());
        }

        return res;
    }

    @Override 
    public DepartmentRes update(DepartmentReq req){

        DepartmentRes res = null;

        try{
            res = departmentService.update(req);
        }
        catch(NotFoundException | ServiceException e){
            System.out.println(e.getMessage());
        }

        return res;

    }

    @Override
    public List<DepartmentRes> viewAll(){

        List<DepartmentRes> res = new ArrayList<>();

        try{
            res = departmentService.getAll();
        }
        catch(NomoreRecordsException e){
            System.out.println(e.getMessage());
        }

        return res;

    }


    
}
