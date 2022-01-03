package Model.Type;

import Model.Value.IValue;
import Model.Value.RefValue;

public class RefType implements IType {
    IType inner;

    public RefType() {
        inner = null;
    }

    public RefType(IType givenInner) {
        inner = givenInner;
    }

    public IType getInner() {
        return inner;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof RefType) {
            return inner.equals(((RefType) o).getInner());
        }
        return false;
    }

    @Override
    public IValue defaultValue() {
        return new RefValue(0, inner);
    }

    @Override
    public String toString() {
        return "Ref(" + inner.toString() + ")";
    }
}
