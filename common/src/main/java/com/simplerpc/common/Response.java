package com.simplerpc.common;

import java.io.Serializable;

/**
 * @Author hu
 * @Description:
 * @Date Create In 19:38 2019/1/21 0021
 */
public class Response implements Serializable {


    private Object result;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}

