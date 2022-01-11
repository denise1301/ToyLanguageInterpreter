package Model.Expression;

import Model.Containers.IHeap;
import Model.Containers.IMap;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Model.Value.IntValue;

public class RelationalExpression implements IExpression {
    IExpression exp1, exp2;
    String operator;

    public RelationalExpression(IExpression e1, IExpression e2, String op) {
        this.exp1 = e1;
        this.exp2 = e2;
        this.operator = op;
    }

    @Override
    public IValue evaluate(IMap<String, IValue> symbolTable, IHeap<IValue> heap) throws Exception {
        IValue value1, value2;
        value1 = this.exp1.evaluate(symbolTable, heap);
        if (value1.getType().equals(new IntType())) {
            value2 = this.exp2.evaluate(symbolTable, heap);
            if (value2.getType().equals(new IntType())) {
                IntValue int1 = (IntValue) value1;
                IntValue int2 = (IntValue) value2;
                int b1, b2;
                b1 = int1.getValue();
                b2 = int2.getValue();
                switch (operator) {
                    case "<":
                        return new BoolValue(b1 < b2);
                    case "<=":
                        return new BoolValue(b1 <= b2);
                    case "==":
                        return new BoolValue(b1 == b2);
                    case "!=":
                        return new BoolValue(b1 != b2);
                    case ">":
                        return new BoolValue(b1 > b2);
                    case ">=":
                        return new BoolValue(b1 >= b2);
                }
            } else {
                throw new Exception("Second operand is not a int!");
            }
        } else {
            throw new Exception("First operand is not a int!");
        }
        return null;
    }

    @Override
    public IType typeCheck(IMap<String, IType> typeEnv) throws Exception {
        IType type1, type2;
        type1 = exp1.typeCheck(typeEnv);
        type2 = exp2.typeCheck(typeEnv);

        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new BoolType();
            } else throw new Exception("Second operand is not an integer!");
        } else throw new Exception("First operand is not an integer!");
    }

    @Override
    public String toString() {
        return this.exp1.toString() + " " + this.operator + " " + this.exp2.toString();
    }
}
