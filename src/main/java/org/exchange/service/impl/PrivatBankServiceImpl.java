package org.exchange.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.exchange.client.PrivatBankClient;
import org.exchange.domain.PrivatBankExchangeRate;
import org.exchange.domain.dto.PrivatBankExchangeRateDTO;
import org.exchange.mapper.PrivatBankMapper;
import org.exchange.repository.PrivatbankRepository;
import org.exchange.service.ExchangeRateResourceService;
import org.exchange.service.PrivatBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrivatBankServiceImpl implements ExchangeRateResourceService, PrivatBankService {

    @Autowired
    private PrivatBankMapper mapper;
    @Autowired
    private PrivatBankClient privatbankClient;
    @Autowired
    private PrivatbankRepository privatbankRepository;
    @Override
    public void getExchangeRate() {
        List<PrivatBankExchangeRateDTO> privatBankExchangeRateDtoList = getExchangeRateToday();
        log.info("The size of exchange rate objects received from Privat Bank: {}", privatBankExchangeRateDtoList.size());
        List<PrivatBankExchangeRate> entities = convertToEntity(privatBankExchangeRateDtoList);
        privatbankRepository.saveAll(entities);
    }

    @Override
    public List<PrivatBankExchangeRateDTO> getAllExchangeRate() {
        List<PrivatBankExchangeRate> exchangeRateEntities = privatbankRepository.findAll();
        List<PrivatBankExchangeRateDTO> exchangeRatesDTOs = convertToDTO(exchangeRateEntities);
        return exchangeRatesDTOs;
    }
    private List<PrivatBankExchangeRate> convertToEntity(List<PrivatBankExchangeRateDTO> privatBankList) {
        List<PrivatBankExchangeRate> privatBankListEntities = privatBankList.stream()
                .map(excRateDTO -> mapper.toPrivatBankEntity(excRateDTO))
                .collect(Collectors.toList());
        return privatBankListEntities;
    }

    private List<PrivatBankExchangeRateDTO> convertToDTO(List<PrivatBankExchangeRate> privatBankList) {
        List<PrivatBankExchangeRateDTO> privatBankListDTOs = privatBankList.stream()
                .map(excRate -> mapper.toPrivatBankDTO(excRate))
                .collect(Collectors.toList());
        return privatBankListDTOs;
    }

    private List<PrivatBankExchangeRateDTO> getExchangeRateToday() {
        PrivatBankExchangeRateDTO[] exchangeRates = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            exchangeRates = mapper.readValue(privatbankClient.getExchangeRate(),  PrivatBankExchangeRateDTO[].class );
        } catch(IOException ex) {
            log.warn("Privat Bank archive server doesn't work");
            ex.printStackTrace();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return Arrays.asList(exchangeRates);
    }
}
