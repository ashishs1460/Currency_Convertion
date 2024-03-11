package com.ashish.currenctconversionservice.controller;

import com.ashish.currenctconversionservice.configuration.CurrencyExchangeProxy;
import com.ashish.currenctconversionservice.data.CurrencyConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {
    @Autowired
    CurrencyExchangeProxy exchangeProxy;

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFeign(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
    ) {

        CurrencyConversion currencyConversion = exchangeProxy.retrieveExchangeValue(from, to);

        return new CurrencyConversion(currencyConversion.getId(),
                from, to, quantity,
                currencyConversion.getConversionMultiple(),
                quantity.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getEnvironment() + " " + "feign");

    }
    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
    ) {

        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from",from);
        uriVariables.put("to",to);

        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity
                ("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                        CurrencyConversion.class, uriVariables);
        System.out.println(responseEntity);

        CurrencyConversion currencyConversion = responseEntity.getBody();

//        return new CurrencyConversion(currencyConversion.getId(),
//                from, to, quantity,
//                currencyConversion.getConversionMultiple(),
//                quantity.multiply(currencyConversion.getConversionMultiple()),
//                currencyConversion.getEnvironment()+ " " + "rest template");


        CurrencyConversion obj = new CurrencyConversion();
        obj.setId(currencyConversion.getId());
        obj.setFrom(to);
        obj.setTo(from);
        obj.setQuantity(quantity);
        obj.setConversionMultiple(currencyConversion.getConversionMultiple());
        obj.setTotalCalculatedAmount(quantity.multiply(currencyConversion.getConversionMultiple()));
        obj.setEnvironment(currencyConversion.getEnvironment());
        return obj;



    }
}
