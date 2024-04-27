package com.prodyna.reserveyourspot.service;

import com.prodyna.reserveyourspot.model.WorkStation;

import java.util.List;

public interface WorkStationService {
    /**
     * Get all WorkStation objects from database with references to Reservation.
     *
     * @return {@link List < WorkStation >} object.
     */
    List<WorkStation> getAll();

    /**
     * Get WorkStation object by id.
     *
     * @param workStationId workStation technical id.
     * @return {@link WorkStation} object.
     */
     WorkStation getWorkStationById(Long workStationId);

    /**
     * Get all WorkStation objects from office room .
     *
     * @param officeRoomId officeRoom id.
     * @return {@link List < WorkStation >} object.
     */
    List<WorkStation> getWorkStationByOfficeRoom(Long officeRoomId);

    /**
     * Create WorkStation object.
     *
     * @param officeRoomId officeRoom id.
     * @param description  description body request param.
     * @param equipment    equipment body request param.
     * @return {@link WorkStation} object.
     */
    WorkStation createWorkStationByOfficeRoom(Long officeRoomId, String description, String equipment);

    /**
     * Update WorkStation object.
     *
     * @param workStation WorkStation body request object..
     * @return {@link WorkStation} object.
     */
    WorkStation updateWorkStation(WorkStation workStation);

    /**
     * Delete WorkStation object.
     *
     * @param workStationId workStation technical id.
     *
     */
    void deleteByWorkStationId(Long workStationId);

    /**
     * Get WorkStation object.
     *
     * @param workStationCode unique code.
     * @return {@link WorkStation} object.
     */
    WorkStation getWorkStationByUniqueCode(String workStationCode);
}
