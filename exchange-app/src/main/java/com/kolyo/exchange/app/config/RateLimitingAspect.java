package com.kolyo.exchange.app.config;

import com.kolyo.exchange.app.exception.RateLimitExceededException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Component
public class RateLimitingAspect {
    private static final ConcurrentHashMap<String, AtomicInteger> requestCounts = new ConcurrentHashMap<>();
    private static final int REQUEST_LIMIT = 100;
    private static final long TIME_LIMIT = 60000; // 1 minute

    @Before("@annotation(RateLimited)")
    public void beforeRequest() {
        String clientId = getClientId(); // Implement method to get client ID
        AtomicInteger count = requestCounts.computeIfAbsent(clientId, k -> new AtomicInteger(0));
        if (count.incrementAndGet() > REQUEST_LIMIT) {
            throw new RateLimitExceededException("Rate limit exceeded");
        }
        if (requestCounts.size() == 1) {
            resetRequestCounts();
        }
    }

    private String getClientId() {
        return "213123";
    }

    private void resetRequestCounts() {
        new Thread(() -> {
            try {
                Thread.sleep(TIME_LIMIT);
                requestCounts.clear();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
