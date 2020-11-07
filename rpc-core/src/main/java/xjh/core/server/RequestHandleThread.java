package xjh.core.server;

import xjh.common.entity.RpcRequest;
import xjh.common.entity.RpcResponse;
import xjh.core.register.ServiceRegistry;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

//客户端请求处理线程
public class RequestHandleThread implements Runnable {

    private Socket socket;

    private ServiceRegistry serviceRegistry;

     private RequestHandle requestHandle;


    public RequestHandleThread(Socket socket, ServiceRegistry serviceRegistry,RequestHandle requestHandle){
        this.socket=socket;
        this.serviceRegistry=serviceRegistry;
        this.requestHandle=requestHandle;
    }


    @Override
    public void run() {
        try(ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(socket.getOutputStream())) {

            //拿到请求
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            //根据请求得到客户端想要访问的接口名
            String interfaceName = rpcRequest.getInterfaceName();
            //根据接口名从服务注册表中得到客户端调用的具体的服务
            Object service = serviceRegistry.getService(interfaceName);
            //执行服务
            Object result=requestHandle.handle(service,rpcRequest);
            //结果返回
            objectOutputStream.writeObject(RpcResponse.success(result));
            objectOutputStream.flush();
            //ClassNotFoundException objectInputStream.readObject可能引起
        } catch (IOException | ClassNotFoundException  e) {
            e.printStackTrace();
        }
    }
}
