package views;

import java.util.Scanner;

import controllers.AuthController;
import dto.request.LoginReq;
import dto.response.UserRes;
import exceptions.NotFoundException;
import factory.UserFactory;
import menus.Menus;
import util.InputUtil;
import util.LogUtil;
import util.Validator;
import views.interfaces.RoleView;

public class LoginView {

    private final AuthController authController;

    public LoginView() {
        this.authController = new AuthController();
    }

    public void doLogin() {

        Scanner sc = InputUtil.getScanner();
        int choice;

        do {
            Menus.entryMenu();
            choice = Validator.getValidChoice(sc);

            switch (choice) {

                case 1: {
                    doLogin(sc);
                    break;
                }
                case 2: {
                    LogUtil.print("Exiting...");
                    break;
                }
            }

        } while (choice != 2);

    }

    private void doLogin(Scanner sc) {

        String email = Validator.getValidEmail(sc);

        if (email == null) {
            return;
        }

        String password = Validator.getValidPassword(sc);

        if (password == null) {
            return;
        }

        UserRes response = authController.doLoginUser(new LoginReq(email, password));

        if (response == null) {
            LogUtil.print("Login Failed...");
        } else {
            LogUtil.print("Login SuceessFully....");
            navigateDashboard(response);
        }

    }

    private void navigateDashboard(UserRes response) {

        try{
            RoleView role = UserFactory.getController(response);
            role.showDashboard();
        }
        catch(NotFoundException e){
            System.out.println(e.getMessage());
        }

        
    }

}
