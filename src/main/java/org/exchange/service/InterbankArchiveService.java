package org.exchange.service;

import org.exchange.domain.dto.InterbankArchiveExchRateFormattedDTO;

import java.util.List;

public interface InterbankArchiveService {

    List<InterbankArchiveExchRateFormattedDTO> getArchiveByDate(String date);
}
