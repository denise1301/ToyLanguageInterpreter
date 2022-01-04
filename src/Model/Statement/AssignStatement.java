package Model.Statement;

import Exceptions.AssignError;
import Exceptions.DefError;
import Model.Containers.IMap;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Type.IType;
import Model.Value.IValue;

public class AssignStatement implements IStatement {
    private final String name;
    private final IExpression expression;

    public AssignStatement(String name, IExpression expression) {
        this.name = name;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IMap<String, IValue> table = state.getSymbolTable();

        if (table.hasKey(name)) {
            IValue value = expression.evaluate(table, state.getHeap());
            IType type = (table.get(name)).getType();
            if (value.getType().equals(type)) {
                table.update(name, value);
            } else throw new AssignError("Type and value mismatch for the variable " + name  + "!");
        } else throw new DefError("The variable " + name + " has not been declared yet!");
        return null;
    }

    @Override
    public IMap<String, IType> typeCheck(IMap<String, IType> typeEnv) throws Exception {
        IType typeVar = typeEnv.get(this.name);
        IType typeExp = this.expression.typeCheck(typeEnv);

        if (typeVar.equals(typeExp))
            return typeEnv;
        else
            throw new Exception("Assignment right hand side and left hand side have different types!");
    }

    @Override
    public String toString() {
        return name + " = " + expression.toString();
    }
}