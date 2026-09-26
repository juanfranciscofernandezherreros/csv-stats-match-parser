package com.example.matchsummary.service;

import com.example.matchsummary.avro.StatsMatchKey;
import com.example.matchsummary.avro.StatsMatchValue;
import com.example.matchsummary.error.NonRetryableCsvException;
import com.example.matchsummary.mapper.StatsMatchMessageMapper;
import com.example.matchsummary.parser.MatchSummaryCsvParser;
import com.example.matchsummary.validation.SafeCsvPathValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class StatsMatchPublishService {
    private final MatchSummaryCsvParser parser;
    private final StatsMatchMessageMapper mapper;
    private final KafkaTemplate<StatsMatchKey, StatsMatchValue> kafka;
    private final SafeCsvPathValidator pathValidator;
    private final String topic;

    public StatsMatchPublishService(
            MatchSummaryCsvParser parser,
            StatsMatchMessageMapper mapper,
            KafkaTemplate<StatsMatchKey, StatsMatchValue> kafka,
            SafeCsvPathValidator pathValidator,
            @Value("${app.kafka.topics.parsed-stats-match}") String topic) {
        this.parser = parser;
        this.mapper = mapper;
        this.kafka = kafka;
        this.pathValidator = pathValidator;
        this.topic = topic;
    }

    public void publish(String eventId, String filePath, String fileType) {
        final Path path;
        try {
            path = pathValidator.validate(filePath);
        } catch (Exception exception) {
            throw new NonRetryableCsvException("Unsafe MATCH_SUMMARY CSV path: " + filePath, exception);
        }

        String fileName = path.getFileName().toString();
        String matchId = fileName.replaceFirst("^MATCH_SUMMARY_", "").replaceFirst("\\.csv$", "");

        try {
            var dto = parser.parse(path);
            kafka.send(topic, mapper.key(eventId), mapper.value(eventId, matchId, dto)).join();
        } catch (Exception exception) {
            if (containsKafkaFailure(exception)) {
                if (exception instanceof RuntimeException runtimeException) {
                    throw runtimeException;
                }
                throw new RuntimeException(exception);
            }
            throw new NonRetryableCsvException("Unable to parse MATCH_SUMMARY CSV: " + filePath, exception);
        }
    }

    private boolean containsKafkaFailure(Throwable error) {
        Throwable current = error;
        while (current != null) {
            if (current instanceof org.springframework.kafka.KafkaException
                    || current instanceof org.apache.kafka.common.KafkaException) {
                return true;
            }
            current = current.getCause();
        }
        return false;
    }
}
