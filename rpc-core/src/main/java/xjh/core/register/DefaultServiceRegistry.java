package xjh.core.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xjh.common.enumeration.RpcError;
import xjh.common.exception.RpcException;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultServiceRegistry implements ServiceRegistry {

    private static final Logger logger=LoggerFactory.getLogger(DefaultServiceRegistry.class);

    private final Map<String,Object> serviceMap=new ConcurrentHashMap<>();

    private final Set<String>registeredService=ConcurrentHashMap.newKeySet();
    @Override
    //注意这里的synchronized
    public synchronized <T> void register(T service) {
        //getName()返回的dao是虚拟机里面的class的表示
        //getCanonicalName()返回的是更容易权理解的表示,普通类getName和getCanonicalName差不多
        //数组等有区别
        String serviceName = service.getClass().getCanonicalName();
        if(serviceMap.containsKey(serviceName)) return;
        registeredService.add(serviceName);
        //getInterfaces获取某类实现的接口
        Class<?>[] interfaces = service.getClass().getInterfaces();
        if(interfaces.length==0){
            throw new RpcException(RpcError.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE);
        }
        for(Class<?>i:interfaces){
            //将服务名与提供服务的对象的对应关系保存在一个 ConcurrentHashMap
            serviceMap.put(i.getCanonicalName(),service);
        }
        logger.info("向接口注册服务",interfaces,service);
    }

    @Override
    public Object getService(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if(service==null){
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }
        return service;
    }
}
