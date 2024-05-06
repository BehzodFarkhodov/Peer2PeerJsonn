package uz.pdp.controller;


import uz.pdp.enumerator.Role;

import uz.pdp.model.User;
import uz.pdp.util.Message;


import java.util.Objects;

import static uz.pdp.controller.Main.*;

public class UserController {

    public static void signUp() {
        String name = inputStr("Enter name :");
        String username = inputStr("Enter username :");
        String password = inputStr("Enter password :");

        if (userService.add(new User(name, username, password, Role.USER))) {
            System.out.println(Message.SUCCESSFULLY);
        } else {
            System.out.println();
        }

    }


    public static void signIn() {

        String username = inputStr("Enter username :");
        String password = inputStr("Enter password :");

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
