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
        props.put("acks", "all");// 记录完整提交，最慢的但是最大可能的持久化
        props.put("retries", 0);// 请求失败重试的次数
        props.put("batch.size", 16384);// batch的大小
        props.put("linger.ms", 1);// 默认情况即使缓冲区有剩余的空间，也会立即发送请求，设置一段时间用来等待从而将缓冲区填的更多，单位为毫秒，producer发送数据会延迟1ms，可以减少发送到kafka服务器的请求数据
        props.put("buffer.memory", 33554432);// 提供给生产者缓冲内存总量
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");// 序列化的方式，
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<String,String>(props);
        for(int i = 0; i < 500000; i++){
            String message = "[" + i + "]";
            logger.info(message);
            // 三个参数分别为topic, key,value，send()是异步的，添加到缓冲区立即返回，更高效。
            producer.send(new ProducerRecord<String,String>("test_reset_topic", "luckKey", message));
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
