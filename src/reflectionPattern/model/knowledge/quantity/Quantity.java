package reflectionPattern.model.knowledge.quantity;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;

/**
 * Created by nagash on 02/09/16.
 */

@Embeddable
public class Quantity {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private Unit    unit;

    @Column(name="quantity_value")
    private Number  value;

    protected Quantity() {}
    public Quantity(@NotNull Number value, @NotNull Unit unit){
        this.unit=unit;
        this.value=value;
    }

    public Number getValue(){
        return value;
    }
    public Unit getUnit(){
        return unit;
    }

    public Quantity convert(@NotNull Unit toUnit) throws ImpossibleConversionException {
        return UnitConverter.converter().convert(this, toUnit);
    }


    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof  Quantity)) return false;
        Quantity q = (Quantity) obj;
        if(this.getUnit().equals(q.getUnit()) && this.getValue().equals(q.getValue()))
            return true;
        else return false;
    }

    @Override
    public int hashCode() {
        int result = unit.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }
}
