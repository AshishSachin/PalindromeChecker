import java.util.Scanner;
import java.util.Stack;
public class PalindromeChecker {




    public static void main(String[] args){
        /*
        UC10
         */
        // Public method exposed to client
        public boolean checkPalindrome(String input) {

            String processedInput = input.replaceAll("\\s+", "").toLowerCase();

            Stack<Character> stack = new Stack<>();

            // Push all characters to stack
            for (int i = 0; i < processedInput.length(); i++) {
                stack.push(processedInput.charAt(i));
            }

            // Compare with original order
            for (int i = 0; i < processedInput.length(); i++) {
                if (processedInput.charAt(i) != stack.pop()) {
                    return false;
                }
            }

            return true;
        }
    }

    // Application Class (Client)
    public class UseCase11PalindromeCheckerApp {

        public static void main(String[] args) {

            Scanner scanner = new Scanner(System.in);

            System.out.println("=== UC11: Object-Oriented Palindrome Service ===");
            System.out.print("Enter a string: ");
            String input = scanner.nextLine();

            // Create object of service class
            PalindromeChecker checker = new PalindromeChecker();

            boolean result = checker.checkPalindrome(input);

            if (result) {
                System.out.println("Result: The given string IS a Palindrome.");
            } else {
                System.out.println("Result: The given string is NOT a Palindrome.");
            }

            scanner.close();


    }
}
