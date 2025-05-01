package org.wsd.util;

import java.time.LocalDateTime;

public class Logger {
    public static void log(String message) {
        System.out.println("[LOG] " + LocalDateTime.now() + " - " + message);
    }

    public static void error(String message, Throwable throwable) {
        System.err.println("[ERROR] " + LocalDateTime.now() + " - " + message);
        throwable.printStackTrace(System.err);
    }
}
