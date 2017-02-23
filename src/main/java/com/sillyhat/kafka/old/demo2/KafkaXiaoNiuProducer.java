//package com.sillyhat.kafka.old.demo2;
//
//import org.apache.commons.lang.RandomStringUtils;
//
//import java.util.Properties;
//
//import kafka.javaapi.producer.Producer;
//import kafka.producer.KeyedMessage;
//import kafka.producer.ProducerConfig;
//
///**
// * Hello world!
// */
//public class KafkaXiaoNiuProducer {
//
//    private final Producer<String, String> producer;
//
//    private static String TEST_TOPIC = "TEST-TOPIC";
//    private static String XIAONIU_TEST_TOPIC = "xioaniu_test_topic";
//
//    private KafkaXiaoNiuProducer() {
//        Properties props = new Properties();
//        //此处配置的是kafka的端口
//        props.put("metadata.broker.list", "192.168.233.129:19001,192.168.233.130:19001,192.168.233.131:19001");
//        //配置value的序列化类
//        props.put("serializer.class", "kafka.serializer.StringEncoder");
//        //配置key的序列化类
//        props.put("key.serializer.class", "kafka.serializer.StringEncoder");
//        //request.required.acks
//        //0, which means that the producer never waits for an acknowledgement from the broker (the same behavior as 0.7). This option provides the lowest latency but the weakest durability guarantees (some data will be lost when a server fails).
//        //1, which means that the producer gets an acknowledgement after the leader replica has received the data. This option provides better durability as the client waits until the server acknowledges the request as successful (only messages that were written to the now-dead leader but not yet replicated will be lost).
//        //-1, which means that the producer gets an acknowledgement after all in-sync replicas have received the data. This option provides the best durability, we guarantee that no messages will be lost as long as at least one in sync replica remains.
//        props.put("request.required.acks", "-1");
//        producer = new Producer<String, String>(new ProducerConfig(props));
//    }
//
//    private void sendMessage(String topic, String message){
//        System.out.println("sending to " + topic + message);
//        KeyedMessage<String, String> data = new KeyedMessage<String, String>(topic, RandomStringUtils.randomAlphabetic(3), message);
//        producer.send(data);
//    }
//
//    void produce(String topic,String data) {
//        sendMessage(topic,data);
//        System.out.println(topic + " ----- " + data);
//    }
//
//    public static void main(String[] args) {
//        (new Thread(new ProducerThread(TEST_TOPIC,data_test))).start();
//        (new Thread(new ProducerThread(XIAONIU_TEST_TOPIC,data_xiaoniu))).start();
//    }
//
//    private static class ProducerThread implements Runnable {
//
//        private String topic;
//        private String data;
//
//        public ProducerThread(String topic,String data) {
//            this.topic = topic;
//            this.data = data;
//        }
//
//        public void run() {
//            for (int i = 0; i < 10000; i++) {
//                new KafkaXiaoNiuProducer().produce(topic,data);
//            }
//        }
//    }
//
//    static String data_test = "aaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbbccccccccccccccccccccccccdddddddddddddddddddddddeeeeeeeeeeeeeeeeeeeeeeeeeeffffffffffffffffffffffffffffgggggggggggggggggggggggggg";
//    static String data_xiaoniu = "{'CHNNO':'APP','TRANSDATE':'2016/10/19 16:56:07','TRANSCODE':'SR005','ARRAYDATA':[{'APPLYSERIALNO':'1476867433032','APPLYTYPE':'010','CUSTOMERNAME':'快来退','CERTID':'130105198506020328','SEX':'2','AUTHORITY':'公交卡','MATURITYDATE':'9999/99/99','EMAIL':'','QQNO':'','WECHAT':'','ALIPAY':'','CUSTOMERPHOTO':'1610-3328','DEGREEEDUCATION':'1','FAMILYSTATUS':'1','MOBILETELEPHONE':'15869690000','NATIVEPROVINCE':'01','NATIVECITY':'01','NATIVECOUNTRYSIDE':'340101','NATIVETOWN':'','NATIVESTREET':'国际经济','NATIVEPLOT':'户籍','NATIVEROOM':'','FLAG2':'2','LIVINGPROVINCE':'01','LIVINGCITY':'01','LIVINGCOUNTRYSIDE':'340101','LIVINGTOWN':'','LIVINGSTREET':'故居','LIVINGPLOT':'黄家驹','LIVINGROOM':'','MARRIAGE':'1','CHILDRENTOTAL':'0','KINSHIPNAME':'废后将军','MEMBERTYPE':'010','MEMBERSFAMILYPHONE':'15836923568','FLAG10':'1','KINSHIPADD':'安徽省合肥市市辖区国际经济户籍','WOKERORSTU':'2','SCHOOLCORP':'清华大学','ENTRANCEDATE':'2015/09','STUDENTNO':'4569855','EDUCATIONALSYSTEM':'040','SCHOOLZIPCODE':'0755','SCHOOLPHONECODE':'25368569','SCHOOLPROVINCE':'01','SCHOOLCITY':'01','SCHOOLTOWN':'','SCHOOLSTREET':'黄金季节','SCHOOLPLOT':'国际经济金','SCHOOLROOM':'','RECEIVEGOODS':'1','GOODSNAMETYPE':'010','GOODSADD':'010','GOODSADD1':'安徽合肥市市辖区故居黄家驹','SSINO':'','SELFMONTHINCOME':'6000','OTHERREVENUE':'200','ALIMONY':'300','FAMILYMONTHINCOME':'12000','OTHERCONTACTNAME':'遇见','CONTACTRELATION':'1','CONTACTTEL':'15436258956','COMMODITYCONO1':'001','BUSINESSTYPE1':'00101','BRANDTYPE1':'不好解决','MANUFACTURER1':'刚回家','PRICE1':'6000','TOTALPRICE':'6000.0','TOTALSUM':'3000','BUSINESSSUM':'3060.0','LOANAPPLICATION':'010','PRODUCTID':'P','CREDITTYPENO':'PPFB030609014','PERIODS':'9','MONTHREPAYMENT':'399.15','FIRSTDUEDAY':'1','DEFAULTDUEDAY':'1','BUSINESSRATE':'0.00643','CUSTOMERSERVICERATES':'0.01406','LOANMANAGEMENTRATE':'0.00167','CREDITCYCLE':'2','PROPERTYINSURANCE':'1','FLEXIBLEREPAYMENT':'1','SERVICEPACKAGE':'2','SERVICEPACKAGEFEE':'0','PRODUCTCODE':'PPFB030609014','COUNTERFEE':'20.0','INSURANCEIDARR':'SXF-02','REPAYMENTNO':'684539666366','REPAYMENTNAME':'快来退','REPAYMENTBANK':'abc','BANKWITHHOLDING':'1','RECOMMENDMOBILEPHONE':'','SALESNO':'P010032','SALESNAME':'合肥市包河区中数数码产品维修部','INTERNALCODE':'1','ANNOTATION':'','SOURCE':'000000','SIGNATURE':'1610-3332','CUSTOMERSIGNATURE':'1610-3332','SALESPHOTO':'1610-3329','POSITIVECERTPHOTO':'1610-3325','OPPOSITECERTPHOTO':'1610-3326','BANKCARDPHOTO':'1610-3327','STUPHOTO':'1610-3331','MINTOTALSUM':'','REPAYMENTBANKPUT':'','BANKINGOUTLETS':'','IMEI':'867279020952224','GPS':'22.570508,114.074897','OPERATETIME':'447947','SMSCOUNT':'0','BANKACTIVE':'684539666366','MERCHANTSERVICEFEE':'60','COMMISSIONPERCENT':'2','SALESCONSULTANTNO':'6010007'}]}";
//}
