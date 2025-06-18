package cielsachen.ccprog3.controller;

import java.util.List;

import cielsachen.ccprog3.helper.PrintColor;
import cielsachen.ccprog3.model.Truck;
import cielsachen.ccprog3.model.transaction.Transaction;
import cielsachen.ccprog3.model.transaction.TransactionIngredient;
import cielsachen.ccprog3.service.TransactionService;

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

            System.out.println("Transaction " + index);
            System.out.println("  Coffee: " + transaction.coffeeName);
            System.out.println("  Ingredients:");

            for (TransactionIngredient transactionIngredient : transaction.getIngredients()) {
                System.out.println("    " + transactionIngredient.ingredient.name + ": "
                        + PrintColor.set(Double.toString(transactionIngredient.amount), PrintColor.BRIGHT_CYAN));
            }

            System.out.println("  Cost: " + PrintColor.set(transaction.toCostString(), PrintColor.BRIGHT_GREEN));
        }
    }
}
