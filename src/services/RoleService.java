package services;

import java.util.ArrayList;
import java.util.List;

import dto.request.RoleReq;
import dto.response.RoleRes;
import exceptions.AlreadyExistsException;
import exceptions.CRUDFailedException;
import exceptions.NomoreRecordsException;
import exceptions.NotFoundException;
import exceptions.ServiceException;
import models.Role;
import repo.RoleRepo;
import services.interfaces.CRUDService;

public class RoleService implements CRUDService<RoleRes, RoleReq> {

    private final RoleRepo roleRepo;

    public RoleService() {
        this.roleRepo = RoleRepo.getRoleRepo();
    }

    @Override
    public RoleRes add(RoleReq req) {

        if (checkAlreadyExisting(req.getRoleName())) {
            throw new AlreadyExistsException("Role name already Existing...");
        }

        try {

            Role role = roleRepo.add(new Role(req.getRoleName()));

            return new RoleRes( role.getRoleName());

        } 
        catch (CRUDFailedException e) {
            throw new ServiceException(e.getMessage());
        }

    }

    private boolean checkAlreadyExisting(String roleName) {

        if (!roleRepo.getAll().isEmpty()) {

            for (Role role : roleRepo.getAll()) {

                if (role.getRoleName().equalsIgnoreCase(roleName)) {
                    return true;
                }
            }

        }

        return false;

    }


    public Role getRoleByName(String roleName){

         if (!roleRepo.getAll().isEmpty()) {

            for (Role role : roleRepo.getAll()) {

                if (role.getRoleName().equalsIgnoreCase(roleName)) {
                    return role;
                }
            }

        }

        return null;
    }

    @Override
    public RoleRes update(RoleReq req) {

        Role role = getRoleByName(req.getOldRoleName());

        if (role == null) {
            throw new NotFoundException("Role was not found...(Invalid Role)");
        }

        role.setRoleName(req.getRoleName());

        try {

            role = roleRepo.update(role);

            return new RoleRes( role.getRoleName());

        } catch (CRUDFailedException e) {

            throw new ServiceException(e.getMessage());
        }

    }

    @Override
    public List<RoleRes> getAll() {

        List<RoleRes> reposonseList = new ArrayList<>();

        if (roleRepo.getAll().isEmpty()) {
            throw new NomoreRecordsException("Nomore Role Records Found...");
        }

        for (Role role : roleRepo.getAll()) {

            reposonseList.add(new RoleRes( role.getRoleName()));
        }

        return reposonseList;

    }

        public List<RoleRes> adminRoles() {

        List<RoleRes> reposonseList = new ArrayList<>();

        if (roleRepo.getAll().isEmpty()) {
            throw new NomoreRecordsException("Nomore Role Records Found...");
        }

        for (Role role : roleRepo.getAll()) {

            if(!role.getRoleName().equals("patient") && !role.getRoleName().equals("doctor")){
                reposonseList.add(new RoleRes( role.getRoleName()));
            }
        }

        return reposonseList;

    }

    @Override
    public RoleRes getById(int id) {

        Role role = roleRepo.getById(id);

        if (role == null) {
            throw new NotFoundException("Role was not found...(Invalid Role Id)");
        }

        return new RoleRes( role.getRoleName());
    }

}
