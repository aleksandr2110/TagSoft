package org.exchange.controller;

import lombok.RequiredArgsConstructor;
import org.exchange.domain.dto.UnitedArchiveExchangeRateDTO;
import org.exchange.domain.dto.UnitedExchangeRateDTO;
import org.exchange.service.ExchangeRateService;
import org.exchange.service.PrivatBankArchive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/currency")
public class ExchangeRateController  {

    // http://localhost:8081/v3/api-docs/
    @Autowired
    private ExchangeRateService exchangeRateService;
    @Autowired
    private PrivatBankArchive privatBankArchive;

    @GetMapping("/average-exchange-rate")
    public ResponseEntity<UnitedExchangeRateDTO> getAverageExchangeRate() {
        UnitedExchangeRateDTO unitExchangeDTO = exchangeRateService.getAllExchangeRate();
        return ResponseEntity.ok(unitExchangeDTO);
    }

    @GetMapping("/average-exchange-rate/archive/{date}")
    public ResponseEntity<UnitedArchiveExchangeRateDTO> getAverageExchangeRateByDate(@PathVariable("date") String date) {
        UnitedArchiveExchangeRateDTO unitExchangeDTO = exchangeRateService.getArchiveByDate(date);
        return ResponseEntity.ok(unitExchangeDTO);
    }
}
