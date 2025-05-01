package org.wsd.generator;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IsinGeneratorTest {

    @Test
    void generatedIsinShouldBe12Characters() {
        String isin = IsinGenerator.generateISIN();
        assertEquals(12, isin.length(), "ISIN should be exactly 12 characters long");
    }

    @Test
    void isinShouldStartWithTwoUppercaseLetters() {
        String isin = IsinGenerator.generateISIN();
        assertTrue(Character.isUpperCase(isin.charAt(0)), "First character should be an uppercase letter");
        assertTrue(Character.isUpperCase(isin.charAt(1)), "Second character should be an uppercase letter");
    }

    @Test
    void isinShouldEndWithDigit() {
        String isin = IsinGenerator.generateISIN();
        assertTrue(Character.isDigit(isin.charAt(11)), "Last character should be a digit");
    }

    @RepeatedTest(10)
    void multipleIsinsShouldBeRandomAndValidFormat() {
        String isin1 = IsinGenerator.generateISIN();
        String isin2 = IsinGenerator.generateISIN();
        assertNotEquals(isin1, isin2, "Generated ISINs should likely be different");
        assertEquals(12, isin1.length());
        assertEquals(12, isin2.length());
    }
}
