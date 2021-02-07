import lombok.Data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Data
public class RpcClientProxy implements InvocationHandler {

    private String host;
    private int port;

    public RpcClientProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 获取代理对象,这部分要好好学习一下！！！！！！！！！！！！！！！！
//     * @param tClass
     * @return
     */
//    public Object getProxy(Class tClass) {
//        return Proxy.newProxyInstance(
//                tClass.getClassLoader(),
//                tClass.getInterfaces(),
//                new RpcClientProxy(host, port)
//        );
//    }

    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameters(args)
                .paramTypes(method.getParameterTypes())
                .build();


        RpcClient rpcClient = new RpcClient();

        return rpcClient.sendRequest(rpcRequest, host, port).getMessage();
    }
}
