package org.wsd.generator;

import java.time.LocalDate;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

public class CertificateUpdateTask implements Callable<CertificateUpdate> {

    private final IsinGenerator isinGenerator;

    public CertificateUpdateTask(IsinGenerator isinGenerator) {
        this.isinGenerator = isinGenerator;
    }

    @Override
    public CertificateUpdate call() {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        long timestamp = System.currentTimeMillis();
        String isin = IsinGenerator.generateISIN();

        double bidPrice = roundToDecimal(random.nextDouble(100.00, 200.01));

        int bidSize = random.nextInt(1000, 5001);

        double askPrice = roundToDecimal(random.nextDouble(100.00, 200.01));

        int askSize = random.nextInt(1000, 10001);

        LocalDate maturityDate = LocalDate.now().plusDays(random.nextLong(1, 731));

        return new CertificateUpdate(timestamp, isin, bidPrice, bidSize,
                askPrice, askSize, maturityDate);
    }

    private double roundToDecimal(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
