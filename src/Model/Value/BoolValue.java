package Model.Value;

import Model.Type.BoolType;
import Model.Type.IType;

public class BoolValue implements IValue {
    boolean val;

    public BoolValue(boolean v) {
        val = v;
    }

    @Override
    public IType getType() {
        return new BoolType();
    }

    public boolean getValue() {
        return val;
    }

    public String toString() {
        if (val) {
            return "True";
        }
        return "False";
    }

    public boolean getOpposite() {
        return !val;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()){
            return false;
        }
        BoolValue objectValue = (BoolValue) o;
        return this.val == objectValue.getValue();
    }
}
