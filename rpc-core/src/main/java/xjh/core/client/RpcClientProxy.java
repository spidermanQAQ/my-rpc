package xjh.core.client;

import xjh.common.entity.RpcRequest;
import xjh.common.entity.RpcResponse;
import xjh.core.client.RpcClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RpcClientProxy implements InvocationHandler {

    private String host;

    private int port;

    public RpcClientProxy(String host,int port){
        this.host=host;
        this.port=port;
    }

    //得到动态代理对象
    public <T>T getProxy(Class<T> clazz){
        //ClassLoader,Class<?>[] interfaces,InvocationHandler
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz},this);
    }

    //执行被代理对象的任何方法都会经过invoke方法
    //proxy代理对象本身，而不是目标对象，一般不用他
    // Method method：本次被调用的代理对象的方法
    // Obeject[] args：本次被调用的代理对象的方法参数
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameters(args)
                .paramTypes(method.getParameterTypes())
                .build();
        RpcClient rpcClient=new RpcClient();
        return ((RpcResponse) rpcClient.sendRequest(rpcRequest, host, port)).getData();

    }
}
