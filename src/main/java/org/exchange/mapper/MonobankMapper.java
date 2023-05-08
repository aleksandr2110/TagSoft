package org.exchange.mapper;

import org.exchange.domain.MonoBankExchangeRate;
import org.exchange.domain.dto.MonobankExchangeRateDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MonobankMapper {

    @Autowired
    private ModelMapper mapper;

    public MonobankExchangeRateDTO toMonoExchangeRateDTO(MonoBankExchangeRate entity) {
        return mapper.map(entity, MonobankExchangeRateDTO.class);
    }

    public MonoBankExchangeRate toMonoExchangeRateEntity(MonobankExchangeRateDTO dto) {
        return mapper.map(dto, MonoBankExchangeRate.class);
    }
}
