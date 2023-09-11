package com.massaro.cuofco;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class RateCheckerIT {

    @Test
    public void happyPath() throws InterruptedException {
        AtomicReference<byte[]> r = new AtomicReference<>();
        TelegramAPI mockTelegramAPI = r::set;
        RateChecker rateChecker = new RateChecker(mockTelegramAPI);
        rateChecker.checkRates();

        assertNotNull(r.get());
        assertTrue(r.get().length > 0);
    }
}
