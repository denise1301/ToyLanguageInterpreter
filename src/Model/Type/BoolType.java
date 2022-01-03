package Model.Type;

import Model.Value.BoolValue;
import Model.Value.IValue;

public class BoolType implements IType {
    public boolean equals(Object another) {
        return another instanceof BoolType;
    }

    public String toString() {
        return "Bool";
    }

    @Override
    public IValue defaultValue() {
        return new BoolValue(false);
    }
}
