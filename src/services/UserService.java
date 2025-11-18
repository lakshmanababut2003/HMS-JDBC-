package services;

import java.util.ArrayList;
import java.util.List;

import dto.request.UserReq;
import dto.response.UserRes;
import exceptions.AlreadyExistsException;
import exceptions.CRUDFailedException;
import exceptions.NomoreRecordsException;
import exceptions.NotFoundException;
import exceptions.ServiceException;
import models.Role;
import models.User;
import repo.UserRepo;
import services.interfaces.CRUDService;
import util.HashUtil;

public class UserService implements CRUDService<UserRes, UserReq> {

    private final UserRepo userRepo;
    private final RoleService roleService;

    public UserService() {
        this.userRepo = UserRepo.getUserRepo();
        this.roleService = new RoleService();
    }

    @Override
    public UserRes add(UserReq req) {

        if (checkAlreadyExisting(req)) {
            throw new AlreadyExistsException("Username or Email or Phone Number Already Exists...");
        }

        Role role = roleService.getRoleByName(req.getRole());

        if(role == null){
            throw new NotFoundException("Role Doesnot exist (Invalid Rolename)");
        }

        try {

            User user = new User(req.getUsername(), req.getEmail(), HashUtil.makeHashedPassword(req.getPassword()), role.getId(),
                    true, req.getGender(), req.getPhone());

            userRepo.add(user);

            return new UserRes( user.getUsername(), user.getEmail(), req.getRole(), user.getGender(),
                    user.getPhone() , user.isActive());

        } catch (CRUDFailedException e) {
            throw new ServiceException(e.getMessage());
        }

    }
    

    @Override
    public UserRes update(UserReq req) {

        User user = getUserByUserName(req.getOldUserName());

        if (user == null) {
            throw new NotFoundException("User does not found...(Invalid User)");
        }

        if (req.hasValidUsername()) {
            user.setUsername(req.getUsername());
        }

        if (req.hasValidEmail()) {
            user.setEmail(req.getEmail());
        }

        if (req.hasValidPhone()) {
            user.setPhone(req.getPhone());
        }

        if (req.hasValidGender()) {
            user.setGender(req.getGender());
        }

        if (req.hasValidRole()) {

            Role role = roleService.getRoleByName(req.getRole());

            if(role == null){
                throw new NotFoundException("Invalid Role...");
            }
            user.setRole(role.getId());
        }

        if (req.hasValidActive()) {
            user.setActive(req.isActive());
        }

        if (req.getPassword() != null && !req.getPassword().isEmpty()) {
            user.setPassword(HashUtil.makeHashedPassword(req.getPassword()));
        }

        try {

            user = userRepo.update(user);

            return new UserRes( user.getEmail(), user.getUsername(), roleService.getById(user.getId()).getRoleName(), user.getGender(), user.getPhone() , user.isActive());

        } 
        catch (CRUDFailedException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<UserRes> getAll(){

        List<UserRes> users = new ArrayList<>();

        if(userRepo.getAll().isEmpty()){
            throw new NomoreRecordsException("Nomore User records found ");
        }

        for(User user : userRepo.getAll()){

            users.add(new UserRes( user.getEmail(), user.getUsername(), roleService.getById(user.getRole()).getRoleName(), user.getGender(), user.getPhone() , user.isActive()));
        }

        return users;
    }

    private boolean checkAlreadyExisting(UserReq userData) {

        if (!userRepo.getAll().isEmpty()) {
            for (User user : userRepo.getAll()) {

                if (userData.getUsername().equalsIgnoreCase(user.getUsername()) ||
                        userData.getEmail().equalsIgnoreCase(user.getEmail()) ||
                        userData.getPhone().equalsIgnoreCase(user.getPhone())) {
                    return true;
                }
            }
        }

        return false;

    }

    public UserRes getUser(String userName){

        try{

            User user = getUserByUserName(userName);

            return new UserRes( user.getEmail(), user.getUsername(), roleService.getById(user.getRole()).getRoleName(), user.getGender(), user.getPhone(), user.isActive());
        }
        catch(NotFoundException e){
            throw new ServiceException(e.getMessage());
        }

        
    }

    public List<UserRes> getAllLabTechnicans(){

        List<User> users = userRepo.getAll();

        if(users.isEmpty()){
            throw new NomoreRecordsException("No more User Record Found...");
        }

        List<UserRes> labTechs = new ArrayList<>();

        for(User user : users){

            if(roleService.getById(user.getRole()).getRoleName().equals("labtechnician")){

                labTechs.add(
                    new UserRes(user.getEmail(), user.getUsername(), roleService.getById(user.getRole()).getRoleName(), user.getGender(), user.getPhone(), user.isActive())
                );
            }
        }

        return labTechs;
    }

    private User getUserByUserName(String userName){

         if(userRepo.getAll().isEmpty()){
            throw new NomoreRecordsException("No more User Records found...");
        }

        for(User user : userRepo.getAll()){

            if(user.getUsername().equalsIgnoreCase(userName)){

                return user;
            }
        }

        throw new NotFoundException("The User was not found (invalid username)...");

    }

}
