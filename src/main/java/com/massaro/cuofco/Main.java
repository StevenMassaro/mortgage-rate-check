package com.massaro.cuofco;

import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;

import java.time.Duration;

public class Main {

    private static final String RETRIES = System.getenv().getOrDefault("RETRIES", "3");

    public static void main(String[] args) {
        RetryPolicy<Object> retryPolicy = RetryPolicy.builder()
                .withMaxRetries(Integer.parseInt(RETRIES))
                .withDelay(Duration.ofMinutes(1), Duration.ofMinutes(10))
                .build();

        TelegramAPI telegramAPI = new TelegramAPIImpl();
        RateChecker rateChecker = new RateChecker(telegramAPI);
        Failsafe.with(retryPolicy).run(rateChecker::checkRates);
    }
}