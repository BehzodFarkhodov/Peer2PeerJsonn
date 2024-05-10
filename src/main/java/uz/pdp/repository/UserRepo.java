package uz.pdp.repository;

import uz.pdp.enumerator.Role;
import uz.pdp.exception.DataNotFoundException;
import uz.pdp.model.Transaction;
import uz.pdp.model.User;
import uz.pdp.service.TransactionService;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class UserRepo extends BaseRepo<User> {
    TransactionService transactionService = new TransactionService(TransactionRepo.getInstance());


    private static final UserRepo userRepo = new UserRepo();


    public static UserRepo getInstance() {
        return userRepo;
    }

    public UserRepo() {
        super.path = "src/main/resources/users.json";
        super.type = User.class;
    }


    public User findByUsername(String username) throws DataNotFoundException {
        return getAll().stream()
                .filter(user -> user.getUsername().equals(username))
                .findAny().orElseThrow(new Supplier<DataNotFoundException>() {
                    @Override
                    public DataNotFoundException get() {
                        return new DataNotFoundException("data not found");
                    }
                });
    }
//    public List<User> topUsers(){
//        ArrayList<User> users = getAll();
//        HashMap<User,Double> hashMap = new HashMap<>();
//        for (User user : users) {
//            List<Transaction> allTransactionsLastWeek = transactionService.getAllTransactionsLastWeek(LocalDate.now(), user.getId());
//            double amount = 0;
//            for (Transaction transaction : allTransactionsLastWeek) {
//                amount+=transaction.getCalculatedCommission();
//            }
//            hashMap.put(user,amount);
//        }
//
//    }


    public HashMap<User, Double> topUsers(){
    ArrayList<User> users = getAll();
    HashMap<User, Double> topUser = new HashMap<>();
    for (User user : users) {
        List<Transaction> allTransactionsLastMonth = transactionService.getAllTransactionsLastWeek(LocalDate.now(), user.getId());
        Double amount = 0D;
        for (Transaction transaction : allTransactionsLastMonth) {
            amount += transaction.getCalculatedCommission();
        }
        topUser.put(user, amount);
    }
    LinkedHashMap<User, Double> sortedTopUsers = topUser.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .limit(getAllUser().size()-2)
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (oldValue, newValue) -> oldValue,
                    LinkedHashMap::new
            ));
    for (Map.Entry<User, Double> entry : sortedTopUsers.entrySet()) {
        User user = entry.getKey();
        Double amount = entry.getValue();
        System.out.println("User: " + user.getName() + ", Amount: " + amount);
    }
    return sortedTopUsers;
}

//    public  List<String> topFiveUsers(HashMap<String, Integer> userScores) {
//        Collection<Integer> scores = userScores.values();
//        List<Integer> sortedScores = new ArrayList<>(scores);
//        Collections.sort(sortedScores, Collections.reverseOrder());
//        List<String> topUsers = new ArrayList<>();
//        int count = 0;
//        for (Integer score : sortedScores) {
//            if (count >= 5) break;
//            for (String user : userScores.keySet()) {
//                if (userScores.get(user).equals(score)) {
//                    topUsers.add(user);
//                    count++;
//                    break;
//                }
//            }
//        }
//        return topUsers;
//    }

public List<User> getAllUser(){
        ArrayList<User> users = new ArrayList<>(getAll());
        return users.stream().filter(user -> user.getRole().equals(Role.USER)).collect(Collectors.toList());
}
    public User getUserByUsername(String username) {
        for (User user : getAllUser()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }



}

