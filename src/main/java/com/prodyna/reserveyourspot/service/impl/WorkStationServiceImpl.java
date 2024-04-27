package com.prodyna.reserveyourspot.service.impl;

import com.prodyna.reserveyourspot.exception.ErrorCode;
import com.prodyna.reserveyourspot.exception.RYSValidationException;
import com.prodyna.reserveyourspot.model.WorkStation;
import com.prodyna.reserveyourspot.repository.OfficeRoomRepository;
import com.prodyna.reserveyourspot.repository.WorkStationRepository;
import com.prodyna.reserveyourspot.service.CommonService;
import com.prodyna.reserveyourspot.service.WorkStationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.prodyna.reserveyourspot.commons.Constants;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WorkStationServiceImpl implements WorkStationService {

    final private OfficeRoomRepository officeRoomRepository;
    final private WorkStationRepository workStationRepository;
    final private CommonService commonService;

    @Override
    public List<WorkStation> getAll() {
        return workStationRepository.getAll();
    }

    @Override
    public WorkStation getWorkStationById(Long workStationId) {
        commonService.validEntryExists(workStationRepository, workStationId);
        return workStationRepository.getWorkStationById(workStationId);
    }

    @Override
    public List<WorkStation> getWorkStationByOfficeRoom(Long officeRoomId) {
        commonService.validEntryExists(officeRoomRepository, officeRoomId);
        return workStationRepository.getWorkStationByOfficeRoom(officeRoomId);
    }

    @Override
    public WorkStation createWorkStationByOfficeRoom(Long officeRoomId, String description, String equipment) {
        commonService.validEntryExists(officeRoomRepository, officeRoomId);
        return workStationRepository.createWorkStationByOfficeRoom(officeRoomId, description, generateMaxUniqueCodeNumber(), equipment);
    }

    @Override
    public WorkStation updateWorkStation(WorkStation workStation) {
        final Long id = workStation.getId();
        final Optional<WorkStation> workStationOptional = workStationRepository.findById(id);
        if (workStationOptional.isPresent()) {
            workStation = workStationRepository.updateWorkStation(workStation.getId(), workStation.getDescription(), workStation.getEquipment());
        } else {
            throw new RYSValidationException(ErrorCode.RESOURCE_NOT_FOUND, String.format("WorkStation with id %s do not exist!", id));
        }
        return workStation;
    }


    @Override
    public void deleteByWorkStationId(Long workStationId) {
        commonService.validEntryExists(workStationRepository, workStationId);
        workStationRepository.delete(workStationId);
    }

    @Override
    public WorkStation getWorkStationByUniqueCode(String workStationCode) {
        return workStationRepository.getWorkStationByUniqueCode(workStationCode).stream().findFirst().orElse(null);
    }

    private String generateMaxUniqueCodeNumber() {
        return Constants.WORK_STATION_UNIQUE_CODE_PREF + commonService.getMaxNumberByType(Constants.WORK_STATION_TYPE);
    }
}
