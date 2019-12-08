import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
//new test

public class App {
    //Scanner to get the input
    private final Scanner sc = new Scanner(System.in);
    //inputs
    private String input1;
    private String input2;

    public static void main(String[] args) {
        //start the application
        new App().start();
    }

    private void setInputs() {
        //get inputs from the user
        //trim the whitespace
        System.out.println("Enter two strings to process:");
        System.out.print("String number 1: ");
        input1 = sc.nextLine().trim();
        System.out.print("String number 2: ");
        input2 = sc.nextLine().trim();
    }

    private void start() {
        //ANSI colors to highlight console outputs
        String BLUE = "\033[0;34m";
        String YELLOW = "\033[0;33m";
        String CLEAR = "\033[0m";

        setInputs();
        //if any of the inputs is empty throw an exception
        if (input1.isEmpty() || input2.isEmpty()) {
            throw new InputMismatchException("Input can't be empty.");
        }
        //show the menu
        showMenu();
        //set choice variable initially to the default value for the first iteration of the loop
        int choice = Integer.MAX_VALUE;

        options:
        while (true) {
            //if this is not the first iteration of the loop or case 1 was not selected display the output
            if (choice != Integer.MAX_VALUE && choice != 1)
                System.out.println("0. Exit | 1. Show menu");

            try {
                //get user's choice
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                //if input is not an integer set choice to default value
                choice = Integer.MAX_VALUE;
            }
            //consume the remaining input leftover from nextInt()
            sc.nextLine();
            //get input from the user
            //select the appropriate case depending on the user input
            switch (choice) {
                case 2:
                    System.out.println("Enter a substring: ");
                    String sub = sc.nextLine();
                    System.out.println("'" + input2 + "'" + BLUE + " does " + (isSubIn(input2, sub) ? "" : "not ")
                            + "contain " + CLEAR + "'" + sub + "'");
                    break;
                case 3:
                    System.out.println("Reversing a string...");
                    input2 = reverse(input2);
                    System.out.println("String reversed is: " + BLUE + input2 + CLEAR);
                    break;
                case 4:
                    System.out.println("Changing case...");
                    input2 = changeCase(input2);
                    System.out.println("Case changed: " + BLUE + input2 + CLEAR);
                    break;
                case 5:
                    System.out.println("Changing both...");
                    input2 = reverse(changeCase(input2));
                    System.out.println("Changed string: " + BLUE + input2 + CLEAR);
                    break;
                case 6:
                    setInputs();
                    System.out.println(BLUE + "Inputs have been changed!" + CLEAR);
                    break;
                case 1:
                    showMenu();
                    break;
                case 0:
                    break options;
                default:
                    System.out.println(YELLOW + "<< check your input >>" + CLEAR);
            }

        }
        System.out.println("The result is: " + BLUE + input1 + " " + input2 + CLEAR);
    }

    //method returns true is the substring(sub) is in the string(str)
//    private boolean isSubIn(String str, String sub) {
//        return str.contains(sub);
//    }
    private boolean isSubIn(String str, String sub) {
        int subLen = sub.length();
        int strLen = str.length();

        if (subLen > strLen) return false;

        for (int i = 0; i <= strLen - subLen; i++) {
            StringBuilder sb = new StringBuilder();

            for (int k = i; k < subLen + i; k++) {
                sb.append(str.charAt(k));
            }

            if (sb.toString().equals(sub))
                return true;
        }
        return false;
    }

    //method reverses a string
    private String reverse(String str) {
        char[] chars = str.toCharArray();

        for (int i = 0; i < str.length() / 2; i++) {
            char temp = chars[i];
            chars[i] = chars[chars.length - (1 + i)];
            chars[chars.length - (1 + i)] = temp;
        }
        return new String(chars);
    }
    //method for changing case of the string
    private String changeCase(String str) {
        //this lambda accepts an int and returns a boolean
        IntPredicate isLower = c -> (c >= 97 && c <= 122);

        //this lambda returns a character in a form of a string
        Converter converter = n -> {
            //check case and set 'change' variable accordingly
            int change = isLower.test(n) ? -32 : 32;
            //check if a codePoint passed refers to a string
            //if it is add or subtract 32
            //if it is not just return a string representation of this character
            return Character.toString(Character.isLetter(n) ? (char) (n + change) : (char) n);
        };

        //convert a string str into lowercase or uppercase
        //return a string
        return str.chars().mapToObj(converter::convert)
                .collect(Collectors.joining());
    }

    //method that shows the menu
    private void showMenu() {
        System.out.println("------------------------");
        System.out.println("2.Search for a substring");
        System.out.println("3.Reverse a string");
        System.out.println("4.Change case");
        System.out.println("5.Reverse and change case");
        System.out.println("6.Change inputs");
        System.out.println("0.Exit");
        System.out.println("------------------------");
    }

    //interface for the 'converter' lambda
    interface Converter {
        String convert(int n);
    }
}
