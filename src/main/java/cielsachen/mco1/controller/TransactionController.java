package cielsachen.mco1.controller;

import java.util.List;

import cielsachen.mco1.helper.PrintColor;
import cielsachen.mco1.model.Truck;
import cielsachen.mco1.model.transaction.Transaction;
import cielsachen.mco1.model.transaction.TransactionIngredient;
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
        System.out
                .println("---------- + " + PrintColor.set("Transactions", PrintColor.BRIGHT_YELLOW) + " + ----------");

        List<Transaction> transactions = this.service.getTransactionsByTruck(truck);

        for (int index = 0; index < transactions.size(); index++) {
            System.out.println();

            Transaction transaction = transactions.get(index);

            System.out.println("Transaction " + (index + 1));
            System.out.println("  Coffee: " + PrintColor.set(transaction.coffeeName, PrintColor.BRIGHT_CYAN));
            System.out.println("  Ingredients:");

            for (TransactionIngredient transactionIngredient : transaction.getIngredients()) {
                System.out
                        .println("    " + transactionIngredient.ingredient.name + ": "
                                + PrintColor.set(
                                        String.format("%.2f", transactionIngredient.getAmount()) + " "
                                                + transactionIngredient.ingredient.unitMeasure,
                                        PrintColor.BRIGHT_CYAN));
            }

            System.out.println("  Cost: " + PrintColor.set(transaction.toCostString(), PrintColor.BRIGHT_GREEN));
        }
    }
}
