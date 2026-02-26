public class PalindromeChecker {


    public static void main(String[] args){
        /*
        UC4
         */
        String input = "madam";


        char[] characters = input.toCharArray();


        int start = 0;
        int end = characters.length - 1;

        boolean isPalindrome = true;

        while (start < end) {
            if (characters[start] != characters[end]) {
                isPalindrome = false;
                break;
            }
            start++;
            end--;
        }


        System.out.println("Input text: " + input);
        System.out.println("Is it a Palindrome? : " + isPalindrome);


    }
}
