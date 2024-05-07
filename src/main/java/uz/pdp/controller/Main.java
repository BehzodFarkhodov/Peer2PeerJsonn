package uz.pdp.controller;

import uz.pdp.enumerator.Role;
import uz.pdp.model.User;
import uz.pdp.repository.CardRepo;
import uz.pdp.repository.TransactionRepo;
import uz.pdp.service.CardService;
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

    public  static  Scanner scannerDouble = new Scanner(System.in);
    public static UserService userService = UserService.getInstance();
    public static CardService cardService = new CardService(new CardRepo());
    public static TransactionService transactionService = new TransactionService(new TransactionRepo());
    public static User currentUser= null;

    public static void main(String[] args) {
        mainMenu();
    }
    static {
        userService.add(new User("1","1","1", Role.ADMIN));
        userService.add(new User("2","2","2", Role.USER));
    }

    private static void mainMenu() {
       while (true){

           System.out.println("1 ----> SIGN UP  |  2 ----> LOGIN   ");
           String command = scannerStr.nextLine();
           switch (command){
               case "1" ->{
                   signUp();}
               case "2" ->{
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
                      TransactionController.peerToPeer(currentUser.getId());
                }
                case "3" -> {
                         TransactionController.transactionsHistory();
                }
                case "4" -> {

                }
                case "0" -> {
                mainMenu();
                }

            }
        }
    }

    public static void adminMenu(){
        System.out.println("1 ---> ADMIN MENU ");
    }



    public static int inputInt(String hint) {
        System.out.println(hint);
        return scannerInt.nextInt();
    }

    public static String inputStr(String hint) {
        System.out.println(hint);
        return scannerStr.nextLine();
    }
}