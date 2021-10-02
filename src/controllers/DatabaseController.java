package controllers;

import servers.worker.Response;
import services.MyMessage;
import services.MyStatus;
import services.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseController {
    public static Connection Connect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
        } catch (Exception e) {
            System.out.println("Connection Error: " + e.getMessage());
            return null;
        }
    }

    public static Boolean query(String sql) {
        try{
            Connection conn = Connect();
            if (conn == null) return false;
            if (sql.isEmpty()) return false;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();
            stmt.close();
            conn.close();
            return  true;
        }catch (SQLException e){
            System.out.println("Query Error: "+e.getMessage());
            return false;
        }
    }

    public static List<Object> select(String sql){
        try{
            Connection conn = Connect();
            if (conn == null) return new ArrayList<>();
            if (sql.isEmpty()) return new ArrayList<>();

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            List<Object> list = new ArrayList<>();

            while (rs.next()){
                HashMap<String ,Object> map = new HashMap<>();

                ResultSetMetaData rsMetaData = rs.getMetaData();
                int numColumns = rsMetaData.getColumnCount();

                for (int i=1; i<=numColumns; i++) {
                    String column_name = rsMetaData.getColumnName(i);
                    map.put(column_name, rs.getString(column_name));
                }

                list.add(map);
            }
            stmt.close();
            rs.close();
            conn.close();
            return list;
        }catch (SQLException e){
            System.out.println("Query Error: "+e.getMessage());
            return new ArrayList<>();
        }
    }

    public static int count(String sql){
        try{
            Connection conn = Connect();
            if (conn == null) return 0;
            if (sql.isEmpty()) return 0;

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return rs.getInt("num");
            }
            stmt.close();
            rs.close();
            conn.close();
            return 0;
        }catch (SQLException e){
            System.out.println("Query Error: "+e.getMessage());
            return 0;
        }
    }

    public static Response insert(String sql) {
         if(query(sql)){
             return Service.getRes(new ArrayList<>(), MyMessage.addSuccess, MyStatus.success);
         }else{
             return Service.getRes(new ArrayList<>(),MyMessage.addFail,MyStatus.fail);
         }
    }

    public static Response update(String sql) {
        if(query(sql)){
            return Service.getRes(new ArrayList<>(), MyMessage.updateSuccess, MyStatus.success);
        }else{
            return Service.getRes(new ArrayList<>(),MyMessage.updateFail,MyStatus.fail);
        }
    }

    public static Response delete(String sql) {
        if(query(sql)){
            return Service.getRes(new ArrayList<>(), MyMessage.deleteSuccess, MyStatus.success);
        }else{
            return Service.getRes(new ArrayList<>(),MyMessage.deleteFail,MyStatus.fail);
        }
    }

    public static Response selectOne(String sql){
        return Service.getRes(select(sql),MyMessage.listOne,MyStatus.success);
    }

    public static Response selectAll(String sql){
        return Service.getRes(select(sql), MyMessage.listAll, MyStatus.success);
    }

    public static Response selectPage(String sqlCount,String sqlPage) {

        int numCount = count(sqlCount);
        List<Object> list = new ArrayList<>();
        HashMap<String,Object> map = new HashMap<>();
        if(numCount > 0){
            List<Object> rsPage = select(sqlPage);
            map.put("count",numCount);
            map.put("rows",rsPage);
        }else{
            map.put("count",numCount);
            map.put("rows",new ArrayList<>());
        }
        list.add(map);
        return Service.getRes(list,MyMessage.listPage,MyStatus.success);
    }
}


