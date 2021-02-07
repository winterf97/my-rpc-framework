public class HelloRpcServerImpl implements HelloRpcServer{
    @Override
    public String hello(RpcObject object) {
        /**
         * java打印的学习
         */
//        System.out.println("获取到的请求内容为 ： {}", object.getMessage());
        System.out.println("获取到的请求内容为 ：");
        System.out.println(object.getMessage());

        return "response from rpc server";
    }
}
