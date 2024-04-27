package com.prodyna.reserveyourspot.service;

import com.prodyna.reserveyourspot.model.OfficeRoom;

import java.util.List;


public interface OfficeRoomService {

    /**
     * Get all OfficeRoom objects from database with references to WorkStation.
     *
     * @return {@link List<OfficeRoom>} object.
     */
    List<OfficeRoom> getAll();

    /**
     * Get OfficeRoom objects from database by id.
     *
     *@param officeRoomId OfficeRoom id.
     * @return {@link OfficeRoom} object.
     */
    OfficeRoom getOfficeRoomById(Long officeRoomId);

    /**
     * Get all OfficeRoom objects from office space .
     *
     * @param officeSpaceId office space id.
     * @return {@link List < OfficeRoom >} object.
     */
    List<OfficeRoom> getOfficeRoomsByOfficeSpace(Long officeSpaceId);

    /**
     * Create OfficeRoom object releted to OfficeSpace.
     *
     * @param officeSpaceId officeSpace technical id.
     * @param name   officeRoom name.
     *
     * @return {@link OfficeRoom} object.
     */
    OfficeRoom createByOfficeSpaceId(Long officeSpaceId, String name);

    /**
     * Update name to OfficeRoom object.
     *
     * @param officeRoom officeRoom request body object.
     * @return {@link OfficeRoom} object.
     */
    OfficeRoom updateOfficeRoom(OfficeRoom officeRoom);

    /**
     * Delete OfficeRoom object.
     *
     * @param officeRoomId officeRoom technical id.
     *
     */
    void deleteOfficeRoomById(Long officeRoomId);
}
