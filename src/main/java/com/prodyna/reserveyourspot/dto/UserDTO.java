package com.prodyna.reserveyourspot.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    private Long id;

    private String name;

    private String email;

    private String password;

    private List<ReservationDTO> reservations;
}
