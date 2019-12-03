import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;

public class App {
    private final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        new App().start();
    }

    private void start() {
        String BLUE = "\033[0;34m";
        String YELLOW = "\033[0;33m";
        String CLEAR = "\033[0m";

        System.out.println("Enter two strings to process:");
        System.out.print("String number 1: ");
        String input1 = sc.nextLine().trim();
        System.out.print("String number 2: ");
        String input2 = sc.nextLine().trim();
        System.out.println();

        if (input1.isEmpty() || input2.isEmpty()) {
            throw new InputMismatchException("Input can't be empty.");
        }

        showMenu();

        int choice = Integer.MAX_VALUE;

        options:
        while (true) {
            if (choice != Integer.MAX_VALUE && choice != 1)
                System.out.println("0. Exit | 1. Show menu");

            choice = sc.nextInt();
            sc.nextLine();


            switch (choice) {
                case 2:
                    System.out.println("Enter a substring: ");
                    String sub = sc.nextLine();
//                    if (isSubIn(input2, sub)) {
//                        System.out.println(input2 + "\033[0;34m contains \033[0m" + sub);
//                    } else {
//                        System.out.println(input2 + "\033[0;34m does not contain \033[0m" + sub);
//                    }
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

    private boolean isSubIn(String str, String sub) {
        return str.contains(sub);
    }

    private String reverse(String str) {
        char[] chars = str.toCharArray();

        for (int i = 0; i < str.length() / 2; i++) {
            char temp = chars[i];
            chars[i] = chars[chars.length - (1 + i)];
            chars[chars.length - (1 + i)] = temp;
        }
        return new String(chars);
    }

//    private String changeCase(String str) {
//        IntPredicate isLower = c -> (c >= 97 && c <= 122);
////        Predicate<Integer> isLetter = Character::isLetter;
//
//        Converter converter = n -> {
////            if (Character.isLetter(n)) {
////                return Character.toString((char) (r + (r >= 97 && r <= 122 ? -32 : 32)));
////                return r >= 97 && r <= 122 ? (char) (r - 32) : (char) (r + 32);
////            }
//            int change = isLower.test(n) ? -32 : 32;
//            return Character.toString(Character.isLetter(n) ? (char) (n + change) : (char) n);
//        };
//
////        char[] chars = str.chars().mapToObj(ch -> {
////            char r = (char) ch;
////            int change = isLower.test(ch) ? -32 : 32;
////
////            return Character.toString(isLetter.test(r) ? (char) (r + change) : r);
////
////        }).collect(Collectors.joining()).toCharArray();
//
////        char[] chars = str.chars().mapToObj(converter::convert)
////                .collect(Collectors.joining()).toCharArray();
//
////        return new String(chars);
//
//        //return a string
//        return str.chars().mapToObj(converter::convert)
//                .collect(Collectors.joining());
//    }

    private String changeCase(String str) {
        IntPredicate isLower = c -> (c >= 97 && c <= 122);

        Converter converter = n -> {
            int change = isLower.test(n) ? -32 : 32;
            return Character.toString(Character.isLetter(n) ? (char) (n + change) : (char) n);
        };

        //return a string
        return str.chars().mapToObj(converter::convert)
                .collect(Collectors.joining());
    }

    private void showMenu() {
        System.out.println("2.Search for a substring");
        System.out.println("3.Reverse a string");
        System.out.println("4.Change case");
        System.out.println("5.Reverse and change case");
        System.out.println("1.Show menu");
        System.out.println("0.Exit");
    }

    interface Converter {
        String convert(int n);
    }
}
