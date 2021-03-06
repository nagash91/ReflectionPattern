package reflectionPattern.test.persistency;

import reflectionPattern.model.knowledge.*;
import reflectionPattern.model.knowledge.quantity.Unit;
import reflectionPattern.model.operational.*;
import reflectionPattern.persistency.PersistencyHelper;
import utility.composite.out.CompositeTree;

import javax.naming.NamingException;
import javax.persistence.EntityTransaction;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by nagash on 02/09/16.
 */
public class SimplePersistencyTest {

    Set<Unit> units = new HashSet<>();
    CompositeType   rootFactTypes;
    CompositeFact   rootPaziente1;
    CompositeFact   rootPaziente2;


    @org.junit.Test
    public void persistencyTestLoadSave() throws NamingException, QuantitativeFact.IllegalQuantitativeUnitException, CompositeFact.IllegalFactTypeException, QualitativeFact.IllegalQualitativePhenomenonException
    {
        //PersistencyHelper.silenceGlobalHibernateLogs();
        // * * * * * * * * * * * * * KNOWLEDGE MODEL * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
        rootFactTypes = new CompositeType("Analisi Medico Sportiva");


            CompositeType typeAnSangue = new CompositeType("Analisi del sangue");

                QuantitativeType typeGlicemia = new QuantitativeType("Livelli di glicemia del sangue ");
                    Unit uMgDl = new Unit("Milligrammi per decilitro", "mg/dl");
                    typeGlicemia.addLegalUnit(uMgDl);
                typeAnSangue.addChild(typeGlicemia);

                QualitativeType typeBloodType =  new QualitativeType("Gruppo sanguigno");
                    Phenomenon phBloodA  = new Phenomenon("A");         typeBloodType.addLegalPhenomenon(phBloodA);
                    Phenomenon phBloodB  = new Phenomenon("B");         typeBloodType.addLegalPhenomenon(phBloodB);
                    Phenomenon phBloodAB = new Phenomenon("AB");        typeBloodType.addLegalPhenomenon(phBloodAB);
                    Phenomenon phBlood0  = new Phenomenon("0");         typeBloodType.addLegalPhenomenon(phBlood0);
                typeAnSangue.addChild(typeBloodType);

            rootFactTypes.addChild(typeAnSangue);




            CompositeType typeAnCardio = new CompositeType("Analisi cardiaca");

                QuantitativeType typeBpm = new QuantitativeType("battiti per minuto");
                    Unit uBpm = new Unit("battiti per minuto", "bpm");  typeBpm.addLegalUnit(uBpm);
                typeAnCardio.addChild(typeBpm);

                QuantitativeType typeBloodPressure = new QuantitativeType("Pressione Sanguigna");
                    Unit uPsi = new Unit("pressione psi", "psi");       typeBloodPressure.addLegalUnit(uPsi);
                    Unit uBar = new Unit("pressione bar", "bar");       typeBloodPressure.addLegalUnit(uBar);
                typeAnCardio.addChild(typeBloodPressure);

            rootFactTypes.addChild(typeAnCardio);




            CompositeType typeAnRespiratoria = new CompositeType("Analisi respiratoria (Spiometrica) ");

                TextualType typeNotes = new TextualType("Notes");
                typeAnRespiratoria.addChild(typeNotes);

                QuantitativeType typeBlowPressure = new QuantitativeType("Pressione respiratoria (???)");
                    typeBlowPressure.addLegalUnit(uPsi);
                    typeBlowPressure.addLegalUnit(uBar);
                typeAnRespiratoria.addChild(typeBlowPressure);

            rootFactTypes.addChild(typeAnRespiratoria);

        // * * * * * * * * * * * END KNOWLEDGE MODEL * * * * * * * * * * * * * * * * * * * * * * * * * * * * *





        // * * * * * * * * * * * * * OPERATIONAL MODEL: PAZIENTE 1 * * * * * * * * * * * * * * * * * * * * *
        rootPaziente1 =  new CompositeFact(rootFactTypes);
        {
            CompositeFact factAnSangue = new CompositeFact(typeAnSangue);
            {
                QuantitativeFact factGlicemia = new QuantitativeFact(typeGlicemia, 180, uMgDl);
                factAnSangue.addChild(factGlicemia);

                QualitativeFact factBloodType = new QualitativeFact(typeBloodType, phBloodA);
                factAnSangue.addChild(factBloodType);
            }
            rootPaziente1.addChild(factAnSangue);

            CompositeFact factAnCardio = new CompositeFact(typeAnCardio);
            {
                QuantitativeFact factBloodPressure = new QuantitativeFact(typeBloodPressure, 2, uPsi);
                factAnCardio.addChild(factBloodPressure);

                QuantitativeFact factBpm = new QuantitativeFact(typeBpm, 73, uBpm);
                factAnCardio.addChild(factBpm);
            }
            rootPaziente1.addChild(factAnCardio);

            CompositeFact factAnResp = new CompositeFact(typeAnRespiratoria);
            {
                QuantitativeFact factBlowPressure = new QuantitativeFact(typeBlowPressure, 12, uPsi);
                factAnResp.addChild(factBlowPressure);

                TextualFact factNotes = new TextualFact(typeNotes, "Problemi respiratori a livello dei bronchi...");
                factAnResp.addChild(factNotes);
            }
            rootPaziente1.addChild(factAnResp);
        }
        // * * * * * * * * * * * END OPERATIONAL MODEL: PAZIENTE 1 * * * * * * * * * * * * * * * * * * * * *



        // * * * * * * * * * * * * * OPERATIONAL MODEL: PAZIENTE 2 * * * * * * * * * * * * * * * * * * * * *
        rootPaziente2 =  new CompositeFact(rootFactTypes);
        {
            CompositeFact factAnSangue = new CompositeFact(typeAnSangue);
            {
                QuantitativeFact factGlicemia = new QuantitativeFact(typeGlicemia, 130, uMgDl);
                factAnSangue.addChild(factGlicemia);

                QualitativeFact factBloodType = new QualitativeFact(typeBloodType, phBloodAB);
                factAnSangue.addChild(factBloodType);
            }
            rootPaziente2.addChild(factAnSangue);

            CompositeFact factAnCardio = new CompositeFact(typeAnCardio);
            {
                QuantitativeFact factBloodPressure = new QuantitativeFact(typeBloodPressure, 1.8, uPsi);
                factAnCardio.addChild(factBloodPressure);

                QuantitativeFact factBpm = new QuantitativeFact(typeBpm, 82, uBpm);
                factAnCardio.addChild(factBpm);
            }
            rootPaziente2.addChild(factAnCardio);

            CompositeFact factAnResp = new CompositeFact(typeAnRespiratoria);
            {
                QuantitativeFact factBlowPressure = new QuantitativeFact(typeBlowPressure, 28, uPsi);
                factAnResp.addChild(factBlowPressure);

                TextualFact factNotes = new TextualFact(typeNotes, "Nessun problema respiratorio riscontrato");
                factAnResp.addChild(factNotes);
            }
            rootPaziente2.addChild(factAnResp);
        }
        // * * * * * * * * * * * END OPERATIONAL MODEL: PAZIENTE 1 * * * * * * * * * * * * * * * * * * * * *





        // HIBERNATE SAVING ENTITIES:
        PersistencyHelper ph = new PersistencyHelper(true).connect();

        long startQueries = ph.statistics().getPrepareStatementCount();

        long idPaziente1;
        long idPaziente2;
        long idFactType;

        EntityTransaction saveTransact = ph.newTransaction();

            saveTransact.begin();
                ph.persist(rootFactTypes);
                ph.persist(rootPaziente1);
                ph.persist(rootPaziente2);
                idPaziente1 = rootPaziente1.getId();
                idPaziente2 = rootPaziente2.getId();
                idFactType=rootFactTypes.getId();
            saveTransact.commit();

        long nQueries = ph.statistics().getPrepareStatementCount() ;//- startQueries;




        CompositeType persistedRootFactTypes = ph.find(CompositeType.class, idFactType);
        CompositeFact persistedRootPaziente1 = ph.find(CompositeFact.class, idPaziente1);
        CompositeFact persistedRootPaziente2 = ph.find(CompositeFact.class, idPaziente2);

        assertTrue(persistedRootFactTypes.equals(rootFactTypes));
        assertTrue(persistedRootPaziente1.equals(rootPaziente1));
        assertTrue(persistedRootPaziente2.equals(rootPaziente2));



        ph.close();

        System.out.print("Original (saved) Fact Type:\n");
        CompositeTree.printTree(rootFactTypes);
        System.out.print("\n\nPersisted (loaded) Fact Type:\n");
        CompositeTree.printTree(persistedRootFactTypes);



        System.out.print("\n\n\n\nOriginal (saved) Fact 1:\n");
        CompositeTree.printTree(rootPaziente1);
        System.out.print("\n\nPersisted (loaded) Fact 1:\n");
        CompositeTree.printTree(persistedRootPaziente1);


        System.out.print("\n\n\n\nOriginal (saved) Fact 2:\n");
        CompositeTree.printTree(rootPaziente2);
        System.out.print("\n\nPersisted (loaded) Fact 2:\n");
        CompositeTree.printTree(persistedRootPaziente2);


        System.out.print("\n\n\nExecuted queries: "  + nQueries +"\n\n");


    }


//    @Ignore
//    @org.junit.Test
//    public void persistencyTestLoad() {
//        // HIBERNATE LOADING ENTITIES:
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");
//        EntityManager em = emf.createEntityManager();
//
//
//        EntityTransaction knowledgeTransact = em.getTransaction();
//        knowledgeTransact.begin();
//            CompositeType rootFactType = em.find(CompositeType.class, idFactType);
//            CompositeFact rootPaziente1 = em.find(CompositeFact.class, idPaziente1);
//            CompositeFact rootPaziente2 = em.find(CompositeFact.class, idPaziente2);
//        knowledgeTransact.commit();
//        em.close();
//
//        OutputManager.knowledgeExplorer(rootFactType);
//
//
//
//    }
}






/* (9/9/2016)
NB: Executed SQL (save queries, insert and update): 96


Hibernate:
    insert
    into
        FactType
        (typeName, FACT_TYPE_DISCRIM, id)
    values
        (?, 'COMPOSITE', ?)
Hibernate:
    insert
    into
        FactType
        (typeName, FACT_TYPE_DISCRIM, id)
    values
        (?, 'COMPOSITE', ?)
Hibernate:
    insert
    into
        FactType
        (typeName, FACT_TYPE_DISCRIM, id)
    values
        (?, 'QUALITATIVE', ?)
Hibernate:
    insert
    into
        Phenomenon
        (value, id)
    values
        (?, ?)
Hibernate:
    insert
    into
        Phenomenon
        (value, id)
    values
        (?, ?)
Hibernate:
    insert
    into
        Phenomenon
        (value, id)
    values
        (?, ?)
Hibernate:
    insert
    into
        Phenomenon
        (value, id)
    values
        (?, ?)
Hibernate:
    insert
    into
        FactType
        (typeName, FACT_TYPE_DISCRIM, id)
    values
        (?, 'QUANTITATIVE', ?)
Hibernate:
    insert
    into
        UNIT
        (name, symbol, id)
    values
        (?, ?, ?)
Hibernate:
    insert
    into
        FactType
        (typeName, FACT_TYPE_DISCRIM, id)
    values
        (?, 'COMPOSITE', ?)
Hibernate:
    insert
    into
        FactType
        (typeName, FACT_TYPE_DISCRIM, id)
    values
        (?, 'TEXTUAL', ?)
Hibernate:
    insert
    into
        FactType
        (typeName, FACT_TYPE_DISCRIM, id)
    values
        (?, 'QUANTITATIVE', ?)
Hibernate:
    insert
    into
        UNIT
        (name, symbol, id)
    values
        (?, ?, ?)
Hibernate:
    insert
    into
        UNIT
        (name, symbol, id)
    values
        (?, ?, ?)
Hibernate:
    insert
    into
        FactType
        (typeName, FACT_TYPE_DISCRIM, id)
    values
        (?, 'COMPOSITE', ?)
Hibernate:
    insert
    into
        FactType
        (typeName, FACT_TYPE_DISCRIM, id)
    values
        (?, 'QUANTITATIVE', ?)
Hibernate:
    insert
    into
        UNIT
        (name, symbol, id)
    values
        (?, ?, ?)
Hibernate:
    insert
    into
        FactType
        (typeName, FACT_TYPE_DISCRIM, id)
    values
        (?, 'QUANTITATIVE', ?)
Hibernate:
    insert
    into
        Fact
        (type_id, FACT_DISCRIM, id)
    values
        (?, 'COMPOSITE', ?)
Hibernate:
    insert
    into
        Fact
        (type_id, FACT_DISCRIM, id)
    values
        (?, 'COMPOSITE', ?)
Hibernate:
    insert
    into
        Fact
        (type_id, textual_value, FACT_DISCRIM, id)
    values
        (?, ?, 'TEXTUAL', ?)
Hibernate:
    insert
    into
        Fact
        (type_id, unit_id, quantity_value, FACT_DISCRIM, id)
    values
        (?, ?, ?, 'QUANTITATIVE', ?)
Hibernate:
    insert
    into
        Fact
        (type_id, FACT_DISCRIM, id)
    values
        (?, 'COMPOSITE', ?)
Hibernate:
    insert
    into
        Fact
        (type_id, phenomenon_id, FACT_DISCRIM, id)
    values
        (?, ?, 'QUALITATIVE', ?)
Hibernate:
    insert
    into
        Fact
        (type_id, unit_id, quantity_value, FACT_DISCRIM, id)
    values
        (?, ?, ?, 'QUANTITATIVE', ?)
Hibernate:
    insert
    into
        Fact
        (type_id, FACT_DISCRIM, id)
    values
        (?, 'COMPOSITE', ?)
Hibernate:
    insert
    into
        Fact
        (type_id, unit_id, quantity_value, FACT_DISCRIM, id)
    values
        (?, ?, ?, 'QUANTITATIVE', ?)
Hibernate:
    insert
    into
        Fact
        (type_id, unit_id, quantity_value, FACT_DISCRIM, id)
    values
        (?, ?, ?, 'QUANTITATIVE', ?)
Hibernate:
    insert
    into
        Fact
        (type_id, FACT_DISCRIM, id)
    values
        (?, 'COMPOSITE', ?)
Hibernate:
    insert
    into
        Fact
        (type_id, FACT_DISCRIM, id)
    values
        (?, 'COMPOSITE', ?)
Hibernate:
    insert
    into
        Fact
        (type_id, unit_id, quantity_value, FACT_DISCRIM, id)
    values
        (?, ?, ?, 'QUANTITATIVE', ?)
Hibernate:
    insert
    into
        Fact
        (type_id, textual_value, FACT_DISCRIM, id)
    values
        (?, ?, 'TEXTUAL', ?)
Hibernate:
    insert
    into
        Fact
        (type_id, FACT_DISCRIM, id)
    values
        (?, 'COMPOSITE', ?)
Hibernate:
    insert
    into
        Fact
        (type_id, unit_id, quantity_value, FACT_DISCRIM, id)
    values
        (?, ?, ?, 'QUANTITATIVE', ?)
Hibernate:
    insert
    into
        Fact
        (type_id, unit_id, quantity_value, FACT_DISCRIM, id)
    values
        (?, ?, ?, 'QUANTITATIVE', ?)
Hibernate:
    insert
    into
        Fact
        (type_id, FACT_DISCRIM, id)
    values
        (?, 'COMPOSITE', ?)
Hibernate:
    insert
    into
        Fact
        (type_id, phenomenon_id, FACT_DISCRIM, id)
    values
        (?, ?, 'QUALITATIVE', ?)
Hibernate:
    insert
    into
        Fact
        (type_id, unit_id, quantity_value, FACT_DISCRIM, id)
    values
        (?, ?, ?, 'QUANTITATIVE', ?)
Hibernate:
    update
        FactType
    set
        parent_type=?
    where
        id=?
Hibernate:
    update
        FactType
    set
        parent_type=?
    where
        id=?
Hibernate:
    update
        FactType
    set
        parent_type=?
    where
        id=?
Hibernate:
    update
        FactType
    set
        parent_type=?
    where
        id=?
Hibernate:
    update
        FactType
    set
        parent_type=?
    where
        id=?
Hibernate:
    insert
    into
        FactType_Phenomenon
        (QualitativeType_id, legalPhenomenons_id)
    values
        (?, ?)
Hibernate:
    insert
    into
        FactType_Phenomenon
        (QualitativeType_id, legalPhenomenons_id)
    values
        (?, ?)
Hibernate:
    insert
    into
        FactType_Phenomenon
        (QualitativeType_id, legalPhenomenons_id)
    values
        (?, ?)
Hibernate:
    insert
    into
        FactType_Phenomenon
        (QualitativeType_id, legalPhenomenons_id)
    values
        (?, ?)
Hibernate:
    insert
    into
        FactType_UNIT
        (QuantitativeType_id, legalUnits_id)
    values
        (?, ?)
Hibernate:
    update
        FactType
    set
        parent_type=?
    where
        id=?
Hibernate:
    update
        FactType
    set
        parent_type=?
    where
        id=?
Hibernate:
    insert
    into
        FactType_UNIT
        (QuantitativeType_id, legalUnits_id)
    values
        (?, ?)
Hibernate:
    insert
    into
        FactType_UNIT
        (QuantitativeType_id, legalUnits_id)
    values
        (?, ?)
Hibernate:
    update
        FactType
    set
        parent_type=?
    where
        id=?
Hibernate:
    update
        FactType
    set
        parent_type=?
    where
        id=?
Hibernate:
    insert
    into
        FactType_UNIT
        (QuantitativeType_id, legalUnits_id)
    values
        (?, ?)
Hibernate:
    insert
    into
        FactType_UNIT
        (QuantitativeType_id, legalUnits_id)
    values
        (?, ?)
Hibernate:
    insert
    into
        FactType_UNIT
        (QuantitativeType_id, legalUnits_id)
    values
        (?, ?)
Hibernate:
    update
        Fact
    set
        parent_fact=?
    where
        id=?
Hibernate:
    update
        Fact
    set
        parent_fact=?
    where
        id=?
Hibernate:
    update
        Fact
    set
        parent_fact=?
    where
        id=?
Hibernate:
    insert
    into
        CompositeFact_compositionTypeCheck
        (CompositeFact_id, compositionTypeCheck_KEY, typeCheckValue)
    values
        (?, ?, ?)
Hibernate:
    insert
    into
        CompositeFact_compositionTypeCheck
        (CompositeFact_id, compositionTypeCheck_KEY, typeCheckValue)
    values
        (?, ?, ?)
Hibernate:
    insert
    into
        CompositeFact_compositionTypeCheck
        (CompositeFact_id, compositionTypeCheck_KEY, typeCheckValue)
    values
        (?, ?, ?)
Hibernate:
    update
        Fact
    set
        parent_fact=?
    where
        id=?
Hibernate:
    update
        Fact
    set
        parent_fact=?
    where
        id=?
Hibernate:
    insert
    into
        CompositeFact_compositionTypeCheck
        (CompositeFact_id, compositionTypeCheck_KEY, typeCheckValue)
    values
        (?, ?, ?)
Hibernate:
    insert
    into
        CompositeFact_compositionTypeCheck
        (CompositeFact_id, compositionTypeCheck_KEY, typeCheckValue)
    values
        (?, ?, ?)
Hibernate:
    update
        Fact
    set
        parent_fact=?
    where
        id=?
Hibernate:
    update
        Fact
    set
        parent_fact=?
    where
        id=?
Hibernate:
    insert
    into
        CompositeFact_compositionTypeCheck
        (CompositeFact_id, compositionTypeCheck_KEY, typeCheckValue)
    values
        (?, ?, ?)
Hibernate:
    insert
    into
        CompositeFact_compositionTypeCheck
        (CompositeFact_id, compositionTypeCheck_KEY, typeCheckValue)
    values
        (?, ?, ?)
Hibernate:
    update
        Fact
    set
        parent_fact=?
    where
        id=?
Hibernate:
    update
        Fact
    set
        parent_fact=?
    where
        id=?
Hibernate:
    insert
    into
        CompositeFact_compositionTypeCheck
        (CompositeFact_id, compositionTypeCheck_KEY, typeCheckValue)
    values
        (?, ?, ?)
Hibernate:
    insert
    into
        CompositeFact_compositionTypeCheck
        (CompositeFact_id, compositionTypeCheck_KEY, typeCheckValue)
    values
        (?, ?, ?)
Hibernate:
    update
        Fact
    set
        parent_fact=?
    where
        id=?
Hibernate:
    update
        Fact
    set
        parent_fact=?
    where
        id=?
Hibernate:
    update
        Fact
    set
        parent_fact=?
    where
        id=?
Hibernate:
    insert
    into
        CompositeFact_compositionTypeCheck
        (CompositeFact_id, compositionTypeCheck_KEY, typeCheckValue)
    values
        (?, ?, ?)
Hibernate:
    insert
    into
        CompositeFact_compositionTypeCheck
        (CompositeFact_id, compositionTypeCheck_KEY, typeCheckValue)
    values
        (?, ?, ?)
Hibernate:
    insert
    into
        CompositeFact_compositionTypeCheck
        (CompositeFact_id, compositionTypeCheck_KEY, typeCheckValue)
    values
        (?, ?, ?)
Hibernate:
    update
        Fact
    set
        parent_fact=?
    where
        id=?
Hibernate:
    update
        Fact
    set
        parent_fact=?
    where
        id=?
Hibernate:
    insert
    into
        CompositeFact_compositionTypeCheck
        (CompositeFact_id, compositionTypeCheck_KEY, typeCheckValue)
    values
        (?, ?, ?)
Hibernate:
    insert
    into
        CompositeFact_compositionTypeCheck
        (CompositeFact_id, compositionTypeCheck_KEY, typeCheckValue)
    values
        (?, ?, ?)
Hibernate:
    update
        Fact
    set
        parent_fact=?
    where
        id=?
Hibernate:
    update
        Fact
    set
        parent_fact=?
    where
        id=?
Hibernate:
    insert
    into
        CompositeFact_compositionTypeCheck
        (CompositeFact_id, compositionTypeCheck_KEY, typeCheckValue)
    values
        (?, ?, ?)
Hibernate:
    insert
    into
        CompositeFact_compositionTypeCheck
        (CompositeFact_id, compositionTypeCheck_KEY, typeCheckValue)
    values
        (?, ?, ?)
Hibernate:
    update
        Fact
    set
        parent_fact=?
    where
        id=?
Hibernate:
    update
        Fact
    set
        parent_fact=?
    where
        id=?
Hibernate:
    insert
    into
        CompositeFact_compositionTypeCheck
        (CompositeFact_id, compositionTypeCheck_KEY, typeCheckValue)
    values
        (?, ?, ?)
Hibernate:
    insert
    into
        CompositeFact_compositionTypeCheck
        (CompositeFact_id, compositionTypeCheck_KEY, typeCheckValue)
    values
        (?, ?, ?)

 */