package org.wsd.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public class CertificateUpdateGenerator {
    private final int threads;
    private final int quotes;

    public CertificateUpdateGenerator(int threads, int quotes) {
        this.threads = threads;
        this.quotes = quotes;
    }

    public Stream<CertificateUpdate> generateQuotes() {
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        List<Future<CertificateUpdate>> futures = new ArrayList<>();
        IsinGenerator isinGenerator = new IsinGenerator();

        for (int i = 0; i < quotes; i++) {
            futures.add(executor.submit(new CertificateUpdateTask(isinGenerator)));
        }

        List<CertificateUpdate> updates = new ArrayList<>();

        for (Future<CertificateUpdate> future : futures) {
            try {
                updates.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();

        return updates.stream();
    }
}
