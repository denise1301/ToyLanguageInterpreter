package Model.Statement;

import Exceptions.TypeError;
import Model.Containers.IMap;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Type.IType;
import Model.Type.StringType;
import Model.Value.IValue;
import Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStatement implements IStatement {
    private final IExpression expression;

    public OpenRFileStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IMap<StringValue, BufferedReader> fileTable = state.getFileTable();
        IMap<String, IValue> symbolTable = state.getSymbolTable();
        IValue evaluatedExpression = this.expression.evaluate(symbolTable, state.getHeap());

        if (evaluatedExpression.getType().equals(new StringType())){
            StringValue stringValue = (StringValue) evaluatedExpression;
            if (!fileTable.hasKey(stringValue)) {
                try {
                    BufferedReader bufferR = new BufferedReader(new FileReader(stringValue.getValue()));
                    fileTable.add(stringValue, bufferR);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                return null;
            }
            throw new Exception("The given file already exists!");
        }
        throw new TypeError("Expression is not of type StringType!");
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
        return "openRFile(" + this.expression.toString() + ")";
    }
}
