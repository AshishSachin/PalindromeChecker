import java.util.*;

class Transaction {

    int id;
    int amount;
    String merchant;
    String account;
    int time; // minutes for simplicity

    Transaction(int id, int amount, String merchant, String account, int time) {
        this.id = id;
        this.amount = amount;
        this.merchant = merchant;
        this.account = account;
        this.time = time;
    }
}

class TransactionAnalyzer {

    List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    // Classic Two-Sum
    public void findTwoSum(int target) {

        HashMap<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : transactions) {

            int complement = target - t.amount;

            if (map.containsKey(complement)) {

                Transaction other = map.get(complement);

                System.out.println("Two-Sum Found: (" +
                        other.id + ", " + t.id + ")");

                return;
            }

            map.put(t.amount, t);
        }

        System.out.println("No pair found");
    }


    // Two-Sum within time window
    public void findTwoSumWithTime(int target, int window) {

        HashMap<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : transactions) {

            int complement = target - t.amount;

            if (map.containsKey(complement)) {

                Transaction other = map.get(complement);

                if (Math.abs(t.time - other.time) <= window) {

                    System.out.println("Two-Sum (time window): (" +
                            other.id + ", " + t.id + ")");
                }
            }

            map.put(t.amount, t);
        }
    }


    // Duplicate detection
    public void detectDuplicates() {

        HashMap<String, List<Transaction>> map = new HashMap<>();

        for (Transaction t : transactions) {

            String key = t.amount + "-" + t.merchant;

            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(t);
        }

        for (String key : map.keySet()) {

            List<Transaction> list = map.get(key);

            if (list.size() > 1) {

                System.out.println("Duplicate Transactions: ");

                for (Transaction t : list) {
                    System.out.println("ID: " + t.id +
                            " Account: " + t.account);
                }
            }
        }
    }


    // K-Sum (simple recursive)
    public void findKSum(int k, int target) {

        List<Integer> result = new ArrayList<>();

        kSumHelper(0, k, target, result);
    }

    private void kSumHelper(int start, int k, int target, List<Integer> result) {

        if (k == 0 && target == 0) {

            System.out.println("K-Sum Found: " + result);
            return;
        }

        if (k == 0)
            return;

        for (int i = start; i < transactions.size(); i++) {

            Transaction t = transactions.get(i);

            result.add(t.id);

            kSumHelper(i + 1,
                    k - 1,
                    target - t.amount,
                    result);

            result.remove(result.size() - 1);
        }
    }
}

public class FinancialTransactionsApp {

    public static void main(String[] args) {

        TransactionAnalyzer analyzer = new TransactionAnalyzer();

        analyzer.addTransaction(new Transaction(1, 500, "Store A", "acc1", 600));
        analyzer.addTransaction(new Transaction(2, 300, "Store B", "acc2", 615));
        analyzer.addTransaction(new Transaction(3, 200, "Store C", "acc3", 630));
        analyzer.addTransaction(new Transaction(4, 500, "Store A", "acc2", 640));

        System.out.println("Two Sum Target = 500");
        analyzer.findTwoSum(500);

        System.out.println("\nTwo Sum with Time Window (60 min)");
        analyzer.findTwoSumWithTime(500, 60);

        System.out.println("\nDuplicate Detection");
        analyzer.detectDuplicates();

        System.out.println("\nK-Sum k=3 target=1000");
        analyzer.findKSum(3, 1000);
    }
}