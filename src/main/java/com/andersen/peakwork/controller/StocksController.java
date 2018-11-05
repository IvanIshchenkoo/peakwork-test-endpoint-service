package com.andersen.peakwork.controller;

import com.andersen.peakwork.model.response.CompanyStock;
import com.andersen.peakwork.service.StocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class StocksController {
    private final StocksService stocksService;

    @Autowired
    public StocksController(StocksService stocksService) {
        this.stocksService = stocksService;
    }

    @GetMapping("/stocks")
    public List<CompanyStock> getStocks(@RequestParam List<String> companies,
                                        @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date from,
                                        @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date to) {
        return stocksService.getStocks(companies, from, to);
    }
}
