package com.prodyna.reserveyourspot.service.impl;

import com.prodyna.reserveyourspot.dto.ReservationDTO;
import com.prodyna.reserveyourspot.exception.ErrorCode;
import com.prodyna.reserveyourspot.exception.RYSValidationException;
import com.prodyna.reserveyourspot.model.OfficeRoom;
import com.prodyna.reserveyourspot.model.OfficeSpace;
import com.prodyna.reserveyourspot.model.Reservation;
import com.prodyna.reserveyourspot.model.WorkStation;
import com.prodyna.reserveyourspot.repository.ReservationRepository;
import com.prodyna.reserveyourspot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ReservationServiceImpl implements ReservationService {


    private final ReservationRepository reservationRepository;
    private final WorkStationService workStationService;
    private final UserService userService;
    private final CommonService commonService;
    private final OfficeRoomService officeRoomService;
    private final OfficeSpaceService officeSpaceService;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  WorkStationService workStationService, @Lazy UserService userService, CommonService commonService,
                                  OfficeRoomService officeRoomService, OfficeSpaceService officeSpaceService) {
        this.reservationRepository = reservationRepository;
        this.workStationService = workStationService;
        this.userService = userService;
        this.commonService = commonService;
        this.officeRoomService = officeRoomService;
        this.officeSpaceService = officeSpaceService;
    }

    @Override
    public List<ReservationDTO> getAll() {
        return reservationRepository.getAll().stream().map(this::convertToReservationDTO).collect(Collectors.toList());
    }

    @Override
    public ReservationDTO createByWorkStation(String workStationCode, String userEmail, LocalDate date) {
        // validation
        doValidation(workStationCode, userEmail, date);
        final Reservation reservation = reservationRepository.createByWorkStation(workStationCode, userEmail, date);
        return this.convertToReservationDTO(reservation);
    }

    @Override
    public List<ReservationDTO> createByWorkStationInRange(String workStationCode, String userName, LocalDate dateFrom, LocalDate dateTo) {

        // validation
        doValidationInRange(workStationCode, userName, dateFrom, dateTo);

        List<Reservation> reservations = new ArrayList<>();
        for (LocalDate date = dateFrom; date.isBefore(dateTo) || date.isEqual(dateTo); date = date.plusDays(1)) {
            reservations.add(reservationRepository.createByWorkStation(workStationCode, userName, date));
        }
        return reservations.stream().map(this::convertToReservationDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteByReservationId(Long reservationId) {
        commonService.validEntryExists(reservationRepository, reservationId);
        reservationRepository.deleteById(reservationId);
    }

    /**
     * Check is reservation available for a given work station and date.
     *
     * @param workStationCode unique code of work station.
     * @param date            date to be check.
     */
    private void isWorkStationAvailableByDate(String workStationCode, LocalDate date) {
        if (!reservationRepository.isWorkStationAvailableForDate(workStationCode, date).isEmpty()) {
            throw new RYSValidationException(ErrorCode.ARGUMENT_NOT_VALID, "Reservation with workStationCode " + workStationCode + " is not available for date " + date.toString());
        }
    }

    /**
     * Do validation for a given dateFrom and dateTo of reservation.
     *
     * @param workStationCode unique code of work station.
     * @param userEmail       email of user.
     * @param date            date of reservation to be check.
     */
    private void doValidation(String workStationCode, String userEmail, LocalDate date) {

        validWorkStationCodeAndEmail(workStationCode, userEmail);

        Optional.ofNullable(date).orElseThrow(() -> new RYSValidationException(ErrorCode.ARGUMENT_NOT_VALID, "Property date must not be null or empty"));

        isWorkStationAvailableByDate(workStationCode, date);
    }

    /**
     * Do validation for a given dateFrom and dateTo of reservation.
     *
     * @param workStationCode unique code of work station.
     * @param userEmail       email of the user.
     * @param localDateFrom   start date of reservation to be check.
     * @param localDateTo     end date of reservation to be check.
     */
    private void doValidationInRange(String workStationCode, String userEmail, LocalDate localDateFrom, LocalDate localDateTo) {

        validWorkStationCodeAndEmail(workStationCode, userEmail);

        if (localDateFrom.isAfter(localDateTo)) {
            throw new IllegalArgumentException("Date from " + localDateFrom.toString() + " is after dateTo " + localDateTo.toString());
        }

        for (LocalDate date = localDateFrom; date.isBefore(localDateTo) || date.isEqual(localDateTo); date = date.plusDays(1)) {
            isWorkStationAvailableByDate(workStationCode, date);
        }
    }

    private void validWorkStationCodeAndEmail(String workStationCode, String userEmail) {
        Optional.ofNullable(userService.getUserByEmail(userEmail)).orElseThrow(() ->
                new RYSValidationException(ErrorCode.ARGUMENT_NOT_VALID, "User with user name " + userEmail + " do not exist!"));

        Optional.ofNullable(workStationService.getWorkStationByUniqueCode(workStationCode)).orElseThrow(() ->
                new RYSValidationException(ErrorCode.ARGUMENT_NOT_VALID, "Work station with workStationCode " + workStationCode + " do not exist!"));
    }

    @Override
    public ReservationDTO convertToReservationDTO(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(reservation.getId());
        reservationDTO.setUserEmail(reservation.getUserEmail());
        reservationDTO.setUserName(reservation.getUserName());
        reservationDTO.setDate(reservation.getDate());

        final WorkStation workStation = workStationService.getWorkStationByUniqueCode(reservation.getWorkStationCode());
        reservationDTO.setWorkStation(workStation);

        final OfficeRoom officeRoom = officeRoomService.getOfficeRoomById(reservation.getOfficeRoomId());
        reservationDTO.setOfficeRoom(officeRoom);

        final OfficeSpace officeSpace = officeSpaceService.getOfficeSpaceById(reservation.getOfficeSpaceId());
        reservationDTO.setOfficeSpace(officeSpace);

        return reservationDTO;
    }
}
