package Model.Value;

import Model.Type.IType;
import Model.Type.IntType;

public class IntValue implements IValue {
    int val;

    public IntValue(int v) {
        val = v;
    }

    public int getValue() {
        return val;
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    public String toString() {
        return Integer.toString(val);
    }

    @Override
    public boolean equals(Object o){
        if (o == null || o.getClass() != this.getClass()){
            return false;
        }
        IntValue objectValue = (IntValue) o;
        return this.val == objectValue.getValue();
    }
}