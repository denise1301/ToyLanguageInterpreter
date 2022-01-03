package Model.Containers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface IHeap<V> {
    int add(V value) throws Exception;
    void update(int key, V value) throws Exception;
    void setContent(Map<Integer, V> newContent);
    Map<Integer, V> getContent();
    V get(int key);
    boolean hasKey(int key);
    Collection<V> values();
}
