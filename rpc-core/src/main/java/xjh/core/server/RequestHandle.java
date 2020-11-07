package xjh.core.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xjh.common.entity.RpcRequest;
import xjh.common.entity.RpcResponse;
import xjh.common.enumeration.ResponseCode;

import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RequestHandle {

    private final Logger logger= LoggerFactory.getLogger(RequestHandle.class);
    public Object handle(Object service,RpcRequest rpcRequest) {
        Object result = null;
        try {
            result = invokeTargetMethod(service, rpcRequest);
            logger.info("方法开始执行");
        } catch (InvocationTargetException | IllegalAccessException e) {
            logger.info("调用时有错误发生：",e);

        }
        return result;
    }

    private Object invokeTargetMethod(Object service,RpcRequest rpcRequest) throws InvocationTargetException, IllegalAccessException {
        Method method;
        try {
            method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
        } catch (NoSuchMethodException e) {
            return RpcResponse.fail(ResponseCode.METHOD_NOT_FOUND);
        }
        return method.invoke(service, rpcRequest.getParameters());
    }


}
