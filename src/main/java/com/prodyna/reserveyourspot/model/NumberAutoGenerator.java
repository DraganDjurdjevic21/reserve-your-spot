package com.prodyna.reserveyourspot.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import javax.validation.constraints.NotBlank;

@Node
@Data
public class NumberAutoGenerator {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private Long number;
    @NotBlank
    private String type;
}
