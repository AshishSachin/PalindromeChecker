public class PalindromeChecker {


    public static void main(String[] args){
        /*
        UC5
         */
        public static void main(String[] args) {

            // Hardcoded input
            String input = "madam";

            // Create Stack
            Stack<Character> stack = new Stack<>();

            // Push all characters into stack
            for (int i = 0; i < input.length(); i++) {
                stack.push(input.charAt(i));
            }

            boolean isPalindrome = true;

            // Pop characters and compare
            for (int i = 0; i < input.length(); i++) {
                char poppedChar = stack.pop();

                if (input.charAt(i) != poppedChar) {
                    isPalindrome = false;
                    break;
                }
            }

            // Print result
            System.out.println("Input text: " + input);
            System.out.println("Is it a Palindrome? : " + isPalindrome);


    }
}
