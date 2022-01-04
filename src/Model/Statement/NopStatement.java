package Model.Statement;

import Model.Containers.IMap;
import Model.ProgramState;
import Model.Type.IType;

public class NopStatement implements IStatement{
    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        return null;
    }

    @Override
    public IMap<String, IType> typeCheck(IMap<String, IType> typeEnv) throws Exception {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "No operation!";
    }
}
