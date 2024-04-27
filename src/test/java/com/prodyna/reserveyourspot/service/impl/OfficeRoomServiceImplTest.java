package com.prodyna.reserveyourspot.service.impl;

import com.prodyna.reserveyourspot.model.NumberAutoGenerator;
import com.prodyna.reserveyourspot.model.OfficeRoom;
import com.prodyna.reserveyourspot.model.OfficeSpace;
import com.prodyna.reserveyourspot.repository.NumberAutoGeneratorRepository;
import com.prodyna.reserveyourspot.repository.OfficeRoomRepository;
import com.prodyna.reserveyourspot.repository.OfficeSpaceRepository;
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
class OfficeRoomServiceImplTest {

    @Mock
    private OfficeRoomRepository officeRoomRepository;
    @Mock
    private OfficeSpaceRepository officeSpaceRepository;
    @Mock
    private NumberAutoGeneratorRepository autoGenerateNumberRepository;
    @InjectMocks
    private OfficeRoomServiceImpl officeRoomService;
    @InjectMocks
    private CommonServiceImpl commonService;

    @BeforeEach
    void setup() {
        officeRoomService = new OfficeRoomServiceImpl(officeSpaceRepository, officeRoomRepository, commonService);
    }

    @Test
    void getAllOfficeRoomShouldReturnAll() {
        officeRoomService.getAll();
        verify(officeRoomRepository).getAll();
    }

    @Test()
    void createOfficeRoomWhenOfficeSpaceIdExistsShouldReturnSample() {
        OfficeRoom officeRoom = new OfficeRoom();
        officeRoom.setName("Prodyna");

        final long officeSpaceId = 1L;

        when(officeRoomRepository.createByOfficeSpaceId(anyLong(), anyString(), anyLong()))
                .thenReturn(officeRoom);
        when(autoGenerateNumberRepository.getMaxNumberByType(anyString()))
                .thenReturn(1L);
        when(officeSpaceRepository.findById(anyLong()))
                .thenReturn(Optional.of(new OfficeSpace()));

        final NumberAutoGenerator numberAutoGenerator = new NumberAutoGenerator();
        numberAutoGenerator.setNumber(1001L);

        when(autoGenerateNumberRepository.update(anyString(), anyLong()))
                .thenReturn(numberAutoGenerator);

        final OfficeRoom officeCreated = officeRoomService.createByOfficeSpaceId(officeSpaceId, officeRoom.getName());

        assertThat(officeRoom).isEqualTo(officeCreated);
    }

    @Test
    void updateOfficeRoomWhenIdExistsShouldReturnSample() {
        OfficeRoom officeRoom = new OfficeRoom();
        officeRoom.setName("Prodyna");
        officeRoom.setId(1L);

        when(officeRoomRepository.updateOfficeRoomNameById(officeRoom.getId(), officeRoom.getName()))
                .thenReturn(officeRoom);
        when(officeRoomRepository.findById(anyLong()))
                .thenReturn(Optional.of(new OfficeRoom()));

        final OfficeRoom officeUpdated = officeRoomService.updateOfficeRoom(officeRoom);

        assertThat(officeRoom).isEqualTo(officeUpdated);
    }

    @Test
    void deleteOfficeRoomByIdWhenExistsShouldReturnVoid() {
        final long id = 1L;
        final OfficeRoom officeRoom = new OfficeRoom();
        officeRoom.setId(id);
        when(officeRoomRepository.findById(id)).thenReturn(Optional.of(officeRoom));

        officeRoomService.deleteOfficeRoomById(id);
        verify(officeRoomRepository).delete(id);
    }
}
