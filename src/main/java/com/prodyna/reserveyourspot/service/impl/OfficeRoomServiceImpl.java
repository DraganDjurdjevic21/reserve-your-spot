package com.prodyna.reserveyourspot.service.impl;

import com.prodyna.reserveyourspot.commons.Constants;
import com.prodyna.reserveyourspot.exception.ErrorCode;
import com.prodyna.reserveyourspot.exception.RYSValidationException;
import com.prodyna.reserveyourspot.model.OfficeRoom;
import com.prodyna.reserveyourspot.repository.OfficeRoomRepository;
import com.prodyna.reserveyourspot.repository.OfficeSpaceRepository;
import com.prodyna.reserveyourspot.service.CommonService;
import com.prodyna.reserveyourspot.service.OfficeRoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OfficeRoomServiceImpl implements OfficeRoomService {

    final private OfficeSpaceRepository officeSpaceRepository;
    final private OfficeRoomRepository officeRoomRepository;
    final private CommonService commonService;

    @Override
    public List<OfficeRoom> getAll() {
        return officeRoomRepository.getAll();
    }

    @Override
    public OfficeRoom getOfficeRoomById(Long officeRoomId) {
        commonService.validEntryExists(officeRoomRepository, officeRoomId);
        return officeRoomRepository.getOfficeRoomById(officeRoomId);
    }

    @Override
    public List<OfficeRoom> getOfficeRoomsByOfficeSpace(Long officeSpaceId) {
        commonService.validEntryExists(officeSpaceRepository, officeSpaceId);
        return officeRoomRepository.getOfficeRoomsByOfficeSpaceId(officeSpaceId);
    }

    @Override
    public OfficeRoom createByOfficeSpaceId(Long officeSpaceId, String name) {
        commonService.validEntryExists(officeSpaceRepository, officeSpaceId);
        return officeRoomRepository.createByOfficeSpaceId(officeSpaceId, name,
                generateMaxUniqueCodeNumber());
    }

    @Override
    public OfficeRoom updateOfficeRoom(OfficeRoom officeRoom) {
        final Long id = officeRoom.getId();
        final Optional<OfficeRoom> officeRoomOptional = officeRoomRepository.findById(id);
        if (officeRoomOptional.isPresent()) {
            officeRoom = officeRoomRepository.updateOfficeRoomNameById(id, officeRoom.getName());
        } else {
            throw new RYSValidationException(ErrorCode.RESOURCE_NOT_FOUND, String.format("No Office Room found with id %s", id));
        }
        return officeRoom;
    }

    @Override
    public void deleteOfficeRoomById(Long officeRoomId) {
        commonService.validEntryExists(officeRoomRepository, officeRoomId);
        officeRoomRepository.delete(officeRoomId);
    }

    private Long generateMaxUniqueCodeNumber() {
        return commonService.getMaxNumberByType(Constants.OFFICE_ROOM_TYPE);
    }
}
