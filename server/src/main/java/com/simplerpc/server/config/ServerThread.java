package com.simplerpc.server.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.simplerpc.common.ClassTypeAdapterFactory;
import com.simplerpc.common.Request;
import com.simplerpc.common.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @Author hu
 * @Description:
 * @Date Create In 17:35 2019/1/22 0022
 */
public class ServerThread implements Runnable {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private Socket client = null;

    public ServerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapterFactory(new ClassTypeAdapterFactory());
            Gson gson = gsonBuilder.create();
            //获取Socket的输出流，用来向客户端发送数据
            PrintStream out = new PrintStream(client.getOutputStream());
            //获取Socket的输入流，用来接收从客户端发送过来的数据
            BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));
            boolean flag = true;
            while (flag) {
                //接收从客户端发送过来的数据
                String str = buf.readLine();
                Request request = gson.fromJson(str, Request.class);
                if (str == null || "".equals(str)) {
                    flag = false;
                } else {
                    Response response = invokeMethod(request);
                    String res = gson.toJson(response);
                    out.println(res);
                }
            }
            out.close();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Response invokeMethod(Request request) {
        String className = request.getClassName();
        String methodName = request.getMethodName();
        Object[] parameters = request.getParameters();
        Class<?>[] parameTypes = request.getParameTypes();
        Object o = RpcConfig.cache.get(className);
        Response response = new Response();
        try {
            Method method = o.getClass().getDeclaredMethod(methodName, parameTypes);
            Object invokeMethod = method.invoke(o, parameters);
            response.setResult(invokeMethod);
        } catch (NoSuchMethodException e) {
            log.error("找不到方法:{}", methodName, e);
        } catch (IllegalAccessException e) {
            log.error("方法不可达：{}", parameters, e);
        } catch (InvocationTargetException e) {
            log.error("执行错误：{}", parameters, e);
        }
        return response;
    }
}
