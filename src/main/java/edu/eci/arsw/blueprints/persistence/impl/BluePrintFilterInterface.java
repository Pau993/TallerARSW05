package edu.eci.arsw.blueprints.persistence.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import edu.eci.arsw.blueprints.filters.BlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;

@Primary
@Service
public class BluePrintFilterInterface implements BlueprintFilter{

    @Override
    public Blueprint filter(Blueprint blueprint) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
