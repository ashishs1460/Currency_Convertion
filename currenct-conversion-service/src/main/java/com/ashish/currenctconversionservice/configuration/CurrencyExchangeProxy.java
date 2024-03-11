package com.ashish.currenctconversionservice.configuration;

import com.ashish.currenctconversionservice.data.CurrencyConversion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

@FeignClient(name="currency-exchange", url="localhost:8000")
public interface CurrencyExchangeProxy {


        @GetMapping("/currency-exchange/from/{from}/to/{to}")
        public CurrencyConversion retrieveExchangeValue(
                @PathVariable String from,
                @PathVariable String to);

}
