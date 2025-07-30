package cielsachen.ccprog3.mco1.controller;

import java.util.List;
import java.util.Map;

import cielsachen.ccprog3.mco1.helper.Output;
import cielsachen.ccprog3.mco1.helper.PrintColor;
import cielsachen.ccprog3.mco1.model.Ingredient;
import cielsachen.ccprog3.mco1.model.Transaction;
import cielsachen.ccprog3.mco1.model.Truck;
import cielsachen.ccprog3.mco1.service.TransactionService;

/** Represents a controller for interacting with transactions. */
public class TransactionController {
    private final TransactionService service;

    /**
     * Creates and returns a new {@code TransactionController} object instance.
     *
     * @param service The transaction service to use.
     */
    public TransactionController(TransactionService service) {
        this.service = service;
    }

    /**
     * Checks if a truck has any linked transactions. This wraps the {@link TransactionService#hasTransactions
     * hasTransactions()} method.
     *
     * @param truck The truck to check.
     * @return Whether the truck has linked transactions.
     */
    public boolean hasTransactions(Truck truck) {
        return this.service.hasTransactions(truck);
    }

    /**
     * Prints all of the transactions linked with a truck.
     *
     * @param truck The truck to use.
     */
    public void printTransactions(Truck truck) {
        Output.printHeader3("Transactions");

        List<Transaction> transactions = this.service.getTransactionsByTruck(truck);

        for (int index = 0; index < transactions.size(); index++) {
            System.out.println();

            Transaction transaction = transactions.get(index);

            System.out.println("Transaction " + (index + 1));
            System.out.println("  Coffee: " + PrintColor.set(transaction.coffeeName, PrintColor.BRIGHT_CYAN));
            System.out.println("  Ingredients:");

            for (Map.Entry<Ingredient, Double> entry : transaction.getIngredients().entrySet()) {
                Ingredient ingredient = entry.getKey();

                System.out.println("    " + ingredient.name + ": "
                        + PrintColor.set(String.format("%.2f", entry.getValue()) + " " + ingredient.unitMeasure,
                                PrintColor.BRIGHT_CYAN));
            }

            System.out.println("  Cost: " + PrintColor.set(transaction.toCostString(), PrintColor.BRIGHT_GREEN));
        }
    }

    /** Prints all transactions linked with all trucks. */
    public void printTransactions() {
        Output.printHeader3("Transactions");

        List<Transaction> transactions = this.service.getTransactions();

        for (int index = 0; index < transactions.size(); index++) {
            System.out.println();

            Transaction transaction = transactions.get(index);

            System.out.println("Transaction " + (index + 1));
            System.out.println(
                    "  Truck ID: " + PrintColor.set(Integer.toString(transaction.truck.id), PrintColor.BRIGHT_CYAN));
            System.out.println("  Coffee: " + PrintColor.set(transaction.coffeeName, PrintColor.BRIGHT_CYAN));
            System.out.println("  Ingredients:");

            for (Map.Entry<Ingredient, Double> entry : transaction.getIngredients().entrySet()) {
                Ingredient ingredient = entry.getKey();

                System.out.println("    " + ingredient.name + ": "
                        + PrintColor.set(String.format("%.2f", entry.getValue()) + " " + ingredient.unitMeasure,
                                PrintColor.BRIGHT_CYAN));
            }

            System.out.println("  Cost: " + PrintColor.set(transaction.toCostString(), PrintColor.BRIGHT_GREEN));
        }
    }
}
