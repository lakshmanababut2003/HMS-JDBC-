package controllers.ManageMenus;

import java.util.ArrayList;
import java.util.List;

import controllers.interfaces.AdminController;
import dto.request.DoctorReq;
import dto.response.DoctorRes;
import exceptions.AlreadyExistsException;
import exceptions.NomoreRecordsException;
import exceptions.NotFoundException;
import exceptions.ServiceException;
import services.DoctorService;

public class DoctorCRUD implements AdminController<DoctorReq, DoctorRes> {

    private final DoctorService doctorService;

    public DoctorCRUD() {
        this.doctorService = new DoctorService();
    }

    @Override
    public DoctorRes add(DoctorReq req) {

        DoctorRes res = null;

        try {
            res = doctorService.add(req);
        } catch (AlreadyExistsException | NotFoundException | ServiceException e) {
            System.out.println(e.getMessage());
        }

        return res;
    }

    @Override
    public DoctorRes update(DoctorReq req) {

        DoctorRes res = null;

        try {
            res = doctorService.update(req);
        } catch (NotFoundException | ServiceException e) {
            System.out.println(e.getMessage());
        }

        return res;
    }

    @Override
    public List<DoctorRes> viewAll() {

        List<DoctorRes> list = new ArrayList<>();

        try {
            list = doctorService.getAll();
        } catch (NomoreRecordsException e) {
            System.out.println(e.getMessage());
        }

        return list;
    }

    @Override
    public DoctorRes getByName(String userName) {

        DoctorRes res = null;

        try {
            res = doctorService.getDoctor(userName);
        } catch (ServiceException | NotFoundException e) {
            System.out.println(e.getMessage());
        }

        return res;
    }
}
