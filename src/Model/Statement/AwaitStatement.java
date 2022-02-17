package Model.Statement;

import Model.Containers.ILatchTable;
import Model.Containers.IMap;
import Model.ProgramState;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.IValue;
import Model.Value.IntValue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitStatement implements IStatement {
    private final String name;
    private static final Lock lock = new ReentrantLock();

    public AwaitStatement(String name) {
        this.name = name;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        lock.lock();
        IMap<String, IValue> symbolTable = state.getSymbolTable();
        ILatchTable latchTable = state.getLatchTable();
        if (symbolTable.hasKey(name)) {
            IntValue index = (IntValue) symbolTable.get(name);
            int foundIndex = index.getValue();
            if (latchTable.containsKey(foundIndex)) {
                if (latchTable.get(foundIndex) != 0) {
                    state.getExecutionStack().push(this);
                }
            } else throw new Exception("Index not found in the Latch Table!");
        } else throw new Exception(name + " is not defined!");
        lock.unlock();
        return null;
    }

    @Override
    public IMap<String, IType> typeCheck(IMap<String, IType> typeEnv) throws Exception {
        if (typeEnv.get(name).equals(new IntType())) {
            return typeEnv;
        } else throw new Exception(name + " is not of type int!");
    }

    @Override
    public String toString() {
        return String.format("await(%s)", name);
    }
}
