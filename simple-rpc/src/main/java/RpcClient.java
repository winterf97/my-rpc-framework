import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RpcClient {
    private static final Logger logger = LoggerFactory.getLogger(RpcClient.class);

    public Object sendRequest(RpcRequest rpcRequest, String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("调用时有错误发生：", e);
            return null;
        }
    }

    public static void main(String[] args) {
//        RpcClient rpcClient = new RpcClient();
//        HelloObject helloObject = new HelloObject(123, "来自客户端的请求");
//        RpcRequest rpcRequest =
//                new RpcRequest(
//                        "HelloService",
//                        "hello",
//                        new Object[]{helloObject},
//                        new Class[]{helloObject.getClass()}
//                );
//        rpcClient.sendRequest(rpcRequest, "localhost", 8080);


        RpcClientProxy proxy = new RpcClientProxy("127.0.0.1", 8080);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "来自客户端的请求");
        String res = helloService.hello(object);
        System.out.println(res);

    }
}
