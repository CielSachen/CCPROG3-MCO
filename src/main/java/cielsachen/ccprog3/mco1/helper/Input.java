package cielsachen.ccprog3.mco1.helper;

import java.util.InputMismatchException;
import java.util.Scanner;

/** Contains helper methods for getting specific input types through the CLI. */
public class Input {
    private final Scanner scanner;

    /**
     * Creates a new {@code Input} object instance.
     * 
     * @param scanner The scanner to use.
     */
    public Input(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Gets a boolean input from the user.
     * 
     * @param prompt    The prompt to show the user.
     * @param isNewLine Whether the prompt is printed on a new line.
     * @return The user's boolean input.
     */
    public boolean getBoolean(String prompt, boolean isNewLine) {
        while (true) {
            try {
                if (isNewLine) {
                    System.out.println();
                }

                System.out.print(prompt + " ");

                boolean givenBoolean = this.scanner.nextBoolean();

                this.scanner.nextLine();

                return givenBoolean;
            } catch (InputMismatchException exception) {
                this.scanner.nextLine();

                System.out.println();

                ExceptionMessage.INVALID_BOOLEAN_INPUT.print();

                if (!isNewLine) {
                    System.out.println();
                }
            }
        }
    }

    /**
     * Gets a boolean input from the user.
     * 
     * @param prompt The prompt to show the user.
     * @return The user's boolean input.
     */
    public boolean getBoolean(String prompt) {
        return this.getBoolean(prompt, false);
    }

    /**
     * Gets a character input from the user.
     * 
     * @param isNewLine Whether the prompt is printed on a new line.
     * @return The user's character input.
     */
    public char getCharacter(boolean isNewLine) {
        try {
            if (isNewLine) {
                System.out.println();
            }

            return Character.toUpperCase(this.scanner.nextLine().charAt(0));
        } catch (StringIndexOutOfBoundsException exception) {
            return ' ';
        }
    }

    /**
     * Gets a character input from the user.
     * 
     * @return The user's character input.
     */
    public char getCharacter() {
        return this.getCharacter(false);
    }

    /**
     * Gets a float input from the user.
     * 
     * @param prompt    The prompt to show the user.
     * @param isNewLine Whether the prompt is printed on a new line.
     * @return The user's float input.
     */
    public float getFloat(String prompt, boolean isNewLine) {
        while (true) {
            try {
                if (isNewLine) {
                    System.out.println();
                }

                System.out.print(prompt + " ");

                float givenFloat = this.scanner.nextFloat();

                this.scanner.nextLine();

                return givenFloat;
            } catch (InputMismatchException exception) {
                this.scanner.nextLine();

                System.out.println();

                ExceptionMessage.INVALID_FLOAT_INPUT.print();

                if (!isNewLine) {
                    System.out.println();
                }
            }
        }
    }

    /**
     * Gets a float input from the user.
     * 
     * @param prompt The prompt to show the user.
     * @return The user's float input.
     */
    public float getFloat(String prompt) {
        return this.getFloat(prompt, false);
    }

    /**
     * Gets an integer input from the user.
     * 
     * @param prompt    The prompt to show the user.
     * @param isNewLine Whether the prompt is printed on a new line.
     * @return The user's integer input.
     */
    public int getInteger(String prompt, boolean isNewLine) {
        while (true) {
            try {
                if (isNewLine) {
                    System.out.println();
                }

                System.out.print(prompt + " ");

                int givenInteger = this.scanner.nextInt();

                this.scanner.nextLine();

                return givenInteger;
            } catch (InputMismatchException exception) {
                this.scanner.nextLine();

                System.out.println();

                ExceptionMessage.INVALID_INTEGER_INPUT.print();

                if (!isNewLine) {
                    System.out.println();
                }
            }
        }
    }

    /**
     * Gets an integer input from the user.
     * 
     * @param prompt The prompt to show the user.
     * @return The user's integer input.
     */
    public int getInteger(String prompt) {
        return this.getInteger(prompt, false);
    }
}
