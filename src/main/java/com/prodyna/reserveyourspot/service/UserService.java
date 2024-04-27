package com.prodyna.reserveyourspot.service;

import com.prodyna.reserveyourspot.dto.UserDTO;
import com.prodyna.reserveyourspot.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;


import java.util.List;

public interface UserService extends UserDetailsService {
    /**
     * Get all User objects from database with references to Reservation.
     *
     * @return {@link List<User>} object.
     */
    List<User> getAll();

    /**
     * Get User object by id.
     *
     * @param userId user id.
     * @return {@link UserDTO} object.
     */
    UserDTO getUserById(Long userId);

    /**
     * Create User object.
     *
     * @param user user body request object.
     * @return {@link User} object.
     */
    User createUser(User user);

    /**
     * Update User object.
     *
     * @param userUpdate user body request object.
     * @return {@link User} object.
     */
    User updateUser(User userUpdate);

    /**
     * Update role to User object.
     *
     * @param userId user technical id.
     * @param role   role to updateOfficeSpace.
     * @return {@link User} object.
     */
    User updateUserRole(Long userId, String role);

    /**
     * Delete User object.
     *
     * @param userId user technical id.
     *
     */
    void deleteByUserId(Long userId);

    /**
     * Get User objects from database by email.
     *
     * @return {@link List<User>} object.
     */
    User getUserByEmail(String userName);

}
