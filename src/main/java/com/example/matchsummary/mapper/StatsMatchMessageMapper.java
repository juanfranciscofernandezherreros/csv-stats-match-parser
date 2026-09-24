package com.example.matchsummary.mapper;
import com.example.matchsummary.avro.*;
import com.example.matchsummary.dto.MatchSummaryDTO;
import org.springframework.stereotype.Component;

@Component
public class StatsMatchMessageMapper {
 public StatsMatchKey key(String eventId){ return StatsMatchKey.newBuilder().setSourceEventId(eventId).build(); }
 public StatsMatchValue parsed(String eventId,String matchId,String fileName,String filePath,String fileType,MatchSummaryDTO d){
  return StatsMatchValue.newBuilder().setSourceEventId(eventId).setEventType("PARSED").setMatchId(matchId)
   .setFileName(fileName).setFilePath(filePath).setFileType(fileType)
   .setDate(d.date()).setHomeName(d.homeName()).setHomeImage(d.homeImage()).setAwayName(d.awayName()).setAwayImage(d.awayImage())
   .setResultHome(d.resultHome()).setResultAway(d.resultAway()).setTotalLocal(d.totalLocal()).setFirstLocal(d.firstLocal())
   .setSecondLocal(d.secondLocal()).setThirdLocal(d.thirdLocal()).setFourthLocal(d.fourthLocal()).setExtraLocal(d.extraLocal())
   .setTotalAway(d.totalAway()).setFirstAway(d.firstAway()).setSecondAway(d.secondAway()).setThirdAway(d.thirdAway())
   .setFourthAway(d.fourthAway()).setExtraAway(d.extraAway()).build();
 }
 public StatsMatchValue failed(String eventId,String matchId,String fileName,String filePath,String fileType,String error){
  return StatsMatchValue.newBuilder().setSourceEventId(eventId).setEventType("FAILED").setMatchId(matchId)
   .setFileName(fileName).setFilePath(filePath).setFileType(fileType).setError(error).build();
 }
}
