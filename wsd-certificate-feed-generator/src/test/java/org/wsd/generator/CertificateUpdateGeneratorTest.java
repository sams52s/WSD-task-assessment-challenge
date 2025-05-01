package org.wsd.generator;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CertificateUpdateGeneratorTest {

    @Test
    void shouldGenerateCorrectNumberOfUpdates() {
        int threads = 4;
        int quotes = 50;

        CertificateUpdateGenerator generator = new CertificateUpdateGenerator(threads, quotes);
        Stream<CertificateUpdate> updateStream = generator.generateQuotes();

        List<CertificateUpdate> updates = updateStream.collect(Collectors.toList());

        assertNotNull(updates, "Generated update list should not be null");
        assertEquals(quotes, updates.size(), "Should generate exactly " + quotes + " updates");
    }

    @Test
    void updatesShouldBeValidObjects() {
        CertificateUpdateGenerator generator = new CertificateUpdateGenerator(2, 10);
        List<CertificateUpdate> updates = generator.generateQuotes().collect(Collectors.toList());

        for (CertificateUpdate update : updates) {
            assertNotNull(update, "Each update should not be null");

            String csv = update.toString();
            assertNotNull(csv, "CSV string should not be null");
            assertTrue(csv.contains(","), "CSV string should contain commas");

            long commaCount = csv.chars().filter(ch -> ch == ',').count();
            assertEquals(6, commaCount, "CSV line should contain exactly 6 commas (7 fields)");
        }
    }

    @Test
    void shouldHandleZeroUpdates() {
        CertificateUpdateGenerator generator = new CertificateUpdateGenerator(2, 0);
        List<CertificateUpdate> updates = generator.generateQuotes().collect(Collectors.toList());

        assertNotNull(updates, "Updates list should not be null even if quotes = 0");
        assertTrue(updates.isEmpty(), "No updates should be generated for quotes = 0");
    }

    @Test
    void shouldNotCrashWithSingleThreadSingleQuote() {
        CertificateUpdateGenerator generator = new CertificateUpdateGenerator(1, 1);
        List<CertificateUpdate> updates = generator.generateQuotes().collect(Collectors.toList());

        assertEquals(1, updates.size(), "Should generate one update with 1 thread and 1 quote");
        assertNotNull(updates.get(0), "Generated update should not be null");
    }
}
