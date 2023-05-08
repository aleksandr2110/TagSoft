package org.exchange.mapper;

import org.exchange.domain.InterbankArchiveExchangeRate;
import org.exchange.domain.dto.InterbankArchiveExchRateDTO;
import org.exchange.domain.dto.InterbankArchiveExchRateFormattedDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InterbankArchiveMapper {

    @Autowired
    private ModelMapper mapper;

    public InterbankArchiveExchRateFormattedDTO toInterbankArchiveExchangeRateDTO(InterbankArchiveExchangeRate entity) {
        return mapper.map(entity, InterbankArchiveExchRateFormattedDTO.class);
    }
    public InterbankArchiveExchangeRate toInterbankArchiveExchangeRate(InterbankArchiveExchRateFormattedDTO dto) {
        return mapper.map(dto, InterbankArchiveExchangeRate.class);
    }
}
