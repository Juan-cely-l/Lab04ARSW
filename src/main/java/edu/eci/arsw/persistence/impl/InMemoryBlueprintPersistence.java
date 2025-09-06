/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.persistence.impl;

import edu.eci.arsw.model.Blueprint;
import edu.eci.arsw.model.Point;
import edu.eci.arsw.persistence.BlueprintNotFoundException;
import edu.eci.arsw.persistence.BlueprintPersistenceException;
import edu.eci.arsw.persistence.BlueprintsPersistence;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author hcadavid
 */
@Repository
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints=new HashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115)};
        Blueprint bp=new Blueprint("luis", "house ",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);

        Point[] pts1=new Point[]{new Point(130, 130),new Point(135, 135)};
        Blueprint bp1=new Blueprint("andres", "pool ",pts1);
        blueprints.put(new Tuple<>(bp1.getAuthor(),bp1.getName()), bp1);

        Point[] pts2=new Point[]{new Point(160, 160),new Point(118, 118)};
        Blueprint bp2=new Blueprint("andres", "pool ",pts2);
        blueprints.put(new Tuple<>(bp2.getAuthor(),bp2.getName()), bp2);

        Point[] pts3=new Point[]{new Point(150, 150),new Point(165, 165)};
        Blueprint bp3=new Blueprint("julian", "backyard ",pts3);
        blueprints.put(new Tuple<>(bp3.getAuthor(),bp3.getName()), bp3);
        
    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }        
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        Blueprint bp= blueprints.get(new Tuple<>(author, bprintname));
        if(bp==null){
            throw new BlueprintNotFoundException("The blueprint " + author + ":" + bprintname + " does not exist");

        }
        return bp;

    }

    @Override
    public Set<Blueprint> getBlueprintByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> authorBlueprints=new HashSet<>();
        for(Map.Entry<Tuple<String,String>,Blueprint> entry : blueprints.entrySet()){
            if((entry.getKey().getElem1().equals(author))){
                authorBlueprints.add(entry.getValue());

            }
        }
        if (authorBlueprints.isEmpty()){
        throw new BlueprintNotFoundException("No blueprints found for author " + author);
        }
        return authorBlueprints;
    }


}
