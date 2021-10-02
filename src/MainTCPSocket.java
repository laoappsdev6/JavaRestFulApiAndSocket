import config.MyConfig;
import servers.TCPSocketServer;

public class MainTCPSocket {
    public static void main(String[] args) {
        new TCPSocketServer(MyConfig.tcpSocketPort);
    }
}