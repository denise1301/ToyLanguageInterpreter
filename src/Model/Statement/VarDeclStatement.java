package Model.Statement;

import Exceptions.DefError;
import Model.Containers.IMap;
import Model.ProgramState;
import Model.Type.IType;
import Model.Value.IValue;

public class VarDeclStatement implements IStatement {
    String name;
    IType type;

    public VarDeclStatement(String name, IType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IMap<String, IValue> table = state.getSymbolTable();
        if (!table.hasKey(name)) {
            table.add(name, type.defaultValue());
        } else throw new DefError("The variable " + name + " has already been declared!");
        return null;
    }

    @Override
    public IMap<String, IType> typeCheck(IMap<String, IType> typeEnv) throws Exception {
        typeEnv.add(this.name, this.type);
        return typeEnv;
    }

    @Override
    public String toString() {
        return type.toString() + " " + name;
    }
}