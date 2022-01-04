package Model.Expression;

import Exceptions.TypeError;
import Model.Containers.IHeap;
import Model.Containers.IMap;
import Model.Containers.MyMap;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Value.BoolValue;
import Model.Value.IValue;

public class LogicExpression implements IExpression {
    private final IExpression exp1;
    private final IExpression exp2;
    private final LogicOperation operation;

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

    @Override
    public IType typeCheck(IMap<String, IType> typeEnv) throws Exception {
        IType type1, type2;
        type1 = exp1.typeCheck(typeEnv);
        type2 = exp2.typeCheck(typeEnv);

        if (type1.equals(new BoolType())) {
            if (type2.equals(new BoolType())) {
                return new BoolType();
            } else throw new Exception("Second operand is not a boolean!");
        } else throw new Exception("First operand is not a boolean!");
    }

    public String toString() {
        String expression = exp1.toString();
        if (operation == LogicOperation.AND) expression += " && ";
        else if (operation == LogicOperation.OR) expression += " || ";
        expression += exp2.toString();
        return expression;
    }
}
