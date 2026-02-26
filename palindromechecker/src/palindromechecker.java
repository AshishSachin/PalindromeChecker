import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
public class PalindromeChecker {


    public static void main(String[] args){
        /*
        UC6
         */


        Scanner scanner = new Scanner(System.in);

        System.out.println("=== UC6: Queue + Stack Based Palindrome Checker ===");
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        // Remove spaces and convert to lowercase
        String processedInput = input.replaceAll("\\s+", "").toLowerCase();

        // Create Queue (FIFO) and Stack (LIFO)
        Queue<Character> queue = new LinkedList<>();
        Stack<Character> stack = new Stack<>();

        // Enqueue and Push characters
        for (int i = 0; i < processedInput.length(); i++) {
            char ch = processedInput.charAt(i);
            queue.add(ch);      // Enqueue
            stack.push(ch);     // Push
        }

        boolean isPalindrome = true;

        // Compare dequeue vs pop
        while (!queue.isEmpty()) {
            char fromQueue = queue.remove();  // Dequeue (FIFO)
            char fromStack = stack.pop();     // Pop (LIFO)

            if (fromQueue != fromStack) {
                isPalindrome = false;
                break;
            }
        }

        // Result
        if (isPalindrome) {
            System.out.println("Result: The given string IS a Palindrome.");
        } else {
            System.out.println("Result: The given string is NOT a Palindrome.");
        }

        scanner.close();


    }
}
