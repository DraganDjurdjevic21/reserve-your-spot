package com.prodyna.reserveyourspot.service.impl;

import com.prodyna.reserveyourspot.exception.RYSValidationException;
import com.prodyna.reserveyourspot.model.User;
import com.prodyna.reserveyourspot.repository.ReservationRepository;
import com.prodyna.reserveyourspot.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;
    @InjectMocks
    private CommonServiceImpl commonService;
    @InjectMocks
    private ReservationServiceImpl reservationService;

    @BeforeEach
    void setup() {
        userService = new UserServiceImpl(passwordEncoder, userRepository, reservationRepository,
                commonService,reservationService);
    }

    @Test
    void getAllUserShouldReturnAll() {
        userService.getAll();
        verify(userRepository).getAll();
    }

    @Test()
    void createUserWhenUserNameNotExistsShouldReturnSample() throws RYSValidationException {
        User userSuccess = new User();
        userSuccess.setName("Test");
        userSuccess.setEmail("test@gmail.com");
        userSuccess.setRole("ROLE_USER");
        userSuccess.setPassword("test");

        when(userRepository.getUserByEmail("test@gmail.com")).thenReturn(Collections.emptyList());
        userService.createUser(userSuccess);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        final User captorUser = userArgumentCaptor.getValue();
        assertThat(captorUser).isEqualTo(userSuccess);

        User userFaild = new User();
        userFaild.setName("Test");
        userFaild.setEmail("test2022@gmail.com");
        userFaild.setRole("ROLE_USER");
        userFaild.setPassword("test");

        when(userRepository.getUserByEmail("test2022@gmail.com")).thenReturn(Collections.singletonList(userFaild));
        assertThrows(RYSValidationException.class, () -> userService.createUser(userFaild));

        ArgumentCaptor<User> userArgumentCaptorFaild = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptorFaild.capture());
        final User captorFaild = userArgumentCaptorFaild.getValue();
        assertThat(captorFaild).isNotEqualTo(userFaild);
    }

    @Test
    void updateUserWhenIdExistsShouldReturnSample() {
        User userSuccess = new User();
        userSuccess.setId(123L);
        userSuccess.setName("Test");
        userSuccess.setEmail("test@gmail.com");
        userSuccess.setRole("ROLE_USER");
        userSuccess.setPassword("test");

        when(userRepository.findById(userSuccess.getId())).thenReturn(Optional.of(userSuccess));
        when(userRepository.updateUser(userSuccess.getId(), userSuccess.getName(), userSuccess.getEmail(),
                userSuccess.getRole(), userSuccess.getPassword())).thenReturn(userSuccess);
        final User userUpdatedSuccess = userService.updateUser(userSuccess);

        assertThat(userSuccess).isEqualTo(userUpdatedSuccess);
    }

    @Test
    void deleteUserByIdWhenExistsShouldReturnVoid() {
        final Long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.of(new User()));
        userService.deleteByUserId(id);

        verify(userRepository).deleteById(id);
    }
}
