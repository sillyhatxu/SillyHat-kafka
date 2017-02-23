package com.sillyhat.kafka.two;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * KafkaProducerThreadExample
 *
 * @author 徐士宽
 * @date 2017/2/23 10:57
 */
public class KafkaProducerThreadExample {

    private static Logger logger = Logger.getLogger(KafkaProducerThreadExample.class);

    private static SimpleDateFormat sdfTime = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
        Runnable run1 = new KafkaProducerThread("threadA");
//        Runnable run2 = new KafkaProducerThread("threadB");
//        Runnable run3 = new KafkaProducerThread("threadC");
//        Runnable run4 = new KafkaProducerThread("threadD");
        fixedThreadPool.execute(run1);
//        fixedThreadPool.execute(run2);
//        fixedThreadPool.execute(run3);
//        fixedThreadPool.execute(run4);
    }

    private static Properties getKafkaProperties(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.233.129:19001,192.168.233.130:19001,192.168.233.131:19001");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }

    static class KafkaProducerThread implements Runnable {

        private String threadName;

        public KafkaProducerThread(String threadName){
            this.threadName = threadName;
        }

        public void run() {
            Producer<String, String> producer = new KafkaProducer<String,String>(getKafkaProperties());
            for (int i = 1; i <= 50000; i++) {
//                String message = "[" + threadName + "][" + sdfTime.format(new Date()) + "][" + i + "]";
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                producer.send(new ProducerRecord<String,String>("log_collect_topic", threadName, message));
                String message = "";
                for (int j = 0; j < 10; j++) {
                    message += i+"";
                }
                producer.send(new ProducerRecord<String,String>("log_collect_topic", threadName, message));
                System.out.println("[" + threadName + "][" + sdfTime.format(new Date()) + "][" + message + "]");
//                logger.info("[" + threadName + "][" + sdfTime.format(new Date()) + "]");
            }
//            logger.info(threadName + " finish");
            producer.close();
        }
    }
}
