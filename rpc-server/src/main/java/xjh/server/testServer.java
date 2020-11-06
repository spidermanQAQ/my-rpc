package xjh.server;

import xjh.api.HelloService;
import xjh.api.HelloServiceImpl;
import xjh.core.server.RpcServer;

public class testServer {
    public static void main(String[] args) {
        HelloService helloService=new HelloServiceImpl();
        RpcServer rpcServer=new RpcServer();
        rpcServer.register(helloService,9000);
    }
}
