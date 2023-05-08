package org.exchange.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "monobank", url = "https://api.monobank.ua/bank/currency")
public interface MonobankClient {

    @GetMapping(consumes = "application/json")
    String getCurrency();
    // https://api.monobank.ua/docs/#tag/Publichni-dani
}
