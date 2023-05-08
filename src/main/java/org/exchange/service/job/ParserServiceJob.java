package org.exchange.service.job;

import lombok.AllArgsConstructor;
import org.exchange.service.impl.InterbankServiceImpl;
import org.exchange.service.impl.MonoBankServiceImpl;
import org.exchange.service.impl.PrivatBankServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ParserServiceJob {

    private MonoBankServiceImpl monoBankSourceService;
    private PrivatBankServiceImpl privatBankService;
    private InterbankServiceImpl interbankSourceService;
    // Cron expression is represented by six fields:
    // second, minute, hour, day of month, month, day(s) of wee
    @Scheduled(cron = "${app.cron-job-parser}")
    public void extractExchangeRateToDatabase() {
        monoBankSourceService.getExchangeRate();
        privatBankService.getExchangeRate();
        interbankSourceService.getExchangeRate();
    }
}
