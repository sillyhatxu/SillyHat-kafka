package com.sillyhat.kafka.two;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.Logger;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * KafkaConsumerThreadExample
 *
 * @author 徐士宽
 * @date 2017/2/23 10:57
 */
public class KafkaConsumerThreadExample {

    private static Logger logger = Logger.getLogger(KafkaConsumerThreadExample.class);

    public static void main(String[] args) {
        String groupId = "groupTestMessage";
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
//        Runnable run1 = new KafkaConsumerThread(groupId + "TestGroupConsumerA","threadA");
//        Runnable run2 = new KafkaConsumerThread(groupId + "TestGroupConsumerB","threadB");
//        Runnable run3 = new KafkaConsumerThread(groupId + "TestGroupConsumerC","threadC");
//        Runnable run4 = new KafkaConsumerThread(groupId + "TestGroupConsumerD","threadD");
//        Runnable run5 = new KafkaConsumerThread(groupId + "TestGroupConsumerG","threadG");
        Runnable run1 = new KafkaConsumerThread("WanYuXiang","threadA");
        Runnable run5 = new KafkaConsumerThread("XuShiKuan","threadG");
//        Runnable run5 = new KafkaConsumerThread(groupId + "TestGroupConsumerFFFFF","threadE");
//        fixedThreadPool.execute(run5);
        fixedThreadPool.execute(run1);
//        fixedThreadPool.execute(run2);
//        fixedThreadPool.execute(run3);
//        fixedThreadPool.execute(run4);
        logger.info("Thread A Start Finish");
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fixedThreadPool.execute(run5);
        logger.info("Thread G Start Finish");
        Thread thread = new Thread(new KafkaConsumerThread2("SillyHat","ThreadH"));
        thread.start();
    }

    private static Properties getKafkaProperties(String groupId){
        Properties props = new Properties();
//./kafka-topics.sh --zookeeper 192.168.233.129:2181,192.168.233.130:2181,192.168.233.131:2181 --list
        props.put("bootstrap.servers", "192.168.233.129:19001,192.168.233.130:19001,192.168.233.131:19001");
//        props.put("group.id", "groupTestMessageThreadB");
        props.put("group.id", groupId);
//        props.put("enable.auto.commit", "true");
//        props.put("auto.commit.interval.ms", "1000");
//        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }


    static class KafkaConsumerThread implements Runnable {

        private String threadName;

        private String groupId;

        public KafkaConsumerThread(String groupId,String threadName){
            this.groupId = groupId;
            this.threadName = threadName;
        }

        public void run() {
            KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(getKafkaProperties(groupId));
            consumer.subscribe(Arrays.asList("log_collect_topic"));
            logger.info("KafkaConsumerExample " + groupId + " Begin");
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(30000);
                for (ConsumerRecord<String, String> record : records){
                    logger.info("------------------------------------------");
                    logger.info("threadName[" + threadName + "] group [" + groupId + "]offset = " + record.offset()+", key = "+record.key()+", value = "+record.value());
                    logger.info("------------------------------------------");
                    System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
                }
            }
        }
    }

    static class KafkaConsumerThread2 implements Runnable {

        private String threadName;

        private String groupId;

        public KafkaConsumerThread2(String groupId,String threadName){
            this.groupId = groupId;
            this.threadName = threadName;
        }

        public void run() {
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("KafkaConsumerThread2 Sleep Finish");
            KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(getKafkaProperties(groupId));
            consumer.subscribe(Arrays.asList("log_collect_topic"));
            logger.info("KafkaConsumerExample " + groupId + " Begin");
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(30000);
                for (ConsumerRecord<String, String> record : records){
                    logger.info("------------------------------------------");
                    logger.info("threadName[" + threadName + "] group [" + groupId + "]offset = " + record.offset()+", key = "+record.key()+", value = "+record.value());
                    logger.info("------------------------------------------");
                    System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
                }
            }
        }
    }
}
