package mvc;
import java.util.*;

public interface Repository <T> {
    public boolean save(T t);
    public T findById(Integer id);
    public List<T> findAll();
    public void delete(T t);
    public T update(T t, Integer id);
}
