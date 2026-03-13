import java.util.*;

class AnalyticsSystem {

    // page → total views
    private HashMap<String, Integer> pageViews = new HashMap<>();

    // page → unique users
    private HashMap<String, Set<String>> uniqueVisitors = new HashMap<>();

    // traffic source → count
    private HashMap<String, Integer> trafficSources = new HashMap<>();


    // Process incoming event
    public void processEvent(String url, String userId, String source) {

        // Count page views
        pageViews.put(url, pageViews.getOrDefault(url, 0) + 1);

        // Track unique visitors
        uniqueVisitors
                .computeIfAbsent(url, k -> new HashSet<>())
                .add(userId);

        // Track traffic sources
        trafficSources.put(source,
                trafficSources.getOrDefault(source, 0) + 1);
    }


    // Get top 10 pages
    public void getDashboard() {

        System.out.println("\nTop Pages:");

        List<Map.Entry<String, Integer>> list =
                new ArrayList<>(pageViews.entrySet());

        // sort by views
        list.sort((a, b) -> b.getValue() - a.getValue());

        int count = 0;

        for (Map.Entry<String, Integer> entry : list) {

            String page = entry.getKey();
            int views = entry.getValue();
            int unique = uniqueVisitors.get(page).size();

            System.out.println((count + 1) + ". " + page +
                    " - " + views + " views (" +
                    unique + " unique)");

            count++;

            if (count == 10)
                break;
        }

        System.out.println("\nTraffic Sources:");

        for (String source : trafficSources.keySet()) {
            System.out.println(source + " → " + trafficSources.get(source));
        }
    }
}


public class RealTimeAnalyticsApp {

    public static void main(String[] args) throws Exception {

        AnalyticsSystem system = new AnalyticsSystem();

        // simulate incoming events
        system.processEvent("/article/breaking-news", "user_123", "google");
        system.processEvent("/article/breaking-news", "user_456", "facebook");
        system.processEvent("/sports/championship", "user_789", "google");
        system.processEvent("/sports/championship", "user_101", "direct");
        system.processEvent("/article/breaking-news", "user_123", "google");

        // dashboard refresh every 5 seconds
        while (true) {

            Thread.sleep(5000);

            system.getDashboard();
        }
    }
}