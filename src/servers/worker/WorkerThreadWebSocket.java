package servers.worker;

import api.BaseApi;
import com.google.gson.Gson;
import org.java_websocket.WebSocket;
import services.MyMessage;
import services.MyStatus;
import services.Service;

import java.util.ArrayList;

public class WorkerThreadWebSocket implements Runnable{

    private final WebSocket webSockets;
    private final String message;

    public WorkerThreadWebSocket(WebSocket webSocket, String message){
        this.message = message;
        this.webSockets = webSocket;
    }

    @Override
    public void run() {

        try{
            System.out.println("Message form client: \n" + message);
            Gson gson = new Gson();
            Request request = gson.fromJson(message,Request.class);

            Response response = BaseApi.checkObject(request);
            response.object = request.object;
            response.method = request.method;

            String data = gson.toJson(response);
            webSockets.send(data);

            System.out.println("Reply to client: \n"+data);
        }catch (Exception e){
            System.out.println(MyMessage.socketError + e.getMessage());
            Gson gson = new Gson();
            webSockets.send(gson.toJson(Service.getRes(new ArrayList<>(),MyMessage.httpError, MyStatus.fail)));
        }
    }
}
