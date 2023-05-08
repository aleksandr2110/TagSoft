package org.exchange.client;

import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(value = "privatBank", url = "https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=5")
@FeignClient(value = "privatBank", url = "https://api.privatbank.ua/")
public interface PrivatBankClient {

    //@GetMapping(consumes = "application/json")
    @GetMapping(value = "/p24api/pubinfo?exchange&coursid=5", consumes = "application/json")
    String getExchangeRate();

    // https://api.privatbank.ua/p24api/exchange_rates?date=01.12.2021
    @GetMapping(value = "/p24api/exchange_rates?date={dayPointMonthPointYear}", consumes = "application/json")
    String getExchangeRateByDate(@RequestParam String dayPointMonthPointYear);
}
