package Model.Statement;

import Model.Containers.IMap;
import Model.Expression.IExpression;
import Model.Expression.ValueExpression;
import Model.ProgramState;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Value.BoolValue;
import Model.Value.IValue;

public class RepeatUntilStatement implements IStatement {
    private final IStatement statement;
    private final IExpression expression;

    public RepeatUntilStatement(IStatement statement, IExpression expression) {
        this.statement = statement;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IValue expressionValue = expression.evaluate(state.getSymbolTable(), state.getHeap());
        if (expressionValue.getType().equals(new BoolType())) {
            BoolValue value = (BoolValue) expressionValue;
            IStatement newWhileStatement = new WhileStatement(new ValueExpression(new BoolValue(value.getOpposite())), statement);
            IStatement newStatement = new CompStatement(statement, newWhileStatement);
            state.getExecutionStack().push(newStatement);
        }
        return null;
    }

    @Override
    public IMap<String, IType> typeCheck(IMap<String, IType> typeEnv) throws Exception {
        IType expressionType;
        expressionType = expression.typeCheck(typeEnv);
        if (expressionType.equals(new BoolType())) {
            statement.typeCheck(typeEnv);
            return typeEnv;
        } else {
            throw new Exception("Expression type is not boolean!");
        }
    }

    @Override
    public String toString() {
        return String.format("\nrepeat {\n %s \n} until {%s}\n", statement.toString(), expression.toString());
    }
}
