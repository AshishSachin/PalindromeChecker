import java.util.*;

public class PalindromeChecker {

    // productId -> stock count
    private static HashMap<String, Integer> inventory = new HashMap<>();

    // productId -> waiting list (FIFO)
    private static HashMap<String, LinkedHashMap<Integer, Boolean>> waitingList = new HashMap<>();


    // Initialize stock
    public static void addProduct(String productId, int stock) {
        inventory.put(productId, stock);
        waitingList.put(productId, new LinkedHashMap<>());
    }


    // Check stock availability
    public static int checkStock(String productId) {
        return inventory.getOrDefault(productId, 0);
    }


    // Purchase item (thread-safe)
    public synchronized static String purchaseItem(String productId, int userId) {

        int stock = inventory.getOrDefault(productId, 0);

        if (stock > 0) {

            stock--;
            inventory.put(productId, stock);

            return "Success, " + stock + " units remaining";
        }

        else {

            LinkedHashMap<Integer, Boolean> queue = waitingList.get(productId);
            queue.put(userId, true);

            int position = queue.size();

            return "Added to waiting list, position #" + position;
        }
    }


    public static void main(String[] args) {

        addProduct("IPHONE15_256GB", 100);

        System.out.println("Stock: " + checkStock("IPHONE15_256GB") + " units available");

        System.out.println(purchaseItem("IPHONE15_256GB", 12345));
        System.out.println(purchaseItem("IPHONE15_256GB", 67890));

        // simulate stock exhaustion
        for(int i = 0; i < 100; i++) {
            purchaseItem("IPHONE15_256GB", i);
        }

        System.out.println(purchaseItem("IPHONE15_256GB", 99999));
    }
}