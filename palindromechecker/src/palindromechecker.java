import java.util.*;

class TokenBucket {

    int maxTokens;
    double refillRate; // tokens per second
    double tokens;
    long lastRefillTime;

    public TokenBucket(int maxTokens, double refillRate) {
        this.maxTokens = maxTokens;
        this.refillRate = refillRate;
        this.tokens = maxTokens;
        this.lastRefillTime = System.currentTimeMillis();
    }

    public synchronized boolean allowRequest() {

        refill();

        if (tokens >= 1) {
            tokens -= 1;
            return true;
        }

        return false;
    }

    private void refill() {

        long now = System.currentTimeMillis();

        double secondsPassed = (now - lastRefillTime) / 1000.0;

        double tokensToAdd = secondsPassed * refillRate;

        tokens = Math.min(maxTokens, tokens + tokensToAdd);

        lastRefillTime = now;
    }

    public int remainingTokens() {
        return (int) tokens;
    }
}

class RateLimiter {

    private HashMap<String, TokenBucket> clientBuckets = new HashMap<>();

    private int MAX_REQUESTS = 1000;
    private double REFILL_RATE = 1000.0 / 3600; // tokens per second

    public void checkRateLimit(String clientId) {

        clientBuckets.putIfAbsent(clientId,
                new TokenBucket(MAX_REQUESTS, REFILL_RATE));

        TokenBucket bucket = clientBuckets.get(clientId);

        if (bucket.allowRequest()) {

            System.out.println("Allowed (" +
                    bucket.remainingTokens() +
                    " requests remaining)");

        } else {

            System.out.println("Denied (Rate limit exceeded)");
        }
    }

    public void getRateLimitStatus(String clientId) {

        TokenBucket bucket = clientBuckets.get(clientId);

        if (bucket == null) {
            System.out.println("Client not found");
            return;
        }

        int used = MAX_REQUESTS - bucket.remainingTokens();

        System.out.println("{used: " + used +
                ", limit: " + MAX_REQUESTS +
                ", remaining: " + bucket.remainingTokens() + "}");
    }
}

public class RateLimiterApp {

    public static void main(String[] args) {

        RateLimiter limiter = new RateLimiter();

        String client = "abc123";

        // simulate requests
        for (int i = 0; i < 5; i++) {
            limiter.checkRateLimit(client);
        }

        limiter.getRateLimitStatus(client);
    }
}