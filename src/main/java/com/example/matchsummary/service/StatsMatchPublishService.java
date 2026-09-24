package com.example.matchsummary.service;

import com.example.matchsummary.avro.StatsMatchKey;
import com.example.matchsummary.avro.StatsMatchValue;
import com.example.matchsummary.mapper.StatsMatchMessageMapper;
import com.example.matchsummary.parser.MatchSummaryCsvParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class StatsMatchPublishService {
    private final MatchSummaryCsvParser parser;
    private final StatsMatchMessageMapper mapper;
    private final KafkaTemplate<StatsMatchKey, StatsMatchValue> kafka;
    private final String topic;

    public StatsMatchPublishService(
            MatchSummaryCsvParser parser,
            StatsMatchMessageMapper mapper,
            KafkaTemplate<StatsMatchKey, StatsMatchValue> kafka,
            @Value("${app.kafka.topics.parsed-stats-match}") String topic) {
        this.parser = parser;
        this.mapper = mapper;
        this.kafka = kafka;
        this.topic = topic;
    }

    public void publish(String eventId, String filePath, String fileType) {
        Path path = Path.of(filePath);
        String fileName = path.getFileName().toString();
        String matchId = fileName.replaceFirst("^MATCH_SUMMARY_", "").replaceFirst("\\.csv$", "");
        try {
            var dto = parser.parse(path);
            kafka.send(topic, mapper.key(eventId), mapper.value(eventId, matchId, dto)).join();
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to parse MATCH_SUMMARY CSV: " + filePath, exception);
        }
    }
}
