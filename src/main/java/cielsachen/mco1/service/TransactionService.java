package cielsachen.mco1.service;

import java.util.Collections;
import java.util.List;

import cielsachen.mco1.model.Transaction;
import cielsachen.mco1.model.Truck;

public class TransactionService {
    private final List<Transaction> transactions;

    public TransactionService(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(this.transactions);
    }

    public List<Transaction> getTransactionsByTruck(Truck truck) {
        return this.transactions.stream().filter((transaction) -> transaction.truck.equals(truck)).toList();
    }

    public boolean hasTransactions(Truck truck) {
        return this.getTransactionsByTruck(truck).size() > 0;
    }
}
