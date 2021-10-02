package servers;

import config.MyConfig;
import servers.worker.WorkerThreadTCPSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPSocketServer {
    public final ExecutorService threadPool = Executors.newFixedThreadPool(MyConfig.threadPollSize);
    public  TCPSocketServer (int port) {

        try(ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                threadPool.execute(new WorkerThreadTCPSocket(serverSocket));
            }
        } catch ( IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
