package uz.pdp.service;

import uz.pdp.model.BaseModel;
import uz.pdp.repository.BaseRepo;

import java.util.ArrayList;

public abstract class BaseService<T extends BaseModel, R extends BaseRepo<T>> {

    R repository;

    public BaseService(R repository) {
        this.repository = repository;
    }

    public boolean add(T t) {
        if (check(t)) {
            return false;
        }
        repository.add(t);
        return true;
    }

    public abstract boolean check(T t);

    public ArrayList<T> getAll() {
        return repository.getAll();
    }
}
