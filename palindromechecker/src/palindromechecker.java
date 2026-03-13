import java.util.*;

class DNSEntry {
    String domain;
    String ipAddress;
    long timestamp;
    long expiryTime;

    DNSEntry(String domain, String ipAddress, int ttlSeconds) {
        this.domain = domain;
        this.ipAddress = ipAddress;
        this.timestamp = System.currentTimeMillis();
        this.expiryTime = timestamp + ttlSeconds * 1000;
    }

    boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}

public class PalindromeChecker {

    private final int MAX_SIZE = 5;

    private LinkedHashMap<String, DNSEntry> cache =
            new LinkedHashMap<String, DNSEntry>(16, 0.75f, true) {

                protected boolean removeEldestEntry(Map.Entry<String, DNSEntry> eldest) {
                    return size() > MAX_SIZE;
                }
            };

    private int hits = 0;
    private int misses = 0;
    private long totalLookupTime = 0;

    public String resolve(String domain) {

        long start = System.nanoTime();

        if (cache.containsKey(domain)) {

            DNSEntry entry = cache.get(domain);

            if (!entry.isExpired()) {
                hits++;
                long end = System.nanoTime();
                totalLookupTime += (end - start);

                System.out.println("Cache HIT → " + entry.ipAddress);
                return entry.ipAddress;
            }

            System.out.println("Cache EXPIRED → Query upstream");
            cache.remove(domain);
        }

        misses++;

        String ip = queryUpstreamDNS(domain);

        cache.put(domain, new DNSEntry(domain, ip, 10));

        long end = System.nanoTime();
        totalLookupTime += (end - start);

        System.out.println("Cache MISS → Query upstream → " + ip + " (TTL: 10s)");

        return ip;
    }

    private String queryUpstreamDNS(String domain) {

        try {
            Thread.sleep(100); // simulate network delay
        } catch (Exception e) {
        }

        Random r = new Random();
        return "172.217.14." + r.nextInt(255);
    }

    public void getCacheStats() {

        int total = hits + misses;

        double hitRate = total == 0 ? 0 : (hits * 100.0) / total;

        double avgLookupTime = total == 0 ? 0 : (totalLookupTime / total) / 1000000.0;

        System.out.println("\nCache Statistics");
        System.out.println("Hits: " + hits);
        System.out.println("Misses: " + misses);
        System.out.println("Hit Rate: " + hitRate + "%");
        System.out.println("Avg Lookup Time: " + avgLookupTime + " ms");
    }

    public static void main(String[] args) throws Exception {

        PalindromeChecker dnsCache = new PalindromeChecker();

        dnsCache.resolve("google.com");
        dnsCache.resolve("google.com");

        Thread.sleep(11000);

        dnsCache.resolve("google.com");

        dnsCache.getCacheStats();
    }
}