package uz.pdp.repository;

import uz.pdp.model.Commission;

public class CommissionRepo extends BaseRepo<Commission> {
    public static final CommissionRepo commissionRepo = new CommissionRepo();

    public static CommissionRepo getInstance() {
        return commissionRepo;
    }

    public CommissionRepo() {
        super.path = "src/main/resources/commission.json";
        super.type = Commission.class;
    }


}
