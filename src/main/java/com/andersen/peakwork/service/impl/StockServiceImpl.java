package com.andersen.peakwork.service.impl;

import com.andersen.peakwork.model.entity.Stock;
import com.andersen.peakwork.model.response.CompanyStock;
import com.andersen.peakwork.model.response.StockPrice;
import com.andersen.peakwork.repository.StockRepository;
import com.andersen.peakwork.service.StocksService;
import com.google.cloud.Timestamp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StockServiceImpl implements StocksService {
    private final StockRepository stockRepository;

    @Autowired
    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public List<CompanyStock> getStocks(List<String> companies, Date from, Date to) {
        Timestamp fromTimestamp = dateToTimestamp(from);
        Timestamp toTimestamp = dateToTimestamp(to);

        List<CompanyStock> companyStockList = new ArrayList<>();
        for (String symbol : companies) {
            List<Stock> stocks = stockRepository.findBySymbolAndTime(symbol, fromTimestamp, toTimestamp)
                    .orElse(Collections.emptyList());
            stockToStockResponse(stocks, symbol).ifPresent(companyStockList::add);
        }
        return companyStockList;
    }

    private Optional<CompanyStock> stockToStockResponse(List<Stock> stocks, String symbol) {
        CompanyStock companyStock = new CompanyStock();
        if (!stocks.isEmpty()) {
            companyStock.setCompanyName(stocks.get(0).getCompanyName());
            companyStock.setLogoUrl(stocks.get(0).getLogoUrl());
            companyStock.setPrices(stocks.stream()
                    .map(stock -> new StockPrice(stock.getPrice(), stock.getTimestamp().toDate()))
                    .collect(Collectors.toList()));
        } else {
            log.info("No stock info for " + symbol + " company.");
            return Optional.empty();
        }
        log.info("Stock info for " + symbol + " company was found.");
        return Optional.of(companyStock);
    }

    private Timestamp dateToTimestamp(@Nullable Date date) {
        if (date != null) {
            return Timestamp.of(date);
        }
        return null;
    }
}
