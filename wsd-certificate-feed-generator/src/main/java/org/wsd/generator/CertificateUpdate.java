package org.wsd.generator;

import java.time.LocalDate;

public class CertificateUpdate {

    private final long timestamp;
    private final String isin;
    private final double bidPrice;
    private final int bidSize;
    private final double askPrice;
    private final int askSize;
    private final LocalDate maturityDate;

    public CertificateUpdate(long timestamp, String isin,
                             double bidPrice, int bidSize,
                             double askPrice, int askSize,
                             LocalDate maturityDate) {
        this.timestamp = timestamp;
        this.isin = isin;
        this.bidPrice = bidPrice;
        this.bidSize = bidSize;
        this.askPrice = askPrice;
        this.askSize = askSize;
        this.maturityDate = maturityDate;
    }

    @Override
    public String toString() {
        return String.format("%d,%s,%.2f,%d,%.2f,%d,%s",
                timestamp, isin, bidPrice, bidSize, askPrice, askSize, maturityDate);
    }
}
