package Model.Statement;

import Exceptions.TypeError;
import Model.Containers.IMap;
import Model.Containers.IStack;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Type.BoolType;
import Model.Value.BoolValue;
import Model.Value.IValue;

public class IfStatement implements IStatement {
    private final IExpression conditionExpression;
    private final IStatement thenStatement;
    private final IStatement elseStatement;

    public IfStatement(IExpression conditionExpression, IStatement thenStatement, IStatement elseStatement) {
        this.conditionExpression = conditionExpression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IStack<IStatement> stack = state.getExecutionStack();
        IMap<String, IValue> table = state.getSymbolTable();
        IValue conditionValue = conditionExpression.evaluate(table, state.getHeap());

        if (conditionValue.getType().equals(new BoolType())) {
            BoolValue value = (BoolValue) conditionValue;
            if (value.getValue()) {
                stack.push(thenStatement);
            } else {
                stack.push(elseStatement);
            }
        } else throw new TypeError("The condition type is incorrect!");
        return null;
    }

    @Override
    public String toString() {
        return "if (" + conditionExpression.toString() + ") then " + thenStatement.toString() + " else " + elseStatement.toString();
    }
}