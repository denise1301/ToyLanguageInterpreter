package Model.Containers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyMap<K, V> implements IMap<K, V> {
    private final HashMap<K, V> map;

    public MyMap(){
        map = new HashMap<>();
    }

    @Override
    public void add(K key, V value) throws Exception {
        if (map.containsKey(key)) {
            throw new Exception("Key already exists!");
        }
        map.put(key, value);
    }

    @Override
    public void remove(K key) throws Exception {
        if (!map.containsKey(key)) {
            throw new Exception("Key doesn't exist!");
        }
        map.remove(key);
    }

    @Override
    public V get(K key){
        return map.get(key);
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Map<K, V> getAll() {
        return map;
    }

    @Override
    public void update(K key, V value) {
        map.put(key, value);
    }

    @Override
    public boolean hasKey(K key) {
        return map.containsKey(key);
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        map.forEach((k, v) -> result.append(k.toString()).append(" = ").append(v.toString()).append("\n"));
        return result.toString();
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

}