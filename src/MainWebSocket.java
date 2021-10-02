import config.MyConfig;
import servers.WebSocketServers;

public class MainWebSocket {
    public static void main(String[] args) {
        new WebSocketServers(MyConfig.webSocketPort).start();
    }
}
