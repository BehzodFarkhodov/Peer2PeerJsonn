package uz.pdp.service;

import uz.pdp.exception.DataNotFoundException;
import uz.pdp.model.User;
import uz.pdp.repository.UserRepo;


public class UserService extends BaseService<User, UserRepo> {

    private static final UserService userService = new UserService();


    public static UserService getInstance() {
        return userService;
    }

    public UserService() {
        super(UserRepo.getInstance());
    }


    public User signIn(String username, String password) throws DataNotFoundException {

        User username1 = repository.findByUsername(username);
        if (username1.getPassword().equals(password)) {
            return username1;
        }
        return null;


    }


    @Override
    public boolean check(User user) {
        return false;
    }
}
