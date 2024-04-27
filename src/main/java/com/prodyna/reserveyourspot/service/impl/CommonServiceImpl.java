package com.prodyna.reserveyourspot.service.impl;

import com.prodyna.reserveyourspot.commons.Constants;
import com.prodyna.reserveyourspot.exception.ErrorCode;
import com.prodyna.reserveyourspot.exception.RYSValidationException;
import com.prodyna.reserveyourspot.model.NumberAutoGenerator;
import com.prodyna.reserveyourspot.repository.*;
import com.prodyna.reserveyourspot.service.CommonService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CommonServiceImpl implements CommonService {

    final private NumberAutoGeneratorRepository autoGenerateNumberRepository;

    @Override
    public Long getMaxNumberByType(String type) {
        final Long maxNumber = autoGenerateNumberRepository.getMaxNumberByType(type);
        if (Optional.ofNullable(autoGenerateNumberRepository.getMaxNumberByType(type)).isPresent()) {
            final long maxNumberIncrease = maxNumber + 1;
            final NumberAutoGenerator update = autoGenerateNumberRepository.update(type, maxNumberIncrease);
            return update.getNumber();
        } else {
            NumberAutoGenerator autoGenerateNumber = new NumberAutoGenerator();
            autoGenerateNumber.setNumber(Constants.UNIQUE_CODE_PREF);
            autoGenerateNumber.setType(type);
            autoGenerateNumberRepository.save(autoGenerateNumber);
            return Constants.UNIQUE_CODE_PREF;
        }
    }

    @SneakyThrows
    @Override
    public <R extends Neo4jRepository> void validEntryExists(R repo, Long recourceId) {
        repo.findById(recourceId).orElseThrow(() -> new RYSValidationException(ErrorCode.RESOURCE_NOT_FOUND,
                String.format("Entry with id %s not found", recourceId)));
    }
}
