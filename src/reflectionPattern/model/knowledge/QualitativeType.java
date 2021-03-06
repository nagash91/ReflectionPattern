/**
 * Created by nagash on 31/08/16.
 */
package reflectionPattern.model.knowledge;


import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Access(AccessType.PROPERTY)
@DiscriminatorValue("QUALITATIVE")
public class QualitativeType extends FactType implements Cloneable
{

    Set<Phenomenon> legalPhenomenons = new HashSet<>();


    protected  QualitativeType () {}
    public     QualitativeType (@NotNull  String typeName) { super(typeName); }
    public      QualitativeType(QualitativeType copy) {
        super(copy);
        for( Phenomenon l : copy.legalPhenomenons)
            legalPhenomenons.add(l.deepCopy());
    }

    @Override public FactType deepCopy() {
        return new QualitativeType(this);
    }




    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="QualitativeType_LegalPhenomenons")
    public    Set<Phenomenon>  getLegalPhenomenons ()                        { return Collections.unmodifiableSet(legalPhenomenons); }
    protected void             setLegalPhenomenons (Set<Phenomenon> phenoms) { this.legalPhenomenons = phenoms; }






 /* *******************************************************************************************************************
    *******************************************************************************************************************
    *******************************************************************************************************************/
     @Override
     public void acceptVisitor(IFactTypeVisitor visitor) {
         visitor.visit(this);
     }



    public void addLegalPhenomenon(@NotNull Phenomenon newLegalPhenomenon) {
        legalPhenomenons.add(newLegalPhenomenon);
    }
    public boolean isPhenomenonLegal(Phenomenon testPhenomenon) {
        if(testPhenomenon == null) return false;
        if(legalPhenomenons.contains(testPhenomenon))
            return true;
        else return false;
    }



    @Override public String toString() {
        String ret = super.toString();
        int size = legalPhenomenons.size();
        if(size > 0)
        {
            ret += " - legals {";
            int i = 0;
            for (Phenomenon p : legalPhenomenons)
                ret += p.getValue() + (++i == size ? "}" : ", ");
        }
        return ret;
    }



    @Override public int hashCode() {
        int result = super.hashCode();
        //result = 31 * result + (legalPhenomenons != null ? legalPhenomenons.hashCode() : 0);
        return result;
    }

    @Override public boolean equals(Object obj) {
        if(this==obj) return true;
        if(super.equals(obj) == false) return false;
        if(! (obj instanceof QualitativeType)) return false;
        QualitativeType qlObj = (QualitativeType)obj;
        if( super.equals(qlObj) &&  qlObj.getLegalPhenomenons().size() == this.getLegalPhenomenons().size()  )
        {
            for (Phenomenon phen : qlObj.getLegalPhenomenons())
            {
                if( ! this.getLegalPhenomenons().contains(phen) ) // Set<Phenomenon>.contains(phen) usa  phen.equals() (equivalenza) non operatore == (identita')
                    return false;
            }
            return true;
        }
        else return false;
    }


}
