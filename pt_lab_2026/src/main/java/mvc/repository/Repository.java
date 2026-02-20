package mvc.repository;
import mvc.Id.Id;

import java.util.*;
import java.util.stream.Collectors;

/*
* T - Object Type
* V - ID Type
* */
public class Repository <T,V extends Id> {
    private final Map<V, T> collection = new HashMap<>();

    public Optional<T> save(T t, V v){ //TODO must be idempotent!!!
        if(t == null) return Optional.empty();
        if(collection.containsValue(t)){
            return Optional.empty(); //TODO what to do in case of similar Objects?
        } else if(collection.containsKey(v)){
            throw new IllegalArgumentException("Object of given key already exists!");
        } else{
            collection.put(v,t);
            return Optional.of(t);
        }
    }

    public Optional<T> findById(Integer id){
        Optional<V> v = collection.keySet()
                .stream()
                .filter(k -> k.getId().equals(id))
                .findFirst();
        if(v.isPresent()){
            return Optional.of(collection.get(v.get()));
        }else{
            return Optional.empty(); //Object does not exists
        }
        // return v.map(collection::get);
    }

    public List<T> findAll(){
        return collection
                .values()
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public void delete(Integer id){
        Optional<V> v = collection.keySet()
                .stream()
                .filter(k -> k.getId().equals(id))
                .findFirst();
        if(v.isPresent()){
            collection.remove(v.get());
        }else{
            throw new IllegalArgumentException("Object of given key does not exists!");
        }
    }

    //TODO needed to be fixed
    public Optional<T> update(T t, Integer id){
        Optional<V> v = collection.keySet()
                .stream()
                .filter(k -> k.getId().equals(id))
                .findFirst();
        if(v.isPresent()){
            T t2 = collection.get(v.get());
            collection.replace(v.get(), t2, t);
            return Optional.of(t);
        }
        return Optional.empty();
    }
}
