package io.socket.backo;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

public class BackoffTest {

    @Test
    public void durationShouldIncreaseTheBackoff() {
        Backoff b = new Backoff();

        assertEquals(100, b.duration(), "Duration should be 100");
        assertEquals(200, b.duration(), "Duration should be 200");
        assertEquals(400, b.duration(), "Duration should be 400");
        assertEquals(800, b.duration(), "Duration should be 800");

        b.reset();
        assertEquals(100, b.duration(), "Duration should be 100 after reset");
        assertEquals(200, b.duration(), "Duration should be 200 after reset");

    }
    public void testBackoffWithJitter() {
        IntStream.range(0, 10).forEach(i -> {
        IntStream.range(0, 10).forEach(i -> {
            Backoff b = new Backoff();
            b.setMin(100);
            b.setMax(10000);
            b.setJitter(0.5);
            IntStream.range(0, 100).forEach(j -> {
                BigInteger ms = BigInteger.valueOf(100).multiply(BigInteger.valueOf(2).pow(j));
                BigInteger deviation = new BigDecimal(ms).multiply(BigDecimal.valueOf(0.5)).toBigInteger();
                BigInteger duration = BigInteger.valueOf(b.duration());
                BigInteger min = ms.subtract(deviation).min(BigInteger.valueOf(10000));

                BigInteger max = ms.add(deviation).min(BigInteger.valueOf(10001));
                assertTrue(min.compareTo(duration) <= 0 && max.compareTo(duration) == 1,
                        min + " <= " + duration + " < " + max);
        });
            });
        });
    }
    @Test
    public void testInvalidJitter() {
        Backoff b = new Backoff();
        b.setJitter(2);
    }
}
