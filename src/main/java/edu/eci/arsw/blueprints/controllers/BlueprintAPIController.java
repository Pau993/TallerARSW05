package edu.eci.arsw.blueprints.controllers;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

@RestController
@RequestMapping("/blueprints")
public class BlueprintAPIController {

    private final BlueprintsServices blueprintsServices;

    public BlueprintAPIController(BlueprintsServices blueprintsServices) {
        this.blueprintsServices = blueprintsServices;
    }

    @GetMapping
    public ResponseEntity<?> getAllBlueprints() {
        try {
            Set<Blueprint> blueprints = blueprintsServices.getAllBlueprints();
            return blueprints.isEmpty()
                    ? new ResponseEntity<>("No blueprints available", HttpStatus.NOT_FOUND)
                    : new ResponseEntity<>(blueprints, HttpStatus.OK);
        } catch (BlueprintPersistenceException e) {
            return new ResponseEntity<>("Error retrieving blueprints", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{author}")
    public ResponseEntity<?> getBlueprintsByAuthor(@PathVariable String author) {
        try {
            Set<Blueprint> blueprints = blueprintsServices.getBlueprintsByAuthor(author);
            return new ResponseEntity<>(blueprints, HttpStatus.OK);
        } catch (BlueprintNotFoundException e) {
            return new ResponseEntity<>("No blueprints found for author: " + author, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{author}/{bpname}")
    public ResponseEntity<?> getBlueprint(@PathVariable String author, @PathVariable String bpname) {
        try {
            Blueprint blueprint = blueprintsServices.getBlueprint(author, bpname);
            return new ResponseEntity<>(blueprint, HttpStatus.OK);
        } catch (BlueprintNotFoundException e) {
            return new ResponseEntity<>("Blueprint not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> addBlueprint(@RequestBody Blueprint blueprint) {
        try {
            blueprintsServices.addNewBlueprint(blueprint);
            return new ResponseEntity<>("Blueprint added successfully", HttpStatus.CREATED);
        } catch (BlueprintPersistenceException e) {
            return new ResponseEntity<>("Blueprint already exists", HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{author}/{bpname}")
    public ResponseEntity<?> updateBlueprint(@PathVariable String author, @PathVariable String bpname, @RequestBody Blueprint updatedBlueprint) {
        try {
            blueprintsServices.updateBlueprint(author, bpname, updatedBlueprint);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BlueprintNotFoundException e) {
            return new ResponseEntity<>("Blueprint not found", HttpStatus.NOT_FOUND);
        }
    }

}
