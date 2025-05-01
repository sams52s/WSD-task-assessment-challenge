package org.wsd.generator;

public class App {
    public static void main(String[] args) {
        if (args.length >= 2) {
            try {
                int threads = Integer.parseInt(args[0]);
                int quotes = Integer.parseInt(args[1]);

                CertificateUpdateGenerator generator = new CertificateUpdateGenerator(threads, quotes);
                generator.generateQuotes().forEach(System.out::println);
                return;

            } catch (NumberFormatException e) {
                throw new NumberFormatException("Invalid number format. Both arguments must be integers.");
            }
        }
        throw new RuntimeException("Expect at least number of threads and number of quotes. But got: " + args.length);
    }
}