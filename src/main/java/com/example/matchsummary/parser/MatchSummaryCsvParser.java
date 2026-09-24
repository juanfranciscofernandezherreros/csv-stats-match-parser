package com.example.matchsummary.parser;
import com.example.matchsummary.dto.MatchSummaryDTO;
import org.apache.commons.csv.*;
import org.springframework.stereotype.Component;
import java.io.*;
import java.nio.file.*;

@Component
public class MatchSummaryCsvParser {
 private static final int EXPECTED_COLUMNS=19;
 public MatchSummaryDTO parse(Path path) throws IOException {
  try(Reader reader=Files.newBufferedReader(path);
      CSVParser parser=CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).setTrim(true).build().parse(reader)){
   CSVRecord result=null;
   for(CSVRecord record:parser){
    if(record.size()==1 && record.get(0).isBlank()) continue;
    if(result!=null) throw new IllegalArgumentException("MATCH_SUMMARY CSV must contain exactly one row");
    if(record.size()!=EXPECTED_COLUMNS) throw new IllegalArgumentException("CSV row "+record.getRecordNumber()+" has "+record.size()+" columns; expected 19");
    result=record;
   }
   if(result==null) throw new IllegalArgumentException("MATCH_SUMMARY CSV has no data row");
   return new MatchSummaryDTO(result.get(0),result.get(1),result.get(2),result.get(3),result.get(4),
    result.get(5),result.get(6),result.get(7),result.get(8),result.get(9),result.get(10),result.get(11),
    result.get(12),result.get(13),result.get(14),result.get(15),result.get(16),result.get(17),result.get(18));
  }
 }
}
