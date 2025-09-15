/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.persistence;

import edu.eci.arsw.model.Blueprint;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author hcadavid
 */
@Repository
public interface BlueprintsPersistence {

    /**
     *
     * @param bp the new blueprint
     * @throws BlueprintPersistenceException if a blueprint with the same name already exists,
     *    or any other low-level persistence error occurs.
     */
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException;

    /**
     *
     * @param author blueprint's author
     * @param bprintname blueprint's author
     * @return the blueprint of the given name and author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author,String bprintname) throws BlueprintNotFoundException;

    public Set<Blueprint> getBlueprintByAuthor(String author) throws BlueprintNotFoundException;

    /**
     *
     * @return all blueprints
     */
    public Set<Blueprint> getAllBlueprints();

    /**
     * Updates an existing blueprint
     * @param author blueprint's author
     * @param bprintname blueprint's name
     * @param updatedBlueprint the updated blueprint
     * @throws BlueprintNotFoundException if the blueprint doesn't exist
     * @throws BlueprintPersistenceException if there's an error updating
     */
    public void updateBlueprint(String author, String bprintname, Blueprint updatedBlueprint) throws BlueprintNotFoundException, BlueprintPersistenceException;
}
