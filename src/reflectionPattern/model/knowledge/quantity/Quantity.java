package reflectionPattern.model.knowledge.quantity;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;

/**
 * Created by nagash on 02/09/16.
 */

@Embeddable
@Access(AccessType.PROPERTY)
public class Quantity {
    private Unit    unit;
    private Number  value;


    protected   Quantity() {}
    public      Quantity(@NotNull Number value, @NotNull Unit unit){
        this.unit=unit;
        this.value=value;
    }
    public      Quantity(Quantity copy) {
        this.unit = copy.unit.deepCopy();
        this.value = copy.value;
    }


    public Quantity deepCopy() {
        return new Quantity(this);
    }


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "unit_id")
    public Unit getUnit ()           { return unit; }
    protected  void  setUnit (Unit unit)  { this.unit = unit; }


    @Column(name="quantity_value")
    public      Number   getValue() { return value; }
    protected   void     setValue(Number value) { this.value = value; }









 /* *******************************************************************************************************************
    *******************************************************************************************************************
    *******************************************************************************************************************/


    @Override
    public String toString() {
        return this.value.toString() + this.unit.toString();
    }

    public Quantity convert(@NotNull Unit toUnit) throws ImpossibleConversionException {
        return UnitConverter.converter().convert(this, toUnit);
    }


    @Override public int hashCode() {
        int result = unit.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }

    @Override public boolean equals(Object obj) {
        if(this==obj) return true;
        if(!(obj instanceof Quantity)) return false;
        Quantity q = (Quantity) obj;
        if(this.getUnit().equals(q.getUnit()) && this.getValue().equals(q.getValue()))
            return true;
        else return false;
    }

}
