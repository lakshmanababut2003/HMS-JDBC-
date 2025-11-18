package views.ManageMenus;

import java.util.Scanner;

import controllers.ManageMenus.RoleCRUD;
import controllers.ManageMenus.UserCRUD;
import dto.request.UserReq;
import dto.response.UserRes;
import menus.Menus;
import models.Gender;
import util.InputUtil;
import util.LogUtil;
import util.Validator;
import views.interfaces.ManageMenuView;

public class UserMenu implements ManageMenuView {

    private final UserCRUD userController = new UserCRUD();
    private final RoleCRUD roleController = new RoleCRUD();

    @Override
    public void showMenus() {

        int choice;
        Scanner sc = InputUtil.getScanner();

        do {
            Menus.ManageMenus("Users");
            choice = Validator.getValidChoice(sc);

            switch (choice) {
                case 1: {
                    addorUpdateUser(sc, "add");
                    break;
                }
                case 2: {
                    addorUpdateUser(sc, "update");
                    break;
                }
                case 3: {
                    viewAllUsers();
                    break;
                }
                case 4: {
                    viewByUserName(sc);
                    break;
                }
                case 5: {
                    LogUtil.print("Go to Back...");
                    break;
                }
                default: {
                    LogUtil.print("Wrong Choice... Try Again...");
                    break;
                }
            }

        } while (choice != 5);

    }

    private void addorUpdateUser(Scanner sc, String operation) {

        int operationId = 0;

        if (operation.equals("update")) {
            operationId = 1;
        }

        String oldUserName = "";

        if (operationId == 1) {
            oldUserName = Validator.getNonEmptyString(sc, "Existing Username");

            if (oldUserName == null) {
                return;
            }

            LogUtil.userRecord(userController.getByName(oldUserName));

        }

        String userName = Validator.getNonEmptyString(sc, "User Name ");

        if (userName == null && operationId != 1) {
            return;
        }

        String email = Validator.getNonEmptyString(sc, "Email");

        if (email == null && operationId != 1) {
            return;
        }

        String password = Validator.getValidPassword(sc);

        if (password == null && operationId != 1) {
            return;
        }

        String phone = Validator.getNonEmptyString(sc, "Phone Number");

        if (phone == null && operationId != 1) {
            return;
        }

        Gender gender = Validator.getValidGender(sc);

        if (gender == null && operationId != 1) {
            return;
        }

        LogUtil.roleTable(roleController.adminRoles());

        String roleName = Validator.getNonEmptyString(sc, "Role");

        if (roleName == null && operationId != 1) {
            return;
        }

        UserRes res;

        if (operationId == 0) {
            res = userController.add(new UserReq(userName, email, password, roleName, gender, phone, true));

            checkNullResponse(res , "Added");
        }
         else {

            res = userController.update(new UserReq(oldUserName ,userName, email, password, roleName, gender, phone, true));
            checkNullResponse(res , "Updated");


        }
    }

    private void viewByUserName(Scanner sc) {

        String userName = Validator.getNonEmptyString(sc, "Username");

        if (userName == null) {
            return;
        }

        UserRes res =  userController.getByName(userName);
        checkNullResponse(res , "Fetched");
    }

    private void viewAllUsers() {

        LogUtil.userTable(userController.viewAll());

    }


    private void checkNullResponse(UserRes res , String operation){

        if(res != null){

            System.out.println("Successfully " + operation + " User...");
            LogUtil.userRecord(res);
            return;
        }

        System.out.println("Failed " + operation + " User...");
    }
}
