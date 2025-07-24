package cielsachen.ccprog3.mco1.service;

import java.util.Collections;
import java.util.List;

import cielsachen.ccprog3.mco1.model.Transaction;
import cielsachen.ccprog3.mco1.model.Truck;

/**
 * Represents a service for managing transactions. This uses a standard Java {@link List} instead of a dedicated
 * repository.
 */
public class TransactionService {
    private final List<Transaction> transactions;

    /**
     * Creates a new {@code TransactionService} object instance.
     * 
     * @param transactions The list of transactions to use.
     */
    public TransactionService(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Gets all transactions linked to any trucks.
     * 
     * @return The transactions.
     */
    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(this.transactions);
    }

    /**
     * Adds a transaction to the list.
     * 
     * @param transaction The transaction to add.
     */
    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    /**
     * Gets all transactions linked with a truck.
     * 
     * @param truck The truck linked with the transactions to get.
     * @return The transactions linked with the truck.
     */
    public List<Transaction> getTransactionsByTruck(Truck truck) {
        return this.transactions.stream().filter((transaction) -> transaction.truck.equals(truck)).toList();
    }

    /**
     * Checks if a truck has any linked transactions.
     * 
     * @param truck The truck to check.
     * @return Whether the truck has linked transactions.
     */
    public boolean hasTransactions(Truck truck) {
        return this.getTransactionsByTruck(truck).size() > 0;
    }
}
