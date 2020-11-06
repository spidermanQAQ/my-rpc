package xjh.server;

import xjh.api.HelloService;
import xjh.api.HelloServiceImpl;
import xjh.core.register.DefaultServiceRegistry;
import xjh.core.register.ServiceRegistry;
import xjh.core.server.RpcServer;

public class testServer {
    public static void main(String[] args) {
        HelloService helloService=new HelloServiceImpl();
        ServiceRegistry serviceRegistry=new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        RpcServer rpcServer=new RpcServer(serviceRegistry);
        rpcServer.start(9000);
    }
}
