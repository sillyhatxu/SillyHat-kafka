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
        props.put("bootstrap.servers", "192.168.233.129:19001,192.168.233.130:19001,192.168.233.131:19001");// 该地址是集群的子集，用来探测集群。
        props.put("group.id", "groupTestZ");// cousumer的分组id
//        props.put("auto.offset.reset", "earliest");//默认latest  是否最大offset,对消费组仅第一次有效

//        props.put("enable.auto.commit", "true");// 自动提交offsets
//        props.put("auto.commit.interval.ms", "1000");// 每隔1s，自动提交offsets
//        props.put("session.timeout.ms", "30000");// Consumer向集群发送自己的心跳，超时则认为Consumer已经死了，kafka会把它的分区分配给其他进程
        props.put("fetch.min.bytes", 1048576);//1(mb)=1048576字节(b)
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");// 反序列化器
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList("test_reset_topic"));// 订阅的topic,可以多个
        logger.info("KafkaConsumerExample Begin");
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(30000);
            for (ConsumerRecord<String, String> record : records){
                logger.info("------------------------------------");
                logger.info(record.topic());
                logger.info(record.offset());
                logger.info(record.key());
                logger.info(record.value());
                System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
            }
        }
    }
}
