package com.yapta.travel.client;

import com.yapta.travel.core.ClassOfService;
import com.yapta.travel.core.FlightSegment;
import com.yapta.travel.core.PriceQuote;
import com.yapta.travel.core.Ticket;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lacrookedbeat on 4/22/18.
 */

public class PriceBusterTest {

    /*
    PriceBusterTest tests for 2 scenarios:
        1. Correct PriceQuote results are received based on less than original price and same class of service.
        2. PriceQuoteNotFoundException occurs if no lower price quotes are found in the same class of service.
     */

    IPriceBusterService priceBusterService = new PriceBusterService();

    @Test
    public void fetchCheaperQuotesFindsOnlyResultsThatAreLessThanTicketPriceAndSameClassOfService() throws PriceQuoteNotFoundException {

        // Arrange
        List<FlightSegment> flightSegments = new ArrayList<>();
        FlightSegment fs1 = new FlightSegment("487K", "10OCT", "SEA", "LAX", "HK1", "250P", "535P", "DCAS*HJQTEX");
        flightSegments.add(fs1);
        FlightSegment fs2 = new FlightSegment("486T", "18OCT", "LAX", "SEA", "HK1", "230P", "513P", "DCAS*HJQTEX");
        flightSegments.add(fs2);
        Ticket validTicket = new Ticket(Money.of(CurrencyUnit.USD, 2500.00), "0277850344766", ClassOfService.BUSINESS, flightSegments);

        // Act
        List<PriceQuote> pqList = priceBusterService.fetchCheaperQuotes(validTicket);
        PriceQuote fetchedPQ = pqList.get(0);

        // Assert
        Assert.assertTrue(fetchedPQ.getPrice().isLessThan(validTicket.getPrice()) &&
            fetchedPQ.getClassOfService() == validTicket.getClassOfService());

    }

    @Test(expected=PriceQuoteNotFoundException.class)
    public void fetchCheaperQuotesThrowsNoPriceQuoteException() throws PriceQuoteNotFoundException {

        List<FlightSegment> flightSegments = new ArrayList<>();
        FlightSegment fs1 = new FlightSegment("487K", "10OCT", "SEA", "LAX", "HK1", "250P", "535P", "DCAS*HJQTEX");
        flightSegments.add(fs1);
        FlightSegment fs2 = new FlightSegment("486T", "18OCT", "LAX", "SEA", "HK1", "230P", "513P", "DCAS*HJQTEX");
        flightSegments.add(fs2);
        Ticket invalidTicket = new Ticket(Money.of(CurrencyUnit.USD, 200.00), "0277850344766", ClassOfService.BUSINESS, flightSegments);

        List<PriceQuote> pqList = priceBusterService.fetchCheaperQuotes(invalidTicket);
        // there should be an exception thrown above since no cheaper quotes found under $200 for biz class
    }

}
