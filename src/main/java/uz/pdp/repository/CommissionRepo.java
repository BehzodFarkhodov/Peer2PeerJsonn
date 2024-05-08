package uz.pdp.repository;

import uz.pdp.enumerator.Category;
import uz.pdp.model.Card;
import uz.pdp.model.Commission;

import javax.swing.*;
import java.util.ArrayList;

public class CommissionRepo extends BaseRepo<Commission> {
    public static final CommissionRepo commissionRepo = new CommissionRepo();

    public static CommissionRepo getInstance() {
        return commissionRepo;
    }

    public CommissionRepo() {
        super.path = "src/main/resources/commission.json";
        super.type = Commission.class;
    }
    public Double getByCard(Category category,Category category1){
        ArrayList<Commission> cards = getAll();
        return cards.stream().filter(commission -> {
            return commission.getFromCard().equals(category) && commission.getToCard().equals(category1) || commission.getToCard()
                    .equals(category) && commission.getFromCard().equals(category1);
        }).findFirst().map(Commission::getPercentage).orElse(null);
    }
}
