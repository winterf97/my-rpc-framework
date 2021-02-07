import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class RpcClient {

    private String host;
    private int port;

    public RpcResponse sendRequest(RpcRequest rpcRequest, String host, int port) throws Exception {
        Socket socket = new Socket(host, port);
        /**
         * 因为这里是传输对象，所以用ObjectStream
         * 问题：不会操作IO？？？？？？？
         */

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        objectOutputStream.writeObject(rpcRequest);
        objectOutputStream.flush();

//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
//        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
//
//        objectOutputStream.writeObject(rpcRequest);
//        objectOutputStream.flush();

        return (RpcResponse) objectInputStream.readObject();

    }


    public static void main(String[] args) {
        RpcClientProxy rpcClientProxy = new RpcClientProxy("127.0.0.1", 8080);
        HelloRpcServer helloRpcServer = rpcClientProxy.getProxy(HelloRpcServer.class);

        RpcObject rpcObject = new RpcObject("hello rpc server");
        String hello = helloRpcServer.hello(rpcObject);
        System.out.println(hello);

    }
}
