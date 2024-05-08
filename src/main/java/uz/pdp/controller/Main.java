package uz.pdp.controller;

import uz.pdp.enumerator.Role;
import uz.pdp.model.User;
import uz.pdp.repository.CardRepo;
import uz.pdp.repository.TransactionRepo;
import uz.pdp.service.CardService;
import uz.pdp.service.CommissionService;
import uz.pdp.service.TransactionService;
import uz.pdp.service.UserService;
import uz.pdp.util.Message;

import java.util.Scanner;

import static uz.pdp.controller.CardController.crudCard;
import static uz.pdp.controller.CardController.deleteCard;
import static uz.pdp.controller.UserController.signIn;
import static uz.pdp.controller.UserController.signUp;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static Scanner scannerInt = new Scanner(System.in);
    public static Scanner scannerStr = new Scanner(System.in);

    public static Scanner scannerDouble = new Scanner(System.in);
    public static UserService userService = UserService.getInstance();
    public static CardService cardService = CardService.getInstance();
    public static TransactionService transactionService = new TransactionService(new TransactionRepo());
    public static CommissionService commissionService = CommissionService.getInstance();
    public static User currentUser = null;

    public static void main(String[] args) {

        mainMenu();
    }

    static {
        userService.add(new User("1", "1", "1", Role.ADMIN));
        userService.add(new User("2", "2", "2", Role.USER));
    }

    static void mainMenu() {
        while (true) {
            System.out.println("1 ----> SIGN UP  |  2 ----> LOGIN   ");
            String command = scannerStr.nextLine();
            switch (command) {
                case "1" -> {
                    signUp();
                }
                case "2" -> {
                    signIn();
                }
                default -> {
                    System.out.println(Message.WRONG);
                }
            }
        }
    }

    public static void userMenu() {
        while (true) {
            System.out.println("1 ---> CRUD CARD  | 2 --->  P2P  | 3 ---> HISTORY CARD  |  4 --->  CURRENCY INFO  | 0 --->  EXIT");
            String command = scannerStr.nextLine();
            switch (command) {
                case "1" -> {
                    crudCard();
                }
                case "2" -> {
                    TransactionController.transferMoney();
                }
                case "3" -> {
                    TransactionController.transactionsHistory();
                }
                case "4" -> {
                    TransactionController.currency();
                }
                case "0" -> {
                    mainMenu();
                }

            }
        }
    }

    public static void adminMenu() {
        System.out.println("1 ---> TRANSACTIONS  |  2 ---> IN PERIOD  |  3 ---> CHANGE COMMISSION  |  0 ---> EXIT");
        String command = scannerStr.nextLine();
        while (true) {
            switch (command){
                case "1" ->{
                    TransactionController.getAllTransactions();
                }
                case "2"->{

                }
                case "3" ->{
                  CommissionController.changeCommission();
                }
                case "0" ->{
                  mainMenu();
                }
            }
        }
    }

    public static int inputInt(String hint) {
        System.out.println(hint);
        return scannerInt.nextInt();
    }

    public static double inputDouble(String hint) {
        System.out.println(hint);
        return scannerDouble.nextDouble();
    }

    public static String inputStr(String hint) {
        System.out.println(hint);
        return scannerStr.nextLine();
    }
}