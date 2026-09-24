package com.example.matchsummary.mapper;

import com.example.matchsummary.dto.MatchSummaryDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatsMatchMessageMapperTest {

    @Test
    void mapsKeyAndValue() {
        var dto = new MatchSummaryDTO(
                "2026-01-01", "H", "hi", "A", "ai",
                "90", "80",
                "90", "20", "20", "20", "30", "0",
                "80", "20", "20", "20", "20", "0"
        );

        var mapper = new StatsMatchMessageMapper();
        var key = mapper.key("e1");
        var value = mapper.value("e1", "m1", dto);

        assertEquals("e1", key.getSourceEventId());
        assertEquals("m1", value.getMatchId());
        assertEquals("H", value.getHomeName());
        assertEquals("90", value.getResultHome());
    }
}
