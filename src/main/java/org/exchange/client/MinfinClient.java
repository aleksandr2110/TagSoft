package org.exchange.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "minfin", url = "http://localhost:8080")
//@FeignClient(value = "minfin", url = "https://api.minfin.com.ua")
public interface MinfinClient {

    // in production replace to url https://api.minfin.com.ua/mb/e76f4fb5b5347d64ff0a2c9b02e9f787295e1ca2/
    @GetMapping(value = "/minfin-exchange-rate", consumes = "application/json")
    //@GetMapping(value = "/mb/e76f4fb5b5347d64ff0a2c9b02e9f787295e1ca2/", consumes = "application/json")
    String getCurrency();

    // in production replace to https://api.minfin.com.ua/summary/e76f4fb5b5347d64ff0a2c9b02e9f787295e1ca2/YYYY-MM-DD/
    @GetMapping(value = "/minfin-average-exchange-rate-period/{dayPointMonthPointYear}", consumes = "application/json")
    //@GetMapping(value = "/summary/e76f4fb5b5347d64ff0a2c9b02e9f787295e1ca2/{dayPointMonthPointYear}", consumes = "application/json")
    String getExchangeRateByDate(@PathVariable String dayPointMonthPointYear);
}
