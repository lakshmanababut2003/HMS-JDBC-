package factory;

import dto.response.UserRes;
import exceptions.NotFoundException;
import views.AdminDashboard;
import views.DoctorDashboard;
import views.LabTechDashboard;
import views.PatientDashboard;
import views.ReceptionistDashboard;
import views.interfaces.RoleView;

public class UserFactory {

    public static RoleView getController(UserRes user) {

        switch (user.getRole().toLowerCase()) {

            case "admin": {
                return new AdminDashboard(user);
            }
            case "doctor": {
                return new DoctorDashboard(user);
            }
            case "patient": {
                return new PatientDashboard(user);
            }
            case "receptionist": {
                return new ReceptionistDashboard(user);
            }
            case "labtechnician": {
                return new LabTechDashboard(user);
            }
            default: {
                throw new NotFoundException("The Role doesnot existing...");
            }
        }
    }

}