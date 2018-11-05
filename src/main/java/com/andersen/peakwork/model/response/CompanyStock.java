package com.andersen.peakwork.model.response;

import lombok.Data;

import java.util.List;

@Data
public class CompanyStock {
    private String companyName;
    private String logoUrl;
    private List<StockPrice> prices;
}
