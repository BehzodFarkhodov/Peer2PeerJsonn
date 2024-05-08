package uz.pdp.controller;

import uz.pdp.enumerator.Category;
import uz.pdp.model.Commission;

import static uz.pdp.controller.Main.*;

public class CommissionController {
    public static void changeCommission() {
        System.out.println("1 ---> HUMO <--> UZCARD  |   2 ---> HUMO <--> VIZA  |  3 ---> UZCARD <--> VIZA  |  0 --> EXIT");
        String command = scannerStr.nextLine();
        switch (command) {
            case "1" -> {
                changeCardsCommission(Category.HUMO,Category.UZCARD);
            }
            case "2" -> {
               changeCardsCommission(Category.HUMO,Category.VIZA);
            }
            case "3" -> {
               changeCardsCommission(Category.UZCARD,Category.VIZA);
            }
            case "0" -> {
               mainMenu();
            }
        }
    }

    public static void changeCardsCommission(Category category1,Category category2){
        System.out.println("Enter the commission (%)  : ");
        double percentage = scannerDouble.nextDouble();
        //commissionService.add(new Commission(category1,category2,percentage));
    }
}
