package Model.Containers;

import java.util.*;

public class MyStack<T> implements IStack<T> {
    private Stack<T> stack;

    public MyStack() {
        stack = new Stack<T>();
    }

    @Override
    public void push(T element) {
        stack.push(element);
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public T top() {
        return stack.peek();
    }

    @Override
    public boolean isEmpty() {
        return stack.empty();
    }

    @Override
    public String toString() {
        List<T> toList = new ArrayList<>(stack);
        Collections.reverse(toList);
        return toList.toString();
    }

    @Override
    public List<T> getAll() {
        return stack;
    }
}
