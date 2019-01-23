package com.simplerpc.server.service;

import com.simplerpc.common.SendMessage;
import org.springframework.stereotype.Service;

/**
 * @Author hu
 * @Description:
 * @Date Create In 17:37 2019/1/22 0022
 */
@Service
public class SendMessageServiceImpl implements SendMessage {
    @Override
    public String sendName(String name) {
        return "hello," + name;
    }
}
