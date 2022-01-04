package Model.Statement;

import Model.Containers.IMap;
import Model.Containers.IStack;
import Model.ProgramState;
import Model.Type.IType;

public class CompStatement implements IStatement {
    private IStatement first;
    private IStatement second;

    public CompStatement(IStatement first, IStatement second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        IStack<IStatement> stack = state.getExecutionStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public IMap<String, IType> typeCheck(IMap<String, IType> typeEnv) throws Exception {
        return this.second.typeCheck(this.first.typeCheck(typeEnv));
    }

    @Override
    public String toString() {
        return "(" + first.toString() + "; " + second.toString() + ")";
    }
}