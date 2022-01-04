package Model.Statement;

import Model.Containers.IMap;
import Model.Containers.IStack;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Value.BoolValue;
import Model.Value.IValue;

public class WhileStatement implements IStatement {
    IExpression expression;
    IStatement whileStatement;

    public WhileStatement(IExpression exp, IStatement whileStatement) {
        this.expression = exp;
        this.whileStatement = whileStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IStack<IStatement> stack = state.getExecutionStack();
        IMap<String, IValue> symbolTable = state.getSymbolTable();
        IValue value = this.expression.evaluate(symbolTable, state.getHeap());
        if (value instanceof BoolValue cond) {
            if (cond.getValue()) {
                stack.push(this);
                stack.push(whileStatement);
            }
        } else throw new Exception("Conditional expression is not a boolean!");
        return null;
    }

    @Override
    public IMap<String, IType> typeCheck(IMap<String, IType> typeEnv) throws Exception {
        IType typeExp = this.expression.typeCheck(typeEnv);
        if (typeExp.equals(new BoolType())) {
            this.whileStatement.typeCheck(typeEnv);
            return typeEnv;
        } else throw new Exception("The condition of While doesn't have the type bool!");
    }

    @Override
    public String toString() {
        return "while(" + expression + ") " + whileStatement + "\n";
    }
}
