
package com.example.kafkatesting4;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 *
 * @author vishal
 */
public class ConsumerTest {
    
    public static void main(String[] args) {
    
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("group.id", "group1");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("session.timeout.ms", "30000");
        properties.put("partition.assignment.strategy", "org.apache.kafka.clients.consumer.RangeAssignor");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        
        KafkaConsumer consumer = new KafkaConsumer(properties);
        consumer.subscribe("test");
        boolean isRunning = true;
        while(isRunning) {
        List<ConsumerRecord> records;
        Map recordsMap = consumer.poll(100);
        if(recordsMap != null && (recordsMap.values() != null)){
            records = new ArrayList(recordsMap.values());
            System.out.println(records);
           }           
        }
        consumer.close();
    }
}
