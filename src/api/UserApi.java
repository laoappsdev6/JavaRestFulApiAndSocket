package api;

import com.google.gson.Gson;
import controllers.UserController;
import models.UserModel;
import org.json.JSONObject;
import servers.worker.Request;
import servers.worker.Response;
import services.MyMessage;
import services.MyMethod;
import services.MyStatus;
import services.Service;

import java.util.ArrayList;

public class UserApi {
    public static Response checkMethod(Request obj){

        try{

            if(obj.data.isEmpty() && !obj.method.equals(MyMethod.listAll)) return Service.getRes(new ArrayList<>(),MyMessage.dataEmpty,MyStatus.fail);

            Gson gson = new Gson();
            UserModel userModel = gson.fromJson(new JSONObject(obj.data).toString(),UserModel.class);

            switch (obj.method){
                case MyMethod.add:
                    String validateAdd = userModel.validateAll();
                    if(!validateAdd.equals("true")) return Service.getRes(new ArrayList<>(), validateAdd, MyStatus.fail);
                    return UserController.add(userModel);
                case MyMethod.update:
                    String validate = userModel.validateAll();
                    if(!validate.equals("true")) return Service.getRes(new ArrayList<>(), validate, MyStatus.fail);
                    return UserController.update(userModel);
                case MyMethod.delete:
                    return UserController.delete(userModel);
                case MyMethod.listOne:
                    return UserController.listOne(userModel);
                case MyMethod.listAll:
                    return UserController.listAll();
                case MyMethod.listPage:
                    return UserController.listPage(userModel);
                default:
                    return Service.getRes(new ArrayList<>(), MyMessage.methodNotFound, MyStatus.fail);
            }

        }catch (Exception e){
            System.out.println("Api error: "+e.getMessage());
            return Service.getRes(new ArrayList<>(),MyMessage.dataError,MyStatus.fail);
        }
    }
}
