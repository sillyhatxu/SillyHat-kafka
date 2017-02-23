//package com.sillyhat.kafka.old.demo3;
//
//import java.util.Properties;
//
//import kafka.javaapi.producer.Producer;
//import kafka.producer.KeyedMessage;
//import kafka.producer.ProducerConfig;
//import kafka.serializer.StringEncoder;
//
//
//public class kafkaProducer extends Thread{
//
//    private String topic;
//
//    public kafkaProducer(String topic){
//        super();
//        this.topic = topic;
//    }
//
//
//    @Override
//    public void run() {
//        Producer producer = createProducer();
//        int i=0;
//        while(true){
//            String data = "message: " + i++;
//            System.out.println(data);
//            producer.send(new KeyedMessage<Integer, String>(topic, data));
////            try {
////                TimeUnit.SECONDS.sleep(1);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
//        }
//    }
//
//    private Producer createProducer() {
//        Properties properties = new Properties();
//        properties.put("zookeeper.connect", "192.168.233.129:12181,192.168.233.130:12181,192.168.233.131:12181");//声明zk
//        properties.put("serializer.class", StringEncoder.class.getName());
//        properties.put("metadata.broker.list", "192.168.233.129:19001,192.168.233.130:19001,192.168.233.131:19001");// 声明kafka broker
//        return new Producer<Integer, String>(new ProducerConfig(properties));
//    }
//    public static void main(String[] args) {
//        new kafkaProducer("TEST-TOPIC").start();// 使用kafka集群中创建好的主题 test
//
//    }
//}