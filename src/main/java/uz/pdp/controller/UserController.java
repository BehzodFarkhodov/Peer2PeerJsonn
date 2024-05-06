package uz.pdp.controller;

import uz.pdp.model.Role;
import uz.pdp.model.User;
import uz.pdp.util.Message;


import static uz.pdp.controller.Main.*;

public class UserController {

    public static void signUp() {
        String name = inputStr("Enter name :");
        String username = inputStr("Enter username :");
        String password = inputStr("Enter password :");

        if (userService.add(new User(name,username,password))) {
            System.out.println(Message.SUCCESSFULLY);
        } else {
            System.out.println();
        }

    }

    public static void signIn() {
        String username = inputStr("Enter username :");
        String password = inputStr("Enter password :");

        currentUser = userService.signIn(username, password);
        if (currentUser==null) {
            System.out.println("Wrong username or password");
        } else if (currentUser.getRole().equals(Role.ADMIN)) {
            AdminController.adminMenu();
        } else {
            System.out.println("Welcome " + currentUser.getName()+" 🫡");
            userMenu();
        }

    }


    public static void userMenu(){
        System.out.println("user ");
    }


}
