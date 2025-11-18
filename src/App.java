import repo.initLoads.InitLoad;
import views.LoginView;

public class App {
    public static void main(String[] args) throws Exception {
        
        InitLoad initLoad = InitLoad.loadAllRepo();
        LoginView loginView = new LoginView();
        loginView.doLogin();
    }
}
