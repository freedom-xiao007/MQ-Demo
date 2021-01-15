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

@Slf4j
public class ExampleApplication {

    private static Producer producer = new Producer();

    private static Consumer consumer = new Consumer();

    public static void main(String[] args) throws InterruptedException {
        startMQProducer();

        startMQConsumer();
    }

    private static void startMQConsumer() {
        Map<String, Object> properties = new HashMap<>(1);
        properties.put("url", "http://localhost:8080");
        consumer.setProperties(properties);

        log.info("start consumer");
        String topic = "testTopic";
        List messages = consumer.poll(topic);
        for (Object object : messages) {
            System.out.println(object.toString());
        }
    }

    private static void startMQProducer() throws InterruptedException {
        Map<String, Object> properties = new HashMap<>(1);
        properties.put("url", "http://localhost:8080");
        producer.setProperties(properties);

        log.info("start producer");
        String topic = "testTopic";
        for(int i = 0; i < 10; i++) {
            producer.send(topic, String.valueOf(i));
            log.info("send :" + i);
        }
    }
}
