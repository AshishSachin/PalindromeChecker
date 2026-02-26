import java.util.Scanner;
public class PalindromeChecker {




    public static void main(String[] args){
        /*
        UC9
         */
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== UC9: Recursive Palindrome Checker ===");
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        // Remove spaces and convert to lowercase
        String processedInput = input.replaceAll("\\s+", "").toLowerCase();

        boolean isPalindrome = checkPalindrome(processedInput, 0, processedInput.length() - 1);

        if (isPalindrome) {
            System.out.println("Result: The given string IS a Palindrome.");
        } else {
            System.out.println("Result: The given string is NOT a Palindrome.");
        }

        scanner.close();
    }

    // Recursive method
    private static boolean checkPalindrome(String str, int start, int end) {

        // Base Condition
        if (start >= end) {
            return true;
        }

        // If mismatch found
        if (str.charAt(start) != str.charAt(end)) {
            return false;
        }

        // Recursive Call
        return checkPalindrome(str, start + 1, end - 1);




    }
}
