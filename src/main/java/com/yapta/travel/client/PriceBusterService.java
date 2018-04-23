package com.yapta.travel.client;

import com.yapta.travel.core.*;
import org.joda.money.Money;

import java.util.*;

/**
 * Created by lacrookedbeat on 4/19/18.
 */
public class PriceBusterService implements IPriceBusterService {

    /*
    PriceBusterService digs up price quotes from the PriceService and filters by cheaper and same class of service
     */

    private ClassOfService cos;
    private Money price;
    private List<FlightSegment> flightSegments;

    private List<PriceQuote> filterPriceQuotes(List<PriceQuote> unfilteredPQ) {
        // remove ones that don't match criteria out of list
        List<PriceQuote> filteredPQ = new ArrayList<>();
        for(PriceQuote pq : unfilteredPQ) {
            if (pq.getClassOfService() == cos && pq.getPrice().isLessThan(price)) {
                filteredPQ.add(pq);
            }
        }
        return filteredPQ;
    }

    @Override
    public List<PriceQuote> fetchCheaperQuotes(Ticket ticket) throws PriceQuoteNotFoundException {

        cos = ticket.getClassOfService();
        price = ticket.getPrice();

        PriceService priceService = new PriceService();

        List<PriceQuote> priceQuotes = priceService.fetchPrices(ticket.getFlightSegments());

        // logic to parse through priceQuotes and only return ones that match ClassOfService and Cheaper Price
        priceQuotes = filterPriceQuotes(priceQuotes);

        if (priceQuotes.size() == 0) {
            throw new PriceQuoteNotFoundException();
        } else {
            return priceQuotes;
        }
    }
}
