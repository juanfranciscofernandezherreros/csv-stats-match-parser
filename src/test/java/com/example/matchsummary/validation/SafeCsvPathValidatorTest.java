package com.example.matchsummary.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SafeCsvPathValidatorTest {

    @TempDir
    Path tempDir;

    @Test
    void acceptsReadableCsvInsideAllowedRoot() throws Exception {
        Path allowed = Files.createDirectory(tempDir.resolve("allowed"));
        Path csv = Files.writeString(allowed.resolve("MATCH_SUMMARY_m1.csv"), "a,b,c\n");

        Path validated = new SafeCsvPathValidator(allowed.toString()).validate(csv.toAbsolutePath().toString());

        assertEquals(csv.toRealPath(), validated);
    }

    @Test
    void rejectsRelativePath() throws Exception {
        Path allowed = Files.createDirectory(tempDir.resolve("allowed"));

        assertThrows(IllegalArgumentException.class,
                () -> new SafeCsvPathValidator(allowed.toString()).validate("MATCH_SUMMARY_m1.csv"));
    }

    @Test
    void rejectsMissingFile() throws Exception {
        Path allowed = Files.createDirectory(tempDir.resolve("allowed"));
        Path missing = allowed.resolve("missing.csv").toAbsolutePath();

        assertThrows(java.io.IOException.class,
                () -> new SafeCsvPathValidator(allowed.toString()).validate(missing.toString()));
    }

    @Test
    void rejectsSymlinkEscapingAllowedRoot() throws Exception {
        Path allowed = Files.createDirectory(tempDir.resolve("allowed"));
        Path outside = Files.createDirectory(tempDir.resolve("outside"));
        Path outsideCsv = Files.writeString(outside.resolve("MATCH_SUMMARY_m1.csv"), "a,b,c\n");
        Path link = allowed.resolve("escape.csv");
        Files.createSymbolicLink(link, outsideCsv);

        assertThrows(IllegalArgumentException.class,
                () -> new SafeCsvPathValidator(allowed.toString()).validate(link.toAbsolutePath().toString()));
    }
}
