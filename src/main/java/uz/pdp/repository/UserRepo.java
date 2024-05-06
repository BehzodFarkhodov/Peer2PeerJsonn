package uz.pdp.repository;

import uz.pdp.exception.DataNotFoundException;
import uz.pdp.model.User;

import java.util.ArrayList;
import java.util.Objects;

public class UserRepo extends BaseRepo<User> {


    private static final UserRepo userRepo = new UserRepo();


    public static UserRepo getInstance() {
        return userRepo;
    }

    public UserRepo() {
        super.path = "src/main/resources/users.json";
        super.type = User.class;
    }


    public User findByUsername(String username) {
        ArrayList<User> users = getAll();

        User user1 = users.stream().filter((user -> user.getUsername().equals(username))).findAny().get();

        if (Objects.isNull(user1)) {
            try {
                throw new DataNotFoundException("data not found ");
            } catch (DataNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
        return user1;
    }
}

