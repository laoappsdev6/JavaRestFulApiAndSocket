import config.MyConfig;
import servers.HTTPServers;

public class MainHTTP {
    public static void main(String[] args) throws Exception {
        new HTTPServers(MyConfig.httpPort);
    }
}
