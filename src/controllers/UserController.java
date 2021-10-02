package controllers;

import models.UserModel;
import servers.worker.Response;
import services.MyMessage;
import services.MyStatus;
import services.Service;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    public static Response add(UserModel data){

        String sqlCheck ="select * from users where username='"+data.username+"'";
        List<Object> list = DatabaseController.select(sqlCheck);
        int num = list.size();
        if(num > 0) {
            return Service.getRes(new ArrayList<>(),data.username+ MyMessage.alreadyExits, MyStatus.fail);
        }else{

            String sql ="insert into users (name,username,password,phoneNumber,remark)"+
                    "value('"+data.name+"','"+data.username+"','"+data.password+"','"+data.phoneNumber+"','"+data.remark+"')";
            return  DatabaseController.insert(sql);
        }
    }

    public static Response update(UserModel data){
        String sqlCheck ="select * from users where username='"+data.username+"' and id!='"+data.id+"'";
        List<Object> list = DatabaseController.select(sqlCheck);
        int num = list.size();

        if(num > 0) {
            return Service.getRes(new ArrayList<>(),data.username+ MyMessage.alreadyExits, MyStatus.fail);
        }else {
            String sql = "update users set name='" + data.name + "',username='" + data.username + "',password='" + data.password + "'" +
                    ",phoneNumber='" + data.phoneNumber + "',remark='" + data.remark + "' where id='" + data.id + "'";
            return DatabaseController.update(sql);
        }
    }

    public static Response delete(UserModel data){
        String sql ="delete from users where id='"+data.id+"'";
        return DatabaseController.delete(sql);
    }

    public static Response listOne(UserModel data){
        String sql = "select * from users where id='"+data.id+"'";
        return DatabaseController.selectOne(sql);
    }

    public static Response listAll(){
        String sql = "select * from users order by id desc";
        return DatabaseController.selectAll(sql);
    }

    public static Response listPage(UserModel data){
        int page = data.page;
        int limit = data.limit;
        int offset = ((page-1)*limit);

        String sqlCount = "select count(*) as num from users";
        String sqlPage = "select * from users  order by id desc limit "+limit+" offset "+offset+"";

        return DatabaseController.selectPage(sqlCount,sqlPage);
    }
}
