package org.exchange.service;

import org.exchange.domain.dto.PrivatBankArchiveExchangeRateDTO;

import java.util.List;

public interface PrivatBankArchive {

    PrivatBankArchiveExchangeRateDTO getArchiveByDate(String date);
}
