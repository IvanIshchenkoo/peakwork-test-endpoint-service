package com.andersen.peakwork.service;

import com.andersen.peakwork.model.response.CompanyStock;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.List;

public interface StocksService {
    /**
     * Returns list that represents companies and their history of prices with timestamp
     * If any of timestamps are null then results will be unbounded by date from direction of missing date.
     *
     * @param companies companies' symbols that will be included in the result
     * @param from      starting date from which prices will be filtered
     * @param to        end date to which prices will be filtered
     * @return Collection of companies and their prices
     */
    List<CompanyStock> getStocks(List<String> companies, @Nullable Date from, @Nullable Date to);
}
