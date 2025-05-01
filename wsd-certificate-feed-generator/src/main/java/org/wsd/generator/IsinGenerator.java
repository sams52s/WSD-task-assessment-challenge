package org.wsd.generator;

import java.util.concurrent.ThreadLocalRandom;

public class IsinGenerator {

    /**
     * Converts a letter A–Z to its numeric equivalent
     * private static int convertCharToDigit(char c) {
     *  private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
     *  private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
     *  private static final Random random = new Random();
     *   private static final Map<Character, Integer> CONVERSION_TABLE = new HashMap<>();
     *static {
     * CONVERSION_TABLE.put('A', 10);
     * CONVERSION_TABLE.put('B', 11);
     * CONVERSION_TABLE.put('C', 12);
     * CONVERSION_TABLE.put('D', 13);
     * This replaces the original HashMap-based conversion table with a simple arithmetic operation.
     */

    private static int convertCharToDigit(char c) {
        return c - 'A' + 10;
    }

    public static String generateISIN() {
        StringBuilder base = new StringBuilder();

        for (int i = 0; i < 2; i++) {
            base.append((char) ('A' + ThreadLocalRandom.current().nextInt(26)));
        }

        for (int i = 0; i < 9; i++) {
            if (ThreadLocalRandom.current().nextBoolean()) {
                base.append((char) ('A' + ThreadLocalRandom.current().nextInt(26)));
            } else {
                base.append(ThreadLocalRandom.current().nextInt(10));
            }
        }

        StringBuilder numeric = new StringBuilder();
        for (char c : base.toString().toCharArray()) {
            numeric.append(Character.isLetter(c) ? convertCharToDigit(c) : c);
        }

        int sum = 0;
        boolean doubleIt = true;
        for (int i = numeric.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(numeric.charAt(i));
            if (doubleIt) digit *= 2;
            sum += digit / 10 + digit % 10;
            doubleIt = !doubleIt;
        }

        int checkDigit = (10 - (sum % 10)) % 10;
        return base.append(checkDigit).toString();
    }
}
