package servers;


import config.MyConfig;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import servers.worker.WorkerThreadWebSocket;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebSocketServers extends WebSocketServer {
    public ExecutorService threadPool = Executors.newFixedThreadPool(MyConfig.threadPollSize);
    public WebSocketServers(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        System.out.println(webSocket.getRemoteSocketAddress().getAddress().getHostAddress() + " connected");
    }

    @Override
    public void onClose(WebSocket webSocket, int code, String reason, boolean remote) {
        System.out.println("Client close");
    }

    @Override
    public void onMessage(WebSocket webSocket, String message) {
        threadPool.execute(new WorkerThreadWebSocket(webSocket,message));
    }

    @Override
    public void onError(WebSocket webSocket, Exception exception) {
        exception.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("WebSocketServer started on port: " + this.getPort());
    }
}
