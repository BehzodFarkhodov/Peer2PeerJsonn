package uz.pdp.controller;


import uz.pdp.enumerator.Role;

import uz.pdp.model.User;
import uz.pdp.util.Message;


import java.util.Objects;
import java.util.SortedMap;

import static uz.pdp.controller.Main.*;

public class UserController {

    public static void signUp() {
        System.out.print("Enter the name : ");
        String name =scannerStr.nextLine();

        System.out.print("Enter the username : ");
        String username = scannerStr.nextLine();

        System.out.print("Enter the password : ");
        String password = scannerStr.nextLine();

        if (userService.add(new User(name, username, password, Role.USER))) {
            System.out.println(Message.SUCCESSFULLY);
        } else {
            System.out.println();
        }

    }


    public static void signIn() {
        System.out.print("Enter the username : ");
        String username = scannerStr.nextLine();

        System.out.print("Enter the password : ");
        String password = scannerStr.nextLine();

        currentUser = userService.signIn(username, password);
        if (Objects.isNull(currentUser)) {
            System.out.println(Message.WRONG);
        } else if (currentUser.getRole().equals(Role.USER)) {
            userMenu();
        } else if (currentUser.getRole().equals(Role.ADMIN)) {
              Main.adminMenu();
        }
    }



}
