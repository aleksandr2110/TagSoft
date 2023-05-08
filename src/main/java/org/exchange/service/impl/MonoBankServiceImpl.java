package org.exchange.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.exchange.client.MonobankClient;
import org.exchange.domain.MonoBankExchangeRate;
import org.exchange.domain.dto.MonobankExchangeRateDTO;
import org.exchange.mapper.MonobankMapper;
import org.exchange.repository.MonoBankRepository;
import org.exchange.service.ExchangeRateResourceService;
import org.exchange.service.MonoBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MonoBankServiceImpl implements ExchangeRateResourceService, MonoBankService {

    @Autowired
    private MonobankMapper mapper;
    @Autowired
    private MonobankClient monobankClient;
    @Autowired
    private MonoBankRepository monoBankRepository;
    @Override
    public void getExchangeRate() {
        List<MonobankExchangeRateDTO> monobankExchangeRateDtoList = getExchangeRateToday();
        log.info("The size of exchange rate objects received from Monobank: {}", monobankExchangeRateDtoList.size());
        List<MonoBankExchangeRate> entities = convertToEntity(monobankExchangeRateDtoList);
        monoBankRepository.saveAll(entities);
    }

    @Override
    public List<MonobankExchangeRateDTO> getAllExchangeRate() {
        List<MonoBankExchangeRate> exchangeRates =  monoBankRepository.findAll();
        List<MonobankExchangeRateDTO> exchangeRatesDTOs = convertToDTO(exchangeRates);
        return exchangeRatesDTOs;
    }

    private List<MonoBankExchangeRate> convertToEntity(List<MonobankExchangeRateDTO> monobankList) {
        List<MonoBankExchangeRate> monobankListEntities = monobankList.stream()
                .map(excRateDTO -> mapper.toMonoExchangeRateEntity(excRateDTO))
                .collect(Collectors.toList());
        return monobankListEntities;
    }

    private List<MonobankExchangeRateDTO> convertToDTO(List<MonoBankExchangeRate> monoBankList) {
        List<MonobankExchangeRateDTO> monoBankListDTOs = monoBankList.stream()
                .map(excRate -> mapper.toMonoExchangeRateDTO(excRate))
                .collect(Collectors.toList());
        return monoBankListDTOs;
    }

    private List<MonobankExchangeRateDTO> getExchangeRateToday() {
        MonobankExchangeRateDTO[] exchangeRates = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            exchangeRates = mapper.readValue(monobankClient.getCurrency(),  MonobankExchangeRateDTO[].class );
        } catch(IOException ex) {
            log.warn("MonoBank archive server doesn't work");
            ex.printStackTrace();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return Arrays.asList(exchangeRates);
    }
}
