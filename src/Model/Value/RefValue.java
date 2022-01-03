package Model.Value;

import Model.Type.IType;
import Model.Type.RefType;

public class RefValue implements IValue{
    private final int address;
    private final IType locationType;

    public RefValue(int given_address, IType given_location_type) {
        this.address = given_address;
        this.locationType = given_location_type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()){
            return false;
        }
        RefValue oValue = (RefValue) o;
        return this.address == oValue.getAddress();
    }

    public int getAddress() {
        return this.address;
    }

    public IType getLocationType() {
        return this.locationType;
    }

    @Override
    public IType getType() {
        return new RefType(this.locationType);
    }

    @Override
    public String toString() {
        return '(' + Integer.toString(this.address) + ", " + this.locationType.toString() + ')';
    }
}
