package com.simplerpc.client;


import com.simplerpc.client.remote.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ClientApplication {

    @Autowired
    private SendMessage sendMessage;

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @RequestMapping("/hello")
    public String hello(String name) {
        return sendMessage.sendName(name);
    }


}

