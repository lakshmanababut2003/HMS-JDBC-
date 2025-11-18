package services;

import java.util.ArrayList;
import java.util.List;

import dto.request.DepartmentReq;
import dto.response.DepartmentRes;
import exceptions.AlreadyExistsException;
import exceptions.CRUDFailedException;
import exceptions.NomoreRecordsException;
import exceptions.NotFoundException;
import exceptions.ServiceException;
import models.Department;
import repo.DepartmentRepo;
import services.interfaces.CRUDService;

public class DepartmentService implements CRUDService<DepartmentRes , DepartmentReq> {
    
    private final DepartmentRepo departmentRepo;

    public DepartmentService(){
        this.departmentRepo = DepartmentRepo.getDepartmentRepo();
    }

    @Override
    public DepartmentRes add(DepartmentReq req){

        if(checkAlreadyExisting(req.getdepartmentName())){
            throw new AlreadyExistsException("Department name is already exists...");
        }

        try{
            Department department = new Department(req.getdepartmentName());
            departmentRepo.add(department);

            return new DepartmentRes( department.getDepartmentName());
        }
        catch(CRUDFailedException e){
            throw new ServiceException(e.getMessage());
        }

    }

    @Override
    public DepartmentRes update(DepartmentReq req){

        Department department = getDepartmentByName(req.getOldDeptName());

        if(department == null){
            throw new NotFoundException("Invalid Department Name...");
        }

        department.setDepartmentName(req.getdepartmentName());

        try{
            departmentRepo.update(department);
            return new DepartmentRes( department.getDepartmentName());
        }
        catch(CRUDFailedException e){
            throw new ServiceException(e.getMessage());
        }

    }

     public Department getDepartmentByName(String dptName){

         if (!departmentRepo.getAll().isEmpty()) {

            for (Department department : departmentRepo.getAll()) {

                if (department.getDepartmentName().equalsIgnoreCase(dptName)) {
                    return department;
                }
            }

        }

        return null;
    }

    @Override 
    public List<DepartmentRes> getAll(){

        List<DepartmentRes> res = new ArrayList<>();

        if(departmentRepo.getAll().isEmpty()){

            throw new NomoreRecordsException("No Departments records found...");
        }

        for(Department department : departmentRepo.getAll()){

            res.add(new DepartmentRes(department.getDepartmentName()));
        }

        return res;

    }

    private boolean checkAlreadyExisting(String dptName){

         if (!departmentRepo.getAll().isEmpty()) {

            for (Department department : departmentRepo.getAll()) {

                if (department.getDepartmentName().equalsIgnoreCase(dptName)) {
                    return true;
                }
            }

        }

        return false;
    }

    @Override 
    public DepartmentRes getById(int id){

        return new DepartmentRes(departmentRepo.getById(id).getDepartmentName());
    }



    
}
