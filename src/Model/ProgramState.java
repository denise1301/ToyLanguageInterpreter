package Model;

import Exceptions.ExeStackError;
import Model.Containers.*;
import Model.Statement.IStatement;
import Model.Value.IValue;
import Model.Value.StringValue;

import java.io.BufferedReader;

public class ProgramState {
    private static int threadCount = 1;
    private final int threadId;
    private IStatement originalProgram;
    private final IStack<IStatement> executionStack;
    private final IMap<String, IValue> symbolTable;
    private IList<IValue> out;
    private final IMap<StringValue, BufferedReader> fileTable;
    private final IHeap<IValue> heap;

    private synchronized static int getThreadId() {
        threadCount++;
        return threadCount - 1;
    }

    public ProgramState(
            IStack<IStatement> executionStack,
            IMap<String, IValue> symbolTable,
            IList<IValue> out,
            IMap<StringValue, BufferedReader> fileTable,
            IHeap<IValue> heap,
            IStatement statement) {
        threadId = getThreadId();
        this.originalProgram = statement;
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.executionStack.push(statement);
    }

    public ProgramState(IStatement statement) {
        threadId = getThreadId();
        executionStack = new MyStack<>();
        symbolTable = new MyMap<>();
        out = new MyList<>();
        fileTable = new MyMap<>();
        heap = new MyHeap<>();
        executionStack.push(statement);
        originalProgram = statement;
    }

    public ProgramState oneStep() throws Exception {
        if (!executionStack.isEmpty()) {
            IStatement currentStatement = executionStack.pop();
            return currentStatement.execute(this);
        } else throw new ExeStackError("Execution stack is empty!");
    }

    public boolean isNotCompleted() {
        return !this.executionStack.isEmpty();
    }

    public IStack<IStatement> getExecutionStack() {
        return executionStack;
    }

    public IMap<String, IValue> getSymbolTable() {
        return symbolTable;
    }

    public IList<IValue> getOut() {
        return out;
    }

    public void setOut(IList<IValue> output) {
        out = output;
    }

    public IMap<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public IHeap<IValue> getHeap() {
        return heap;
    }

    public String toString() {
        String result = "-----------------------------------" + "\n";
        result = result + "Thread ID: " + threadId + "\n";
        result = result + "Execution stack: " + executionStack.toString() + "\n";
        result = result + "Symbol table: " + symbolTable.toString() + "\n";
        result = result + "Output: " + out.toString() + "\n";
        result = result + "File table: " + fileTable.toString() + "\n";
        result = result + "Heap: " + heap.toString() + "\n";
        return result;
    }

    public int getIdThread() {
        return threadId;
    }
}
