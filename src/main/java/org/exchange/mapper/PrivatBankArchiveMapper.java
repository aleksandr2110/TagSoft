package org.exchange.mapper;

import org.exchange.domain.PrivatBankArchiveEntity;
import org.exchange.domain.PrivatBankArchiveExchangeRate;
import org.exchange.domain.dto.PrivatBankArchiveDTO;
import org.exchange.domain.dto.PrivatBankArchiveExchangeRateDTO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrivatBankArchiveMapper {

    @Autowired
    private ModelMapper mapper;

    public PrivatBankArchiveExchangeRateDTO toPrivatBankExchangeRateDTO(PrivatBankArchiveExchangeRate entity) {
        return mapper.map(entity, PrivatBankArchiveExchangeRateDTO.class);
    }

    public PrivatBankArchiveExchangeRate toPrivatBankExchangeRateEntity(PrivatBankArchiveExchangeRateDTO dto) {
        return mapper.map(dto, PrivatBankArchiveExchangeRate.class);
    }

    public PrivatBankArchiveDTO toPrivatBankArchiveDTO(PrivatBankArchiveEntity entity) {
        return mapper.map(entity, PrivatBankArchiveDTO.class);
    }

    public PrivatBankArchiveEntity toPrivatBankArchive(PrivatBankArchiveDTO dto) {
        return mapper.map(dto, PrivatBankArchiveEntity.class);
    }
}
