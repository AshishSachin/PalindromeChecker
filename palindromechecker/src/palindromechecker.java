import java.util.Scanner;
import java.util.Stack;

// PalindromeChecker class (Encapsulation)
class PalindromeChecker {

    // Method to check if a string is palindrome
    public boolean checkPalindrome(String input) {

        Stack<Character> stack = new Stack<>();

        // Push characters into stack
        for (int i = 0; i < input.length(); i++) {
            stack.push(input.charAt(i));
        }

        // Create reversed string using stack
        String reversed = "";
        while (!stack.isEmpty()) {
            reversed = reversed + stack.pop();
        }

        // Compare original and reversed string
        return input.equals(reversed);
    }
}

// Main Application Class
public class UseCase11PalindromeCheckerApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Palindrome Checker App ===");
        System.out.print("Enter a string: ");

        String input = scanner.nextLine();

        // Create object of PalindromeChecker
        PalindromeChecker checker = new PalindromeChecker();

        // Check palindrome
        boolean result = checker.checkPalindrome(input);

        if (result) {
            System.out.println("The string is a Palindrome.");
        } else {
            System.out.println("The string is NOT a Palindrome.");
        }

        scanner.close();
    }
}
