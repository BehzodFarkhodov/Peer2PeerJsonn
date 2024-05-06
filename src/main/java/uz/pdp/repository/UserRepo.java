package uz.pdp.repository;

import uz.pdp.exception.DataNotFoundException;
import uz.pdp.model.User;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class UserRepo extends BaseRepo<User> {


    private static final UserRepo userRepo = new UserRepo();


    public static UserRepo getInstance() {
        return userRepo;
    }

    public UserRepo() {
        super.path = "src/main/resources\\users.json";
        super.type = User.class;
    }


    public User findByUsername(String username) throws DataNotFoundException {
        ArrayList<User> users = getAll();

        Optional<User> user1 = users.stream().filter((user -> user.getUsername().equals(username))).findAny();

        if (user1.isEmpty()) throw new DataNotFoundException("data not found");
        return user1.get();
    }
}

