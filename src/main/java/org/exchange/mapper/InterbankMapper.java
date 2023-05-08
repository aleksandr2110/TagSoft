package org.exchange.mapper;

import org.exchange.domain.InterbankExchangeRate;
import org.exchange.domain.dto.InterbankExchangeRateDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InterbankMapper {

    @Autowired
    private ModelMapper mapper;

    public InterbankExchangeRateDTO interbankExchangeRateDTO(InterbankExchangeRate entity) {
        return mapper.map(entity, InterbankExchangeRateDTO.class);
    }
    public InterbankExchangeRate toMinfinInterbankExchangeRate(InterbankExchangeRateDTO dto) {
        return mapper.map(dto, InterbankExchangeRate.class);
    }
}
