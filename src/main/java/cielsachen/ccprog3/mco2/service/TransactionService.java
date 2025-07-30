package cielsachen.ccprog3.mco2.service;

import java.util.Collections;
import java.util.List;

import cielsachen.ccprog3.mco2.model.Transaction;
import cielsachen.ccprog3.mco2.model.Truck;

/**
 * Represents a service for managing transactions. The business logic happens here instead of in the models.
 * <p>
 * This uses a standard Java {@link List list} instead of a dedicated repository.
 */
public class TransactionService {
    private final List<Transaction> transactions;

    /**
     * Creates and returns a new {@code TransactionService} object instance.
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
     * Gets all transactions linked with a truck.
     *
     * @param truck The truck linked with the transactions to get.
     * @return The transactions linked with the truck.
     */
    public List<Transaction> getTransactionsByTruck(Truck truck) {
        return this.transactions.stream().filter((t) -> t.truck.equals(truck)).toList();
    }

    /**
     * Adds a transaction to the list.
     *
     * @param transaction The transaction to add.
     */
    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }
}
