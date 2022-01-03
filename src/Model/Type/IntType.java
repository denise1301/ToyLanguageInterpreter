package Model.Type;

import Model.Value.IValue;
import Model.Value.IntValue;

public class IntType implements IType {
    public boolean equals(Object another) {
        return another instanceof IntType;
    }

    public String toString() {
        return "Int";
    }

    @Override
    public IValue defaultValue() {
        return new IntValue(0);
    }
}
