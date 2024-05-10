package uz.pdp.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import uz.pdp.exception.DataNotFoundException;
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
import java.util.*;
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





/*  public List<Transaction> getAllUserIncomeTransactions(UUID userId) {
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
    }*/

    public List<Transaction> getAllUserIncomeTransactions(UUID userId) {
        List<Card> cards=cardService.getAllCards(currentUser.getId());
        return getAll().stream()
                .filter(transaction -> cards.stream()
                        .map(Card::getId)
                        .anyMatch((cardId -> cardId.equals(transaction.getToCard()))))
                .collect(Collectors.toList());
    }

   /* public List<Transaction> getAllUserOutComeTransactions(UUID userId) {
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
    }*/

    public List<Transaction> getAllUserOutComeTransactions(UUID userId) {
        List<Card>cards=cardService.getAllCards(currentUser.getId());

        return getAll().stream()
                .filter((transaction -> cards.stream()
                        .map(Card::getId)
                        .anyMatch((cardId-> cardId.equals(transaction.getFromCard())))))
                .collect(Collectors.toList());


    }




//    public void addTransaction(UUID fromCard, UUID toCard, double amount) throws DataNotFoundException {
//        Transaction transaction = new Transaction(fromCard, toCard, amount);
//        add(transaction);
//        CardRepo cardRepo = CardRepo.getInstance();
//        Card fromCardObj = cardRepo.getById(fromCard);
//        Card toCardObj = cardRepo.getById(toCard);
//        if (fromCardObj != null && toCardObj != null) {
//            fromCardObj.setBalance(fromCardObj.getBalance() - amount);
//            toCardObj.setBalance(toCardObj.getBalance() + amount);
//
//            cardRepo.update(fromCardObj);
//            cardRepo.update(toCardObj);
//            System.out.println(Message.SUCCESSFULLY);
//        } else {
//            System.out.println(Message.WRONG);
//        }
//    }
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
    public List<Transaction> getAllTransactionsLastWeek(LocalDate localDate, UUID id){
        LocalDate localDate1 = localDate.minusDays(7);
        List<Transaction> transactions = getAllUserTransactions(id);
       return transactions.stream().filter(transaction -> transaction.getCreatedDate().isAfter(localDate.atStartOfDay())).collect(Collectors.toList());
    }

    public List<Transaction> getIncomeTransactions(UUID id){
        ArrayList<Transaction> transactions = getAll();
        return transactions.stream().filter(transaction -> transaction.getToCard().equals(id)).collect(Collectors.toList());
    }


    public List<Transaction> getUserTransactionsInPeriod(LocalDate date, LocalDate date2){
        ArrayList<Transaction> transactions = new ArrayList<>(getAll());
        return transactions.stream().
                filter(transaction -> transaction.getCreatedDate().isAfter(date.atStartOfDay()) && transaction.getCreatedDate().
                        isBefore(date2.atStartOfDay())).collect(Collectors.toList());
    }

    public List<Transaction> getAllByUser(UUID id){
        ArrayList<Transaction> transactions = new ArrayList<>(getAll());
        return transactions.stream().filter(transaction -> transaction.getOwnerId().equals(id)).collect(Collectors.toList());
    }

    public List<Transaction> getAllByCard(UUID cardId){
        return getAll().stream()
                .filter(transaction -> transaction.getFromCard().equals(cardId) || transaction.getToCard().equals(cardId))
                .collect(Collectors.toList());
    }

}
