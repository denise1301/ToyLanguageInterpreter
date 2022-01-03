package Model.Expression;

import Exceptions.DefError;
import Model.Containers.IHeap;
import Model.Containers.IMap;
import Model.Value.IValue;

public class VarExpression implements IExpression {
    String name;

    public VarExpression(String name) {
        this.name = name;
    }

    @Override
    public IValue evaluate(IMap<String, IValue> symbolTable, IHeap<IValue> heap) throws Exception {
        if(!symbolTable.hasKey(name))
            throw new DefError("Undefined variable with name " + name);
        else return symbolTable.get(name);
    }

    public String toString() {
        return name;
    }
}
