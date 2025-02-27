package edu.eci.arsw.blueprints.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;
import edu.eci.arsw.blueprints.filters.BlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import jakarta.annotation.PostConstruct;

/**
 * Service class for managing blueprints. Provides methods to add, retrieve, and
 * list blueprints. Utilizes dependency injection to interact with the
 * persistence layer. Applies a filter to blueprints before returning them.
 *
 * @author hcadavid
 */
@Service
public class BlueprintsServices {

    @Autowired
    public BlueprintsPersistence bpp;

    @Autowired
    public BlueprintFilter filter;

    private final ConcurrentHashMap<String, Object> locks = new ConcurrentHashMap<>();

    /**
     * Adds a new blueprint to the persistence layer.
     *
     * @param bp the blueprint to add
     * @throws BlueprintPersistenceException if a blueprint with the same name
     * already exists
     */
    public void addNewBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        String author = bp.getAuthor();
        locks.putIfAbsent(author, new Object());

        synchronized (locks.get(author)) {
            System.out.println("📝 Agregando blueprint: " + author + " - " + bp.getName());
            bpp.saveBlueprint(bp);
        }
    }
    

    /**
     * Retrieves all blueprints from the persistence layer, applying a filter.
     *
     * @return a set of all filtered blueprints
     */
    public Set<Blueprint> getAllBlueprints() throws BlueprintPersistenceException {
        // Aplicamos el filtro solo si es necesario
        Set<Blueprint> blueprints = bpp.getAllBlueprints();
        // return blueprints.stream().map(filter::filter).collect(Collectors.toSet()); // ❌ COMENTADO
        return blueprints; // ✅ TEMPORALMENTE SIN FILTRO
    }
    
    /**
     * Retrieves a specific blueprint by author and name, applying a filter.
     *
     * @param author blueprint's author
     * @param name blueprint's name
     * @return the filtered blueprint of the given name created by the given
     * author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {
        locks.putIfAbsent(author, new Object());
    
        synchronized (locks.get(author)) {
            Blueprint bp = bpp.getBlueprint(author, name);
            return bp; // ✅ Devuelve el blueprint sin filtrar temporalmente
        }
    }
    
    

    /**
     * Retrieves all blueprints by a specific author, applying a filter.
     *
     * @param author blueprint's author
     * @return all the filtered blueprints of the given author
     * @throws BlueprintNotFoundException if the given author doesn't exist
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        return bpp.getBlueprintsByAuthor(author).stream()
                .map(filter::filter)
                .collect(Collectors.toSet());
    }

    public void updateBlueprint(String author, String bpname, Blueprint updatedBlueprint) throws BlueprintNotFoundException {
        locks.putIfAbsent(author, new Object());

        synchronized (locks.get(author)) {
            Blueprint existingBlueprint = getBlueprint(author, bpname);
            existingBlueprint.setPoints(updatedBlueprint.getPoints());
        }
    }
    

    @PostConstruct
    public void init() {
        System.out.println("✅ BlueprintsServices se ha inicializado correctamente.");
    }
    
}
