import java.util.*;

public class PalindromeChecker {

    // Reverse Method
    public static boolean reverseMethod(String text){
        String reversed = new StringBuilder(text).reverse().toString();
        return text.equals(reversed);
    }

    // Stack Method
    public static boolean stackMethod(String text){
        Stack<Character> stack = new Stack<>();

        for(char c : text.toCharArray()){
            stack.push(c);
        }

        String reversed = "";

        while(!stack.isEmpty()){
            reversed += stack.pop();
        }

        return text.equals(reversed);
    }

    // Deque Method
    public static boolean dequeMethod(String text){
        Deque<Character> deque = new ArrayDeque<>();

        for(char c : text.toCharArray()){
            deque.addLast(c);
        }

        while(deque.size() > 1){
            if(deque.removeFirst() != deque.removeLast()){
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter a string:");
        String input = sc.nextLine();

        long start, end;

        // Reverse Method
        start = System.nanoTime();
        boolean r1 = reverseMethod(input);
        end = System.nanoTime();
        System.out.println("Reverse Method Result: " + r1);
        System.out.println("Time Taken: " + (end - start) + " ns");

        // Stack Method
        start = System.nanoTime();
        boolean r2 = stackMethod(input);
        end = System.nanoTime();
        System.out.println("Stack Method Result: " + r2);
        System.out.println("Time Taken: " + (end - start) + " ns");

        // Deque Method
        start = System.nanoTime();
        boolean r3 = dequeMethod(input);
        end = System.nanoTime();
        System.out.println("Deque Method Result: " + r3);
        System.out.println("Time Taken: " + (end - start) + " ns");

        sc.close();
    }
}