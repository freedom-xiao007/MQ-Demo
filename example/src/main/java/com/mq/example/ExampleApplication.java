package com.mq.example;

import com.mq.core.core.consumer.Consumer;
import com.mq.core.core.producer.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExampleApplication {

    private static Producer producer = new Producer();

    private static Consumer consumer = new Consumer();

    public static void main(String[] args) throws InterruptedException {
        int messageAmount = 1000;
        startMQProducer(messageAmount);
        startMQConsumer(messageAmount);
    }

    private static void startMQConsumer(int messageAmount) {
        Map<String, Object> properties = new HashMap<>(1);
        properties.put("url", "http://localhost:8080");
        consumer.setProperties(properties);
        String topic = "testTopic";
        int amount = messageAmount;

        System.out.println("Start consumer test");
        long start = System.currentTimeMillis();

        while (amount > 0) {
           amount -= consumer.poll(topic).size();
        }

        System.out.println("Consumer " + messageAmount + " messages spend time : " + (System.currentTimeMillis() - start) + " " +
                "ms");
    }

    private static void startMQProducer(int messageAmount) {
        Map<String, Object> properties = new HashMap<>(1);
        properties.put("url", "http://localhost:8080");
        producer.setProperties(properties);
        String topic = "testTopic";

        System.out.println("start producer test");
        long start = System.currentTimeMillis();

        for(int i = 0; i < messageAmount; i++) {
            producer.send(topic, String.valueOf(i));
        }

        System.out.println("Producer " + messageAmount + " messages spend time : " +
                (System.currentTimeMillis() - start) + " ms ");
    }
}
