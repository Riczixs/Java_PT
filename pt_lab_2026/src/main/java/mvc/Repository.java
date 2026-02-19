package mvc;
import java.util.*;
import java.util.stream.Collectors;

/*
* T - Object Type
* V - ID Type
* */
public class Repository <T,V extends Id> {
    private Map<V, T> collection = new HashMap<>();
    public Optional<T> save(T t, V v){
        if(t == null) return Optional.empty();
        if(collection.containsValue(t)) return Optional.empty();
        collection.put(v,t);
        return Optional.of(t);
    }
    public Optional<T> findById(V v){
        return Optional.ofNullable(collection.get(v));
    }
    public List<T> findAll(){
        return collection
                .values()
                .stream()
                .filter(Objects::isNull)
                .collect(Collectors.toList());
    }
//
//    public void delete(T t);
//    public T update(T t, Integer id);
}
