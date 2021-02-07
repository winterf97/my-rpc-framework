import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * request只负责传送请求，具体的请求内容，应该封装在函数的参数里面。
 */

@Data
@Builder
public class RpcRequest implements Serializable {

    /**
     * 待调用接口名称
     */
    private String interfaceName;
    /**
     * 待调用方法名称
     */
    private String methodName;
    /**
     * 调用方法的参数
     */
    private Object[] parameters;
    /**
     * 调用方法的参数类型
     */
    private Class<?>[] paramTypes;
}
