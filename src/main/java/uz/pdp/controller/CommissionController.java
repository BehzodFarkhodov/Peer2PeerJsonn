package uz.pdp.controller;

import uz.pdp.enumerator.Category;
import uz.pdp.model.Commission;
import uz.pdp.util.Message;

import javax.swing.*;

import static uz.pdp.controller.Main.*;

public class CommissionController {

    public static void changeCommission() {
        System.out.println("1 ---> ChangeCommissionDifferenceCategory  |  2 ---> ChangeCommissionSameCategory  |  0 ---> Exit");
        String command = scannerStr.nextLine();
        switch (command) {
            case "1" -> {
                changeCommissionDifference();
            }
            case "2" -> {
                changeCommissionTheSameCategory();
            }
            case "0" -> {
                adminMenu();
            }
            default -> {
                System.out.println(Message.WRONG);
            }
        }
    }

    public static void changeCommissionDifference() {
        System.out.println("1 ---> HUMO <--> UZCARD  |   2 ---> HUMO <--> VIZA  |  3 ---> UZCARD <--> VIZA  |  0 --> EXIT");
        String command = scannerStr.nextLine();
        switch (command) {
            case "1" -> {
                changeCardsCommission(Category.HUMO, Category.UZCARD);
            }
            case "2" -> {
                changeCardsCommission(Category.HUMO, Category.VIZA);
            }
            case "3" -> {
                changeCardsCommission(Category.UZCARD, Category.VIZA);
            }
            case "0" -> {
                adminMenu();
            }

        }
    }

    public static void changeCommissionTheSameCategory() {
        System.out.println("1 ---> HUMO <--> HUMO  |  2 ---> VIZA <-->  3  |  3 ---> UZCARD <--> UZCARD  |  0 ---> EXIT");
        String command = scannerStr.nextLine();
        switch (command) {
            case "1" -> {
                changeCardsCommission(Category.HUMO, Category.HUMO);
            }
            case "2" -> {
                changeCardsCommission(Category.VIZA, Category.VIZA);
            }
            case "3" -> {
                changeCardsCommission(Category.UZCARD, Category.UZCARD);
            }
            case "0" -> {
                adminMenu();
            }
            default -> {
                System.out.println(Message.WRONG);
            }
        }
    }


    public static void changeCardsCommission(Category category1, Category category2) {
        System.out.println("Enter the commission (%)  : ");
        Double percentage = scannerDouble.nextDouble();

        commissionService.add(new Commission(category1, category2, percentage));
        System.out.println("Commission changed");


    }
}
