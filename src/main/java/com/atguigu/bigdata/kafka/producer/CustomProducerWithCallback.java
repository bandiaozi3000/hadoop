package com.atguigu.bigdata.kafka.producer;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class CustomProducerWithCallback {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "zed97:9092");//kafka 集群，broker-list
        props.put("acks", "all");
        props.put("retries", 1);//重试次数
        props.put("batch.size", 16384);//批次大小
        props.put("linger.ms", 1);//等待时间
        props.put("buffer.memory", 33554432);//RecordAccumulator 缓冲区大小
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //自定义分区
        props.put("partitioner.class", "com.atguigu.bigdata.kafka.partitioner.MyPartitioner");
        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 100; i++) {
             final int  num = i;
            producer.send(new ProducerRecord<String, String>("first", "atguigu--"+Integer.toString(i),"atguigu--"+Integer.toString(i)), new Callback() {
                //回调函数，该方法会在 Producer 收到 ack 时调用，为异步调用
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception == null) {
                        System.out.println("partition:"+metadata.partition()+",success->" + metadata.offset()+"---"+num);
                    } else {
                        exception.printStackTrace();
                    }
                }
            });
        }
        producer.close();
    }
}

