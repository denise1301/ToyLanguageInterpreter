package Model.Containers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MyHeap<V> implements IHeap<V> {
    private HashMap<Integer, V> heap;
    private int freeAddress;

    public MyHeap() {
        heap = new HashMap<Integer, V>();
        freeAddress = 1;
    }

    @Override
    public int add(V value) throws Exception {
        if (heap.containsKey(freeAddress)) {
            throw new Exception("Key already exists!");
        }
        heap.put(freeAddress++, value);
        return freeAddress - 1;
    }

    @Override
    public void update(int key, V value) throws Exception {
        if (!heap.containsKey(key)) {
            throw new Exception("Key doesn't exist!");
        }
        heap.put(key, value);
    }

    @Override
    public void setContent(Map<Integer, V> newContent) {
        heap = (HashMap<Integer, V>) newContent;
    }

    @Override
    public Map<Integer, V> getContent() {
        return heap;
    }

    @Override
    public V get(int key) {
        return heap.get(key);
    }

    @Override
    public boolean hasKey(int key) {
        return heap.containsKey(key);
    }

    @Override
    public Collection<V> values() {
        return heap.values();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        heap.forEach((k, v) -> result.append(k.toString()).append(" = ").append(v.toString()).append("\n"));
        return result.toString();
    }
}
