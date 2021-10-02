package models;

import services.MyMessage;

public class UserModel {
    public int id;
    public String name;
    public String username;
    public String password;
    public String phoneNumber;
    public String remark;

    public int page;
    public int limit;
    public String validateAll(){
        if(name.trim().isEmpty()) return "name"+ MyMessage.isEmpty;
        if(username.trim().isEmpty()) return "username"+ MyMessage.isEmpty;
        if(password.trim().isEmpty()) return "password"+MyMessage.isEmpty;
        if(phoneNumber.trim().isEmpty()) return "phoneNumber"+MyMessage.isEmpty;
        return "true";
    }
}
