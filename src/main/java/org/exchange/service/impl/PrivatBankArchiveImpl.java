package org.exchange.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.exchange.client.PrivatBankClient;
import org.exchange.domain.PrivatBankArchiveExchangeRate;
import org.exchange.domain.dto.PrivatBankArchiveDTO;
import org.exchange.domain.dto.PrivatBankArchiveExchangeRateDTO;
import org.exchange.exception.EntityNotFoundException;
import org.exchange.mapper.PrivatBankArchiveMapper;
import org.exchange.repository.PrivatBankArchiveInnerRepository;
import org.exchange.repository.PrivatBankArchiveRepository;
import org.exchange.domain.PrivatBankArchiveEntity;
import org.exchange.service.PrivatBankArchive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class PrivatBankArchiveImpl implements PrivatBankArchive {
    @Autowired
    private PrivatBankClient privatBankClient;
    @Autowired
    private PrivatBankArchiveMapper privatBankArchiveMapper;
    @Autowired
    private PrivatBankArchiveRepository privatBankArchiveRepository;
    @Autowired
    private PrivatBankArchiveInnerRepository privatBankArchiveInnerRepository;
    @Transactional
    @Override
    public PrivatBankArchiveExchangeRateDTO getArchiveByDate(String date) {
        PrivatBankArchiveExchangeRateDTO archiveExchRateDto = null;
        PrivatBankArchiveExchangeRate archiveExchRate = null;
        Optional<PrivatBankArchiveExchangeRate> privatBankExcRateDbEntity = privatBankArchiveRepository.fetchAllByDate(date);
        if (privatBankExcRateDbEntity.isPresent()) {
            archiveExchRate = privatBankExcRateDbEntity.get();
            archiveExchRateDto = privatBankArchiveMapper.toPrivatBankExchangeRateDTO(archiveExchRate);
        } else {
        archiveExchRateDto = getExchangeRateByDate(date);
        log.info("The day of archive exchange rate {} with size of objects received from Privat Bank : {}",
                date, archiveExchRateDto.getExchangeRate().size());

        savePrivatBankArchive(archiveExchRateDto);
        archiveExchRate = fetchExchangeRateByDate(date);

        archiveExchRateDto = privatBankArchiveMapper.toPrivatBankExchangeRateDTO(archiveExchRate);
        }
        List<PrivatBankArchiveDTO> privatBankListArchiveDto = getPrivatBankArchiveList(archiveExchRate.getId());
        archiveExchRateDto.setExchangeRate(privatBankListArchiveDto);
        return archiveExchRateDto;
    }

    private PrivatBankArchiveExchangeRateDTO getExchangeRateByDate(String date) {
        PrivatBankArchiveExchangeRateDTO archiveExchangeRate = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            archiveExchangeRate = mapper.readValue(privatBankClient.getExchangeRateByDate(date),  PrivatBankArchiveExchangeRateDTO.class );
        } catch(IOException ex) {
            log.warn("Privat Bank archive server for {} date doesn't work", date);
            ex.printStackTrace();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return archiveExchangeRate;
    }

    private PrivatBankArchiveExchangeRate fetchExchangeRateByDate(String date) {
        PrivatBankArchiveExchangeRate archiveExchRate = privatBankArchiveRepository.fetchAllByDate(date)
                .orElseThrow(() -> new EntityNotFoundException("Privat Bank archive by date " + date + " not found"));
        return  archiveExchRate;
    }

    private void savePrivatBankArchive(PrivatBankArchiveExchangeRateDTO archiveExchRateDto) {
        PrivatBankArchiveExchangeRate newPrivatBankArchExcRate = new PrivatBankArchiveExchangeRate();
        newPrivatBankArchExcRate.setBank(archiveExchRateDto.getBank());
        newPrivatBankArchExcRate.setDate(archiveExchRateDto.getDate());
        newPrivatBankArchExcRate.setBaseCurrency(archiveExchRateDto.getBaseCurrency());
        newPrivatBankArchExcRate.setBaseCurrencyLit(archiveExchRateDto.getBaseCurrencyLit());
        privatBankArchiveRepository.save(newPrivatBankArchExcRate);

        List<PrivatBankArchiveDTO> privatBankArchiveList = archiveExchRateDto.getExchangeRate();
        List<PrivatBankArchiveEntity> collectedArchiveExchangeRate = privatBankArchiveList.stream()
                .map(excRateDTO -> privatBankArchiveMapper.toPrivatBankArchive(excRateDTO))
                .collect(Collectors.toList());

        List<PrivatBankArchiveEntity> preparedArchiveExchangeRate = new ArrayList<>();
        for (PrivatBankArchiveEntity entity : collectedArchiveExchangeRate) {
            entity.setPrivatBankArchiveExchangeRate(newPrivatBankArchExcRate);
            preparedArchiveExchangeRate.add(entity);
        }
        privatBankArchiveInnerRepository.saveAll(preparedArchiveExchangeRate);
    }

    private List<PrivatBankArchiveDTO> getPrivatBankArchiveList(Integer exchangeRateId) {
        List<PrivatBankArchiveEntity> arhiveListEntities = privatBankArchiveInnerRepository.fetchAllByExchangeRateId(exchangeRateId);
        List<PrivatBankArchiveDTO> privatBankArchiveListDTO = arhiveListEntities.stream()
                    .map(excRate -> privatBankArchiveMapper.toPrivatBankArchiveDTO(excRate))
                    .collect(Collectors.toList());
        return privatBankArchiveListDTO;
    }
}
