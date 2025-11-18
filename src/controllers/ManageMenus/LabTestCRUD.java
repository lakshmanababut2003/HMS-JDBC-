package controllers.ManageMenus;

import java.util.ArrayList;
import java.util.List;

import controllers.interfaces.AdminController;
import dto.request.LabTestReq;
import dto.response.LabTestRes;
import exceptions.AlreadyExistsException;
import exceptions.NomoreRecordsException;
import exceptions.NotFoundException;
import exceptions.ServiceException;
import services.LabTestService;

public class LabTestCRUD implements AdminController<LabTestReq , LabTestRes> {

    private final LabTestService testService;

    public LabTestCRUD(){
        this.testService = new LabTestService();
    }

    @Override
    public LabTestRes add(LabTestReq req){

        LabTestRes res = null;

        try{
            res = testService.add(req);
        }
        catch(AlreadyExistsException | ServiceException e){
            System.out.println(e.getMessage());
        }

        return res;
    }

    @Override
    public LabTestRes update(LabTestReq req){

        LabTestRes res = null;

        try{
            res = testService.update(req);
        }
        catch(ServiceException e){
            System.out.println(e.getMessage());
        }

        return res;
    }

    @Override
    public List<LabTestRes> viewAll(){

        List<LabTestRes> res = new ArrayList<>();

        try{
            res = testService.getAll();
        }
        catch(NomoreRecordsException e){
            System.out.println(e.getMessage());
        }

        return res;
    }

    @Override 
    public LabTestRes getByName(String labTestName){

        LabTestRes res = null;

        try{
            res = testService.getLabTestByName(labTestName);
        }
        catch(NotFoundException e){
            System.out.println(e.getMessage());
        }

        return res;
    }
}
