package services;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import servers.worker.Request;
import servers.worker.Response;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Service {
    public static Request readResquest(InputStreamReader isr){
        try{
            BufferedReader br = new BufferedReader(isr);

            int b;
            StringBuilder buf = new StringBuilder();
            while ((b = br.read()) != -1) {
                buf.append((char) b);
            }
            br.close();
            isr.close();

            System.out.println("📲📲 Data coming :\n" +buf);
            Gson gson = new Gson();
            return gson.fromJson(buf.toString(),Request.class);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new Request();
        }
    }
    public static void writeResponse(HttpExchange exchange,Response obj) {
        try{
            Gson gson = new Gson();
            String data = gson.toJson(obj);

            String encoding = "UTF-8";
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=" + encoding);
            byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, bytes.length);

            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();

            System.out.println("🚀🚀 Reply to client: \n"+data);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static Response getRes(List<Object> data,String message,int status){
        Response response = new Response();
        response.data = data;
        response.message = message;
        response.status = status;
        return response;
    }
}
