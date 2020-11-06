package xjh.core.server;

import xjh.common.entity.RpcRequest;
import xjh.common.entity.RpcResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class WorkerThread implements Runnable {
    private Socket socket;
    private Object service;

    public WorkerThread(Socket socket,Object service){
        this.socket=socket;
        this.service=service;
    }

    @Override
    public void run() {
        try(ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            //参数：方法名，参数类型
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
            Object returnObject = method.invoke(service, rpcRequest.getParameters());
            objectOutputStream.writeObject(RpcResponse.success(returnObject));
            objectOutputStream.flush();
            //ClassNotFoundException objectInputStream.readObject可能会出现的问题
            //NoSuchMethodException  service.getClass().getMethod可能会出现的问题
            //IllegalAccessException(没有权限异常) method.invoke可能会出现的问题
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }



}
