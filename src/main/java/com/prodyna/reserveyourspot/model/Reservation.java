package com.prodyna.reserveyourspot.model;

import com.prodyna.reserveyourspot.commons.Constants;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Node
@Data
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String userEmail;
    private String userName;
    @NotBlank
    private String workStationCode;
    @DateTimeFormat(pattern = Constants.RESERVATION_DATE_FORMAT)
    private LocalDate date;
    private Long officeRoomId;
    private Long officeSpaceId;
}
