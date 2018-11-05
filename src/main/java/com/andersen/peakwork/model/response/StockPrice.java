package com.andersen.peakwork.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class StockPrice {
    private Double price;
    private Date timestamp;
}
