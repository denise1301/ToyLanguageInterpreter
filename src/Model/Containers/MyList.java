package Model.Containers;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements IList<T> {
    private ArrayList<T> list;

    public MyList() {
        list = new ArrayList<T>();
    }

    public MyList(List<T> list) {
        this.list = (ArrayList<T>) list;
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public ArrayList<T> getAll() {
        return list;
    }

    @Override
    public boolean add(T elem) {
        return list.add(elem);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
