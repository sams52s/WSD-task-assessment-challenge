package org.wsd.generator;

import org.wsd.util.Logger;

public class App {
    public static void main(String[] args) {
        if (args.length >= 2) {
            try {
                int threads = Integer.parseInt(args[0]);
                int quotes = Integer.parseInt(args[1]);

                Logger.log("Starting certificate generation with " + threads + " threads and " + quotes + " updates.");

                CertificateUpdateGenerator generator = new CertificateUpdateGenerator(threads, quotes);
                generator.generateQuotes().forEach(System.out::println);

                Logger.log("Certificate generation completed.");

                return;

            } catch (NumberFormatException e) {
                Logger.error("Invalid input arguments: expected 2 integers.", e);
                throw new NumberFormatException("Invalid number format. Both arguments must be integers.");
            }
        }

        throw new RuntimeException("Expect at least number of threads and number of quotes. But got: " + args.length);
    }
}