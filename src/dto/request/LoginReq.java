package dto.request;

import util.HashUtil;

public class LoginReq{

    private String email;
    private String password;

    public LoginReq(String email , String password){

        this.email = email;
        this.password = HashUtil.makeHashedPassword(password);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


}