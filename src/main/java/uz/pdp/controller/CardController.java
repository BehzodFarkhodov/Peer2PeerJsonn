package uz.pdp.controller;

import uz.pdp.enumerator.Category;
import uz.pdp.model.Card;
import uz.pdp.util.Message;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static uz.pdp.controller.Main.*;

public class CardController {
    public static void crudCard() {
        System.out.println(" 1 : Create card \t 2 : Delete Card\t  3 Show Card \t 0 : Exit  ");
        String command = scannerStr.nextLine();
        switch (command) {
            case "1" -> createCard();
            case "2" -> deleteCard();
            case "3" -> showCard();

        }

    }

    private static void deleteCard() {
        List<Card> cards = showCard();

        try {
            int choose = inputInt("Choose for Delete ->") - 1;
            cardService.delete(cards.get(choose).getId());
            System.out.println(Message.DELETE);
        } catch (InputMismatchException | IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }

    }

    private static List<Card> showCard() {
        List<Card> allCard = cardService.getAllCard(currentUser.getId());

        AtomicInteger index = new AtomicInteger(1);

        allCard.stream()
                .map((card -> index.getAndIncrement() + ". " + card.toString()))
                .forEach(System.out::println);
        return allCard;




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
        cardService.add(new Card(currentUser.getId(), number, category, balance));
        System.out.println(Message.SUCCESSFULLY);
    }

    public static Category getCategory() {
        System.out.println(" 1 : HUMO  2 : UZCARD 3 : VIZA  0 : EXIT ");
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

   /* private static void accept(Card card) {
        (Card::toString);
    }*/
}
