package services;

import java.util.ArrayList;
import java.util.List;

import dto.request.LabTestReq;
import dto.response.LabTestRes;
import exceptions.AlreadyExistsException;
import exceptions.CRUDFailedException;
import exceptions.NomoreRecordsException;
import exceptions.NotFoundException;
import exceptions.ServiceException;
import models.LabTest;
import repo.LabTestRepo;
import services.interfaces.CRUDService;

public class LabTestService implements CRUDService<LabTestRes, LabTestReq> {

    private final LabTestRepo testRepo;

    public LabTestService(){
        this.testRepo = LabTestRepo.getLabTestRepo();
    }

    @Override
    public LabTestRes add(LabTestReq req){

        if(checkAlreadyExisting(req.getTestName())) {
            throw new AlreadyExistsException("LabTest Already Exists...");
        }

        try{
            LabTest test = testRepo.add(new LabTest(req.getTestName(), req.getFees()));
            return new LabTestRes( test.getTestName(), test.getFees());

        } catch(CRUDFailedException e){
            throw new ServiceException(e.getMessage());
        }
    }

    private boolean checkAlreadyExisting(String testName){

        if(!testRepo.getAll().isEmpty()) {
            for(LabTest t : testRepo.getAll()){
                if(t.getTestName().equalsIgnoreCase(testName)){
                    return true;
                }
            }
        }

        return false;
    }

    private LabTest getTestByName(String name){
        return testRepo.getLabTestByName(name);
    }

  
    public LabTestRes getLabTestByName(String name){

        LabTest test = null;

        try{

            test = getTestByName(name);
        }
        catch(NotFoundException e){
            throw new NotFoundException("LabTest Not Found...(Invalid Test)");
        }

        return new LabTestRes(test.getTestName(), test.getFees());
    }

    @Override
    public LabTestRes update(LabTestReq req){

        LabTest test = null;

        try{
            test = getTestByName(req.getOldTestName());
        }
         catch(NotFoundException e){
            throw new NotFoundException("LabTest Not Found...(Invalid Test)");
        }

        test.setTestName(req.getTestName());
        test.setFees(req.getFees());

        try{
            test = testRepo.update(test);
            return new LabTestRes( test.getTestName(), test.getFees());

        } catch(CRUDFailedException e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<LabTestRes> getAll(){

        List<LabTestRes> list = new ArrayList<>();

        if(testRepo.getAll().isEmpty()){
            throw new NomoreRecordsException("No LabTest Records Found...");
        }

        for(LabTest test : testRepo.getAll()){
            list.add(new LabTestRes( test.getTestName(), test.getFees()));
        }

        return list;
    }

    @Override
    public LabTestRes getById(int id){

        LabTest test = testRepo.getById(id);

        if(test == null){
            throw new NotFoundException("LabTest Not Found...(Invalid ID)");
        }

        return new LabTestRes( test.getTestName(), test.getFees());
    }
}
