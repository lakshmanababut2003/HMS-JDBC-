package views.ManageMenus;

import java.util.Scanner;

import controllers.ManageMenus.DepartmentCRUD;
import dto.request.DepartmentReq;
import dto.response.DepartmentRes;
import menus.Menus;
import util.InputUtil;
import util.LogUtil;
import util.Validator;
import views.interfaces.ManageMenuView;

public class DepartmentMenu implements ManageMenuView {

    private final DepartmentCRUD departmentController;

    public DepartmentMenu(){
        this.departmentController = new DepartmentCRUD();
    }

    @Override
    public void showMenus(){

        int  choice;
        Scanner sc = InputUtil.getScanner();

        do{
            Menus.ManageMenusforRoleAndDpt("Department");
            choice = Validator.getValidChoice(sc);

            switch(choice){

                case 1:{
                    addOrUpdateDepartment(sc, "add");
                    break;
                }
                case 2:{
                    addOrUpdateDepartment(sc, "update");
                    break;
                }
                case 3:{
                    viewAllDepartments();
                    break;
                }
                case 4:{
                    LogUtil.print("Go to Back...");
                    break;
                }
                default:{
                    LogUtil.print("Wrong Choice... try Again");
                    break;
                }

            }

        }
        while(choice != 4);

    }

    private void addOrUpdateDepartment(Scanner sc , String operation){

        int operationId = 0;

        if(operation.equals("update")){
            operationId = 1;
        }

        String oldDptName = "";

        if(operationId == 1){

            viewAllDepartments();
            oldDptName = Validator.getNonEmptyString(sc, "Existing Department Name ");

            if(oldDptName == null){
                return;
            }

        }

        String dptName = Validator.getNonEmptyString(sc, "Department Name");

        if(dptName == null){
            return;
        }

        DepartmentRes res;

        if(operationId == 0){
            res = departmentController.add(new DepartmentReq(dptName));
        }
        else{
            res = departmentController.update(new DepartmentReq(oldDptName, dptName));
        }

        checkNullResponse(res, operationId == 0 ? "Added" : "Updated");


    }

    private void viewAllDepartments(){

        LogUtil.deptTable(departmentController.viewAll());
    }

     private void checkNullResponse(DepartmentRes res, String operation) {

        if (res != null) {
            System.out.println("Role " + operation + " successfully...");
            LogUtil.deptRecord(res);
            return;
        }

        System.out.println("Faile to " + operation + " Role");

    }
    
}
