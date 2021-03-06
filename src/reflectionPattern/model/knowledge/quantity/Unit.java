package reflectionPattern.model.knowledge.quantity;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.Map;

/**
 * Created by nagash on 02/09/16.
 */


@Entity
@Table(name = "UNIT")
public class Unit {

    protected Unit ()  {
        name = symbol = null;
    }
    public    Unit (@NotNull String name, @NotNull String symbol) {
        this.name=name;
        this.symbol=symbol;
    }
    public    Unit (@NotNull Unit copy) {
        this.name = copy.name;
        this.symbol = copy.symbol;
    }

    public Unit deepCopy() {
        return new Unit(this);
    }

    @Column(name = "id")
    @Id @GeneratedValue
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SYMBOL")
    private String symbol;




    public String getName(){
        return name;
    }
    public String getSymbol(){
        return symbol;
    }





 /* *******************************************************************************************************************
    *******************************************************************************************************************
    *******************************************************************************************************************/




    public void addConversionRatio (Unit toUnit, Number ratio) throws ImpossibleConversionException {
        UnitConverter.converter().newConversionRatio(this, toUnit, ratio );
    }
    public Map<Unit, Number> getConversions() {
        return UnitConverter.converter().getConversions(this);
    }


    @Override
    public String toString() {
        return "[" + this.symbol + "]" + " (" + this.name + ")";
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        result = 31 * result + symbol.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof Unit)) return false;
        if( obj instanceof Unit)
        {
            Unit unit = (Unit) obj;
            if(unit.getName().equals(this.getName()) && unit.getSymbol().equals(this.getSymbol())  )
                return true;
            else return false;
        }
        else return false;
    }

}
