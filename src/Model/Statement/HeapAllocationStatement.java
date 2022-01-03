package Model.Statement;

import Model.Containers.IHeap;
import Model.Containers.IMap;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Type.RefType;
import Model.Value.IValue;
import Model.Value.RefValue;

public class HeapAllocationStatement implements IStatement {
    private String name;
    private IExpression expression;

    public HeapAllocationStatement(String name, IExpression exp) {
        this.name = name;
        this.expression = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IMap<String, IValue> symbolTable = state.getSymbolTable();
        IHeap<IValue> heap = state.getHeap();

        if (symbolTable.hasKey(name)) {
            IValue value = symbolTable.get(name);
            if (value.getType() instanceof RefType) {
                IValue evaluatedExpression = this.expression.evaluate(symbolTable, heap);
                RefValue refValue = (RefValue) value;
                if (evaluatedExpression.getType().equals(refValue.getLocationType())) {
                    int key = heap.add(evaluatedExpression);
                    RefValue newRefValue = new RefValue(key, refValue.getLocationType());
                    symbolTable.update(this.name, newRefValue);
                    return null;
                } else throw new Exception("The types do not coincide!");
            } else throw new Exception("The variable associated to the provided VarName is not of type RefType");
        } else throw new Exception("The provided VarName couldn't be found in Symbol Table");
    }

    @Override
    public String toString() {return "(new " + name + " " + this.expression.toString() + ")";}
}
