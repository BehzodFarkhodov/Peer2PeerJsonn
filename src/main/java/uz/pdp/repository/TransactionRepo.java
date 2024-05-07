package uz.pdp.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import uz.pdp.model.Card;
import uz.pdp.model.Transaction;
import uz.pdp.model.User;
import uz.pdp.service.TransactionService;
import uz.pdp.util.Message;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TransactionRepo extends BaseRepo<Transaction> {
    private static final TransactionRepo transactionRepo = new TransactionRepo();

    public static TransactionRepo getInstance() {
        return transactionRepo;
    }
    public TransactionRepo() {
        super.path = "src/main/resources/transaction.json";
        super.type = Transaction.class;
    }

    public List<Transaction> getUserTransactions(UUID userId) {
        List<Transaction> allTransactions = getAll();
        List<Transaction> userTransactions = new ArrayList<>();
        for (Transaction transaction : allTransactions) {
            if (transaction.getFromCard().equals(userId)) {
                userTransactions.add(transaction);
            }
        }
        return userTransactions;
    }
    public void addTransaction(UUID fromCard, UUID toCard, double amount) {
        Transaction transaction = new Transaction(fromCard, toCard, amount);
        add(transaction);
        CardRepo cardRepo = CardRepo.getInstance();
        Card fromCardObj = cardRepo.getById(fromCard);
        Card toCardObj = cardRepo.getById(toCard);
        if (fromCardObj != null && toCardObj != null) {
            fromCardObj.setBalance(fromCardObj.getBalance() - amount);
            toCardObj.setBalance(toCardObj.getBalance() + amount);

            cardRepo.update(fromCardObj);
            cardRepo.update(toCardObj);
            System.out.println(Message.SUCCESSFULLY);
        } else {
            System.out.println(Message.WRONG);
        }
    }
    public List<Transaction> getAllTransactionsFromFile() {
        List<Transaction> transactions = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            transactions = objectMapper.readValue(new File(path), new TypeReference<List<Transaction>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public List<Transaction> getOutcomeTransactions(UUID id){
        ArrayList<Transaction> transactions = getAll();
        return transactions.stream().filter(transaction -> transaction.getFromCard().equals(id)).toList();
    }
    public List<Transaction> getIncomeTransactions(UUID id){
        ArrayList<Transaction> transactions = getAll();
        return transactions.stream().filter(transaction -> transaction.getToCard().equals(id)).collect(Collectors.toList());
    }













    public List<Transaction> getUserTransactionsInPeriod(UUID userId, LocalDateTime date1, LocalDateTime date2) {
        List<Transaction> transactions = getUserTransactions(userId);
        return transactions.stream().filter(transaction -> transaction.getCreatedDate().isAfter(date1) &&
                transaction.getCreatedDate().isBefore(date2)).toList();
    }
}
