import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注意多线程并发的问题
 */
public class DefaultServiceRegistry implements ServiceRegistry{

    private static final Logger logger = LoggerFactory.getLogger(DefaultServiceRegistry.class);

    /**
     * 注意，这里设置的是并发map
     */
    private final Map<String, Object> serviceMap = new ConcurrentHashMap<>();
    private final Set<String> registeredService = ConcurrentHashMap.newKeySet();

    @Override
    public synchronized <T> void register(T service) {
        /**
         * 学习 ： .getCanonicalName()
         * 方法                 值
         * getName            [Ljava.lang.String;
         * getCanonicalName   java.lang.String[]
         * getSimpleName      String[]
         * 要获取类 所以要具体的 可以解析的类名
         */
        String serviceName = service.getClass().getCanonicalName();

        if(registeredService.contains(serviceName)) return;

        registeredService.add(serviceName);
        Class<?>[] interfaces = service.getClass().getInterfaces();

        /**
         * 自定义的异常处理 ！！！！！
         */
//        if(interfaces.length == 0) {
//            throw new RpcException(RpcError.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE);
//        }

        for(Class<?> i : interfaces) {
            serviceMap.put(i.getCanonicalName(), service);
        }

        logger.info("向接口: {} 注册服务: {}", interfaces, serviceName);

    }

    @Override
    public Object getService(String serviceName) {
        Object service = serviceMap.get(serviceName);

//        if(service == null) {
//            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
//        }

        return service;
    }
}
