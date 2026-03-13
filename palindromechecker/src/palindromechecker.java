import java.util.*;

class PlagiarismDetector {

    // n-gram → documents mapping
    private HashMap<String, Set<String>> ngramIndex = new HashMap<>();

    private int N = 5; // 5-grams

    // Add document to database
    public void addDocument(String documentId, String text) {

        List<String> ngrams = generateNgrams(text);

        for (String gram : ngrams) {
            ngramIndex
                    .computeIfAbsent(gram, k -> new HashSet<>())
                    .add(documentId);
        }
    }

    // Analyze a new document
    public void analyzeDocument(String documentId, String text) {

        List<String> ngrams = generateNgrams(text);

        System.out.println("Extracted " + ngrams.size() + " n-grams");

        HashMap<String, Integer> matchCount = new HashMap<>();

        for (String gram : ngrams) {

            if (ngramIndex.containsKey(gram)) {

                for (String doc : ngramIndex.get(gram)) {
                    matchCount.put(doc, matchCount.getOrDefault(doc, 0) + 1);
                }
            }
        }

        for (String doc : matchCount.keySet()) {

            int matches = matchCount.get(doc);

            double similarity = (matches * 100.0) / ngrams.size();

            System.out.println("Found " + matches +
                    " matching n-grams with \"" + doc + "\"");

            System.out.println("Similarity: " + similarity + "%");

            if (similarity > 60) {
                System.out.println("PLAGIARISM DETECTED");
            }

            System.out.println();
        }
    }

    // Generate n-grams
    private List<String> generateNgrams(String text) {

        String[] words = text.toLowerCase().split("\\s+");

        List<String> ngrams = new ArrayList<>();

        for (int i = 0; i <= words.length - N; i++) {

            StringBuilder gram = new StringBuilder();

            for (int j = 0; j < N; j++) {
                gram.append(words[i + j]).append(" ");
            }

            ngrams.add(gram.toString().trim());
        }

        return ngrams;
    }
}

public class PlagiarismDetectionApp {

    public static void main(String[] args) {

        PlagiarismDetector detector = new PlagiarismDetector();

        // Existing documents
        detector.addDocument("essay_089.txt",
                "machine learning algorithms improve automatically through experience");

        detector.addDocument("essay_092.txt",
                "machine learning algorithms improve automatically through experience using data");

        // New document to analyze
        detector.analyzeDocument("essay_123.txt",
                "machine learning algorithms improve automatically through experience using training data");
    }
}