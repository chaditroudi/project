package gourmand.services;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {
    void create(T item) throws SQLException;

    T getById(int id) throws SQLException;

    List<T> getAll() throws SQLException;
    void update(int id, T updatedItem) throws SQLException;


    void delete(int id) throws SQLException;
}
