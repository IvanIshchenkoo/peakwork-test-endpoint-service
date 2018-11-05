package com.andersen.peakwork.repository.impl;

import com.andersen.peakwork.model.entity.Stock;
import com.andersen.peakwork.repository.StockRepository;
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.*;
import com.google.cloud.datastore.StructuredQuery.CompositeFilter;
import com.google.cloud.datastore.StructuredQuery.Filter;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class StockRepositoryImpl implements StockRepository {
    private final Datastore datastore;

    @Value("${datastore.kind}")
    private String kind;

    @Autowired
    public StockRepositoryImpl(Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    public Optional<List<Stock>> findBySymbolAndTime(String symbol, @Nullable Timestamp from, @Nullable Timestamp to) {
        List<Filter> filters = new ArrayList<>();
        if (from != null) {
            filters.add(PropertyFilter.ge(Stock.TIMESTAMP_PROPERTY, from));
        }
        if (to != null) {
            filters.add(PropertyFilter.le(Stock.TIMESTAMP_PROPERTY, to));
        }

        EntityQuery query = Query.newEntityQueryBuilder().setKind(kind)
                .setFilter(CompositeFilter
                        .and(PropertyFilter.eq(Stock.SYMBOL_PROPERTY, symbol), filters.toArray(new Filter[0]))).build();

        QueryResults<Entity> companyStocks = datastore.run(query);

        List<Stock> stocks = new ArrayList<>();
        while (companyStocks.hasNext()) {
            Entity entity = companyStocks.next();
            Stock stock = new Stock();
            stock.setCompanyName(entity.getString(Stock.COMPANY_NAME_PROPERTY));
            stock.setSymbol(entity.getString(Stock.SYMBOL_PROPERTY));
            stock.setLogoUrl(entity.getString(Stock.LOGO_URL_PROPERTY));
            stock.setPrice(entity.getDouble(Stock.PRICE_PROPERTY));
            stock.setTimestamp(entity.getTimestamp(Stock.TIMESTAMP_PROPERTY));
            stocks.add(stock);
        }

        if (stocks.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(stocks);
    }
}
