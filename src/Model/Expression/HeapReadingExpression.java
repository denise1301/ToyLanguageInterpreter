package Model.Expression;

import Model.Containers.IHeap;
import Model.Containers.IMap;
import Model.Value.IValue;
import Model.Value.RefValue;

public class HeapReadingExpression implements IExpression {
    private IExpression exp;

    public HeapReadingExpression(IExpression exp) {
        this.exp = exp;
    }

    @Override
    public IValue evaluate(IMap<String, IValue> symbolTable, IHeap<IValue> heap) throws Exception {
        IValue value = exp.evaluate(symbolTable, heap);
        if (value instanceof RefValue) {
            int key = ((RefValue) value).getAddress();
            if (heap.hasKey(key)) {
                return heap.get(key);
            }
            throw new Exception("The Heap does not contain that key!");
        }
        throw new Exception("The evaluated expression is not of type RefValue!");
    }

    @Override
    public String toString() {
        return "rH(" + this.exp.toString() + ")";
    }
}
