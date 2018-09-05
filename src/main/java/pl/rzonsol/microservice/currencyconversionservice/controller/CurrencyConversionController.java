package pl.rzonsol.microservice.currencyconversionservice.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.rzonsol.microservice.currencyconversionservice.model.CurrencyConversionBean;

import java.math.BigDecimal;

@RestController
public class CurrencyConversionController {


    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity){

        return CurrencyConversionBean.builder()
                .id(1L)
                .from("USD")
                .to("PLN")
                .port(8100)
                .quantity(BigDecimal.valueOf(100))
                .build();
    }
}
