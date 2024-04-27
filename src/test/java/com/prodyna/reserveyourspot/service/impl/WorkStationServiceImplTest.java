package com.prodyna.reserveyourspot.service.impl;

import com.prodyna.reserveyourspot.model.NumberAutoGenerator;
import com.prodyna.reserveyourspot.model.OfficeRoom;
import com.prodyna.reserveyourspot.model.WorkStation;
import com.prodyna.reserveyourspot.repository.NumberAutoGeneratorRepository;
import com.prodyna.reserveyourspot.repository.OfficeRoomRepository;
import com.prodyna.reserveyourspot.repository.WorkStationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkStationServiceImplTest {

    @Mock
    private WorkStationRepository workStationRepository;
    @Mock
    private OfficeRoomRepository officeRoomRepository;
    @Mock
    private NumberAutoGeneratorRepository autoGenerateNumberRepository;
    @InjectMocks
    private WorkStationServiceImpl workStationService;
    @InjectMocks
    private CommonServiceImpl commonService;

    @BeforeEach
    void setup() {
        workStationService = new WorkStationServiceImpl(officeRoomRepository, workStationRepository, commonService);
    }

    @Test
    void getAllWorkStationShouldReturnAll() {
        workStationService.getAll();
        verify(workStationRepository).getAll();
    }

    @Test()
    void createWorkStationWhenOfficeRoomIdExistsShouldReturnSample() {
        WorkStation workStation = new WorkStation();
        workStation.setDescription("Mac Work Station");
        workStation.setEquipment("table, monitors, docking station");

        final long officeRoomId = 1L;

        when(workStationRepository.createWorkStationByOfficeRoom(anyLong(), anyString(), anyString(), anyString()))
                .thenReturn(workStation);

        when(autoGenerateNumberRepository.getMaxNumberByType(anyString()))
                .thenReturn(1L);
        when(officeRoomRepository.findById(anyLong()))
                .thenReturn(Optional.of(new OfficeRoom()));

        final NumberAutoGenerator numberAutoGenerator = new NumberAutoGenerator();
        numberAutoGenerator.setNumber(1001L);

        when(autoGenerateNumberRepository.update(anyString(), anyLong()))
                .thenReturn(numberAutoGenerator);
        final WorkStation workStationCreated = workStationService.createWorkStationByOfficeRoom(officeRoomId,
                workStation.getDescription(), workStation.getEquipment());

        assertThat(workStation).isEqualTo(workStationCreated);
    }

    @Test
    void updateWorkStationWhenIdExistsShouldReturnSample() {
        WorkStation workStation = new WorkStation();
        workStation.setDescription("Mac Work Station");
        workStation.setEquipment("table, monitors, docking station");
        workStation.setId(1L);

        when(workStationRepository.findById(workStation.getId()))
                .thenReturn(Optional.of(workStation));
        when(workStationRepository.updateWorkStation(workStation.getId(), workStation.getDescription(), workStation.getEquipment()))
                .thenReturn(workStation);

        final WorkStation workStationUpdate = workStationService.updateWorkStation(workStation);

        assertThat(workStationUpdate).isEqualTo(workStation);
    }

    @Test
    void deleteWorkStationByIdWhenExistsShouldReturnVoid() {
        final long id = 1L;

        when(workStationRepository.findById(id)).thenReturn(Optional.of(new WorkStation()));
        workStationService.deleteByWorkStationId(id);

        verify(workStationRepository).delete(id);
    }
}
