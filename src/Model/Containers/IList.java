package Model.Containers;

import java.util.ArrayList;

public interface IList<T> {
    T get(int index);
    ArrayList<T> getAll();
    boolean add(T elem);
    void clear();
    int size();
    boolean isEmpty();
    String toString();
}
