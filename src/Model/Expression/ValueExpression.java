package Model.Expression;

import Model.Containers.IHeap;
import Model.Containers.IMap;
import Model.Value.IValue;

public class ValueExpression implements IExpression {
    IValue value;

    public ValueExpression(IValue val) {
        this.value = val;
    }

    @Override
    public IValue evaluate(IMap<String, IValue> symbolTable, IHeap<IValue> heap) {
        return value;
    }

    public String toString() {
        return value.toString();
    }
}
