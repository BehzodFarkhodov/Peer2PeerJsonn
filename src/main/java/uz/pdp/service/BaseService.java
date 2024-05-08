package uz.pdp.service;

import uz.pdp.model.BaseModel;
import uz.pdp.repository.BaseRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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


    public List<T> getActive() {
        return repository.getActive();
    }

    public void delete(UUID id) {
        repository.delete(id);
    }

    public abstract boolean check(T t);

    public ArrayList<T> getAll() {
        return repository.getAll();
    }
}
