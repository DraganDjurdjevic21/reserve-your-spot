package com.prodyna.reserveyourspot.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Node
@Data
public class OfficeRoom {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String name;
    private Long uniqueCode;
    List<Long> workStationReferences;
}
