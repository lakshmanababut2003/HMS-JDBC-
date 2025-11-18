package views.ManageMenus;

import java.util.Scanner;

import controllers.ManageMenus.RoleCRUD;
import dto.request.RoleReq;
import dto.response.RoleRes;
import menus.Menus;
import util.InputUtil;
import util.LogUtil;
import util.Validator;
import views.interfaces.ManageMenuView;

public class RoleMenu implements ManageMenuView {

    private final RoleCRUD roleController = new RoleCRUD();

    @Override
    public void showMenus() {
        int choice;
        Scanner sc = InputUtil.getScanner();

        do {
            Menus.ManageMenusforRoleAndDpt("Role");
            choice = Validator.getValidChoice(sc);

            switch (choice) {
                case 1: {
                    addOrUpdateRole(sc , "add");
                    break;
                }
                case 2: {
                    addOrUpdateRole(sc , "update");
                    break;
                }
                case 3: {
                    viewAllRoles();
                    break;
                }
                case 4: {
                    LogUtil.print("Go to Back...");
                    break;
                }
                default: {
                    LogUtil.print("Wrong Choice... Try Again...");
                    break;
                }
            }

        } while (choice != 4);

    }

    private void addOrUpdateRole(Scanner sc, String operation) {

        int operationId = 0;
        String oldRoleName = "";

        if (operation.equals("update")) {
            operationId = 1;
        }

        if (operationId == 1) {

            viewAllRoles();
            oldRoleName = Validator.getNonEmptyString(sc, "Existing Role Name");

            if (oldRoleName == null) {
                return;
            }

        }

        String roleName = Validator.getNonEmptyString(sc, "Role Name ");

        if(roleName == null){
            return;
        }

        RoleRes res;

        if(operationId == 0){
            res = roleController.add(new RoleReq(roleName));
        }
        else{
            res = roleController.update(new RoleReq(oldRoleName , roleName));
        }

        checkNullResponse(res, operationId == 0 ? "Added" : "Updated");


    }

    private void viewAllRoles() {

        LogUtil.roleTable(roleController.viewAll());
    }

    private void checkNullResponse(RoleRes res, String operation) {

        if (res != null) {
            System.out.println("Role " + operation + " successfully...");
            LogUtil.roleRecord(res);
            return;
        }

        System.out.println("Faile to " + operation + " Role");

    }

}
