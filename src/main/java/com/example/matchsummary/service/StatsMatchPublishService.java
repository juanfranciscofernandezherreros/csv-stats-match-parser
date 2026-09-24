package com.example.matchsummary.service;
import com.example.matchsummary.avro.*;
import com.example.matchsummary.mapper.StatsMatchMessageMapper;
import com.example.matchsummary.parser.MatchSummaryCsvParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.nio.file.Path;

@Service
public class StatsMatchPublishService {
 private final MatchSummaryCsvParser parser; private final StatsMatchMessageMapper mapper;
 private final KafkaTemplate<StatsMatchKey,StatsMatchValue> kafka; private final String topic;
 public StatsMatchPublishService(MatchSummaryCsvParser parser,StatsMatchMessageMapper mapper,
   KafkaTemplate<StatsMatchKey,StatsMatchValue> kafka,@Value("${app.kafka.topics.parsed-stats-match}") String topic){
  this.parser=parser;this.mapper=mapper;this.kafka=kafka;this.topic=topic;
 }
 public void publish(String eventId,String filePath,String fileType){
  String fileName=Path.of(filePath).getFileName().toString();
  String matchId=fileName.replaceFirst("^MATCH_SUMMARY_","").replaceFirst("\\.csv$","");
  StatsMatchKey key=mapper.key(eventId);
  try{
   var dto=parser.parse(Path.of(filePath));
   kafka.send(topic,key,mapper.parsed(eventId,matchId,fileName,filePath,fileType,dto)).join();
  }catch(Exception e){
   String message=e.getMessage()==null?e.getClass().getSimpleName():e.getMessage();
   try{kafka.send(topic,key,mapper.failed(eventId,matchId,fileName,filePath,fileType,message)).join();}catch(Exception ignored){}
   throw new IllegalStateException("Unable to parse MATCH_SUMMARY CSV: "+filePath,e);
  }
 }
}
