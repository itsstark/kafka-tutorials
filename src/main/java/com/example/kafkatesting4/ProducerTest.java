
package com.example.kafkatesting4;

import java.util.Properties;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

/**
 *
 * @author vishal
 */
public class ProducerTest {
    
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("zk.connect","127.0.0.1:2181");
        properties.put("serializer.class", "kafka.serializer.StringEncoder");
        properties.put("metadata.broker.list", "localhost:9092");
        
        ProducerConfig config = new ProducerConfig(properties);
        Producer<String, String> producer = new Producer(config);
        KeyedMessage<String, String> data = new KeyedMessage("test", "text message");
        producer.send(data);
        producer.close();
    }
    
}
