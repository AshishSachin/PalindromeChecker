import java.util.*;

class VideoData {
    String videoId;
    String content;

    VideoData(String videoId, String content) {
        this.videoId = videoId;
        this.content = content;
    }
}

class MultiLevelCache {

    // L1 Cache (Memory)
    private LinkedHashMap<String, VideoData> L1 =
            new LinkedHashMap<String, VideoData>(10000, 0.75f, true) {
                protected boolean removeEldestEntry(Map.Entry<String, VideoData> eldest) {
                    return size() > 10000;
                }
            };

    // L2 Cache (SSD simulated)
    private LinkedHashMap<String, VideoData> L2 =
            new LinkedHashMap<String, VideoData>(100000, 0.75f, true) {
                protected boolean removeEldestEntry(Map.Entry<String, VideoData> eldest) {
                    return size() > 100000;
                }
            };

    // L3 Database
    private HashMap<String, VideoData> L3 = new HashMap<>();

    // Statistics
    int L1Hits = 0;
    int L2Hits = 0;
    int L3Hits = 0;

    public MultiLevelCache() {

        // Simulated database content
        L3.put("video_123", new VideoData("video_123", "Movie A"));
        L3.put("video_456", new VideoData("video_456", "Movie B"));
        L3.put("video_999", new VideoData("video_999", "Movie C"));
    }

    public VideoData getVideo(String videoId) {

        long start = System.currentTimeMillis();

        // L1 check
        if (L1.containsKey(videoId)) {

            L1Hits++;
            System.out.println("L1 Cache HIT (0.5ms)");
            return L1.get(videoId);
        }

        System.out.println("L1 Cache MISS");

        // L2 check
        if (L2.containsKey(videoId)) {

            L2Hits++;

            VideoData video = L2.get(videoId);

            System.out.println("L2 Cache HIT (5ms)");

            // Promote to L1
            L1.put(videoId, video);

            System.out.println("Promoted to L1");

            return video;
        }

        System.out.println("L2 Cache MISS");

        // L3 database
        if (L3.containsKey(videoId)) {

            L3Hits++;

            VideoData video = L3.get(videoId);

            System.out.println("L3 Database HIT (150ms)");

            // Add to L2
            L2.put(videoId, video);

            return video;
        }

        System.out.println("Video not found");

        return null;
    }

    public void getStatistics() {

        int total = L1Hits + L2Hits + L3Hits;

        System.out.println("\nCache Statistics:");

        if (total == 0) {
            System.out.println("No requests yet");
            return;
        }

        System.out.println("L1 Hit Rate: " + (L1Hits * 100.0 / total) + "%");
        System.out.println("L2 Hit Rate: " + (L2Hits * 100.0 / total) + "%");
        System.out.println("L3 Hit Rate: " + (L3Hits * 100.0 / total) + "%");

        double overall = ((L1Hits + L2Hits) * 100.0 / total);

        System.out.println("Overall Cache Hit Rate: " + overall + "%");
    }
}

public class MultiLevelCacheApp {

    public static void main(String[] args) {

        MultiLevelCache cache = new MultiLevelCache();

        System.out.println("Request 1:");
        cache.getVideo("video_123");

        System.out.println("\nRequest 2:");
        cache.getVideo("video_123");

        System.out.println("\nRequest 3:");
        cache.getVideo("video_999");

        cache.getStatistics();
    }
}