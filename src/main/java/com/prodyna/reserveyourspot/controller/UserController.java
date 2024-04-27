package com.prodyna.reserveyourspot.controller;

import com.prodyna.reserveyourspot.dto.UserDTO;
import com.prodyna.reserveyourspot.endpoints.Endpoints;
import com.prodyna.reserveyourspot.exception.ErrorCode;
import com.prodyna.reserveyourspot.model.User;
import com.prodyna.reserveyourspot.service.UserService;
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
 * The Controller of user API.
 *
 * @author Dragan Djurdjevic, PRODYNA SE
 */
@RestController()
@AllArgsConstructor
@RequestMapping(Endpoints.User.USER_ROOT)
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class UserController {
    final private UserService userService;

    /**
     * Get all User objects from database with references to Reservation.
     *
     * @return {@link ResponseEntity} object.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "You do not have right permissions to get this entries", response = ErrorCode.class),
            @ApiResponse(code = 404, message = "Resource not found", response = ErrorCode.class),
            @ApiResponse(code = 500, message = "Internal server error occurred", response = ErrorCode.class)}
    )
    @GetMapping(Endpoints.User.GET_ALL)
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    /**
     * Get all User objects from database with references to Reservation.
     *
     * @return {@link ResponseEntity} object.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "You do not have right permissions to get this entries", response = ErrorCode.class),
            @ApiResponse(code = 404, message = "Resource not found", response = ErrorCode.class),
            @ApiResponse(code = 500, message = "Internal server error occurred", response = ErrorCode.class)}
    )
    @GetMapping(Endpoints.User.GET_BY_ID)
    public ResponseEntity<UserDTO> getById(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    /**
     * Create User object.
     *
     * @param user user body request object.
     * @return {@link ResponseEntity} object.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Entry created"),
            @ApiResponse(code = 403, message = "You do not have right permissions to create this entry", response = ErrorCode.class),
            @ApiResponse(code = 404, message = "Resource not found", response = ErrorCode.class),
            @ApiResponse(code = 500, message = "Internal server error occurred", response = ErrorCode.class)}
    )
    @PostMapping(Endpoints.User.CREATE)
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }


    /**
     * Update User object.
     *
     * @param user user body request object.
     * @return {@link ResponseEntity} object.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Entry updated"),
            @ApiResponse(code = 403, message = "You do not have right permissions to updateOfficeSpace this entry", response = ErrorCode.class),
            @ApiResponse(code = 404, message = "Resource not found", response = ErrorCode.class),
            @ApiResponse(code = 500, message = "Internal server error occurred", response = ErrorCode.class)}
    )
    @PutMapping(Endpoints.User.UPDATE)
    public ResponseEntity<User> update(@Valid @RequestBody() User user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    /**
     * Update role of User object.
     *
     * @param userId user technical id.
     * @param role   role to updateOfficeSpace.
     * @return {@link ResponseEntity} object.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Entry role updated"),
            @ApiResponse(code = 403, message = "You do not have right permissions to updateOfficeSpace role for this entry", response = ErrorCode.class),
            @ApiResponse(code = 404, message = "Resource not found", response = ErrorCode.class),
            @ApiResponse(code = 500, message = "Internal server error occurred", response = ErrorCode.class)}
    )
    @PatchMapping(Endpoints.User.UPDATE_ROLE)
    public ResponseEntity<User> updateUserRole(@PathVariable("userId") Long userId, @PathVariable("role") String role) {
        return ResponseEntity.ok(userService.updateUserRole(userId, role));
    }

    /**
     * Delete User object.
     *
     * @param userId user technical id.
     * @return {@link ResponseEntity} object.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Entry deleted"),
            @ApiResponse(code = 403, message = "You do not have right permissions to delete this entry", response = ErrorCode.class),
            @ApiResponse(code = 404, message = "Resource not found", response = ErrorCode.class),
            @ApiResponse(code = 500, message = "Internal server error occurred", response = ErrorCode.class)}
    )
    @DeleteMapping(Endpoints.User.DELETE)
    public ResponseEntity deleteById(@PathVariable("userId") Long userId) {
        userService.deleteByUserId(userId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
