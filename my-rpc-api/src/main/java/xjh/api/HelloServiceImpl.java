package xjh.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloServiceImpl implements HelloService {
    private static final Logger logger= LoggerFactory.getLogger(HelloServiceImpl.class);
    public String hello(HelloObject object) {
        logger.info("接收到："+object.getMessage());
        return "这是调用的返回值"+object.getId();
    }
}
