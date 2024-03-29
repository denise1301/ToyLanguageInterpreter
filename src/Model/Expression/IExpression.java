package Model.Expression;

import Model.Containers.IHeap;
import Model.Containers.IMap;
import Model.Value.IValue;

public interface IExpression {
    IValue evaluate(IMap<String, IValue> symbolTable, IHeap<IValue> heap) throws Exception;

    String toString();
}
