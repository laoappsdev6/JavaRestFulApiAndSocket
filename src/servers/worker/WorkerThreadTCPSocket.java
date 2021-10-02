package servers.worker;

import api.BaseApi;
import com.google.gson.Gson;
import services.MyMessage;
import services.MyStatus;
import services.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class WorkerThreadTCPSocket implements Runnable{

    private final ServerSocket serverSocket;

    public WorkerThreadTCPSocket(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {

        try{
            Socket socket = serverSocket.accept();
            System.out.println("New client connected");

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String message = reader.readLine();

            System.out.println("Message form client: \n"+message);

            Gson gson = new Gson();
            Request request = gson.fromJson(message,Request.class);

            Response response = BaseApi.checkObject(request);
            response.object = request.object;
            response.method = request.method;

            String data = gson.toJson(response);
            OutputStream output = socket.getOutputStream();
            output.write(data.getBytes());

            System.out.println("Reply to client: \n"+data);

            socket.close();
        }catch (Exception e){
            System.out.println(MyMessage.tcpError + e.getMessage());
        }
    }
}
