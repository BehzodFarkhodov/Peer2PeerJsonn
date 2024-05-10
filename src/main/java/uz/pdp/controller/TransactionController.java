package uz.pdp.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import uz.pdp.exception.DataNotFoundException;
import uz.pdp.model.Card;
import uz.pdp.model.Commission;
import uz.pdp.model.Transaction;
import uz.pdp.model.User;
import uz.pdp.service.TransactionService;
import uz.pdp.service.UserService;
import uz.pdp.util.Message;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static uz.pdp.controller.Main.*;


public class TransactionController {
    //    public static void peerToPeer(UUID userId) {
//        UserService userService = new UserService();
//        List<Card> userCards = cardService.getAllCard(currentUser.getId());
//        System.out.println("Your cards:");
//        for (int i = 0; i < userCards.size(); i++) {
//            System.out.println((i + 1) + ". " + userCards.get(i).getCardNumber());
//        }
//
//        System.out.println("Enter the number of your card to send money: ");
//        int selectedCardIndex = scannerInt.nextInt();
//        if (selectedCardIndex < 1 || selectedCardIndex > userCards.size()) {
//            System.out.println("Invalid card number!");
//            return;
//        }
//        Card senderCard = userCards.get(selectedCardIndex - 1);
//        System.out.println("Enter the receiver's card number: ");
//        String receiverCardNumber = scannerStr.nextLine();
//        Card receiverCard = cardService.getCardByCardNumber(receiverCardNumber);
//        System.out.println("Enter the amount to send: ");
//        double amount = scannerDouble.nextDouble();
//
//        double commission = TransactionService.calculateCommission(amount);
//        double totalAmount = amount + commission;
//
//        System.out.println("Are you sure you want to send " + amount + " UZS to " + receiverCardNumber + " with a commission of " + commission + " UZS? (yes/no)");
//        String choice = scannerStr.nextLine();
//        if (!choice.equalsIgnoreCase("yes")) {
//            System.out.println("Transaction cancelled.");
//            return;
//        }
//
//        try {
//           transactionService.addTransaction(senderCard.getId(), receiverCard.getId(), totalAmount);
//        } catch (DataNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//      z  transactionService.add(new Transaction(senderCard.getId(), receiverCard.getId(), amount));
//    }
    public static void transferMoney() {
        List<Card> cards = cardService.getAllCard(currentUser.getId());
        if(cards.isEmpty()){
            System.out.println("Cards not found 👎 ");
            userMenu();
        }
        int i = 1;
        for (Card card : cards) {
            System.out.println(i++ + ". " + card.getCardNumber() + " | " + card.getBalance() + " | " + card.getCategory() + " | " + card.getOwnerId());
        }
        System.out.println("Choose one -> | 0 -> Exit ");
        int index = 0;
        try {
            index = scannerInt.nextInt() - 1;
            if ((index == -1 || index>cards.size())) userMenu();
            System.out.print("Enter a to Card -> ");

            String toCard = scannerStr.nextLine();

            List<Card> cards1 = searchCard(toCard);

            if (cards1.isEmpty()) {
                System.out.println("No such card exists 🤕");
                System.out.println();
                transferMoney();
            }

            System.out.println("Choose one -> | 0 -> Exit");
            ;
            int choice = 0;
            try {
                choice = scannerInt.nextInt() - 1;
                if (choice == -1) userMenu();
            } catch (InputMismatchException | IndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }

            System.out.print("Enter a price 🤑 -> ");
            double price = scannerInt.nextDouble();

            if (cardService.transferMoney(cards.get(index).getId(), cards1.get(choice).getId(), price)) {
                Commission commission = new Commission(cards.get(index).getCategory(), cards1.get(choice).getCategory(), price);
//                Double byCard = commissionService.getByCard(cards.get(index).getCategory(), cards1.get(choice).getCategory());
                transactionService.add(new Transaction(cards.get(index).getId(), cards1.get(choice).getId(), price,commission.getPercentage(),currentUser.getId()));

            } else {
                System.out.println("Your balance is minus 😐");
            }

        } catch (InputMismatchException | IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());

        }


    }

    public static List<Card> searchCard(String card) {
        List<Card> cards = cardService.getAllCards(card);
        cards.forEach(System.out::println);
        return cards;
    }


    public static void transactionsHistory() {
        while (true) {
            System.out.println("1 ---> ALL TRANSACTIONS  |  2 ---> INCOME  |  3 ---> OUTCOME  |  0 ---> EXIT");
            String command = scannerStr.nextLine();
            switch (command) {
                case "1" -> {
                    getALLTransaction();
                }
                case "2" -> {
                    getAllUserIncomeTransaction();
                }
                case "3" -> {
                    getAllUserOutTransaction();
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

//    public static void getAllTransactions() {
//        List<User> users = userService.getAllUser();
//        int i =1;
//        for (User user : users) {
//            System.out.println(i++ + "." + " username : " + user.getUsername() + " | " + " password : " + user.getPassword());
//        }
//        System.out.println("Choose one : ");
//        int choice = scannerInt.nextInt() - 1;
//
//        List<Transaction> transactions = transactionService.getAllByUser(users.get(choice).getId());
//        transactions.forEach(System.out::println);
////        List<Transaction> all = transactionService.getAll();
////        all.stream().forEach(System.out::println);
//        adminMenu();
//    }

    public static void getAllUsersTransactions() {
        System.out.println("1 ---> ALL TRANSACTIONS  |  2 ---> ALL TRANSACTION BY SEARCH USERS  |  0 ---> EXIT ");
        String command = scannerStr.nextLine();

        switch (command) {
         case "1" ->{
             getAllTransactions();
         }
         case "2" ->{
             searchByUserTransactions();
         }
         case "0" ->{
             adminMenu();
             return;
         }
            default -> {
                System.out.println(Message.WRONG);
                return;
            }
        }
    }
public static void getAllTransactions() {
    List<User> users = userService.getAllUser();

    int i = 1;
    for (User user : users) {
        System.out.println(i++ + ". " + "USERNAME: " + user.getUsername() + " | PASSWORD: " + user.getPassword());
    }

    System.out.println("Choose a user: ");
    int choice = scannerInt.nextInt() - 1;

    User selectedUser = users.get(choice);
    List<Card> userCards = cardService.getAllCard(selectedUser.getId());
    if(userCards.isEmpty()){
        System.out.println("Cards not found ❌");
        adminMenu();
    }

    System.out.println("User's Cards 💲: ");
    int j = 1;
    for (Card card : userCards) {
        System.out.println(j++ + "." + "CARD NUMBER :  "  + card.getCardNumber() + " | " + "  BALANCE : " + card.getBalance() + " | CARD TYPE: " + card.getCategory());
    }


    System.out.println("Choose a card: ");
    int cardChoice = scannerInt.nextInt() - 1;

    Card selectedCard = userCards.get(cardChoice);
    List<Transaction> transactions = transactionService.getAllByCard(selectedCard.getId());

    transactions.forEach(System.out::println);

    adminMenu();
}



    public static void getLastWeekTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactionsLastWeek(LocalDate.now(), currentUser.getId());

        transactions.forEach(System.out::println);
    }

    public static List<Transaction> getAllTransactionsLastMonth(LocalDate lcd, UUID id) {
        LocalDate localDate = lcd.minusDays(30);
        ArrayList<Transaction> transactions = new ArrayList<>(transactionService.getUserTransactions(id));
        return transactions.stream().filter(transaction -> transaction.getCreatedDate().isAfter(localDate.atStartOfDay())).collect(Collectors.toList());
    }


    public static void getAllTransactionsLastMonth() {
        List<Transaction> transactions = getAllTransactionsLastMonth(LocalDate.now(),currentUser.getId());
        transactions.forEach(System.out::println);
    }

    public static List<Transaction> getAllTransactionsLastWeek(LocalDate lcd, UUID id) {
        LocalDate localDate = lcd.minusWeeks(2);
        ArrayList<Transaction> transactions = new ArrayList<>(transactionService.getUserTransactions(id));
        return transactions.stream().filter(transaction -> transaction.getCreatedDate().isAfter(localDate.atStartOfDay())).collect(Collectors.toList());
    }

    public static void getAllTransactionsLastWeek() {
        List<Transaction> transactions = getAllTransactionsLastWeek(LocalDate.now(),currentUser.getId());
        transactions.forEach(System.out::println);
    }


    public static void getALLTransaction() {
        List<Card> cards = cardService.getAllCard(currentUser.getId());
        if(cards.isEmpty()){
            System.out.println("Cards not found 👎");
            userMenu();
        }

        int i = 1;
        for (Card card : cards) {
            System.out.println(i++ + "." + "CARD NUMBER : " + card.getCardNumber() + " | "  + "BALANCE : " + card.getBalance() + " | " + "CARD TYPE : " +  card.getCategory());
        }
        System.out.println("Choose your card : ");
        int choice = scannerInt.nextInt()-1;
        if(choice < 0 || choice > cards.size()){
            userMenu();
        }
        List<Transaction> getAllTransaction = transactionService.getAllByCard(cards.get(choice).getId());
       if(getAllTransaction.isEmpty()){
           System.out.println("EMPTY ❌");
           userMenu();
       }
        getAllTransaction.forEach(System.out::println);

        System.out.println("1 ---> LAST WEEK TRANSACTIONS  |  2 ---> LAST MONTH TRANSACTIONS  | 0 ---> EXIT");
        String command = scannerStr.nextLine();

        switch (command) {
            case "1" -> {
                getAllTransactionsLastWeek();
            }
            case "2" -> {
                getAllTransactionsLastMonth();
            }
            case "0" -> {
                userMenu();
            }
            default -> {
                System.out.println(Message.WRONG);
            }

        }
    }

    public static void getAllUserIncomeTransaction() {
        List<Card> cards = cardService.getAllCard(currentUser.getId());
        if(cards.isEmpty()){
            System.out.println("Cards not found ");
            userMenu();
        }
        System.out.println("Your all cards : ");
        int i = 1;
        for (Card card : cards) {
            System.out.println(i++ + "." + "CARD NUMBER : " + card.getCardNumber() + " | "  + "BALANCE : " + card.getBalance() + " | " + "CARD TYPE : " +  card.getCategory());
        }
        System.out.println("Choose your card : ");
        int choice = scannerInt.nextInt() - 1;

        List<Transaction> transactions = transactionService.getIncomeTransactions(cards.get(choice).getId());
        if (transactions.isEmpty()) {
            System.out.println("EMPTY ❌");
            userMenu();
        }
        transactions.forEach(System.out::println);
    }

    public static void getAllUserOutTransaction() {
        List<Card> cards = cardService.getAllCard(currentUser.getId());
        if(cards.isEmpty()){
            System.out.println("Cards not found ");
            userMenu();
        }
        int i = 1;
        for (Card card : cards) {
            System.out.println(i++ + "." + "CARD NUMBER : " + card.getCardNumber() + " | "  + "BALANCE : " + card.getBalance() + " | " + "CARD TYPE : " +  card.getCategory());
        }
        System.out.println("Choose your card : ");
        int choice = scannerInt.nextInt() - 1;
        List<Transaction> transactions = transactionService.getOutcomeTransactions(cards.get(choice).getId());
        if(transactions.isEmpty()){
            System.out.println("EMPTY ❌");
            userMenu();
        }
        transactions.forEach(System.out::println);
    }

    public static void currency() {
        while (true) {
            System.out.println("1 ---> SUM TO ANOTHER CURRENCY  |  2 ---> ANOTHER CURRENCY TO SUM  |  0 ---> EXIT ");
           String command = scannerStr.nextLine();
            switch (command) {
                case "1" -> currencySum();
                case "2" -> currencyAnother();
                case "0" -> {
                    userMenu();
                }
            }
        }

    }

    public static void currencyAnother() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ArrayList<Bank> banks = objectMapper.readValue(new URL("https://cbu.uz/uz/arkhiv-kursov-valyut/json/"), new TypeReference<ArrayList<Bank>>() {});

            int i = 1;
            for (Bank bank1 : banks) {
                System.out.println(i++ + "." + bank1);
            }

            System.out.println("Choose one Currency : ");
            int choice = scannerInt.nextInt() - 1;
            Bank selectedBank = banks.get(choice);

            System.out.println("Enter the amount in USD " + selectedBank.getCcy() + ":");
            double summa = scannerDouble.nextDouble();

            double rate = selectedBank.getRate();
            double result = rate / summa;

            System.out.println("Equivalent in UZS: " + result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void currencySum() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            ArrayList<Bank> banks = objectMapper.readValue(new URL("https://cbu.uz/uz/arkhiv-kursov-valyut/json/"), new TypeReference<ArrayList<Bank>>() {
            });
            int i = 1;
            for (Bank bank1 : banks) {
                System.out.println(i++ + " ." + bank1);
            }

            System.out.println("Choose one Currency ->");
            int choose = scannerInt.nextInt() - 1;

            Bank bank = banks.get(choose);
            System.out.println("Enter the amount in UZS");

            double enterSumma = scannerDouble.nextDouble();
            System.out.println("Equivalent in USD: " + enterSumma / bank.getRate());


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void betweenDaysTransaction() {
        System.out.print("Enter days (dd/MM/yyyy) -> ");
        String lsd = null;
        LocalDate date = LocalDate.parse(lsd = scannerStr.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        System.out.print("Enter second day (dd/MM/yyyy) -> ");
        String lsd2 = null;
        LocalDate date2 = LocalDate.parse(lsd2 = scannerStr.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        List<Transaction> transactionList = transactionService.getUserTransactionsInPeriod(date, date2);
        transactionList.forEach(System.out::println);
        adminMenu();

    }
    public static void topUsers2() {
        List<User> users = userService.getAllUser();
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        Map<User, Double> userCommissionMap = new HashMap<>();

        for (User user : users) {
            List<Transaction> userTransactions = transactionService.getAllByUser(user.getId());
            double totalCommission = userTransactions.stream()
                    .mapToDouble(Transaction::getCalculatedCommission)
                    .sum();
            userCommissionMap.put(user, totalCommission);
        }

        List<Map.Entry<User, Double>> topUsers = userCommissionMap.entrySet().stream()
                .sorted((entry1, entry2) -> Double.compare(entry2.getValue(), entry1.getValue()))
                .limit(5)
                .toList();

        if (topUsers.isEmpty()) {
            System.out.println("No users with transactions found.");
            return;
        }

        System.out.println("Top 5 users with the highest commission:");
        int rank = 1;
        for (Map.Entry<User, Double> entry : topUsers) {
            System.out.println(rank++ + ". Username: " + entry.getKey().getUsername());
        }
    }



    public static void searchByUserTransactions(){
        System.out.println("Enter username to search for user transactions 🔎 : ");
        String username = scannerStr.nextLine();
        User user = userService.getUserByUsername(username);

        if (user == null) {
            System.out.println("User not found ❌ ");
            adminMenu();
            return;
        }

        List<Card> userCards = cardService.getAllCard(user.getId());
        if (userCards.isEmpty()) {
            System.out.println("No cards found for user ❌ : " + username);
            adminMenu();
            return;
        }

        System.out.println("Choose a card for transactions: ");
        int i = 1;
        for (Card card : userCards) {
            System.out.println(i++ + ". CARD NUMBER: " + card.getCardNumber() + " | BALANCE: " + card.getBalance() + " | CARD TYPE: " + card.getCategory());
        }

        int choice = scannerInt.nextInt() - 1;
        if (choice < 0 || choice >= userCards.size()) {
            System.out.println(Message.WRONG);
            adminMenu();
            return;
        }
        Card selectedCard = userCards.get(choice);
        List<Transaction> cardTransactions = transactionService.getAllByCard(selectedCard.getId());

        if (cardTransactions.isEmpty()) {
            System.out.println("No transactions found for selected card ❌ :  " + selectedCard.getCardNumber());
        } else {
            System.out.println("Transactions for card " + selectedCard.getCardNumber() + ":");
            cardTransactions.forEach(System.out::println);
        }

        adminMenu();
    }



}











