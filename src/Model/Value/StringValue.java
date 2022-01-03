package Model.Value;

import Model.Type.IType;
import Model.Type.StringType;

public class StringValue implements IValue {
    String val;

    public StringValue(String v) {
        val = v;
    }

    public String getValue() {
        return val;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    public String toString() {
        return val;
    }

    @Override
    public boolean equals(Object o){
        if (o == null || o.getClass() != this.getClass()){
            return false;
        }
        StringValue objectValue = (StringValue) o;
        return this.val.equals(objectValue.getValue());
    }
}
