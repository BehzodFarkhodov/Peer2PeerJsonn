package uz.pdp.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import uz.pdp.model.BaseModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


public class BaseRepo<T extends BaseModel> {
    protected String path;

    protected Class<T> type;
    protected static ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);



    public void add(T t) {
        ArrayList<T> data = read();
        data.add(t);
        write(data);
    }




    public List<T> getActive() {
       return  getAll().stream()
               .filter(T:: isActive)
               .collect(Collectors.toList());
    }

    public void write(ArrayList<T> data) {
        try {
            objectMapper.writeValue(new File(path), data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Optional<T> findById(UUID id){
        ArrayList<T> data = read();
       return data.stream().filter(t -> t.getId().equals(id)).findFirst();
    }

    public ArrayList<T> read() {
        try {
            return objectMapper.readValue(new File(path), TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, type));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public <T extends BaseModel> void updateDateFiles(List<T> data) {
        try {
            objectMapper.writeValue(new File(path), data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public ArrayList<T> getAll() {
        return read();
    }


    public void delete(UUID id) {
        ArrayList<T> data = read();
        for (T item : data) {
            if (item.getId().equals(id)) {
                item.setActive(false);
                write(data);
                return;
            }
        }
    }

}
