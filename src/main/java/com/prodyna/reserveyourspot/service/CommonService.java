package com.prodyna.reserveyourspot.service;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CommonService {
    /**
     * Get max number for a given type.
     *
     * @param type model type of auto generate number.
     * @return {@link Long} object.
     */
    Long getMaxNumberByType(String type);

    /**
     * Checks if the Office Space exists.
     *
     * @param recourceId office space id.
     */
    <R extends Neo4jRepository> void validEntryExists(R repo, Long recourceId);
}
