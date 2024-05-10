package uz.pdp.controller;


import uz.pdp.enumerator.Role;

import uz.pdp.exception.DataNotFoundException;
import uz.pdp.model.User;
import uz.pdp.util.Message;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


import static uz.pdp.controller.Main.*;

public class UserController {

    public static void signUp() {
        System.out.print("Enter the name : ");
        String name = scannerStr.nextLine();

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


        try {
            currentUser = userService.signIn(username, password);
        } catch (DataNotFoundException e) {
            System.out.println("data not found");
            signIn();
        }

        if (Objects.isNull(currentUser)) {
            System.out.println(Message.WRONG);
            signIn();
        } else if (currentUser.getRole().equals(Role.USER)) {
            System.out.println("WELCOME " + currentUser.getUsername() + "😃");
            userMenu();
        } else if (currentUser.getRole().equals(Role.ADMIN)) {
            System.out.println("WELCOME ADMIN ⭐⭐");
            Main.adminMenu();
        }
    }

    public static void topUsers(){
        HashMap<User,Double> userDoubleHashMap = userService.topUsers();
        userDoubleHashMap.forEach((user, aDouble) -> {
            System.out.println(user.getUsername());
        });
    }

//    public static void topFiveUsers(){
//        List<User> users = userService.topFiveUsers()
//
//    }


}