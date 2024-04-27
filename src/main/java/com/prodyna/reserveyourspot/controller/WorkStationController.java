package com.prodyna.reserveyourspot.controller;

import com.prodyna.reserveyourspot.endpoints.Endpoints;
import com.prodyna.reserveyourspot.exception.ErrorCode;
import com.prodyna.reserveyourspot.model.WorkStation;
import com.prodyna.reserveyourspot.service.WorkStationService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * The Controller of work station space API.
 *
 * @author Dragan Djurdjevic, PRODYNA SE
 */
@RestController
@AllArgsConstructor
@RequestMapping(Endpoints.WorkStation.OFFICE_ROOM_ROOT)
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class WorkStationController {

    final private WorkStationService workStationService;

    /**
     * Get all WorkStation objects from database with references to Reservation.
     *
     * @return {@link ResponseEntity} object.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "You do not have right permissions to get this entries", response = ErrorCode.class),
            @ApiResponse(code = 404, message = "Resource not found", response = ErrorCode.class),
            @ApiResponse(code = 500, message = "Internal server error occurred", response = ErrorCode.class)}
    )
    @GetMapping(value = Endpoints.WorkStation.GET_ALL)
    public ResponseEntity getAll() {
        return ResponseEntity.ok(workStationService.getAll());
    }

    /**
     * Get WorkStation object by id.
     *
     * @param workStationId workStation technical id.
     * @return {@link ResponseEntity} object.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "You do not have right permissions to get this entries", response = ErrorCode.class),
            @ApiResponse(code = 404, message = "Resource not found", response = ErrorCode.class),
            @ApiResponse(code = 500, message = "Internal server error occurred", response = ErrorCode.class)}
    )
    @GetMapping(value = Endpoints.WorkStation.GET_BY_WORK_STATION_ID)
    public ResponseEntity getById(@PathVariable("workStationId") Long workStationId) {
        return ResponseEntity.ok(workStationService.getWorkStationById(workStationId));
    }

    /**
     * Get all WorkStation objects from office room.
     *
     * @param officeRoomId officeRoom technical id.
     * @return {@link ResponseEntity} object.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "You do not have right permissions to get this entry", response = ErrorCode.class),
            @ApiResponse(code = 404, message = "Resource not found", response = ErrorCode.class),
            @ApiResponse(code = 500, message = "Internal server error occurred", response = ErrorCode.class)}
    )
    @GetMapping(value = Endpoints.WorkStation.GET_BY_OFFICE_ROOM_ID)
    public ResponseEntity getByOfficeRoom(@PathVariable("officeRoomId") Long officeRoomId) {
        return ResponseEntity.ok(workStationService.getWorkStationByOfficeRoom(officeRoomId));
    }

    /**
     * Create WorkStation object.
     *
     * @param officeRoomId officeRoom id.
     * @param workStation  workStation body request object.
     * @return {@link ResponseEntity} object.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Entry created"),
            @ApiResponse(code = 403, message = "You do not have right permissions to create this entry", response = ErrorCode.class),
            @ApiResponse(code = 404, message = "Resource not found", response = ErrorCode.class),
            @ApiResponse(code = 500, message = "Internal server error occurred", response = ErrorCode.class)}
    )
    @PostMapping(value = Endpoints.WorkStation.CREATE)
    public ResponseEntity create(@PathVariable("officeRoomId") Long officeRoomId, @Valid @RequestBody WorkStation workStation) {
        return new ResponseEntity<>(workStationService.createWorkStationByOfficeRoom(officeRoomId, workStation.getDescription(), workStation.getEquipment()), HttpStatus.CREATED);
    }

    /**
     * Update WorkStation object.
     *
     * @param workStation WorkStation body request object..
     * @return {@link ResponseEntity} object.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Entry updated"),
            @ApiResponse(code = 403, message = "You do not have right permissions to updateOfficeSpace this entry", response = ErrorCode.class),
            @ApiResponse(code = 404, message = "Resource not found", response = ErrorCode.class),
            @ApiResponse(code = 500, message = "Internal server error occurred", response = ErrorCode.class)}
    )
    @PatchMapping(value = Endpoints.WorkStation.UPDATE)
    public ResponseEntity update(@Valid @RequestBody WorkStation workStation) {
        return ResponseEntity.ok(workStationService.updateWorkStation(workStation));
    }

    /**
     * Delete WorkStation object.
     *
     * @param workStationId workStation technical id.
     * @return {@link ResponseEntity} object.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Entry deleted"),
            @ApiResponse(code = 403, message = "You do not have right permissions to delete this entry", response = ErrorCode.class),
            @ApiResponse(code = 404, message = "Resource not found", response = ErrorCode.class),
            @ApiResponse(code = 500, message = "Internal server error occurred", response = ErrorCode.class)}
    )
    @DeleteMapping(value = Endpoints.WorkStation.DELETE)
    public ResponseEntity deleteById(@PathVariable("workStationId") Long workStationId) {
        workStationService.deleteByWorkStationId(workStationId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
