package menus;

public class Menus {

    public static void entryMenu(){

        System.out.println();
        System.out.println("----------Welcome To HMS--------------");
        System.out.println("1.Login");
        System.out.println("2.Exit");
        System.out.println();
    }

    public static void adminMenu(){

        System.out.println();
        System.out.println("-------------- Admin Dashboard -----------------");
        System.out.println("1.Manage Roles");
        System.out.println("2.Manage Departments");
        System.out.println("3.Manage Users (Receptionsit , Pharmacist , LabTechician , Patient , Doctor)");
        System.out.println("4.Manage Doctors");
        System.out.println("5.Manage LabTest");
        System.out.println("6.Logout");

    }

    public static void ManageMenus(String menu){

         System.out.println();
        System.out.println("-------------- "+ menu +" Menu --------------------");
        System.out.println("1.Add New " + menu);
        System.out.println("2.Update " + menu);
        System.out.println("3.View All " + menu);
        System.out.println("4.View "+ menu + " By Name ");
        System.out.println("5.Back");
        System.out.println();

    }

    public static void ManageMenusforRoleAndDpt(String menu){

         System.out.println();
        System.out.println("-------------- "+ menu +" Menu --------------------");
        System.out.println("1.Add New " + menu);
        System.out.println("2.Update " + menu);
        System.out.println("3.View All " + menu);
        System.out.println("4.Back");
        System.out.println();

    }


    public static void doctorMenu(){

        System.out.println();
        System.out.println("-------------- Doctor Dashboard -------------------");
        System.out.println("1.Generate Slots");
        System.out.println("2.View My Slots");
        System.out.println("3.View My Appointments");
        System.out.println("4.Update Appointment Status");
        System.out.println("5.Update Prescription");
        System.out.println("6.View Prescription");
        System.out.println("7.Request Lab Test");
        System.out.println("8.View Lab Test Result");
        System.out.println("9.Logout");
        System.out.println();

    }

    public static void patientMenu(){

        System.out.println();
        System.out.println("-------------- Patient Dashboard -------------------");
        System.out.println("1.View Appointments");
        System.out.println("2.Book New Appointment");
        System.out.println("3.View Test Reports");
        System.out.println("4.View My Prescrption");
        System.out.println("5.Logout");
        System.out.println();

    }

    public static void labTechMenu(){

        System.out.println();
        System.out.println("---------------- Lab Technician Dashborad ---------------------");
        System.out.println("1.View Assigned Lab Test");
        System.out.println("2.Update Lab Test Result");
        System.out.println("3.View Complete Lab Results");
        System.out.println("4.Logout");
        System.out.println();

    }

    public static void pharamacistMenu(){

        System.out.println();
        System.out.println("------------------- Pharmacist Dashboard ----------------------");
        System.out.println("1.Add New Medicine");
        System.out.println("2.View All Medicine");
        System.out.println("3.Update Medicine");
        System.out.println("4.Issuse to Patients");
        System.out.println("5.Logout");
        System.out.println();

    }

    public static void receptionsitMenu(){
        System.out.println();
        System.out.println("---------------- Receptionist Dashboard --------------------");
        System.out.println("1.Register New Patient");
        System.out.println("2.View All Patients");
        System.out.println("3.View Patient By Username");
        System.out.println("4.Make Appointment");
        System.out.println("5.View All Appointments");
        System.out.println("6.Logout");
        System.out.println();
    }
    
}
