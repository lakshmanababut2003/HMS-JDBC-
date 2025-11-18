package factory;

import exceptions.NotFoundException;
import views.ManageMenus.DepartmentMenu;
import views.ManageMenus.DoctorMenu;
import views.ManageMenus.LabTestMenu;
import views.ManageMenus.RoleMenu;
import views.ManageMenus.UserMenu;
import views.interfaces.ManageMenuView;

public class MenusFactory {

    public static ManageMenuView getMenu(String menuName){

        switch(menuName){
            case "role" :{
                return new RoleMenu();
            }
            case "user":{
                return new UserMenu();
            }
            case "labtest":{
                return new LabTestMenu();
            }
            case "department":{
                return new DepartmentMenu();
            }
            case "doctor":{
                return new DoctorMenu();
            }
            default:{
                throw new NotFoundException("Menu was not found (404)");
            }
        }

    } 
    
}
