package Model.Statement;

import Exceptions.DefError;
import Exceptions.TypeError;
import Model.Containers.IMap;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Value.IValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import java.io.BufferedReader;

public class ReadRFileStatement implements IStatement {
    private final IExpression expression;
    private final String name;

    public ReadRFileStatement(IExpression exp, String varName) {
        this.expression = exp;
        this.name = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IMap<StringValue, BufferedReader> fileTable = state.getFileTable();
        IMap<String, IValue> symbolTable = state.getSymbolTable();

        if (symbolTable.hasKey(this.name)) {
            IValue value = symbolTable.get(this.name);
            if (value.getType().equals(new IntType())) {
                IValue cond = this.expression.evaluate(symbolTable, state.getHeap());
                if (cond.getType().equals(new StringType())) {
                    StringValue expValue = (StringValue) cond;
                    if (fileTable.hasKey(expValue)) {
                        BufferedReader bufferR = fileTable.get(expValue);
                        String line = bufferR.readLine();
                        if ((line != null)) {
                            symbolTable.update(name, new IntValue(Integer.parseInt(line)));
                        } else {
                            symbolTable.update(name, new IntValue(0));
                        }
                        return null;
                    } else throw new Exception("There is no entry associated to this filename");
                } else throw new TypeError("Given variable is not an string!");
            } else throw new TypeError("Given variable is not an int!");
        } else throw new DefError("Given variable is not defined!");
    }

    @Override
    public String toString() {
        return "readFile(" + this.expression.toString() + "," + this.name + ")";
    }
}
