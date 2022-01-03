package Model.Statement;

import Model.Containers.IList;
import Model.Containers.IMap;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Value.IValue;

public class PrintStatement implements IStatement {
    IExpression expression;

    public PrintStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IList<IValue> out = state.getOut();
        IMap<String, IValue> table = state.getSymbolTable();
        out.add(expression.evaluate(table, state.getHeap()));
        state.setOut(out);
        return null;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }
}