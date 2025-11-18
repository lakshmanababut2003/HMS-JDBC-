package views;

import java.util.Scanner;

import dto.response.UserRes;
import exceptions.NotFoundException;
import factory.MenusFactory;
import menus.Menus;
import util.InputUtil;
import util.LogUtil;
import util.Validator;
import views.interfaces.ManageMenuView;
import views.interfaces.RoleView;

public class AdminDashboard implements RoleView {

    private final UserRes user;

    public AdminDashboard(UserRes user){
        this.user = user;
    }

    public void showDashboard(){

        LogUtil.print("Welcome Back : " + user.getUsername());
        int choice;
        Scanner sc = InputUtil.getScanner();

        do{

            Menus.adminMenu();
            choice = Validator.getValidChoice(sc);

            switch(choice){

                case 1:{
                    redirect("role");
                    break;
                }
                case 2:{
                    redirect("department");
                    break;
                }
                case 3:{
                    redirect("user");
                    break;
                }
                case 4:{
                    redirect("doctor");
                    break;
                }
                case 5:{
                    redirect("labtest");
                    break;
                }
                case 6:{
                    LogUtil.print("Logout...");
                    break;
                }
                default:{
                    LogUtil.print("Wrong Choice...Try Again...");
                    break;
                }
            }

        }
        while(choice != 6);

    }

    private void redirect(String menuName){

        try{
            ManageMenuView menu = MenusFactory.getMenu(menuName);
            menu.showMenus();
        }
        catch(NotFoundException e){
            System.out.println(e.getMessage());
        }

        
    }
    
}
