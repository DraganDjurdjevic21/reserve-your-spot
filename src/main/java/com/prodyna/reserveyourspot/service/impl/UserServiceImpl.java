package com.prodyna.reserveyourspot.service.impl;

import com.google.common.collect.Sets;
import com.prodyna.reserveyourspot.dto.ReservationDTO;
import com.prodyna.reserveyourspot.dto.UserDTO;
import com.prodyna.reserveyourspot.exception.ErrorCode;
import com.prodyna.reserveyourspot.exception.RYSValidationException;
import com.prodyna.reserveyourspot.model.*;
import com.prodyna.reserveyourspot.repository.ReservationRepository;
import com.prodyna.reserveyourspot.repository.UserRepository;
import com.prodyna.reserveyourspot.security.model.RYSUserDetails;
import com.prodyna.reserveyourspot.service.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.EnumUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.prodyna.reserveyourspot.model.UserRoleEnum.*;

/**
 * The implementation of user service.
 *
 * @author Dragan Djurdjevic, PRODYNA SE
 * @see UserService
 */

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final CommonService commonService;

/*    private final OfficeSpaceService officeSpaceService;
    private final OfficeRoomService officeRoomService;
    private final WorkStationService workStationService;*/

    private final ReservationService reservationService;


    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public UserDTO getUserById(Long userId) {
        commonService.validEntryExists(userRepository, userId);
        final User user = userRepository.getUserById(userId);
        return convertToUserDTO(user);
    }

    @Override
    public User createUser(User user) {
        // check does email already exist
        validEmail(user.getEmail());
        // do validation for sended role value
        validRoleValue(user.getRole());

        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        final Long id = user.getId();
        String role = user.getRole();

        final Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            // check does email already exist to other user
            validEmailForExistingUser(user);
            // do validation for sended role value
            validRoleValue(role);
            user = userRepository.updateUser(id, user.getName(), user.getEmail(), user.getRole(), user.getPassword());
        } else {
            throw new RYSValidationException(ErrorCode.RESOURCE_NOT_FOUND, String.format("User with id %s do not exist!", id));
        }
        return user;
    }

    @Override
    public User updateUserRole(Long userId, String role) {
        commonService.validEntryExists(userRepository, userId);
        // do validation for sended role value
        validRoleValue(role);
        return userRepository.updateRole(userId, role);
    }

    @Override
    public void deleteByUserId(Long userId) {
        commonService.validEntryExists(userRepository, userId);
        userRepository.deleteById(userId);
    }

    /**
     * Get User by email.
     *
     * @param email user email.
     * @return {@link User} object.
     */
    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email).stream().findFirst().orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserDetails(username);
    }

    /**
     * Do validation for sended role value.
     *
     * @param role user role.
     */
    private void validRoleValue(String role) {
        if (!EnumUtils.isValidEnum(UserRoleEnum.class, role)) {
            throw new RYSValidationException(ErrorCode.RESOURCE_NOT_FOUND, String.format("Role %s is not available. Currently are available %s", role, Arrays.toString(values())));
        }
    }

    /**
     * Check does email already exist.
     *
     * @param userName user email to be check.
     */
    private void validEmail(String userName) {
        if (!userRepository.getUserByEmail(userName).isEmpty()) {
            throw new RYSValidationException(ErrorCode.INVALID_INPUT, String.format("Email %s already exist!", userName));
        }
    }

    /**
     * Check does email belongs to another user.
     *
     * @param user user object to be check.
     */
    private void validEmailForExistingUser(User user) {
        final List<Long> userIds = userRepository.getUserByEmail(user.getEmail()).stream().map(User::getId).collect(Collectors.toList());
        if (!userIds.isEmpty() && !userIds.contains(user.getId())) {
            throw new RYSValidationException(ErrorCode.INVALID_INPUT, String.format("Email %s is not available!", user.getEmail()));
        }
    }

    /**
     * Create UserDetails with authority by user email.
     *
     * @param userName user name.
     * @return {@link RYSUserDetails} object.
     */
    private RYSUserDetails getUserDetails(String userName) {
        final User user = getUserByEmail(userName);

        HashSet<SimpleGrantedAuthority> simpleGrantedAuthorities = Sets.newHashSet(new SimpleGrantedAuthority(user.getRole()));

        return new RYSUserDetails(simpleGrantedAuthorities,
                userName,
                passwordEncoder.encode(user.getPassword()), true,
                true, true, true);
    }

    private UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setPassword(user.getPassword());

        final List<ReservationDTO> reservationDTOS = user.getReservations().stream().map(id ->
                reservationService.convertToReservationDTO(reservationRepository.getById(id))).collect(Collectors.toList());

        userDTO.setReservations(reservationDTOS);

        return userDTO;
    }
}
