package com.prodyna.reserveyourspot.service.impl;

import com.prodyna.reserveyourspot.dto.ReservationDTO;
import com.prodyna.reserveyourspot.model.*;
import com.prodyna.reserveyourspot.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private WorkStationRepository workStationRepository;
    @Mock
    OfficeRoomRepository officeRoomRepository;
    @Mock
    OfficeSpaceRepository officeSpaceRepository;
    @InjectMocks
    private ReservationServiceImpl reservationService;
    @InjectMocks
    private WorkStationServiceImpl workStationService;
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private CommonServiceImpl commonService;
    @InjectMocks
    private OfficeRoomServiceImpl officeRoomService;
    @InjectMocks
    private OfficeSpaceServiceImpl officeSpaceService;


    @BeforeEach
    void setup() {
        reservationService = new ReservationServiceImpl(reservationRepository, workStationService, userService,
                commonService, officeRoomService, officeSpaceService);
    }

    @Test
    void getAllReservationShouldReturnAll() {
        reservationService.getAll();
        verify(reservationRepository).getAll();
    }

    @Test()
    void createReservationWhenUserNameAndWorkStationCodeExistsShouldReturnSample() {
        Reservation reservation = new Reservation();
        reservation.setUserName("test");
        reservation.setUserEmail("test@gmail.com");
        reservation.setDate(LocalDate.now());
        reservation.setWorkStationCode("PD12351");
        reservation.setOfficeRoomId(1L);
        reservation.setOfficeSpaceId(1L);

        when(reservationRepository.createByWorkStation(anyString(), anyString(), any(LocalDate.class)))
                .thenReturn(reservation);
        when(userRepository.getUserByEmail(anyString()))
                .thenReturn(Collections.singletonList(new User()));
        when(workStationRepository.getWorkStationByUniqueCode(anyString()))
                .thenReturn(Collections.singletonList(new WorkStation()));


        when(officeRoomRepository.getOfficeRoomById(anyLong()))
                .thenReturn(new OfficeRoom());

        when(officeSpaceRepository.getOfficeSpaceById(anyLong()))
                .thenReturn(new OfficeSpace());

        final ReservationDTO reservationDTOCreated = reservationService.createByWorkStation(reservation.getWorkStationCode(),
                reservation.getUserEmail(), reservation.getDate());

        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(reservation.getId());
        reservationDTO.setUserEmail(reservation.getUserEmail());
        reservationDTO.setUserName(reservation.getUserName());
        reservationDTO.setDate(reservation.getDate());
        reservationDTO.setWorkStation(new WorkStation());
        reservationDTO.setOfficeRoom(new OfficeRoom());
        reservationDTO.setOfficeSpace(new OfficeSpace());

        assertThat(reservationDTO).isEqualTo(reservationDTOCreated);
    }

    @Test
    void deleteReservationByIdWhenExistsShouldReturnVoid() {
        final long id = 1L;
        reservationService.deleteByReservationId(id);
        verify(reservationRepository).deleteById(id);
    }
}
