package com.devleofulco.conversor.de.modedas.service;

import com.devleofulco.conversor.de.modedas.model.ExchangeRateResponse;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeRateService {

    @Value("${exchange.rate.api.url}")
    private String apiUrl;
    @Value("${exchange.rate.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final Gson gson;

    public ExchangeRateService(RestTemplate restTemplate, Gson gson){
        this.restTemplate = restTemplate;
        this.gson = gson;
    }

    public ExchangeRateResponse getExchangeRates(){
        String url = String.format("%s/%s/latest/USD",apiUrl,apiKey);
        System.out.println("Requesting URL: " + url);
        String jsonResponse = restTemplate.getForObject(url,String.class);
        System.out.println("Response: "+ jsonResponse);
        return gson.fromJson(jsonResponse, ExchangeRateResponse.class);
    }

    public Double getExchangeRate(String currency){
        ExchangeRateResponse response = getExchangeRates();
        if (response != null && "sucess".equals(response.getResult())&& response.getConversion_rates() != null){
            Double rate = response.getConversion_rates().get(currency);
            if(rate!=null){
                return rate;
            }else {
                System.out.println("Currency not found:" + currency);
            }
        }else {
            System.out.println("Error in response: " + (response!=null ? response.getResult():"null response"));
        }
        return null;
    }
}
