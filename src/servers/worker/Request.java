package servers.worker;

import java.util.HashMap;

public class Request {
    public String object = null;
    public String method;
    public HashMap<String,Object> data;
    public String token;
}
