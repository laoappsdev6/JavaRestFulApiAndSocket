package api;

import com.google.gson.Gson;
import controllers.LoginController;
import models.LoginModel;
import org.json.JSONObject;
import servers.worker.Request;
import servers.worker.Response;
import services.MyMessage;
import services.MyMethod;
import services.MyStatus;
import services.Service;

import java.util.ArrayList;

public class LoginApi {
    public static Response checkMethod(Request obj){

        try{

            if(obj.data.isEmpty() && !obj.method.equals(MyMethod.listAll)) return Service.getRes(new ArrayList<>(),MyMessage.dataEmpty,MyStatus.fail);

            Gson gson = new Gson();
            LoginModel loginModel = gson.fromJson(new JSONObject(obj.data).toString(),LoginModel.class);

            switch (obj.method){
                case MyMethod.authorize:
                    String validateAdd = loginModel.validateAll();
                    if(!validateAdd.equals("true")) return Service.getRes(new ArrayList<>(), validateAdd, MyStatus.fail);
                    return LoginController.authorize(loginModel);
                default:
                    return Service.getRes(new ArrayList<>(), MyMessage.methodNotFound, MyStatus.fail);
            }

        }catch (Exception e){
            System.out.println("Api error: "+e.getMessage());
            return Service.getRes(new ArrayList<>(),MyMessage.dataError,MyStatus.fail);
        }
    }
}
