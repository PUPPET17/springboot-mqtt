package com.datadoghq.pej.service;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class MessagingService {

    private static final Logger logger = LoggerFactory.getLogger(MessagingService.class);

    @Resource
    private IMqttClient mqttClient;

    public void publish(final String topic, final String data, int qos, boolean retained)
            throws MqttException {

        byte[] byteMsg = data.getBytes();

        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(byteMsg);
        mqttMessage.setQos(qos);
        mqttMessage.setRetained(retained);

        mqttClient.publish(topic, mqttMessage);
    }


    public void subscribe(final String topic) throws MqttException {

        mqttClient.subscribeWithResponse(topic, (tpic, msg) -> {

            byte[] payload = msg.getPayload();
            String message = new String(payload);

            logger.info("Message received: {}", message);
        });
    }

}
