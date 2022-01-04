package Model.Statement;

import Model.Containers.IHeap;
import Model.Containers.IMap;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Type.IType;
import Model.Type.RefType;
import Model.Value.IValue;
import Model.Value.RefValue;

public class HeapAllocationStatement implements IStatement {
    private final String name;
    private final IExpression expression;

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
    public IMap<String, IType> typeCheck(IMap<String, IType> typeEnv) throws Exception {
        IType typeVar = typeEnv.get(this.name);
        IType typeExp = this.expression.typeCheck(typeEnv);
        if (typeVar.equals(new RefType(typeExp))) {
            return typeEnv;
        } else throw new Exception("In the Heap Allocation the right hand side and left hand side have different types!");
    }

    @Override
    public String toString() {return "(new " + name + " " + this.expression.toString() + ")";}
}
