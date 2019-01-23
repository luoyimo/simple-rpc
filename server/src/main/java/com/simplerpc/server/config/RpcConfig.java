package com.simplerpc.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author hu
 * @Description:
 * @Date Create In 17:31 2019/1/22 0022
 */
@Component
public class RpcConfig implements ApplicationContextAware, InitializingBean {

    private final Logger log = LoggerFactory.getLogger(getClass());


    private ApplicationContext applicationContext;


    public static Map<String, Object> cache = new HashMap<>();

    @Value("${rpc.port}")
    private Integer port;


    @Override
    public void afterPropertiesSet() throws IOException {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(Service.class);
        for (Object bean : beansWithAnnotation.values()) {
            Class<?> clazz = bean.getClass();
            Class<?>[] interfaces = clazz.getInterfaces();
            for (Class<?> inte : interfaces) {
                cache.put(getClassName(inte.getName()), bean);
                log.info("remote service name:" + inte.getName());
            }
        }
        startPort();
    }


    private String getClassName(String className) {
        String name = className.substring(className.lastIndexOf(".") + 1);
        name = name.substring(0, 1).toLowerCase() + className.substring(1);
        return name;
    }

    private void startPort() throws IOException {
        //服务端监听客户端请求的TCP连接
        ServerSocket server = new ServerSocket(port);
        Socket client;
        while (true) {
            client = server.accept();
            log.info("客户端连接成功！");
            //处理客户请求 TODO 可加入线程池执行
            new Thread(new ServerThread(client)).start();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
