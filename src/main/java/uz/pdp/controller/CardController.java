package uz.pdp.controller;

import uz.pdp.enumerator.Category;
import uz.pdp.model.Card;
import uz.pdp.util.Message;

import static uz.pdp.controller.Main.*;

public class CardController {
    public static void crudCard() {
        System.out.println(" 1 : Create card 2 : Delete Card  0 : Exit  ");
        String command = scannerStr.nextLine();
        switch (command) {
            case "1" -> {
                createCard();
            }
        }

    }

    private static void createCard() {
        System.out.println(" 1 : HUMO  2 : UZCARD  3 : VIZA  4 : EXIT ");

        System.out.println("Enter the card type : ");
        String choice = scannerStr.nextLine();

        System.out.println("Enter the card number ( 1 dan 16 gacha raqam ) : ");
        String number = scannerStr.nextLine();

        System.out.println("Enter the card balance : ");
        double balance = scannerInt.nextDouble();


    }

//    public static Category getCategory() {
//        System.out.println(" 1 : HUMO  2 : UZCARD 3 : VIZA  0 : EXIT ");
//        String choice = scannerStr.nextLine();
//        Category category = null;
//        switch (choice) {
//            case "1" -> category = Category.HUMO;
//            case "2" -> category = Category.UZCARD;
//            case "3" -> category = Category.VIZA;
//            case "0" -> {
//                userMenu();
//
//            }
//            default -> System.out.println(Message.WRONG);
//        }
//
//    }
}
