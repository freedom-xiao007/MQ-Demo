package com.mq.example;

import com.mq.core.core.common.Constants;
import com.mq.core.core.consumer.Consumer;
import com.mq.core.core.consumer.HttpConsumer;
import com.mq.core.core.producer.HttpProducer;
import com.mq.core.core.producer.Producer;
import com.mq.core.core.producer.WebsocketProducer;

import java.util.HashMap;
import java.util.Map;

public class ExampleApplication {

    public static void main(String[] args) throws InterruptedException {
        int messageAmount = 10;
        String topic = "testTopic";
        int getRate = 10;

//        startHttpMQProducer(messageAmount, topic);
        startWebsocketMqProducer(messageAmount, topic);

        startHttpMQConsumer(messageAmount, topic, getRate);
    }

    private static void startWebsocketMqProducer(int messageAmount, String topic) {
        Map<String, Object> properties = new HashMap<>(1);
        properties.put(Constants.URL, "ws://localhost:8080/producer");
        properties.put(Constants.SEND_TIMEOUT, 1000);
        Producer producer = new WebsocketProducer(properties);

        int amount = messageAmount;

        System.out.println("start producer test");
        long start = System.currentTimeMillis();

        while (amount > 0) {
            if(producer.send(topic, "producerMessage")) {
                amount -= 1;
            } else {
                System.out.println("send failed");
            }
        }

        System.out.println("Producer " + messageAmount + " messages spend time : " +
                (System.currentTimeMillis() - start) + " ms ");
    }

    private static void startHttpMQConsumer(int messageAmount, String topic, int getRate) {
        Map<String, Object> properties = new HashMap<>(1);
        properties.put("url", "http://localhost:8080");
        properties.put("topic", topic);
        properties.put("group", "groupTest");
        Consumer consumer = new HttpConsumer(properties);
        int amount = messageAmount;

        System.out.println("Start consumer test");
        long start = System.currentTimeMillis();

        while (amount > 0) {
           amount -= consumer.poll(getRate).size();
        }

        System.out.println("Consumer " + messageAmount + " messages spend time : " + (System.currentTimeMillis() - start) + " " +
                "ms");
    }

    private static void startHttpMQProducer(int messageAmount, String topic) {
        Map<String, Object> properties = new HashMap<>(1);
        properties.put("url", "http://localhost:8080");
        Producer producer = new HttpProducer(properties);

        System.out.println("start producer test");
        long start = System.currentTimeMillis();

        for(int i = 0; i < messageAmount; i++) {
            if (!producer.send(topic, String.valueOf(i))) {
                System.out.println("send failed");
            }
        }

        System.out.println("Producer " + messageAmount + " messages spend time : " +
                (System.currentTimeMillis() - start) + " ms ");
    }
}
