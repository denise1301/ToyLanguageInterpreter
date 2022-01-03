package Model.Expression;

import Exceptions.TypeError;
import Model.Containers.IHeap;
import Model.Containers.IMap;
import Model.Type.BoolType;
import Model.Value.BoolValue;
import Model.Value.IValue;

public class LogicExpression implements IExpression {
    private IExpression exp1;
    private IExpression exp2;
    private LogicOperation operation;

    public LogicExpression(IExpression exp1, IExpression exp2, LogicOperation operation) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.operation = operation;
    }

    @Override
    public IValue evaluate(IMap<String, IValue> symbolTable, IHeap<IValue> heap) throws Exception {
        IValue val1, val2;
        val1 = exp1.evaluate(symbolTable, heap);
        if (val1.getType().equals(new BoolType())) {
            val2 = exp2.evaluate(symbolTable, heap);
            if (val2.getType().equals(new BoolType())) {
                BoolValue i1 = (BoolValue)val1, i2 = (BoolValue)val2;
                boolean n1 = i1.getValue(), n2 = i2.getValue();
                if (operation == LogicOperation.AND) return new BoolValue(n1 && n2);
                else if (operation == LogicOperation.OR) return new BoolValue(n1 || n2);
            } else throw new TypeError("The entered value is not a boolean!");
        } else throw new TypeError("The entered value is not a boolean!");
        return new BoolValue(false);
    }

    public String toString() {
        String expression = exp1.toString();
        if (operation == LogicOperation.AND) expression += " && ";
        else if (operation == LogicOperation.OR) expression += " || ";
        expression += exp2.toString();
        return expression;
    }
}
