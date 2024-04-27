package com.prodyna.reserveyourspot.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Node
@Data
public class WorkStation {
    @Id @GeneratedValue
    private Long id;
    private String uniqueCode;
    @NotBlank
    private String equipment;
    @NotBlank
    private String description;
    private List<Long> reservationReferences = new ArrayList<>();
}
