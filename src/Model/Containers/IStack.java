package Model.Containers;

public interface IStack<T> {
    void push(T element);
    T pop();
    T top();
    boolean isEmpty();
    String toString();
}
