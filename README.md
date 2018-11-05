Endpoint service
==================
This service exposes endpoint which can be used to get prices for the specified company or companies.

User can limit data by specifying starting date and ending date, or can just specify one date in that case data will be unbound by time from that direction.

Example request: 
1. `/stocks?companies=FB,AAPL&from=2017-11-17 00:00:00&to=2018-11-19 23:59:59`
2. `/stocks?companies=FB,AAPL`
3. `/stocks?companies=FB,AAPL&from=2017-11-17 00:00:00`
4. `/stocks?companies=FB,AAPL&to=2018-11-19 23:59:59`

Date includes hours and minutes. Prices can be filtered in time frame during one day.

All data is queried for google datastore, so data must be populated first before querying the endpoint. If there's no data for the specified range or company, endpoint will return empty list.

