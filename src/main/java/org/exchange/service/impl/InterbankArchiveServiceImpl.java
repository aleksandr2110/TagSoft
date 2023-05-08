package org.exchange.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.exchange.client.MinfinClient;
import org.exchange.domain.InterbankArchiveExchangeRate;
import org.exchange.domain.dto.InterbankArchiveDTO;
import org.exchange.domain.dto.InterbankArchiveExchRateDTO;
import org.exchange.domain.dto.InterbankArchiveExchRateFormattedDTO;
import org.exchange.mapper.InterbankArchiveMapper;
import org.exchange.repository.MinfinArchiveRepository;
import org.exchange.service.InterbankArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InterbankArchiveServiceImpl implements InterbankArchiveService {

    @Autowired
    private MinfinClient minfinClient;
    @Autowired
    private InterbankArchiveMapper interbankArchiveMapper;
    @Autowired
    private MinfinArchiveRepository minfinArchiveRepository;

    @Transactional
    @Override
    public List<InterbankArchiveExchRateFormattedDTO> getArchiveByDate(String date) {
        InterbankArchiveExchRateDTO archiveExchRateDto = getExchangeRateByDate(date);
        List<InterbankArchiveExchRateFormattedDTO> interbankArchExchRateFormattedDTOList = toFormattedDto(archiveExchRateDto, date);
        log.info("The day of archive exchange rate {} with size of objects received from minfin (Interbank) {}",
                date, interbankArchExchRateFormattedDTOList.size());

        saveInterbankArchiveArchive(interbankArchExchRateFormattedDTOList);
        List<InterbankArchiveExchangeRate> interbankArchiveExcRateEntity = fetchExchangeRateByDate(date);
        return convertToListDto(interbankArchiveExcRateEntity);
    }
    private InterbankArchiveExchRateDTO getExchangeRateByDate(String date) {
        InterbankArchiveExchRateDTO archiveExchangeRate = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            String invertedDate = convertDate(date);
            archiveExchangeRate = mapper.readValue(minfinClient.getExchangeRateByDate(invertedDate),  InterbankArchiveExchRateDTO.class );
        } catch(IOException ex) {
            log.warn("Minfin archive server for {} date doesn't work", date);
            ex.printStackTrace();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return archiveExchangeRate;
    }

    private String convertDate(String date) {
        // 01.12.2021
        // https://api.minfin.com.ua/mb/e76f4fb5b5347d64ff0a2c9b02e9f787295e1ca2/2021-01-12/
        String day = date.substring(0, 2);
        String month = date.substring(3,5);
        String year = date.substring(6);
        String invertedDate = year + "-" + month + "-" + day;
        return invertedDate;
    }

    private List<InterbankArchiveExchRateFormattedDTO> toFormattedDto(
            InterbankArchiveExchRateDTO archiveExchangeRateDto, String date) {
        Map<String, InterbankArchiveDTO> currenciesMap = new HashMap<>();
        currenciesMap.put("usd", archiveExchangeRateDto.getUsd());
        currenciesMap.put("eur", archiveExchangeRateDto.getEur());
        currenciesMap.put("rub", archiveExchangeRateDto.getRub());
        currenciesMap.put("gbp", archiveExchangeRateDto.getGbp());
        currenciesMap.put("pln", archiveExchangeRateDto.getPln());
        currenciesMap.put("chf", archiveExchangeRateDto.getChf());
        currenciesMap.put("czk", archiveExchangeRateDto.getCzk());
        currenciesMap.put("cad", archiveExchangeRateDto.getCad());
        currenciesMap.put("huf", archiveExchangeRateDto.getHuf());
        List<InterbankArchiveExchRateFormattedDTO> interbankArchRateFormattedDtoList = new ArrayList<>();
        for (Map.Entry<String, InterbankArchiveDTO> currency : currenciesMap.entrySet()) {
            InterbankArchiveExchRateFormattedDTO interbankArchExcRate = new InterbankArchiveExchRateFormattedDTO();
            interbankArchExcRate.setArchiveDate(date);
            interbankArchExcRate.setCurrency(currency.getKey());
            interbankArchExcRate.setAsk(currency.getValue().getAsk());
            interbankArchExcRate.setBid(currency.getValue().getBid());
            interbankArchExcRate.setTrendAsk(currency.getValue().getTrendAsk());
            interbankArchExcRate.setTrendBid(currency.getValue().getTrendBid());
            interbankArchRateFormattedDtoList.add(interbankArchExcRate);
        }
        return interbankArchRateFormattedDtoList;
    }

    private void saveInterbankArchiveArchive(List<InterbankArchiveExchRateFormattedDTO> interbankArchExchRateDTOList){
        List<InterbankArchiveExchangeRate> interbankArchListEntities = interbankArchExchRateDTOList.stream()
                .map(exchRateDto -> interbankArchiveMapper.toInterbankArchiveExchangeRate(exchRateDto))
                .collect(Collectors.toList());
        minfinArchiveRepository.saveAll(interbankArchListEntities);
    }
    private List<InterbankArchiveExchangeRate> fetchExchangeRateByDate(String date) {
        return minfinArchiveRepository.fetchAllByDate(date);
    }

    private List<InterbankArchiveExchRateFormattedDTO> convertToListDto(List<InterbankArchiveExchangeRate> interbankArchiveExcRateEntity) {
        return interbankArchiveExcRateEntity.stream()
                .map(exchRate -> interbankArchiveMapper.toInterbankArchiveExchangeRateDTO(exchRate))
                .collect(Collectors.toList());
    }
}