package Model.Statement;

import Model.Containers.IHeap;
import Model.Containers.IMap;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Type.IType;
import Model.Type.RefType;
import Model.Value.IValue;
import Model.Value.RefValue;

public class HeapWritingStatement implements IStatement {
    private final String name;
    private final IExpression expression;

    public HeapWritingStatement(String name, IExpression expression) {
        this.name = name;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IMap<String, IValue> symbolTable = state.getSymbolTable();
        IHeap<IValue> heap = state.getHeap();

        if (symbolTable.hasKey(name)) {
            IValue value = symbolTable.get(name);
            if (value.getType() instanceof RefType) {
                int key = ((RefValue) value).getAddress();
                if (heap.hasKey(key)) {
                    IValue secondValue = this.expression.evaluate(symbolTable, heap);
                    if (secondValue.getType().equals(((RefValue) value).getLocationType())) {
                        heap.update(key, secondValue);
                        return null;
                    }
                    throw new Exception("Types do not coincide!");
                }
                throw new Exception("The Heap does not contain that key!");
            }
            throw new Exception("The variable associated to the provided VarName is not of type RefType");
        }
        throw new Exception("VarName not found in SymbolTable!");
    }

    @Override
    public IMap<String, IType> typeCheck(IMap<String, IType> typeEnv) throws Exception {
        IType typeVar = typeEnv.get(this.name);
        IType typeExp = this.expression.typeCheck(typeEnv);
        if (typeVar.equals(new RefType(typeExp))) {
            return typeEnv;
        } else throw new Exception("In the Heap Writing the right hand side and left hand side have different types!");
    }

    @Override
    public String toString() {
        return "wH(" + name + " " + this.expression.toString() + ")";
    }
}
