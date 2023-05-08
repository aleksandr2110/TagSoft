package org.exchange.mapper;


import org.exchange.domain.PrivatBankExchangeRate;
import org.exchange.domain.dto.PrivatBankExchangeRateDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrivatBankMapper {

    @Autowired
    private ModelMapper mapper;

    public PrivatBankExchangeRateDTO toPrivatBankDTO(PrivatBankExchangeRate entity) {
        return mapper.map(entity, PrivatBankExchangeRateDTO.class);
    }
    public PrivatBankExchangeRate toPrivatBankEntity(PrivatBankExchangeRateDTO privatBankDTO) {
        return mapper.map(privatBankDTO, PrivatBankExchangeRate.class);
    }
}
