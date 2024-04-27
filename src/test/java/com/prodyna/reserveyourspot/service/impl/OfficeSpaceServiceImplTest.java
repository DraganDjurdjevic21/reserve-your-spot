package com.prodyna.reserveyourspot.service.impl;

import com.prodyna.reserveyourspot.model.OfficeSpace;
import com.prodyna.reserveyourspot.repository.OfficeSpaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OfficeSpaceServiceImplTest {

    @Mock
    private OfficeSpaceRepository officeSpaceRepository;
    @InjectMocks
    private OfficeSpaceServiceImpl officeSpaceService;
    @InjectMocks
    private CommonServiceImpl commonService;

    @BeforeEach
    void setup() {
        officeSpaceService = new OfficeSpaceServiceImpl(officeSpaceRepository, commonService);
    }

    @Test
    void getAllOfficeSpaceShouldReturnAll() {
        officeSpaceService.getAll();
        verify(officeSpaceRepository).getAll();
    }

    @Test()
    void createOfficeSpaceWhenNameNotExistsShouldReturnSample() {
        OfficeSpace officeSpace = new OfficeSpace();
        officeSpace.setName("Prodyna");
        officeSpace.setDescription("PRODYNA Business");

        officeSpaceService.createOfficeSpace(officeSpace);

        ArgumentCaptor<OfficeSpace> userArgumentCaptor = ArgumentCaptor.forClass(OfficeSpace.class);
        verify(officeSpaceRepository).save(userArgumentCaptor.capture());
        final OfficeSpace captorOfficeSpace = userArgumentCaptor.getValue();
        assertThat(captorOfficeSpace).isEqualTo(officeSpace);
    }

    @Test
    void updateOfficeSpaceWhenIdExistsShouldReturnSample() {
        OfficeSpace officeSpace = new OfficeSpace();
        officeSpace.setName("Prodyna");
        officeSpace.setDescription("PRODYNA Business");
        officeSpace.setId(1L);

        when(officeSpaceRepository.findById(officeSpace.getId())).thenReturn(Optional.of(officeSpace));
        when(officeSpaceRepository.updateOfficeSpace(officeSpace.getId(), officeSpace.getName(), officeSpace.getDescription()))
                .thenReturn(officeSpace);
        final OfficeSpace officeSpaceUpdated = officeSpaceService.updateOfficeSpace(officeSpace);

        assertThat(officeSpaceUpdated).isEqualTo(officeSpace);
    }

    @Test
    void deleteOfficeSpaceByIdWhenExistsShouldReturnVoid() {
        final long id = 1L;

        when(officeSpaceRepository.findById(id)).thenReturn(Optional.of(new OfficeSpace()));
        officeSpaceService.deleteOfficeSpaceById(id);

        verify(officeSpaceRepository).delete(id);
    }
}
