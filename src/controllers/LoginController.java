package controllers;

import models.LoginModel;
import org.json.JSONArray;
import org.json.JSONObject;
import servers.worker.Response;
import services.Jwt;
import services.MyMessage;
import services.MyStatus;
import services.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginController {
    public static Response authorize(LoginModel data){
        String sql ="select * from users where username='"+data.username+"' and password ='"+data.password+"'";
        List<Object> list = DatabaseController.select(sql);
        int num = list.size();

        if(num > 0){

            JSONArray jsonArray = new JSONArray(list);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String name = jsonObject.getString("name");
            String username = jsonObject.getString("username");
            String password = jsonObject.getString("phonenumber");

            String jwt = Jwt.jwtEncode(name,username,password);

            List<Object> dataRes = new ArrayList<>();
            HashMap<String,Object> map = new HashMap<>();
            map.put("token",jwt);
            map.put("row",list);
            dataRes.add(map);

            return Service.getRes(dataRes,MyMessage.loginSuccess,MyStatus.success);
        }else{
            return Service.getRes(new ArrayList<>(), MyMessage.aCountWrong, MyStatus.fail);
        }
    }
}
