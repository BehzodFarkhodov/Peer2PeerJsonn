package uz.pdp.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import uz.pdp.model.Card;
import uz.pdp.model.Transaction;
import uz.pdp.model.User;
import uz.pdp.service.TransactionService;
import uz.pdp.util.Message;
import static uz.pdp.controller.Main.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
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

//    public List<Transaction> getAllUserTransactions(UUID userId) {
//        List<Transaction> allTrans = getAll();
//        List<Transaction> userTransactions = new ArrayList<>();
//
//        List<Card> allCards  =  cardService.getAllCard(currentUser.getId());
//
//        for (Card card : allCards) {
//            for (Transaction transaction : allTrans) {
//                if(transaction.getFromCard().equals(card.getId()) || transaction.getToCard().equals(card.getId())){
//                    userTransactions.add(transaction);
//                }
//            }
//        }
//        return userTransactions;
//    }
public List<Transaction> getAllUserTransactions(UUID userId) {
    List<Transaction> allTrans = getAll();
    List<Transaction> userTransactions = new ArrayList<>();

    Map<UUID, Card> userCardsMap = cardService.getAllCards(userId)
            .stream()
            .collect(Collectors.toMap(Card::getId, Function.identity()));

    for (Transaction transaction : allTrans) {
        if (userCardsMap.containsKey(transaction.getFromCard()) || userCardsMap.containsKey(transaction.getToCard())) {
            userTransactions.add(transaction);
        }
    }
    return userTransactions;
}





    public List<Transaction> getAllUserIncomeTransactions(UUID userId) {
        List<Transaction> allTrans = getAll();
        List<Transaction> userTransactions = new ArrayList<>();

        List<Card> allCards  =  cardService.getAllCard(currentUser.getId());

        for (Card card : allCards) {
            for (Transaction transaction : allTrans) {
                if(transaction.getToCard().equals(card.getId())){
                    userTransactions.add(transaction);
                }
            }
        }
        return userTransactions;
    }

    public List<Transaction> getAllUserOutComeTransactions(UUID userId) {
        List<Transaction> allTrans = getAll();
        List<Transaction> userTransactions = new ArrayList<>();

        List<Card> allCards  =  cardService.getAllCard(currentUser.getId());

        for (Card card : allCards) {
            for (Transaction transaction : allTrans) {
                if(transaction.getFromCard().equals(card.getId())){
                    userTransactions.add(transaction);
                }
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
        List<Transaction> transactions = getAllUserTransactions(userId);
        return transactions.stream().filter(transaction -> transaction.getCreatedDate().isAfter(date1) &&
                transaction.getCreatedDate().isBefore(date2)).toList();
    }
}
