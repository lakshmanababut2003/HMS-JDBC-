package controllers;

import dto.request.LoginReq;
import dto.response.UserRes;
import exceptions.InvaildUserException;
import services.AuthService;

public class AuthController {

    private final AuthService autService;

    public AuthController() {
        this.autService = new AuthService();
    }

    public UserRes doLoginUser(LoginReq req) {

        UserRes response = null;

        try {

            response = autService.checkValidLoginUser(req);

        } catch (InvaildUserException e) {
            System.out.println(e.getMessage());
        }

        return response;
    }
}
