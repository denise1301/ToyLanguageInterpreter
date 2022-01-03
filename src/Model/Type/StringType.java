package Model.Type;

import Model.Value.IValue;
import Model.Value.StringValue;

public class StringType implements IType {
    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()){
            return false;
        }
        return true;
    }

    public String toString() {
        return "String";
    }

    @Override
    public IValue defaultValue() {
        return new StringValue("");
    }
}
