package com.prodyna.reserveyourspot.service;

import com.prodyna.reserveyourspot.model.OfficeSpace;

import java.util.List;

public interface OfficeSpaceService {

    /**
     * Get all OfficeSpace objects from database with references to OfficeRoom.
     *
     * @return {@link List<OfficeSpace>} object.
     */
    List<OfficeSpace> getAll();

    /**
     * Get OfficeSpace objects from database by id.
     *
     *@param officeSpaceId officeSpace id.
     * @return {@link OfficeSpace} object.
     */
    OfficeSpace getOfficeSpaceById(Long officeSpaceId);

    /**
     * Create OfficeSpace object.
     *
     * @param officeSpace officeSpace body request object.
     * @return {@link OfficeSpace} object.
     */
    OfficeSpace createOfficeSpace(OfficeSpace officeSpace);

    /**
     * Update OfficeSpace object.
     *
     * @param officeSpaceUpdate officeSpace body request object.
     * @return {@link OfficeSpace} object.
     */
    OfficeSpace updateOfficeSpace(OfficeSpace officeSpaceUpdate);

    /**
     * Delete OfficeSpace object.
     *
     * @param officeSpaceId office space technical id.
     *
     */
    void deleteOfficeSpaceById(Long officeSpaceId);
}
