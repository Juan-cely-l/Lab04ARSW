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

import java.util.concurrent.ConcurrentHashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author hcadavid
 */
@Repository
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints=new ConcurrentHashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115)};
        Blueprint bp=new Blueprint("luis", "house",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);

        Point[] pts1=new Point[]{new Point(130, 130),new Point(135, 135)};
        Blueprint bp1=new Blueprint("andres", "pool",pts1);
        blueprints.put(new Tuple<>(bp1.getAuthor(),bp1.getName()), bp1);

        Point[] pts2=new Point[]{new Point(160, 160),new Point(118, 118)};
        Blueprint bp2=new Blueprint("andres", "garden",pts2);
        blueprints.put(new Tuple<>(bp2.getAuthor(),bp2.getName()), bp2);

        Point[] pts3=new Point[]{new Point(150, 150),new Point(165, 165)};
        Blueprint bp3=new Blueprint("julian", "backyard",pts3);
        blueprints.put(new Tuple<>(bp3.getAuthor(),bp3.getName()), bp3);

        Point[] pts4=new Point[]{new Point(200, 200),new Point(180, 180)};
        Blueprint bp4=new Blueprint("maria", "kitchen",pts4);
        blueprints.put(new Tuple<>(bp4.getAuthor(),bp4.getName()), bp4);

        Point[] pts5=new Point[]{new Point(100, 100),new Point(120, 120)};
        Blueprint bp5=new Blueprint("maria", "livingroom",pts5);
        blueprints.put(new Tuple<>(bp5.getAuthor(),bp5.getName()), bp5);

    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        Tuple<String, String> key = new Tuple<>(bp.getAuthor(), bp.getName());
        if (blueprints.putIfAbsent(key, bp) != null) {
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
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

    @Override
    public Set<Blueprint> getAllBlueprints() {
        return new HashSet<>(blueprints.values());
    }

    @Override
    public void updateBlueprint(String author, String bprintname, Blueprint updatedBlueprint) throws BlueprintNotFoundException, BlueprintPersistenceException {
        Tuple<String, String> key = new Tuple<>(author, bprintname);
        if (blueprints.get(key) == null) {
            throw new BlueprintNotFoundException("The blueprint " + author + ":" + bprintname + " does not exist");
        }
        // Actualizar el blueprint de forma atómica usando replace
        blueprints.replace(key, updatedBlueprint);
    }
}
