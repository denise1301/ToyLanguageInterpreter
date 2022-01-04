package Model.Statement;

import Model.Containers.IMap;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Type.IType;
import Model.Type.StringType;
import Model.Value.IValue;
import Model.Value.StringValue;
import java.io.BufferedReader;

public class CloseRFileStatement implements IStatement {
    private final IExpression expression;

    public CloseRFileStatement(IExpression expression){
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IMap<StringValue, BufferedReader> fileTable = state.getFileTable();
        IMap<String, IValue> symbolTable = state.getSymbolTable();
        IValue expressionValue = expression.evaluate(symbolTable, state.getHeap());

        if (expressionValue.getType().equals(new StringType())) {
            StringValue stringValue = (StringValue) expressionValue;
            if (fileTable.hasKey(stringValue)) {
                BufferedReader bufferedReader = fileTable.get(stringValue);
                bufferedReader.close();
                fileTable.remove(stringValue);
                return null;
            } else throw new Exception("There is no entry associated to this filename");
        } else throw new Exception("The type must be a string");
    }

    @Override
    public IMap<String, IType> typeCheck(IMap<String, IType> typeEnv) throws Exception {
        IType typeExp = this.expression.typeCheck(typeEnv);
        if (typeExp.equals(new StringType())) {
            return typeEnv;
        } else throw new Exception("The expression doesn't have a String type!");
    }

    @Override
    public String toString() {
        return "closeFile(" + this.expression.toString() + ")";
    }
}
