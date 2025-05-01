package org.wsd.generator;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CertificateUpdateGeneratorTest {

    @Test
    public void generateQuotes() {
        CertificateUpdateGenerator certificateUpdateGenerator = new CertificateUpdateGenerator(10, 100);
        Stream<CertificateUpdate> quotes = certificateUpdateGenerator.generateQuotes();
        assertNotNull(quotes);
        assertEquals(100, quotes.count());
    }

    @Test
    public void generateQuotes_shouldReturnCorrectCount() {
        CertificateUpdateGenerator generator = new CertificateUpdateGenerator(4, 50);
        Stream<CertificateUpdate> quotes = generator.generateQuotes();

        assertNotNull(quotes, "Generated stream should not be null");
        assertEquals(50, quotes.count(), "Should return exactly 50 updates");
    }

    @Test
    public void generateQuotes_shouldReturnNonNullObjects() {
        CertificateUpdateGenerator generator = new CertificateUpdateGenerator(2, 10);
        List<CertificateUpdate> updates = generator.generateQuotes().collect(Collectors.toList());

        assertFalse(updates.isEmpty(), "Updates list should not be empty");
        updates.forEach(update -> assertNotNull(update, "Each update should be non-null"));
    }
}
