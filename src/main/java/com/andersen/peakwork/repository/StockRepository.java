package com.andersen.peakwork.repository;

import com.andersen.peakwork.model.entity.Stock;
import com.google.cloud.Timestamp;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository {
    /**
     * Finds list of stocks by specified symbol. If "from" and "to" timestamps were specified will filter by that range.
     * If any of timestamps are null then results will be unbounded by date from direction of missing timestamp.
     *
     * @param symbol company symbol
     * @param from   starting timestamp, may be null
     * @param to     end timestamp, may be null
     * @return list of Stocks, with all  fields populated.
     */
    Optional<List<Stock>> findBySymbolAndTime(String symbol, @Nullable Timestamp from, @Nullable Timestamp to);
}
