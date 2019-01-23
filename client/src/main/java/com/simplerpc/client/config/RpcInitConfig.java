package com.simplerpc.client.config;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

/**
 * @Author hu
 * @Description:
 * @Date Create In 19:24 2019/1/21 0021
 */
public class RpcInitConfig implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        ClassPathScanningCandidateComponentProvider provider = getScanner();
        //设置扫描器
        provider.addIncludeFilter(new AnnotationTypeFilter(RpcClient.class));
        //扫描此包下的所有带有@RpcClient的注解的类
        Set<BeanDefinition> beanDefinitionSet = provider.findCandidateComponents("com.simplerpc.client.remote");
        for (BeanDefinition beanDefinition : beanDefinitionSet) {
            if (beanDefinition instanceof AnnotatedBeanDefinition) {

                String beanClassName = beanDefinition.getBeanClassName();
                //将RpcClient的工厂类注册进去
                BeanDefinitionBuilder builder = BeanDefinitionBuilder
                        .genericBeanDefinition(RpcClientFactoryBean.class);
                //设置RpcClinetFactoryBean工厂类中的构造函数的值
                builder.addConstructorArgValue(beanClassName);
                builder.getBeanDefinition().setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
                //将其注册进容器中
                beanDefinitionRegistry.registerBeanDefinition(beanClassName,
                        builder.getBeanDefinition());
            }
        }
    }


    protected ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(false) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                return beanDefinition.getMetadata().isInterface()
                        && beanDefinition.getMetadata().isIndependent();
            }
        };
    }

}
