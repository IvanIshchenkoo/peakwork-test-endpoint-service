package com.andersen.peakwork.service.impl;

import com.andersen.peakwork.PeakworkApplication;
import com.andersen.peakwork.model.entity.Stock;
import com.andersen.peakwork.model.response.CompanyStock;
import com.andersen.peakwork.repository.StockRepository;
import com.andersen.peakwork.service.StocksService;
import com.google.cloud.Timestamp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PeakworkApplication.class)
public class StockServiceImplTest {

    @MockBean
    private StockRepository stockRepository;

    @Autowired
    private StocksService stocksService;

    private List<Stock> appleStocks;
    private List<Stock> facebookStocks;
    private Stock appleStock1;
    private Stock appleStock2;
    private Stock facebookStock1;

    @Before
    public void setup() {
        appleStock1 = new Stock();
        appleStock1.setCompanyName("Apple Inc.");
        appleStock1.setSymbol("AAPL");
        appleStock1.setLogoUrl("https://storage.googleapis.com/iex/api/logos/AAPL.png");
        appleStock1.setTimestamp(Timestamp.now());
        appleStock1.setPrice(172.5);

        appleStock2 = new Stock();
        appleStock2.setCompanyName("Apple Inc.");
        appleStock2.setSymbol("AAPL");
        appleStock2.setLogoUrl("https://storage.googleapis.com/iex/api/logos/AAPL.png");
        appleStock2.setTimestamp(Timestamp.now());
        appleStock2.setPrice(172.8);

        facebookStock1 = new Stock();
        facebookStock1.setCompanyName("Facebook Inc.");
        facebookStock1.setSymbol("FB");
        facebookStock1.setLogoUrl("https://storage.googleapis.com/iex/api/logos/FB.png");
        facebookStock1.setTimestamp(Timestamp.now());
        facebookStock1.setPrice(172.9);

        appleStocks = new ArrayList<>();
        appleStocks.add(appleStock1);
        appleStocks.add(appleStock2);

        facebookStocks = new ArrayList<>();
        facebookStocks.add(facebookStock1);
    }

    @Test
    public void getStocks_oneCompany_returnCompanyWithPricesSet() {
        String symbol = "AAPL";
        when(stockRepository.findBySymbolAndTime(symbol, null, null)).thenReturn(Optional.of(appleStocks));
        List<CompanyStock> stocks = stocksService.getStocks(Collections.singletonList(symbol), null, null);

        assertEquals(1, stocks.size());
        assertEquals(appleStock1.getCompanyName(), stocks.get(0).getCompanyName());
        assertEquals(appleStock1.getLogoUrl(), stocks.get(0).getLogoUrl());
        assertEquals(2, stocks.get(0).getPrices().size());
        assertEquals(appleStock1.getPrice(), stocks.get(0).getPrices().get(0).getPrice());
    }

    @Test
    public void getStocks_multipleCompanies_listOfTwoCompaniesWithPrices() {
        String appleSymbol = "AAPL";
        String facebookSymbol = "FB";
        when(stockRepository.findBySymbolAndTime(appleSymbol, null, null)).thenReturn(Optional.of(appleStocks));
        when(stockRepository.findBySymbolAndTime(facebookSymbol, null, null)).thenReturn(Optional.of(facebookStocks));

        List<CompanyStock> stocks = stocksService.getStocks(Arrays.asList(appleSymbol, facebookSymbol), null, null);

        assertEquals(2, stocks.size());
        assertEquals("Apple Inc.", stocks.get(0).getCompanyName());
        assertEquals("Facebook Inc.", stocks.get(1).getCompanyName());
        assertEquals(appleStock1.getCompanyName(), stocks.get(0).getCompanyName());
        assertEquals(facebookStock1.getCompanyName(), stocks.get(1).getCompanyName());
        assertEquals(appleStocks.size(), stocks.get(0).getPrices().size());
        assertEquals(facebookStocks.size(), stocks.get(1).getPrices().size());
    }

    @Test
    public void getStocks_unsupportedSymbol_returnEmptyList() {
        String unknownSymbol = "?";
        when(stockRepository.findBySymbolAndTime(unknownSymbol, null, null)).thenReturn(Optional.empty());
        List<CompanyStock> stocks = stocksService.getStocks(Collections.singletonList(unknownSymbol), null, null);

        assertTrue(stocks.isEmpty());
    }
}