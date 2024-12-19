import java.util.Stack;

public class ProblemSolvingTask {

    public static void main(String[] args) {
        // Test cases
        System.out.println(reverseParentheses("abd(jnb)asdf"));  // Output: abd(bnj)asdf
        System.out.println(reverseParentheses("abdjnbasdf"));   // Output: abdjnbasdf
        System.out.println(reverseParentheses("dd(df)a(ghhh)")); // Output: dd(fd)a(hhhg)
    }

    public static String reverseParentheses(String s) {
        Stack<Character> stack = new Stack<>();

        for (char c : s.toCharArray()) {
            if (c == ')') {
                StringBuilder reversedSubstring = new StringBuilder();

                while (stack.peek() != '(') {
                    reversedSubstring.append(stack.pop());
                }
                stack.push(')');
                stack.pop();

                for (char ch : reversedSubstring.toString().toCharArray()) {
                    stack.push(ch);
                }

                stack.push(')');
            } else {
                stack.push(c);
            }
        }

        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }

        return result.reverse().toString();
    }


}
