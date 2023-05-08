package org.exchange.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.exchange.client.PrivatBankClient;
import org.exchange.domain.dto.*;
import org.exchange.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExchangeRateServiceImpl implements ExchangeRateService {

    @Autowired
    private PrivatBankClient privatBankClient;
    @Autowired
    private MonoBankService monoBankService;
    @Autowired
    private PrivatBankService privatBankService;
    @Autowired
    private InterbankService interbankService;
    @Autowired
    private InterbankArchiveService interbankArchiveService;
    @Autowired
    private PrivatBankArchive privatBankArchive;
    @Override
    public UnitedExchangeRateDTO getAllExchangeRate() {
        UnitedExchangeRateDTO unitedExchangeRateDTO = new UnitedExchangeRateDTO();
        List<MonobankExchangeRateDTO> allMonobankExchangeRateDTO = monoBankService.getAllExchangeRate();
        List<PrivatBankExchangeRateDTO> allPrivatBankExchangeRateDTO = privatBankService.getAllExchangeRate();
        List<InterbankExchangeRateDTO> allInterbankExchangeRateDTO = interbankService.getAllExchangeRate();
        unitedExchangeRateDTO.setMonobankExchListDto(allMonobankExchangeRateDTO);
        unitedExchangeRateDTO.setPrivatBankExcheRatetListDto(allPrivatBankExchangeRateDTO);
        unitedExchangeRateDTO.setInterbankExchangeRateDtoList(allInterbankExchangeRateDTO);
        Map<String, Map<String, Double>> averageCurrency = findAverageUsdAndEuro(allMonobankExchangeRateDTO,
                allPrivatBankExchangeRateDTO, allInterbankExchangeRateDTO);
        unitedExchangeRateDTO.setAverageBuyUsd(averageCurrency.get("usd").get("buy").toString());
        unitedExchangeRateDTO.setAverageSellUsd(averageCurrency.get("usd").get("sell").toString());
        unitedExchangeRateDTO.setAverageBuyEuro(averageCurrency.get("euro").get("buy").toString());
        unitedExchangeRateDTO.setAverageSellEuro(averageCurrency.get("euro").get("sell").toString());
        return unitedExchangeRateDTO;
    }

    @Override
    public UnitedArchiveExchangeRateDTO getArchiveByDate(String date) {
        UnitedArchiveExchangeRateDTO unitedArchiveExchangeRateDTO = new UnitedArchiveExchangeRateDTO();
        PrivatBankArchiveExchangeRateDTO privatBankArchiveExchRateDto = privatBankArchive.getArchiveByDate(date);
        List<InterbankArchiveExchRateFormattedDTO> interbankArchiveExcRateDto = interbankArchiveService.getArchiveByDate(date);
        Map<String, Map<Double, Double>> averageArchExcRate = findAverageExchangeRate(privatBankArchiveExchRateDto.getExchangeRate(),
                interbankArchiveExcRateDto);
        log.info("Retrieved archive by date {}", date);
        unitedArchiveExchangeRateDTO.setDateArchive(date);
        unitedArchiveExchangeRateDTO.setAverageArchExcRate(averageArchExcRate);
        return unitedArchiveExchangeRateDTO;
    }

    private Map<String, Map<String, Double>> findAverageUsdAndEuro(List<MonobankExchangeRateDTO> monobankList,
                                                            List<PrivatBankExchangeRateDTO> privatbankList,
                                                            List<InterbankExchangeRateDTO> interbankList) {
        MonobankExchangeRateDTO monobankCurrencyUsd = monobankList.stream()
                .filter(dtoUsd -> dtoUsd.getCurrencyCodeA().equals(840) && dtoUsd.getCurrencyCodeB().equals(980))
                .findFirst()
                .get();
        MonobankExchangeRateDTO monobankCurrencyEuro = monobankList.stream()
                .filter(dtoEuro -> dtoEuro.getCurrencyCodeA().equals(978) && dtoEuro.getCurrencyCodeB().equals(980))
                .findFirst()
                .get();

        PrivatBankExchangeRateDTO privatbankCurrencyUsd = privatbankList.stream()
                .filter(dtoUsd -> dtoUsd.getCcy().equals("USD"))
                .findFirst()
                .get();
        PrivatBankExchangeRateDTO privatbankCurrencyEuro = privatbankList.stream()
                .filter(dtoEuro -> dtoEuro.getCcy().equals("EUR"))
                .findFirst()
                .get();

        InterbankExchangeRateDTO interbankCurrencyUsd = interbankList.stream()
                .filter(dtoUsd -> dtoUsd.getCurrency().equals("usd"))
                .findFirst()
                .get();
        InterbankExchangeRateDTO interbankCurrencyEuro = interbankList.stream()
                .filter(dtoEuro -> dtoEuro.getCurrency().equals("eur"))
                .findFirst()
                .get();

        Map<String, Double> buySellUsd = findAverageUsd(monobankCurrencyUsd, privatbankCurrencyUsd, interbankCurrencyUsd);
        Map<String, Double> buySellEuro = findAverageEuro(monobankCurrencyEuro, privatbankCurrencyEuro, interbankCurrencyEuro);
        Map<String, Map<String, Double>> currencyMap = new HashMap<>();
        currencyMap.put("usd", buySellUsd);
        currencyMap.put("euro", buySellEuro);
        return currencyMap;
    }

    private Map<String, Double> findAverageUsd(MonobankExchangeRateDTO monobankCurrency,
                                                            PrivatBankExchangeRateDTO privatbankCurrency,
                                                            InterbankExchangeRateDTO interbankCurrency) {
        Double monobankBuyUsd = Double.parseDouble(monobankCurrency.getRateBuy());
        Double monobankSellUsd = Double.parseDouble(monobankCurrency.getRateSell());

        Double privatBankBuyUsd = Double.parseDouble(privatbankCurrency.getBuy());
        Double privatBankSellUsd = Double.parseDouble(privatbankCurrency.getSale());

        Double interbankBuyUsd = Double.parseDouble(interbankCurrency.getAsk());
        Double interbankSellUsd = Double.parseDouble(interbankCurrency.getBid());

        Double avarageBuyUSD = 0.0;
        Double avarageSellUSD = 0.0;
        avarageBuyUSD = (monobankBuyUsd + privatBankBuyUsd + interbankBuyUsd) / 3;
        avarageSellUSD = (monobankSellUsd + privatBankSellUsd + interbankSellUsd) / 3;
        Map<String, Double> buySellUsd = new HashMap<>();
        buySellUsd.put("buy", avarageBuyUSD);
        buySellUsd.put("sell", avarageSellUSD);
        return buySellUsd;
    }

    private Map<String, Double> findAverageEuro(MonobankExchangeRateDTO monobankCurrency,
                            PrivatBankExchangeRateDTO privatBankCurrency,
                            InterbankExchangeRateDTO interbankCurrency) {
        Double monobankBuyUsd = Double.parseDouble(monobankCurrency.getRateBuy());
        Double monobankSellUsd = Double.parseDouble(monobankCurrency.getRateSell());

        Double privatBankBuyEur = Double.parseDouble(privatBankCurrency.getBuy());
        Double privatBankSellEur = Double.parseDouble(privatBankCurrency.getSale());

        Double interbankBankBuyEur = Double.parseDouble(interbankCurrency.getAsk());
        Double interbankBankSellEur = Double.parseDouble(interbankCurrency.getBid());

        Double avarageBuyEur = 0.0;
        Double avarageSellEur = 0.0;
        avarageBuyEur = (monobankBuyUsd + privatBankBuyEur + interbankBankBuyEur) / 3;
        avarageSellEur = (monobankSellUsd + privatBankSellEur + interbankBankSellEur) / 3;
        Map<String, Double> buySellEur = new HashMap<>();
        buySellEur.put("buy", avarageBuyEur);
        buySellEur.put("sell", avarageSellEur);
        return buySellEur;
    }

    private Map<String, Map<Double, Double>> findAverageExchangeRate(List<PrivatBankArchiveDTO> privatExchangeRateList,
                                        List<InterbankArchiveExchRateFormattedDTO> interbankExchRateList) {
        Map<String, Map<Double, Double>> averageExchangeRate = new HashMap<>();
        for (PrivatBankArchiveDTO privatArchExchangeRate : privatExchangeRateList) {
            for(InterbankArchiveExchRateFormattedDTO interbankArchiveExchRate : interbankExchRateList) {
                if (privatArchExchangeRate.getCurrency().toLowerCase().equals(interbankArchiveExchRate.getCurrency().toLowerCase())) {
                    Map<Double, Double> saleBuyCurrency = new HashMap<>();
                    Double saleCurrency = compareAndGetAverage(privatArchExchangeRate.getSaleRate(),
                            Double.parseDouble(interbankArchiveExchRate.getAsk()));
                    Double buyCurrency = compareAndGetAverage(privatArchExchangeRate.getPurchaseRate(),
                            Double.parseDouble(interbankArchiveExchRate.getBid()));
                    saleBuyCurrency.put(saleCurrency, buyCurrency);
                    averageExchangeRate.put(privatArchExchangeRate.getCurrency().toLowerCase() +": sale/buy", saleBuyCurrency);
                    log.info("currency {}: sale currency {}, buy sale {}",
                            privatArchExchangeRate.getCurrency().toLowerCase(), saleCurrency, buyCurrency);
                    break;
                }
            }
        }
        return averageExchangeRate;
    }

    private Double compareAndGetAverage(Double firstValue, Double secondValue) {
        Optional<Double> firstValueOpt = Optional.ofNullable(firstValue);
        Optional<Double> secondValueOpt = Optional.ofNullable(secondValue);
        Double firstExist = 0.0;
        Double secondExist = 0.0;
        int divider = 0;
        if (firstValueOpt.isPresent()) {
            firstExist = firstValueOpt.get();
            divider++;
        }
        if (secondValueOpt.isPresent()) {
            secondExist = secondValueOpt.get();
            divider++;
        }
        return (firstExist + secondExist)/divider;
    }
}
