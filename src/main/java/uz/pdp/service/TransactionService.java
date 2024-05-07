package uz.pdp.service;

import uz.pdp.model.Transaction;
import uz.pdp.repository.TransactionRepo;

import java.util.List;
import java.util.UUID;

public class TransactionService extends BaseService<Transaction, TransactionRepo> {
    public TransactionService(TransactionRepo repository) {
        super(repository);
    }

    public static double calculateCommission(double amount) {
        return amount * 0.01;
    }

    @Override
    public boolean check(Transaction transaction) {
        return false;
    }
    public List<Transaction> getUserTransactions(UUID userId) {
        return repository.getUserTransactions(userId);
    }
    public void addTransaction(UUID fromCard, UUID toCard, double amount) {
        repository.addTransaction(fromCard, toCard, amount);
    }
    public List<Transaction> getAllTransactionsFromFile() {
        return repository.getAllTransactionsFromFile();
    }

}
