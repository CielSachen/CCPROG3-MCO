package cielsachen.mco1.helper;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {
    private final Scanner scanner;

    public Input(Scanner scanner) {
        this.scanner = scanner;
    }

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

    public boolean getBoolean(String prompt) {
        return this.getBoolean(prompt, false);
    }

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

    public char getCharacter() {
        return this.getCharacter(false);
    }

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

    public float getFloat(String prompt) {
        return this.getFloat(prompt, false);
    }

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

    public int getInteger(String prompt) {
        return this.getInteger(prompt, false);
    }
}
