package uz.pdp.service;

import uz.pdp.exception.DataNotFoundException;
import uz.pdp.model.User;
import uz.pdp.repository.UserRepo;

import java.util.ArrayList;
import java.util.Optional;


public class UserService extends BaseService<User, UserRepo> {

    private static final UserService userService = new UserService();


    public static UserService getInstance() {
        return userService;
    }

    public UserService() {
        super(UserRepo.getInstance());
    }


    public User findByUsername(String username) throws DataNotFoundException {
        return repository.findByUsername(username);
    }


    public User signIn(String username, String password) throws DataNotFoundException {
        User byUsername = repository.findByUsername(username);
        if (byUsername.getPassword().equals(password)) {
            return byUsername;
        }
        return null;
    }

    @Override
    public boolean check(User user) {
        return getAll().stream()
                .anyMatch((user1 -> user1.getUsername().equals(user.getUsername())));

    }
}
