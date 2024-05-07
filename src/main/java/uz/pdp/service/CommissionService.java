package uz.pdp.service;

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
}
