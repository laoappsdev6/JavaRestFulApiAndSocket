package servers.worker;

import api.BaseApi;
import com.sun.net.httpserver.HttpExchange;

import services.MyMessage;
import services.MyStatus;
import services.Service;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class WorkerThreadHTTP implements Runnable{
    private final HttpExchange exchange;
    public WorkerThreadHTTP(HttpExchange exchange){
        this.exchange = exchange;
    }

    @Override
    public void run() {

        try{
            InputStreamReader isr =  new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
            Request request = Service.readResquest(isr);

            Response response = BaseApi.checkObject(request);
            response.object = request.object;
            response.method = request.method;

            Service.writeResponse(exchange, response);
        }catch (Exception e){
            System.out.println(MyMessage.httpError+e.getMessage());
            Service.writeResponse(exchange,Service.getRes(new ArrayList<>(), MyMessage.httpError,MyStatus.fail));
        }

    }
}
