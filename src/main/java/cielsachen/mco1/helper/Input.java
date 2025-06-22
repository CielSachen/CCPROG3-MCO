package cielsachen.mco1.helper;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {
    public static final String INTEGER_ID_ERROR_MESSAGE = "Only input from one of the integer IDs!";
    public static final String CHARACTER_ID_ERROR_MESSAGE = "Only input from one of the character IDs!";

    private static Input instance = null;

    private final Scanner scanner;

    private Input(Scanner scanner) {
        this.scanner = scanner;
    }

    public static Input getInstance(Scanner scanner) {
        if (Input.instance == null) {
            Input.instance = new Input(scanner);
        }

        return Input.instance;
    }

    public boolean getBoolean(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + " ");

                boolean response = this.scanner.nextBoolean();

                this.scanner.nextLine();

                return response;
            } catch (InputMismatchException exception) {
                this.scanner.nextLine();

                System.out.println();

                System.out.println(PrintColor.set("Only input `true` or `false`!", PrintColor.RED));

                System.out.println();
            }
        }
    }

    public char getCharacter() {
        try {
            return Character.toUpperCase(this.scanner.nextLine().charAt(0));
        } catch (StringIndexOutOfBoundsException exception) {
            return ' ';
        }
    }

    public float getFloat(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + " ");

                float response = this.scanner.nextFloat();

                this.scanner.nextLine();

                return response;
            } catch (InputMismatchException exception) {
                this.scanner.nextLine();

                System.out.println();

                System.out.println(PrintColor.set("Only input a floating point number!", PrintColor.RED));

                System.out.println();
            }
        }
    }

    public int getInteger(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + " ");

                int response = this.scanner.nextInt();

                this.scanner.nextLine();

                return response;
            } catch (InputMismatchException exception) {
                this.scanner.nextLine();

                System.out.println();

                System.out.println(PrintColor.set("Only input an integer!", PrintColor.RED));

                System.out.println();
            }
        }
    }
}
