package uz.pdp.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import uz.pdp.model.BaseModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class BaseRepo<T extends BaseModel> {
    protected String path;

    protected Class<T>type;
    protected static ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    private final TypeReference<ArrayList<T>> typeReference = new TypeReference<ArrayList<T>>() {
    };


    public void add(T t) {
        ArrayList<T> data = read();
        data.add(t);
        write(data);
    }


    public void write(ArrayList<T> data) {
        try {
            objectMapper.writeValue(new File(path), data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<T> read() {

        try {
            return objectMapper.readValue(new File(path), TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,type));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public ArrayList<T> getAll() {
        return read();
    }
}
