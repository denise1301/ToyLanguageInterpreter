package Model.Statement;

import Model.Containers.IHeap;
import Model.Containers.ILatchTable;
import Model.Containers.IMap;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.IValue;
import Model.Value.IntValue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLatchStatement implements IStatement {
    private final String name;
    private final IExpression expression;
    private static final Lock lock = new ReentrantLock();

    public NewLatchStatement(String name, IExpression expression) {
        this.name = name;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        lock.lock();
        IMap<String, IValue> symbolTable = state.getSymbolTable();
        IHeap<IValue> heap = state.getHeap();
        ILatchTable latchTable = state.getLatchTable();
        IntValue nr = (IntValue) (expression.evaluate(symbolTable, heap));
        int number = nr.getValue();
        int freeAddress = latchTable.getFreeAddress();
        latchTable.put(freeAddress, number);
        if (symbolTable.hasKey(name)) {
            symbolTable.update(name, new IntValue(freeAddress));
        } else throw new Exception(name + " is not defined in the Symbol Table!");
        lock.unlock();
        return null;
    }

    @Override
    public IMap<String, IType> typeCheck(IMap<String, IType> typeEnv) throws Exception {
        if (typeEnv.get(name).equals(new IntType())) {
            if (expression.typeCheck(typeEnv).equals(new IntType())) {
                return typeEnv;
            } else throw new Exception("Expression is not of type int!");
        } else throw new Exception(name + " is not of type int!");
    }

    @Override
    public String toString() {
        return String.format("newLatch(%s, %s)", name, expression.toString());
    }
}
