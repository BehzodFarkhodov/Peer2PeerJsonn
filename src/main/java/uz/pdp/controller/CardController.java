package uz.pdp.controller;

import uz.pdp.enumerator.Category;
import uz.pdp.model.Card;
import uz.pdp.util.Message;

import javax.swing.*;
import java.util.List;

import static uz.pdp.controller.Main.*;

public class CardController {
    public static void crudCard() {
        while (true) {
            System.out.println("1 ---> CREATE CARD  |  2 --->  ShOW CARDS  |  3 ---> DELETE CARD  |  0 --->  EXIT ");
            String command = scannerStr.nextLine();
            switch (command) {
                case "1" -> {
                    createCard();
                }
                case "2" -> {
                    showCards();
                }
                case "3" -> {
                    deleteCard();
                }
                case "0" -> {
                    userMenu();
                }
            }
        }
    }

    private static void createCard() {
        Category category = getCategory();

        System.out.println("Enter the card number ( 1 dan 16 gacha raqam ) : ");
        String number = scannerStr.nextLine();

        System.out.println("Enter the card balance : ");
        double balance = scannerDouble.nextDouble();

        if (number.length() != 16) {
            System.out.println(Message.WRONG);
        }
        cardService.add(new Card(currentUser.getId(), number, category, balance, true));
        System.out.println(Message.SUCCESSFULLY);
    }

    public static Category getCategory() {
        System.out.println("1 --->  HUMO  |   2 --->  UZCARD   | 3 --->  VIZA  |   0 --->  EXIT ");
        String choice = scannerStr.nextLine();
        Category category = null;
        switch (choice) {
            case "1" -> {
                category = Category.HUMO;
            }
            case "2" -> {
                category = Category.UZCARD;
            }
            case "3" -> {
                category = Category.VIZA;
            }
            case "0" -> {
                userMenu();
            }
            default -> System.out.println(Message.WRONG);
        }
        return category;
    }

    public static List<Card> showCards() {
        List<Card> cards = cardService.getAllCard(currentUser.getId());
        if (cards.isEmpty()) {
            System.out.println(Message.NOTFOUND);
        } else {
            for (Card card : cards) {
                System.out.println("Card Number: " + card.getCardNumber());
                System.out.println("Card Type: " + card.getCategory());
                System.out.println("Balance: " + card.getBalance());
                System.out.println("------------------------");
            }
        }
        return cards;
    }

    public static void deleteCard() {
        List<Card> cards = showCards();
        int i = 1;
        for (Card card : cards) {
            System.out.println(i++ + "." + card.getCardNumber() + " | " + card.getBalance() + " | " + card.getCategory());
        }
        System.out.println("Choose one : ");
        int choice = scannerInt.nextInt() - 1;
        if ((choice >= cards.size())) {
            System.out.println(Message.WRONG);
        } else {
            cardService.delete(cards.get(choice).getId());
            System.out.println(Message.DELETE);
        }
    }

}


