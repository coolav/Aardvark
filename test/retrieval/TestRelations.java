/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package retrieval;

import java.util.Map;
import junit.framework.TestCase;
import uib.retrieval.model.EnumRelations;
import uib.retrieval.model.EnumRelations.Relations;

/**
 *
 * @author Olav
 */
public class TestRelations extends TestCase {

    private final EnumRelations enumRealtions = new EnumRelations();
    //private Relation oldRelations = new Relation();

    @SuppressWarnings("unchecked")
    private final Map<uib.retrieval.model.EnumRelations.Relations, String> 
            relation = enumRealtions.getRelations();

    public void testMapping() {
        
        for (Relations r : relation.keySet()) {
            System.out.println("Relation : " + r.getRelation() + "  Inverse : " + r.getInverse());
        }
    }
    
    public void testRelations(){
    
    }
}
