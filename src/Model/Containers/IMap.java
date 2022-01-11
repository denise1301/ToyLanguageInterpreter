package Model.Containers;

import java.util.Collection;
import java.util.Set;

public interface IMap<K, V> {
    void add(K key, V Value) throws Exception;
    void remove(K key) throws Exception;
    V get(K key);
    Collection<V> values();
    void update(K key, V value);
    boolean hasKey(K key);
    boolean isEmpty();
    String toString();
    int size();
    Set<K> keySet();
}