package com.datadoghq.pej.controller;

import com.datadoghq.pej.service.MessagingService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.*;

@RestController
public class MqttController {

    private static final Logger logger = LoggerFactory.getLogger(MqttController.class);

    @Resource
    private MessagingService messagingService;

    @GetMapping("/Mqtt")
    public String mqttCall() throws MqttException, InterruptedException, IOException {

        final String topic = "pejman/topic/event";

        messagingService.subscribe(topic);

        messagingService.publish(topic, "This is a sample message published to topic pejman/topic/event", 0, true);
        //context.close();

        return "OK\n";
    }

}