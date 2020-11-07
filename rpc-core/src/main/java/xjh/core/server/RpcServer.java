package xjh.core.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xjh.common.entity.RpcRequest;
import xjh.core.register.ServiceRegistry;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class RpcServer {

    private final ExecutorService threadPool;

    private static final Logger logger= LoggerFactory.getLogger(RpcServer.class);

    private final ServiceRegistry serviceRegistry;

    private RequestHandle requestHandle=new RequestHandle();

    public RpcServer(ServiceRegistry serviceRegistry){

        this.serviceRegistry=serviceRegistry;
        //核心线程数
        int corePoolSize=5;
        //最大线程数
        int maximumPoolSize=50;
        //线程活动保持时间
        int keepAliveTime=60;
        //TimeUnit.SECONDS线程活动保持的单位时间
        //工作队列
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(100);
        //创建线程的工厂
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workingQueue, threadFactory);
    }

    public void start(int port){
        try(ServerSocket serverSocket=new ServerSocket(port)) {
            logger.info("服务正在启动");
            Socket socket;
            while((socket= serverSocket.accept())!=null){
                logger.info("客户端已连接，IP为:"+socket.getInetAddress());
                threadPool.execute(new RequestHandleThread(socket,serviceRegistry,requestHandle));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            logger.info("连接时有错误发送");
            e.printStackTrace();
        }
    }
}
