package com.sillyhat.kafka.one;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;
import java.util.Properties;

/**
 * kafka生产者简单示例
 *
 * @author 徐士宽
 * @date 2017/2/21 14:29
 */

public class KafkaProducerSimpleness {

    private static Logger logger = Logger.getLogger(KafkaProducerSimpleness.class);

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.233.129:19001,192.168.233.130:19001,192.168.233.131:19001");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<String,String>(props);
        for(int i = 0; i < 500000; i++){
            String message = "[" + i + "]";
            logger.info(message);
            producer.send(new ProducerRecord<String,String>("log_collect_topic", "luckKey", message));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logger.info("finish");
        producer.close();
    }
}
