package api;

import servers.worker.Request;
import servers.worker.Response;
import services.*;

import java.util.ArrayList;


public class BaseApi {
    public static Response checkObject(Request obj){
        try{

            if(!obj.object.equals(MyObject.login))
                if(!Jwt.jwtValidation(obj.token)) return Service.getRes(new ArrayList<>(),MyMessage.noAuthorize,MyStatus.fail);

            switch (obj.object){
                case MyObject.user:
                    return UserApi.checkMethod(obj);
                case MyObject.login:
                    return LoginApi.checkMethod(obj);
                default:
                    return Service.getRes(new ArrayList<>(),MyMessage.objectNotFound, MyStatus.fail);
            }

        }catch (Exception e){
            System.out.println("Base api error: "+e.getMessage());
            return Service.getRes(new ArrayList<>(),MyMessage.objectError,MyStatus.fail);
        }
    }
}
