import java.util.*;

class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    boolean isEnd = false;
}

class AutocompleteSystem {

    private TrieNode root = new TrieNode();

    // query → frequency
    private HashMap<String, Integer> frequencyMap = new HashMap<>();


    // Insert query into Trie
    public void insertQuery(String query) {

        TrieNode node = root;

        for (char c : query.toCharArray()) {

            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
        }

        node.isEnd = true;

        frequencyMap.put(query,
                frequencyMap.getOrDefault(query, 0) + 1);
    }


    // Search prefix suggestions
    public void search(String prefix) {

        TrieNode node = root;

        for (char c : prefix.toCharArray()) {

            if (!node.children.containsKey(c)) {
                System.out.println("No suggestions found");
                return;
            }

            node = node.children.get(c);
        }

        List<String> results = new ArrayList<>();
        dfs(node, prefix, results);

        PriorityQueue<String> topK =
                new PriorityQueue<>(
                        (a, b) -> frequencyMap.get(a) - frequencyMap.get(b)
                );

        for (String query : results) {

            topK.offer(query);

            if (topK.size() > 10)
                topK.poll();
        }

        List<String> suggestions = new ArrayList<>();

        while (!topK.isEmpty())
            suggestions.add(topK.poll());

        Collections.reverse(suggestions);

        System.out.println("\nSuggestions for \"" + prefix + "\":");

        for (String s : suggestions) {
            System.out.println(s + " (" + frequencyMap.get(s) + " searches)");
        }
    }


    // DFS to collect words from Trie
    private void dfs(TrieNode node, String word, List<String> results) {

        if (node.isEnd)
            results.add(word);

        for (char c : node.children.keySet()) {

            dfs(node.children.get(c),
                    word + c,
                    results);
        }
    }


    // Update frequency after search
    public void updateFrequency(String query) {

        insertQuery(query);

        System.out.println(query + " → Frequency: " +
                frequencyMap.get(query));
    }
}

public class AutocompleteApp {

    public static void main(String[] args) {

        AutocompleteSystem system = new AutocompleteSystem();

        // existing search queries
        system.insertQuery("java tutorial");
        system.insertQuery("javascript");
        system.insertQuery("java download");
        system.insertQuery("java tutorial");
        system.insertQuery("java tutorial");

        // search suggestions
        system.search("jav");

        // update frequency
        system.updateFrequency("java 21 features");

        system.search("java");
    }
}