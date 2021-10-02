package servers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import config.MyConfig;
import servers.worker.WorkerThreadHTTP;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HTTPServers {
    public static final ExecutorService threadPool = Executors.newFixedThreadPool(MyConfig.threadPollSize);

    public HTTPServers(int port) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        System.out.println("HTTPServer started on port: " + port);
        server.createContext("/Java",new MyHandler());
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange){

            threadPool.execute(new WorkerThreadHTTP(exchange));
        }
    }
}