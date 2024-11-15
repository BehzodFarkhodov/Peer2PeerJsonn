package uz.pdp.service;

import uz.pdp.exception.DataNotFoundException;
import uz.pdp.model.Card;
import uz.pdp.model.Transaction;
import uz.pdp.repository.TransactionRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TransactionService extends BaseService<Transaction, TransactionRepo> {
    static CommissionService commissionService = new CommissionService();

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
        return repository.getAllUserTransactions(userId);
    }

    //    public void addTransaction(UUID fromCard, UUID toCard, double amount) throws DataNotFoundException {
//        Optional<Transaction> byId = findById(fromCard);
//        Optional<Transaction> byId1 = findById(toCard);
//        Double byCard = commissionService.getByCard(byId.get().getFromCard(), byId1.get().getToCard());
//        repository.addTransaction(fromCard, toCard, amount, by);
//    }
    public List<Transaction> getAllTransactionsFromFile() {
        return repository.getAllTransactionsFromFile();
    }

    public List<Transaction> getOutcomeTransactions(UUID id) {
        return repository.getOutcomeTransactions(id);
    }

    public List<Transaction> getIncomeTransactions(UUID id) {
        return repository.getIncomeTransactions(id);
    }

    public List<Transaction> getAllUserIncomeTransactions(UUID userId) {
        return repository.getAllUserIncomeTransactions(userId);
    }

    public List<Transaction> getAllUserOutComeTransactions(UUID userId) {
        return repository.getAllUserOutComeTransactions(userId);
    }

    public List<Transaction> getAllTransactionsLastWeek(LocalDate localDate, UUID id) {
        return repository.getAllTransactionsLastWeek(localDate, id);
    }

    public List<Transaction> getUserTransactionsInPeriod(LocalDate date, LocalDate date2) {
        return repository.getUserTransactionsInPeriod(date, date2);
    }







    public List<Transaction> getAllByUser(UUID id){
        return repository.getAllByUser(id);
    }
    public List<Transaction> getAllByCard(UUID cardId){
        return repository.getAllByCard(cardId);
    }



}
