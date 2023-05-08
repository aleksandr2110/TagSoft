package org.exchange.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.exchange.client.MinfinClient;
import org.exchange.domain.InterbankExchangeRate;
import org.exchange.domain.dto.InterbankExchangeRateDTO;
import org.exchange.mapper.InterbankMapper;
import org.exchange.repository.MinfinRepository;
import org.exchange.service.ExchangeRateResourceService;
import org.exchange.service.InterbankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InterbankServiceImpl implements ExchangeRateResourceService, InterbankService {

    @Autowired
    private MinfinRepository minfinRepository;
    @Autowired
    private MinfinClient minfinClient;
    @Autowired
    private InterbankMapper mapper;
    @Override
    public void getExchangeRate() {
        List<InterbankExchangeRateDTO> interbankExcRateList = getExchangeRateToday();
        log.info("The size of exchange rate objects received from minfin : {}", interbankExcRateList.size());
        List<InterbankExchangeRate> entities = convertToEntity(interbankExcRateList);
        minfinRepository.saveAll(entities);
    }

    @Override
    public List<InterbankExchangeRateDTO> getAllExchangeRate() {
        List<InterbankExchangeRate> exchangeRates = minfinRepository.findAll();
        List<InterbankExchangeRateDTO> exchangeRatesDTOs = convertToDto(exchangeRates);
        return exchangeRatesDTOs;
    }

    private List<InterbankExchangeRateDTO> getExchangeRateToday() {
        InterbankExchangeRateDTO[] exchangeRates = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            exchangeRates = mapper.readValue(minfinClient.getCurrency(),  InterbankExchangeRateDTO[].class );
        } catch(IOException ex) {
            log.warn("Minfin (Interbank) server doesn't work");
            ex.printStackTrace();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        List<InterbankExchangeRateDTO> interbankExcRateDTO = Arrays.asList(exchangeRates);
        return interbankExcRateDTO;
    }
     private List<InterbankExchangeRate> convertToEntity(List<InterbankExchangeRateDTO> minfinList) {
        List<InterbankExchangeRate> monobankListEntities = minfinList.stream()
                .map(excRateDTO -> mapper.toMinfinInterbankExchangeRate(excRateDTO))
                .collect(Collectors.toList());
        return monobankListEntities;
    }

    private List<InterbankExchangeRateDTO> convertToDto(List<InterbankExchangeRate> minfinList) {
        List<InterbankExchangeRateDTO> interbankExchangeRateDTOList = minfinList.stream()
                .map(excRate -> mapper.interbankExchangeRateDTO(excRate))
                .collect(Collectors.toList());
        return interbankExchangeRateDTOList;
    }
}
