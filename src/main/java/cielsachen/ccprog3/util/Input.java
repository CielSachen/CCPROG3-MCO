package cielsachen.ccprog3.util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {
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

                System.out.println(PrintColor.setColor("Only input `true` or `false`!", PrintColor.RED));

                System.out.println();
            }
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

                System.out.println(PrintColor.setColor("Only input a floating point number!", PrintColor.RED));

                System.out.println();
            }
        }
    }
}
