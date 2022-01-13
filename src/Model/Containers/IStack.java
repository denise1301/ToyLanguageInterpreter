package Model.Containers;

import java.util.List;
import java.util.Stack;

public interface IStack<T> {
    void push(T element);
    T pop();
    T top();
    boolean isEmpty();
    String toString();
    List<T> getAll();
    Stack<T> cloneStack();
}
