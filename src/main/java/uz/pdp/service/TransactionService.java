package uz.pdp.service;

import uz.pdp.model.Card;
import uz.pdp.model.Transaction;
import uz.pdp.repository.TransactionRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static uz.pdp.controller.Main.*;

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

//    public List<Transaction> getAllIncomeTransactionsForUser(UUID userId) {
//        return repository.getAllIncomeTransactionsForUser(userId);
//    }

    public List<Transaction> getOutcomeTransactions(UUID id){
        return repository.getOutcomeTransactions(id);
    }
    public List<Transaction> getIncomeTransactions(UUID id){
        return repository.getIncomeTransactions(id);
    }
    public List<Transaction> getAllUserTransactions(UUID userId) {
        List<Transaction> allTransactions = transactionService.getAll();
        List<Transaction> incomeTransactions = new ArrayList<>();

        List<Card> allCards = cardService.getAllCard(currentUser.getId());
        for (Card card : allCards) { //
            for (Transaction trans : allTransactions) { //
                if(trans.getToCard().equals(card.getId()) ||
                        trans.getFromCard().equals(card.getId())){
                    incomeTransactions.add(trans);
                    System.out.println(trans);
                }
            }
        }
        return incomeTransactions;
    }

    public List<Transaction> getAllUserIncomeTransaction(UUID userId) {
        List<Transaction> allTransactions = transactionService.getAll();
        List<Transaction> incomeTransactions = new ArrayList<>();

        List<Card> allCards = cardService.getAllCard(currentUser.getId());
        for (Card card : allCards) {
            for (Transaction trans : allTransactions) {
                if(trans.getFromCard().equals(card.getId())){
                    incomeTransactions.add(trans);
                }
            }
        }
        return incomeTransactions;
    }
    public List<Transaction> getAllUserOutcomeTransaction(UUID userId) {
        List<Transaction> allTransactions = transactionService.getAll();
        List<Transaction> outcomeTransactions = new ArrayList<>();

        List<Card> allCards = cardService.getAllCard(currentUser.getId());
        for (Card card : allCards) {
            for (Transaction trans : allTransactions) {
                if(trans.getToCard().equals(card.getId())){
                    outcomeTransactions.add(trans);
                }
            }
        }
        return outcomeTransactions;
    }


}
