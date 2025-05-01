package org.wsd.generator;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class IsinGenerator {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Random random = new Random();

    private static final Map<Character, Integer> CONVERSION_TABLE = new HashMap<>();

    static {
        CONVERSION_TABLE.put('A', 10);
        CONVERSION_TABLE.put('B', 11);
        CONVERSION_TABLE.put('C', 12);
        CONVERSION_TABLE.put('D', 13);
        CONVERSION_TABLE.put('E', 14);
        CONVERSION_TABLE.put('F', 15);
        CONVERSION_TABLE.put('G', 16);
        CONVERSION_TABLE.put('H', 17);
        CONVERSION_TABLE.put('I', 18);
        CONVERSION_TABLE.put('J', 19);
        CONVERSION_TABLE.put('K', 20);
        CONVERSION_TABLE.put('L', 21);
        CONVERSION_TABLE.put('M', 22);
        CONVERSION_TABLE.put('N', 23);
        CONVERSION_TABLE.put('O', 24);
        CONVERSION_TABLE.put('P', 25);
        CONVERSION_TABLE.put('Q', 26);
        CONVERSION_TABLE.put('R', 27);
        CONVERSION_TABLE.put('S', 28);
        CONVERSION_TABLE.put('T', 29);
        CONVERSION_TABLE.put('U', 30);
        CONVERSION_TABLE.put('V', 31);
        CONVERSION_TABLE.put('W', 32);
        CONVERSION_TABLE.put('X', 33);
        CONVERSION_TABLE.put('Y', 34);
        CONVERSION_TABLE.put('Z', 35);

        for (int i = 0; i <= 9; i++) {
            CONVERSION_TABLE.put(Character.forDigit(i, 10), i);
        }
    }

    public String generate() {
        StringBuilder isin = new StringBuilder();

        for (int i = 0; i < 2; i++) {
            isin.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }

        for (int i = 0; i < 9; i++) {
            isin.append(ALPHANUMERIC.charAt(random.nextInt(ALPHANUMERIC.length())));
        }

        int checkDigit = calculateCheckDigit(isin.toString());
        isin.append(checkDigit);

        return isin.toString();
    }

    public int calculateCheckDigit(String isin) {
        if (isin == null || isin.length() < 11) {
            throw new IllegalArgumentException("ISIN base must be at least 11 characters long");
        }

        StringBuilder converted = new StringBuilder();
        for (char c : isin.toCharArray()) {
            Integer value = CONVERSION_TABLE.get(Character.toUpperCase(c));
            if (value == null) {
                throw new IllegalArgumentException("Invalid character in ISIN: " + c);
            }
            converted.append(value).append(" ");
        }

        String[] numbers = converted.toString().trim().split(" ");
        int sum = 0;
        for (int i = numbers.length - 1; i >= 0; i--) {
            int num = Integer.parseInt(numbers[i]);
            if ((numbers.length - i) % 2 == 1) {
                num *= 2;
            }
            while (num > 0) {
                sum += num % 10;
                num /= 10;
            }
        }
        int nextMultipleOf10 = (sum + 9) / 10 * 10;
        return nextMultipleOf10 - sum;
    }

    public boolean isValid(String isin) {
        if (isin == null || isin.length() != 12) {
            return false;
        }

        int providedCheckDigit = Character.getNumericValue(isin.charAt(11));

        try {
            int calculatedCheckDigit = calculateCheckDigit(isin.substring(0, 11));
            return providedCheckDigit == calculatedCheckDigit;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}