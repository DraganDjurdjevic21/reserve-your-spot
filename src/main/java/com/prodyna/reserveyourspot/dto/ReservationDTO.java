package com.prodyna.reserveyourspot.dto;

import com.prodyna.reserveyourspot.model.OfficeRoom;
import com.prodyna.reserveyourspot.model.OfficeSpace;
import com.prodyna.reserveyourspot.model.WorkStation;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationDTO {
    private Long id;
    private String userEmail;
    private String userName;
    private LocalDate date;
    private WorkStation workStation;
    private OfficeRoom officeRoom;
    private OfficeSpace officeSpace;
}
