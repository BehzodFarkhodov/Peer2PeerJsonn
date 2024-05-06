package uz.pdp.controller;

import uz.pdp.enumerator.Role;
import uz.pdp.model.User;
import uz.pdp.repository.CardRepo;
import uz.pdp.service.CardService;
import uz.pdp.service.UserService;

import java.util.Scanner;

import static uz.pdp.controller.CardController.crudCard;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static Scanner scannerInt = new Scanner(System.in);
    public static Scanner scannerStr = new Scanner(System.in);

    public static UserService userService = UserService.getInstance();
    public static CardService cardService = new CardService(new CardRepo());
    public static User currentUser= null;

    public static void main(String[] args) {

        mainMenu();
    }
    static {
        userService.add(new User("1","1","1", Role.ADMIN));
        userService.add(new User("2","2","2", Role.USER));
    }

    private static void mainMenu() {
        while (true) {
            System.out.println("1 SignUp \t 2 SignIn \t 0 Ext");
            String command = inputStr("Choose ->");
            switch (command) {
                case "1" -> UserController.signUp();
                case "2"->UserController.signIn();
                case "0" -> {
                    return;
                }
            }
        }
    }

    public static void userMenu() {
        while (true) {
            System.out.println("1 : Crud Card  2 : P2P  3 : History By Card  4 : Currency Info 5 : Exit");
            String command = scannerStr.nextLine();
            switch (command) {
                case "1" -> {
                    crudCard();
                }
                case "2" -> {

                }
                case "3" -> {

                }
                case "4" -> {

                }
                case "0" -> {

                }

            }
        }
    }
    public static void adminMenu(){
        while (true){

        }
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