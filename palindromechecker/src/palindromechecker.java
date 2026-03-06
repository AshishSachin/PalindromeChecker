import java.util.*;

public class PalindromeChecker {

    // Stores username -> userId
    private static HashMap<String, Integer> users = new HashMap<>();

    // Stores username -> attempt count
    private static HashMap<String, Integer> attempts = new HashMap<>();

    // Check if username is available
    public static boolean checkAvailability(String username) {

        // Track attempt frequency
        attempts.put(username, attempts.getOrDefault(username, 0) + 1);

        // O(1) lookup
        return !users.containsKey(username);
    }

    // Register a new user
    public static void registerUser(String username, int userId) {
        users.put(username, userId);
    }

    // Suggest alternative usernames
    public static List<String> suggestAlternatives(String username) {

        List<String> suggestions = new ArrayList<>();

        suggestions.add(username + "1");
        suggestions.add(username + "2");
        suggestions.add(username.replace("_", "."));
        suggestions.add(username + "_official");

        return suggestions;
    }

    // Find most attempted username
    public static String getMostAttempted() {

        String most = "";
        int max = 0;

        for (String user : attempts.keySet()) {
            if (attempts.get(user) > max) {
                max = attempts.get(user);
                most = user;
            }
        }

        return most + " (" + max + " attempts)";
    }

    public static void main(String[] args) {

        registerUser("john_doe", 101);
        registerUser("admin", 102);

        System.out.println("john_doe available: " + checkAvailability("john_doe"));
        System.out.println("jane_smith available: " + checkAvailability("jane_smith"));

        System.out.println("Suggestions: " + suggestAlternatives("john_doe"));

        System.out.println("Most attempted: " + getMostAttempted());
    }
}