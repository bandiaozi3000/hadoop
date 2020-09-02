package com.atguigu.bigdata.kafka.producer;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class CustomProducerWithoutCallback {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        //kafka 集 群 ，broker-list
        //props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "zed97:9092");
        props.put("bootstrap.servers", "zed97:9092");
        props.put("acks", "all");
        //重试次数props.put("retries", 1);
        //批次大小props.put("batch.size", 16384);
        //等待时间props.put("linger.ms", 1);
        //RecordAccumulator 缓冲区大小
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 10; i++) {
            producer.send(new ProducerRecord<String, String>("first",  0,"atguigu--"+Integer.toString(i),"atguigu--"+Integer.toString(i)));
        }
        producer.close();
    }
}

