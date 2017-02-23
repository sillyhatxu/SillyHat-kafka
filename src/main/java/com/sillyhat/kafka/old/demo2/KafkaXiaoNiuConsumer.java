//package com.sillyhat.kafka.old.demo2;
//
//import org.apache.commons.lang.math.NumberUtils;
//import org.apache.log4j.Logger;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import kafka.consumer.Consumer;
//import kafka.consumer.ConsumerConfig;
//import kafka.consumer.ConsumerIterator;
//import kafka.consumer.KafkaStream;
//import kafka.javaapi.consumer.ConsumerConnector;
//import kafka.message.MessageAndMetadata;
//
//public class KafkaXiaoNiuConsumer {
//
//    private static final Logger logger = Logger.getLogger(KafkaXiaoNiuConsumer.class);
//
//    private ConsumerConnector connector;
//
//    private ExecutorService executor;
//
//    private String topics = "TEST-TOPIC,xioaniu_test_topic";
//    private String TEST_TOPIC = "TEST-TOPIC";
//    private String XIAONIU_TEST_TOPIC = "xioaniu_test_topic";
//
//    private String consumerThreadQuantity = "1,1";
//
//    private void createConnection(){
//        Properties props = new Properties();
//        //zookeeper 配置
//        props.put("zookeeper.connect", "192.168.233.129:2181,192.168.233.130:2181,192.168.233.131:2181");
//        //group 代表一个消费组
//        props.put("group.id", "test-xiaoniu-group");
//        //zk连接超时
//        props.put("zookeeper.session.timeout.ms", "20000");
//        props.put("zookeeper.sync.time.ms", "200");
//        props.put("auto.commit.interval.ms", "1000");
//        props.put("auto.offset.reset", "smallest");
//        //序列化类
//        props.put("serializer.class", "kafka.serializer.StringEncoder");
//        ConsumerConfig config = new ConsumerConfig(props);
//        connector = Consumer.createJavaConsumerConnector(config);
//    }
//    private KafkaXiaoNiuConsumer() {
//        createConnection();
//        consume();
//    }
//
//
//    public void consume() {
//        Map<String, Integer> topicMap = new HashMap<String, Integer>();
//        String[]  topicArray = topics.split(",");
//        String[]  quantityArray = consumerThreadQuantity.split(",");
//        for (int i = 0; i < topicArray.length; i ++) {
//            topicMap.put(topicArray[i], NumberUtils.toInt( quantityArray[i], 1 ));
//        }
//        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = connector.createMessageStreams(topicMap);
//        // init thread pool
//        executor = Executors.newCachedThreadPool();
//        for (String topic : topicArray) {
//            List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);
//            for (KafkaStream<byte[], byte[]> stream : streams) {
//                executor.submit(new ConsumerThread(stream));
//            }
//        }
//    }
//
//    private class ConsumerThread implements Runnable {
//
//        /**
//         * kafka流
//         */
//        private KafkaStream<byte[], byte[]> stream;
//
//        public ConsumerThread(KafkaStream<byte[], byte[]> stream) {
//            this.stream = stream;
//        }
//
//        private void testTopic(String content){
//            logger.info("test topic abcdefghahaabcdefg");
//            logger.info(content);
//        }
//
//        private void xiaoniuTopic(String content){
//            logger.info("xiaoniu topic xiaoniuhahaxiaoniu");
//            logger.info(content);
//        }
//
//        public void run() {
//            ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
//            while (iterator.hasNext()) {
//                try {
//                    MessageAndMetadata<byte[], byte[]> message = iterator.next();
//                    String content = new String(message.message(), "UTF-8");
//                    logger.info("accepting from " + message.topic() +" : "+ content);
//                    if(message.topic().equals(TEST_TOPIC)){
//                        testTopic(content);
//                    }else if(message.topic().equals(XIAONIU_TEST_TOPIC)){
//                        xiaoniuTopic(content);
//                    }else{
//                        logger.error("no the topic");
//                    }
//                } catch (Throwable e) {
//                    logger.error("KafkaStream error", e);
//                }
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        new KafkaXiaoNiuConsumer().consume();
//    }
//}