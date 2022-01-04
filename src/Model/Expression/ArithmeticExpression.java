package Model.Expression;

import Exceptions.DivisionByZero;
import Exceptions.TypeError;
import Model.Containers.IHeap;
import Model.Containers.IMap;
import Model.Containers.MyMap;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.IValue;
import Model.Value.IntValue;

import java.lang.reflect.Type;

public class ArithmeticExpression implements IExpression {
    private final IExpression exp1;
    private final IExpression exp2;
    private final ArithmeticOperation operation;

    public ArithmeticExpression(IExpression exp1, IExpression exp2, ArithmeticOperation operation){
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.operation = operation;
    }

    @Override
    public IValue evaluate(IMap<String, IValue> symbolTable, IHeap<IValue> heap) throws Exception {
        IValue val1, val2;
        val1 = exp1.evaluate(symbolTable, heap);
        if (val1.getType().equals(new IntType())) {
            val2 = exp2.evaluate(symbolTable, heap);
            if (val2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue)val1, i2 = (IntValue)val2;
                int n1 = i1.getValue(), n2 = i2.getValue();
                if (operation == ArithmeticOperation.PLUS) return new IntValue(n1 + n2);
                else if (operation == ArithmeticOperation.MINUS) return new IntValue(n1 - n2);
                else if (operation == ArithmeticOperation.MULTIPLY) return new IntValue(n1 * n2);
                else if (operation == ArithmeticOperation.DIVIDE) {
                    if (n2 == 0) throw new DivisionByZero("Division by 0!");
                    else return new IntValue(n1 / n2);
                }
            } else throw new TypeError("The entered value is not an integer!");
        } else throw new TypeError("The entered value is not an integer!");
        return new IntValue(0);
    }

    @Override
    public IType typeCheck(IMap<String, IType> typeEnv) throws Exception {
        IType type1, type2;
        type1 = exp1.typeCheck(typeEnv);
        type2 = exp2.typeCheck(typeEnv);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new IntType();
            } else throw new Exception("Second operand is not an integer!");
        } else throw new Exception("First operand is not an integer!");
    }

    @Override
    public String toString(){
        String expression = "(" + exp1.toString();
        if (operation == ArithmeticOperation.PLUS) expression += "+";
        else if (operation == ArithmeticOperation.MINUS) expression += "-";
        else if (operation == ArithmeticOperation.MULTIPLY) expression += "*";
        else if (operation == ArithmeticOperation.DIVIDE) expression += "/";
        expression += exp2.toString() + ")";
        return expression;
    }
}