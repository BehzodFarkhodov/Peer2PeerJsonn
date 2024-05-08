package uz.pdp.service;

import uz.pdp.enumerator.Category;
import uz.pdp.model.Commission;
import uz.pdp.repository.CommissionRepo;

public class CommissionService extends BaseService<Commission, CommissionRepo> {

    public static final CommissionService commissionService = new CommissionService();

    public static CommissionService getInstance() {
        return commissionService;
    }

    public CommissionService() {
        super(CommissionRepo.getInstance());
    }

    @Override
    public boolean check(Commission commission) {
        return false;
    }
    public Double getByCard(Category category, Category category1){
        return repository.getByCard(category,category1);
    }
}
