package com.prodyna.reserveyourspot.controller;

import com.prodyna.reserveyourspot.endpoints.Endpoints;
import com.prodyna.reserveyourspot.exception.ErrorCode;
import com.prodyna.reserveyourspot.model.OfficeSpace;
import com.prodyna.reserveyourspot.service.OfficeSpaceService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * The Controller of office space API.
 *
 * @author Dragan Djurdjevic, PRODYNA SE
 */
@RestController
@AllArgsConstructor
@RequestMapping(Endpoints.OfficeSpace.OFFICE_SPACE_ROOT)
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class OfficeSpaceController {

    final private OfficeSpaceService officeSpaceService;

    /**
     * Get all OfficeSpace objects from database with references to OfficeRoom.
     *
     * @return {@link ResponseEntity} object.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "You do not have right permissions to get this entries", response = ErrorCode.class),
            @ApiResponse(code = 404, message = "Resource not found", response = ErrorCode.class),
            @ApiResponse(code = 500, message = "Internal server error occurred", response = ErrorCode.class)}
    )
    @GetMapping(value = Endpoints.OfficeSpace.GET_ALL)
    public ResponseEntity<List<OfficeSpace>> getAll() {
        return ResponseEntity.ok(officeSpaceService.getAll());
    }

    /**
     * Get OfficeSpace objects from database by id.
     *
     * @param officeSpaceId officeSpace id.
     * @return {@link ResponseEntity} object.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "You do not have right permissions to get this entry", response = ErrorCode.class),
            @ApiResponse(code = 404, message = "Resource not found", response = ErrorCode.class),
            @ApiResponse(code = 500, message = "Internal server error occurred", response = ErrorCode.class)}
    )
    @GetMapping(value = Endpoints.OfficeSpace.GET_BY_OFFICE_SPACE_ID)
    public ResponseEntity getById(@PathVariable("officeSpaceId") Long officeSpaceId) {
        return ResponseEntity.ok(officeSpaceService.getOfficeSpaceById(officeSpaceId));
    }

    /**
     * Create OfficeSpace object.
     *
     * @param officeSpace officeSpace body request object.
     * @return {@link ResponseEntity} object.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Entry created"),
            @ApiResponse(code = 403, message = "You do not have right permissions to create this entry", response = ErrorCode.class),
            @ApiResponse(code = 404, message = "Resource not found", response = ErrorCode.class),
            @ApiResponse(code = 500, message = "Internal server error occurred", response = ErrorCode.class)}
    )
    @PostMapping(value = Endpoints.OfficeSpace.CREATE)
    public ResponseEntity<OfficeSpace> create(@Valid @RequestBody OfficeSpace officeSpace) {
        return new ResponseEntity<>(officeSpaceService.createOfficeSpace(officeSpace), HttpStatus.CREATED);
    }

    /**
     * Update OfficeSpace object.
     *
     * @param officeSpace officeSpace body request object.
     * @return {@link ResponseEntity} object.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Entry updated"),
            @ApiResponse(code = 403, message = "You do not have right permissions to updateOfficeSpace this entry", response = ErrorCode.class),
            @ApiResponse(code = 404, message = "Resource not found", response = ErrorCode.class),
            @ApiResponse(code = 500, message = "Internal server error occurred", response = ErrorCode.class)}
    )
    @PutMapping(value = Endpoints.OfficeSpace.UPDATE)
    public ResponseEntity<OfficeSpace> update(@Valid @RequestBody OfficeSpace officeSpace) {
        return ResponseEntity.ok(officeSpaceService.updateOfficeSpace(officeSpace));
    }

    /**
     * Delete OfficeSpace object.
     *
     * @param officeSpaceId office space technical id.
     * @return {@link ResponseEntity} object.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Entry deleted"),
            @ApiResponse(code = 403, message = "You do not have right permissions to delete this entry", response = ErrorCode.class),
            @ApiResponse(code = 404, message = "Resource not found", response = ErrorCode.class),
            @ApiResponse(code = 500, message = "Internal server error occurred", response = ErrorCode.class)}
    )
    @DeleteMapping(value = Endpoints.OfficeSpace.DELETE)
    public ResponseEntity deleteById(@PathVariable("officeSpaceId") Long officeSpaceId) {
        officeSpaceService.deleteOfficeSpaceById(officeSpaceId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
