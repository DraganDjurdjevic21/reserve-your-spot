package com.prodyna.reserveyourspot.controller;

import com.prodyna.reserveyourspot.commons.Constants;
import com.prodyna.reserveyourspot.endpoints.Endpoints;
import com.prodyna.reserveyourspot.exception.ErrorCode;
import com.prodyna.reserveyourspot.model.Reservation;
import com.prodyna.reserveyourspot.service.ReservationService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

/**
 * The Controller of reservation space API.
 *
 * @author Dragan Djurdjevic, PRODYNA SE
 */
@RestController
@AllArgsConstructor
@RequestMapping(Endpoints.Reservation.RESERVATION_ROOT)
public class ReservationController {

    final private ReservationService reservationService;

    /**
     * Get all Reservation objects from database.
     *
     * @return {@link ResponseEntity} object.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "You do not have right permissions to get this entries", response = ErrorCode.class),
            @ApiResponse(code = 404, message = "Resource not found", response = ErrorCode.class),
            @ApiResponse(code = 500, message = "Internal server error occurred", response = ErrorCode.class)}
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(Endpoints.Reservation.GET_ALL)
    public ResponseEntity getAll() {
        return ResponseEntity.ok(reservationService.getAll());
    }

    /**
     * Create Reservation object.
     *
     * @param reservation Reservation request object.
     * @return {@link ResponseEntity} object.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Entry created"),
            @ApiResponse(code = 403, message = "You do not have right permissions to create this entry", response = ErrorCode.class),
            @ApiResponse(code = 404, message = "Resource not found", response = ErrorCode.class),
            @ApiResponse(code = 500, message = "Internal server error occurred", response = ErrorCode.class)}
    )
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @PostMapping(value = Endpoints.Reservation.CREATE)
    public ResponseEntity create(@Valid @RequestBody Reservation reservation) {
        return new ResponseEntity<>(reservationService.createByWorkStation(reservation.getWorkStationCode(), reservation.getUserEmail(),
                reservation.getDate()), HttpStatus.CREATED);
    }

    /**
     * Create Reservation object.
     *
     * @param reservation Reservation request object.
     * @param dateFrom    start date of reservation.
     * @param dateTo      end date of reservation.
     * @return {@link ResponseEntity} object.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Entries created by range"),
            @ApiResponse(code = 403, message = "You do not have right permissions to create this entries", response = ErrorCode.class),
            @ApiResponse(code = 404, message = "Resource not found", response = ErrorCode.class),
            @ApiResponse(code = 500, message = "Internal server error occurred", response = ErrorCode.class)}
    )
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @PostMapping(value = Endpoints.Reservation.CREATE_IN_RANGE)
    public ResponseEntity createInRange(@Valid @RequestBody Reservation reservation,
                                        @PathVariable("dateFrom") @DateTimeFormat(pattern = Constants.RESERVATION_DATE_FORMAT) LocalDate dateFrom, @PathVariable("dateTo") @DateTimeFormat(pattern = Constants.RESERVATION_DATE_FORMAT) LocalDate dateTo) {
        return new ResponseEntity<>(reservationService.createByWorkStationInRange(reservation.getWorkStationCode(), reservation.getUserEmail(), dateFrom, dateTo), HttpStatus.CREATED);
    }

    /**
     * Delete Reservation object.
     *
     * @param reservationId reservation technical id.
     * @return {@link ResponseEntity} object.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Entry deleted"),
            @ApiResponse(code = 403, message = "You do not have right permissions to delete this entry", response = ErrorCode.class),
            @ApiResponse(code = 404, message = "Resource not found", response = ErrorCode.class),
            @ApiResponse(code = 500, message = "Internal server error occurred", response = ErrorCode.class)}
    )
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @DeleteMapping(value = Endpoints.Reservation.DELETE)
    public ResponseEntity delete(@PathVariable("reservationId") Long reservationId) {
        reservationService.deleteByReservationId(reservationId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
