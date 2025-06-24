package cielsachen.mco1.controller;

import java.util.List;
import java.util.Map;

import cielsachen.mco1.helper.Output;
import cielsachen.mco1.helper.PrintColor;
import cielsachen.mco1.model.Ingredient;
import cielsachen.mco1.model.Transaction;
import cielsachen.mco1.model.Truck;
import cielsachen.mco1.service.TransactionService;

public class TransactionController {
    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    public boolean hasTransactions(Truck truck) {
        return this.service.hasTransactions(truck);
    }

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
