package services;

import dto.request.LoginReq;
import dto.response.UserRes;
import exceptions.InvaildUserException;
import models.User;
import repo.RoleRepo;
import repo.UserRepo;

public class AuthService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    public AuthService() {

        this.userRepo = UserRepo.getUserRepo();
        this.roleRepo = RoleRepo.getRoleRepo();
    }

    public UserRes checkValidLoginUser(LoginReq req) {

        for (User user : userRepo.getAll()) {

            if (req.getEmail().equals(user.getEmail()) && req.getPassword().equals(user.getPassword())) {

                return new UserRes(
                        user.getEmail(),
                        user.getUsername(),
                        roleRepo.getRoleNameById(user.getRole()),
                        user.getGender(),
                        user.getPhone(),
                        user.isActive());
            }
        }

        throw new InvaildUserException("Invalid email or password...");
    }

}
