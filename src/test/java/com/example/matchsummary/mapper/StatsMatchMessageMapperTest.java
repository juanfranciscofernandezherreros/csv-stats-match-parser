package com.example.matchsummary.mapper;
import com.example.matchsummary.dto.MatchSummaryDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class StatsMatchMessageMapperTest {
 @Test void mapsParsedMessage(){
  var d=new MatchSummaryDTO("2026-01-01","H","hi","A","ai","90","80","90","20","20","20","30","0","80","20","20","20","20","0");
  var m=new StatsMatchMessageMapper().parsed("e1","m1","MATCH_SUMMARY_m1.csv","/data/x","MATCH_SUMMARY",d);
  assertEquals("PARSED",m.getEventType()); assertEquals("m1",m.getMatchId()); assertEquals("H",m.getHomeName());
 }
}
