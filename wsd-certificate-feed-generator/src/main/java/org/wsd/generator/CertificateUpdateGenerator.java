package org.wsd.generator;

import org.wsd.util.Logger;

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
        Logger.log("Initializing thread pool with " + threads + " threads.");

        ExecutorService executor = Executors.newFixedThreadPool(threads);
        List<Future<CertificateUpdate>> futures = new ArrayList<>();
        IsinGenerator isinGenerator = new IsinGenerator();

        Logger.log("Submitting " + quotes + " tasks to executor.");

        for (int i = 0; i < quotes; i++) {
            futures.add(executor.submit(new CertificateUpdateTask(isinGenerator)));
        }

        List<CertificateUpdate> updates = new ArrayList<>();

        for (Future<CertificateUpdate> future : futures) {
            try {
                updates.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                Logger.error("Error while executing task", e);
                e.printStackTrace();
            }
        }

        executor.shutdown();
        Logger.log("All tasks completed. Returning result stream.");

        return updates.stream();
    }
}
