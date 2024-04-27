package com.prodyna.reserveyourspot.controller;

import com.prodyna.reserveyourspot.endpoints.Endpoints;
import com.prodyna.reserveyourspot.exception.ErrorCode;
import com.prodyna.reserveyourspot.model.OfficeRoom;
import com.prodyna.reserveyourspot.service.OfficeRoomService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * The Controller of office room API.
 *
 * @author Dragan Djurdjevic, PRODYNA SE
 */
@RestController
@AllArgsConstructor
@RequestMapping(Endpoints.OfficeRoom.OFFICE_ROOM_ROOT)
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class OfficeRoomController {

    final private OfficeRoomService officeRoomService;

    /**
     * Get all OfficeRoom objects from database with references to WorkStation.
     *
     * @return {@link ResponseEntity} object.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "You do not have right permissions to get this entries", response = ErrorCode.class),
            @ApiResponse(code = 404, message = "Resource not found", response = ErrorCode.class),
            @ApiResponse(code = 500, message = "Internal server error occurred", response = ErrorCode.class)}
    )
    @GetMapping(value = Endpoints.OfficeRoom.GET_ALL)
    public ResponseEntity getAll() {
        return ResponseEntity.ok(officeRoomService.getAll());
    }

    /**
     * Get OfficeRoom objects from database by id.
     *
     * @param officeRoomId officeRoom technical id.
     * @return {@link ResponseEntity} object.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "You do not have right permissions to get this entry", response = ErrorCode.class),
            @ApiResponse(code = 404, message = "Resource not found", response = ErrorCode.class),
            @ApiResponse(code = 500, message = "Internal server error occurred", response = ErrorCode.class)}
    )
    @GetMapping(value = Endpoints.OfficeRoom.GET_BY_OFFICE_ROOM_ID)
    public ResponseEntity geById(@PathVariable("officeRoomId") Long officeRoomId) {
        return ResponseEntity.ok(officeRoomService.getOfficeRoomById(officeRoomId));
    }

    /**
     * Get all OfficeRoom objects from OfficeRoom.
     *
     * @param officeSpaceId officeSpace technical id.
     * @return {@link ResponseEntity} object.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "You do not have right permissions to get this entry", response = ErrorCode.class),
            @ApiResponse(code = 404, message = "Resource not found", response = ErrorCode.class),
            @ApiResponse(code = 500, message = "Internal server error occurred", response = ErrorCode.class)}
    )
    @GetMapping(value = Endpoints.OfficeRoom.GET_BY_OFFICE_SPACE_ID)
    public ResponseEntity getByOfficeSpace(@PathVariable("officeSpaceId") Long officeSpaceId) {
        return ResponseEntity.ok(officeRoomService.getOfficeRoomsByOfficeSpace(officeSpaceId));
    }

    /**
     * Create OfficeRoom object.
     *
     * @param officeSpaceId orelated fficeSpace id.
     * @param officeRoom    officeRoom body request object.
     * @return {@link ResponseEntity} object.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Entry created"),
            @ApiResponse(code = 403, message = "You do not have right permissions to create this entry", response = ErrorCode.class),
            @ApiResponse(code = 404, message = "Resource not found", response = ErrorCode.class),
            @ApiResponse(code = 500, message = "Internal server error occurred", response = ErrorCode.class)}
    )
    @PostMapping(value = Endpoints.OfficeRoom.CREATE)
    public ResponseEntity create(@PathVariable("officeSpaceId") Long officeSpaceId, @Valid @RequestBody OfficeRoom officeRoom) {
        return new ResponseEntity<>(officeRoomService.createByOfficeSpaceId(officeSpaceId, officeRoom.getName()), HttpStatus.CREATED);
    }

    /**
     * Update OfficeRoom object.
     *
     * @param officeRoom officeRoom body request object.
     * @return {@link ResponseEntity} object.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Entry updated"),
            @ApiResponse(code = 403, message = "You do not have right permissions to updateOfficeSpace this entry", response = ErrorCode.class),
            @ApiResponse(code = 404, message = "Resource not found", response = ErrorCode.class),
            @ApiResponse(code = 500, message = "Internal server error occurred", response = ErrorCode.class)}
    )
    @PatchMapping(value = Endpoints.OfficeRoom.UPDATE)
    public ResponseEntity update(@Valid @RequestBody OfficeRoom officeRoom) {
        return ResponseEntity.ok(officeRoomService.updateOfficeRoom(officeRoom));
    }

    /**
     * Delete OfficeRoom object.
     *
     * @param officeRoomId officeRoom technical id.
     * @return {@link ResponseEntity} object.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Entry deleted"),
            @ApiResponse(code = 403, message = "You do not have right permissions to delete this entry", response = ErrorCode.class),
            @ApiResponse(code = 404, message = "Resource not found", response = ErrorCode.class),
            @ApiResponse(code = 500, message = "Internal server error occurred", response = ErrorCode.class)}
    )
    @DeleteMapping(value = Endpoints.OfficeRoom.DELETE)
    public ResponseEntity deleteById(@PathVariable("officeRoomId") Long officeRoomId) {
        officeRoomService.deleteOfficeRoomById(officeRoomId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
