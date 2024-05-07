package uz.pdp.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import uz.pdp.model.Card;
import uz.pdp.model.Transaction;
import uz.pdp.service.TransactionService;
import uz.pdp.service.UserService;
import uz.pdp.util.Message;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static uz.pdp.controller.Main.*;


public class TransactionController {
    public static void peerToPeer(UUID userId) {
        UserService userService = new UserService();
        List<Card> userCards = cardService.getAllCard(currentUser.getId());
        System.out.println("Your cards:");
        for (int i = 0; i < userCards.size(); i++) {
            System.out.println((i + 1) + ". " + userCards.get(i).getCardNumber());
        }

        System.out.println("Enter the number of your card to send money: ");
        int selectedCardIndex = scannerInt.nextInt();
        if (selectedCardIndex < 1 || selectedCardIndex > userCards.size()) {
            System.out.println("Invalid card number!");
            return;
        }
        Card senderCard = userCards.get(selectedCardIndex - 1);
        System.out.println("Enter the receiver's card number: ");
        String receiverCardNumber = scannerStr.nextLine();
        Card receiverCard = cardService.getCardByCardNumber(receiverCardNumber);
        System.out.println("Enter the amount to send: ");
        double amount = scannerDouble.nextDouble();

        double commission = TransactionService.calculateCommission(amount); // 1% komissiya
        double totalAmount = amount + commission;

        System.out.println("Are you sure you want to send " + amount + " UZS to " + receiverCardNumber + " with a commission of " + commission + " UZS? (yes/no)");
        String choice = scannerStr.nextLine();
        if (!choice.equalsIgnoreCase("yes")) {
            System.out.println("Transaction cancelled.");
            return;
        }

        transactionService.addTransaction(senderCard.getId(), receiverCard.getId(), totalAmount);

        transactionService.add(new Transaction(senderCard.getId(), receiverCard.getId(), amount));
    }

    public static void transactionsHistory() {
        while (true) {
            System.out.println("1 ---> ALL TRANSACTIONS  |  2 ---> INCOME  |  3 ---> OUTCOME  |  0 ---> EXIT");
            String command = scannerStr.nextLine();
            switch (command) {
                case "1" -> {
                    getAllTransactions();
                }
                case "2" -> {
                    getLastWeekTransactions();
                }
                case "3" -> {

                }
                case "0" -> {
                    userMenu();
                }
                default -> {
                    System.out.println(Message.WRONG);
                }
            }
        }


    }

    public static List<Transaction> getAllTransactions() {
        List<Transaction> all = transactionService.getAllTransactionsFromFile();
        all.forEach(transaction -> {
            System.out.println("From Card: " + transaction.getFromCard());
            System.out.println("To Card: " + transaction.getToCard());
            System.out.println("Amount: " + transaction.getAmount());
            System.out.println("--------------------");
        });
        return all;
    }

    public static void getLastWeekTransactions() {
        List<Transaction> AllTransaction = transactionService.getAllTransactionsFromFile();
        List<Transaction> lastWeekTransactions = new ArrayList<>();
        LocalDateTime lastWeek = LocalDateTime.now().minusWeeks(1);
        for (Transaction transaction : AllTransaction) {
            if (transaction.getTransactionDate().isAfter(lastWeek)) {
                lastWeekTransactions.add(transaction);
            }
        }
    }

    public static void currency() {

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            ArrayList<Bank> banks = objectMapper.readValue(new URL("https://cbu.uz/uz/arkhiv-kursov-valyut/json/"), new TypeReference<ArrayList<Bank>>() {
            });
            int i = 1;
            for (Bank bank1 : banks) {
                System.out.println(i++ + " ." + bank1);
            }

            int choose = inputInt("Choose one Valuate ->") - 1;


            Bank bank = banks.get(choose);
            double enterSumma = inputInt("Choose Enter Summa :");

            System.out.println(enterSumma / bank.getRate());


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}











