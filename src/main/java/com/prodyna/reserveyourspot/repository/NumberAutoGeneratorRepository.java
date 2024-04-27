package com.prodyna.reserveyourspot.repository;

import com.prodyna.reserveyourspot.model.NumberAutoGenerator;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

public interface NumberAutoGeneratorRepository extends Neo4jRepository<NumberAutoGenerator, Long> {

    @Query("MATCH (a:NumberAutoGenerator) where a.type=$type RETURN max(a.number)")
    Long getMaxNumberByType(@Param("type") String type);

    @Query("MATCH (a:NumberAutoGenerator) where a.type=$type " +
            "SET a.number = $number " +
            "RETURN a")
    NumberAutoGenerator update(@Param("type") String type, @Param("number") Long number);
}
