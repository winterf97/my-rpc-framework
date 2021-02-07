import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class RpcServer {

    private int port;
    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);

    public RpcServer(int port) {
        this.port = port;
    }

    public void start() throws Exception{
        /**
         * server是在本机开启的，当然不需要host了
         */

        logger.info("服务器正在启动...");
        ServerSocket serverSocket = new ServerSocket(port);

        Socket client = serverSocket.accept();

        logger.info("客户端连接！Ip为：" + client.getInetAddress());

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getOutputStream());
        ObjectInputStream objectInputStream = new ObjectInputStream(client.getInputStream());

        RpcRequest rpcRequest = (RpcRequest)objectInputStream.readObject();

        /**
         * 利用反射来加载类,需要学习一下
         */
        Class<?> tagetClass = HelloRpcServerImpl.class;
        Method method = tagetClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
        Object invoke = method.invoke(new HelloRpcServerImpl(), rpcRequest.getParameters());
        RpcResponse rpcResponse = new RpcResponse((String) invoke);

        objectOutputStream.writeObject(rpcResponse);
        objectOutputStream.flush();
    }


    public static void main(String[] args) throws Exception {
        RpcServer rpcServer = new RpcServer(8080);
        rpcServer.start();

    }


}
