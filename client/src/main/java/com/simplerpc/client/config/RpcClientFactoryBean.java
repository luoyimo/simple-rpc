package com.simplerpc.client.config;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Proxy;

/**
 * @Author hu
 * @Description:
 * @Date Create In 19:33 2019/1/21 0021
 */
public class RpcClientFactoryBean  implements FactoryBean {


    @Autowired
    private RpcDynamicPro rpcDynamicPro;

    private Class<?> classType;


    public RpcClientFactoryBean(Class<?> classType) {
        this.classType = classType;
    }

    @Override
    public Object getObject() {
        ClassLoader classLoader = classType.getClassLoader();
        Object object = Proxy.newProxyInstance(classLoader, new Class<?>[]{classType}, rpcDynamicPro);
        return object;
    }

    @Override
    public Class<?> getObjectType() {
        return this.classType;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }


}
