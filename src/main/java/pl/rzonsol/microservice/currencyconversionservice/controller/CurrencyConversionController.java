package pl.rzonsol.microservice.currencyconversionservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.rzonsol.microservice.currencyconversionservice.feignproxy.CurrencyExchangeServiceProxy;
import pl.rzonsol.microservice.currencyconversionservice.model.CurrencyConversionBean;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConversionController {

    @Autowired
    private CurrencyExchangeServiceProxy proxy;

    @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity){

        CurrencyConversionBean response = proxy.retrieveExchangeValue(from, to);

        return CurrencyConversionBean.builder()
                .id(response.getId())
                .from(from)
                .to(to)
                .conversionMultiple(response.getConversionMultiple())
                .quantity(quantity)
                .totalCalculatedAmount(quantity.multiply(response.getConversionMultiple()))
                .port(response.getPort())
                .build();
    }

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity){

        Map<String, String> uriVariable = new HashMap<>();
        uriVariable.put("from", from);
        uriVariable.put("to", to);

        ResponseEntity<CurrencyConversionBean> respopnseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversionBean.class,
                uriVariable);

        CurrencyConversionBean response = respopnseEntity.getBody();

        return CurrencyConversionBean.builder()
                .id(response.getId())
                .from(from)
                .to(to)
                .conversionMultiple(response.getConversionMultiple())
                .quantity(quantity)
                .totalCalculatedAmount(quantity.multiply(response.getConversionMultiple()))
                .port(response.getPort())
                .build();
    }
}
