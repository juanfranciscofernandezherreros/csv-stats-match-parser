package com.example.matchsummary.consumer;
import com.example.csvwatcher.watcher.*;
import com.example.matchsummary.service.StatsMatchPublishService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class FileReadyConsumer {
 private final StatsMatchPublishService publisher;
 public FileReadyConsumer(StatsMatchPublishService publisher){this.publisher=publisher;}
 @KafkaListener(topics="${app.kafka.topics.file-ready}",groupId="${spring.kafka.consumer.group-id}")
 public void listen(ConsumerRecord<FileEventKey,FileEventValue> record){
  FileEventValue value=record.value();
  if(value==null || !"MATCH_SUMMARY".equalsIgnoreCase(value.getFileType())) return;
  publisher.publish(record.key().getUniqueId(),value.getFilePath(),value.getFileType());
 }
}
