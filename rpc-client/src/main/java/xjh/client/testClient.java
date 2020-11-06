package xjh.client;

import xjh.api.HelloObject;
import xjh.api.HelloService;
import xjh.core.client.RpcClientProxy;

public class testClient {
    public static void main(String[] args) {
        RpcClientProxy proxy=new RpcClientProxy("localhost",9000);
        HelloService service = proxy.getProxy(HelloService.class);
        HelloObject object=new HelloObject(12,"this is a message");
        String res=service.hello(object);
        System.out.println(res);

    }
}
