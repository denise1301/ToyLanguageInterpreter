package Model.Statement;

import Model.Containers.IMap;
import Model.Containers.IStack;
import Model.Containers.MyStack;
import Model.ProgramState;
import Model.Type.IType;

public class ForkStatement implements IStatement {
    private final IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IStack<IStatement> newExecutionStack = new MyStack<IStatement>();
        return new ProgramState(
                newExecutionStack,
                state.getSymbolTable(),
                state.getOut(),
                state.getFileTable(),
                state.getHeap(),
                statement);
    }

    @Override
    public IMap<String, IType> typeCheck(IMap<String, IType> typeEnv) throws Exception {
        this.statement.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "fork(" + this.statement.toString() + ")";
    }
}
