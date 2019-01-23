package com.simplerpc.common;

import java.io.Serializable;

/**
 * @Author hu
 * @Description:
 * @Date Create In 19:36 2019/1/21 0021
 */
public class Request implements Serializable {

    /**
     * 类名称
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 参数类型
     */
    private Class<?>[] parameTypes;
    /**
     * 参数
     */
    private Object[] parameters;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameTypes() {
        return parameTypes;
    }

    public void setParameTypes(Class<?>[] parameTypes) {
        this.parameTypes = parameTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
