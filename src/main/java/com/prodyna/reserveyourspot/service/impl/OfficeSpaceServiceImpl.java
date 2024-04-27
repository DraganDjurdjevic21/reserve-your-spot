package com.prodyna.reserveyourspot.service.impl;

import com.prodyna.reserveyourspot.exception.ErrorCode;
import com.prodyna.reserveyourspot.exception.RYSValidationException;
import com.prodyna.reserveyourspot.model.OfficeSpace;
import com.prodyna.reserveyourspot.repository.OfficeSpaceRepository;
import com.prodyna.reserveyourspot.service.CommonService;
import com.prodyna.reserveyourspot.service.OfficeSpaceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The implementation of office space service.
 *
 * @author Dragan Djurdjevic, PRODYNA SE
 * @see OfficeSpaceService
 */
@AllArgsConstructor
@Service
public class OfficeSpaceServiceImpl implements OfficeSpaceService {

    final private OfficeSpaceRepository officeSpaceRepository;
    final private CommonService commonService;

    @Override
    public List<OfficeSpace> getAll() {
        return officeSpaceRepository.getAll();
    }

    @Override
    public OfficeSpace getOfficeSpaceById(Long officeSpaceId) {
        commonService.validEntryExists(officeSpaceRepository, officeSpaceId);
        return officeSpaceRepository.getOfficeSpaceById(officeSpaceId);
    }

    @Override
    public OfficeSpace createOfficeSpace(OfficeSpace officeSpace) {
        if (Optional.ofNullable(officeSpaceRepository.getOfficeSpaceByName(officeSpace.getName())).isPresent()) {
            throw new RYSValidationException(ErrorCode.INVALID_INPUT, String.format("Office space with name %s already exist!", officeSpace.getName()));
        }
        return officeSpaceRepository.save(officeSpace);
    }

    @Override
    public OfficeSpace updateOfficeSpace(OfficeSpace officeSpaceUpdate) {
        Long id = officeSpaceUpdate.getId();
        final Optional<OfficeSpace> officeSpaceOptional = officeSpaceRepository.findById(id);
        if (officeSpaceOptional.isPresent()) {
            officeSpaceUpdate = officeSpaceRepository.updateOfficeSpace(id, officeSpaceUpdate.getName(), officeSpaceUpdate.getDescription());
        } else {
            throw new RYSValidationException(ErrorCode.RESOURCE_NOT_FOUND, String.format("Office space with id %s do not exist!", id));
        }
        return officeSpaceUpdate;
    }

    @Override
    public void deleteOfficeSpaceById(Long officeSpaceId) {
        commonService.validEntryExists(officeSpaceRepository, officeSpaceId);
        officeSpaceRepository.delete(officeSpaceId);
    }
}
