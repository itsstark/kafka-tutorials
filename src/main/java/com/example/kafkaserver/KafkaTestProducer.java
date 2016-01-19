
package com.example.kafkaserver;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 *
 * @author vishal
 */
public class KafkaTestProducer {
    
    public KafkaTestProducer(String topic, String date, String logClass, String message) {
        
        
        System.out.println("Kafka reached");
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("acks", "all");
        properties.put("retries", 0);
        properties.put("batch.size", 16384);
        properties.put("linger.ms", 1);
        properties.put("buffer.memory", 33554432);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        System.out.println("Kafka Properties set");
        
        Producer<String, String> producer = new KafkaProducer(properties);
        producer.send(new ProducerRecord(topic, date + " " + logClass + " " + message));
        System.out.println(topic + " " + date + " " + logClass + " " + message);
        producer.close();
    }
}
