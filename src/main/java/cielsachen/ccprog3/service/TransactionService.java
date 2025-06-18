package cielsachen.ccprog3.service;

import java.util.List;

import cielsachen.ccprog3.model.Truck;
import cielsachen.ccprog3.model.transaction.Transaction;

public class TransactionService {
    private final List<Transaction> transactions;

    public TransactionService(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    public List<Transaction> getTransactionsByTruck(Truck truck) {
        return this.transactions.stream().filter((transaction) -> transaction.truckId == truck.id).toList();
    }

    public boolean hasTransactions(Truck truck) {
        return this.getTransactionsByTruck(truck).size() > 0;
    }
}
