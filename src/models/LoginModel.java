package models;

import services.MyMessage;

public class LoginModel {
    public String username;
    public String password;

    public String validateAll(){
        if(username.trim().isEmpty()) return "username"+ MyMessage.isEmpty;
        if(password.trim().isEmpty()) return "password"+MyMessage.isEmpty;
        return "true";
    }
}
