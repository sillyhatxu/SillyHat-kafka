package com.sillyhat.kafka.one;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.Logger;
import java.util.Arrays;
import java.util.Properties;

/**
 * kafka消费者简单示例
 *
 * @author 徐士宽
 * @date 2017/2/23 10:13
 */
public class KafkaConsumerSimpleness {

    private static Logger logger = Logger.getLogger(KafkaConsumerSimpleness.class);

    public static void main(String[] args) {
        Properties props = new Properties();
//./kafka-topics.sh --zookeeper 192.168.233.129:2181,192.168.233.130:2181,192.168.233.131:2181 --list
        props.put("bootstrap.servers", "192.168.233.129:19001,192.168.233.130:19001,192.168.233.131:19001");
        props.put("group.id", "groupTestXusk");
//        props.put("enable.auto.commit", "true");
//        props.put("auto.commit.interval.ms", "1000");
//        props.put("session.timeout.ms", "30000");
        props.put("fetch.min.bytes", 1048576);//1(mb)=1048576字节(b)
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList("log_collect_topic"));
        logger.info("KafkaConsumerExample Begin");
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(30000);
            for (ConsumerRecord<String, String> record : records){
                logger.info("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
                logger.info(record.offset());
                logger.info(record.key());
                logger.info(record.value());
                System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
            }
        }
    }
}
