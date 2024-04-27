package com.prodyna.reserveyourspot.service;

import com.prodyna.reserveyourspot.dto.ReservationDTO;
import com.prodyna.reserveyourspot.model.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {

    /**
     * Get all Reservation objects from database.
     *
     * @return {@link List < Reservation >} object.
     */
    List<ReservationDTO> getAll();

    /**
     * Create Reservation object.
     *
     * @param date            date of reservation.
     * @param userEmail       user email of reservation.
     * @param workStationCode unique work station code.
     * @return {@link ReservationDTO} object.
     */
    ReservationDTO createByWorkStation(String workStationCode, String userEmail, LocalDate date);

    /**
     * Create List of Reservation object by range.
     *
     * @param workStationCode unique work station code.
     * @param userName        user of reservation user name.
     * @param dateTo          end date of reservation.
     * @param dateFrom        start date of reservation.
     * @return {@link ReservationDTO} object.
     */
    List<ReservationDTO> createByWorkStationInRange(String workStationCode, String userName, LocalDate dateFrom, LocalDate dateTo);


    /**
     * Delete Reservation object.
     *
     * @param reservationId reservation technical id.
     */
    void deleteByReservationId(Long reservationId);

    /**
     * Convert Reservation object to DTO.
     *
     * @param reservation object.
     */
    ReservationDTO convertToReservationDTO(Reservation reservation);
}
