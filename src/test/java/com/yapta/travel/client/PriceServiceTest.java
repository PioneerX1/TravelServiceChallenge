package com.yapta.travel.client;

import com.google.common.collect.Lists;
import com.yapta.travel.core.ClassOfService;
import com.yapta.travel.core.FlightSegment;
import com.yapta.travel.core.PriceQuote;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PriceServiceTest {

    private IPriceService priceService = new PriceService();

    @Test
    public void testFetchPricesReturnsExpectedPricesGivenExistingFlightSegments() {
        //Arrange
        List<FlightSegment> flightSegments = Lists.newArrayList(
                new FlightSegment("487K", "10OCT", "SEA", "LAX", "HK1", "250P", "535P", "DCAS*HJQTEX"),
                new FlightSegment("486T", "18OCT", "LAX", "SEA", "HK1", "230P", "513P", "DCAS*HJQTEX"));
        List<PriceQuote> expectedPriceQuotes = Lists.newArrayList(
                new PriceQuote(Money.of(CurrencyUnit.USD, 285.15), ClassOfService.ECONOMY),
                new PriceQuote(Money.of(CurrencyUnit.USD, 29000.00), ClassOfService.FIRST),
                new PriceQuote(Money.of(CurrencyUnit.USD, 2840.00), ClassOfService.BUSINESS),
                new PriceQuote(Money.of(CurrencyUnit.USD, 2150.00), ClassOfService.BUSINESS)
        );

        //Act
        List<PriceQuote> actualPriceQuotes = priceService.fetchPrices(flightSegments);

        //Assert
        Assert.assertEquals(expectedPriceQuotes, actualPriceQuotes);
    }

    @Test
    public void testFetchPricesReturnsEmptyListGivenNonExistingFlightSegments() {
        //Arrange
        List<FlightSegment> nonExistingFlightSegments = Lists.newArrayList(
                new FlightSegment("120X", "10OCT", "SEA", "LAX", "HK1", "250P", "535P", "DCAS*HJQTEX"),
                new FlightSegment("134K", "18OCT", "LAX", "SEA", "HK1", "230P", "513P", "DCAS*HJQTEX"));

        //Act
        List<PriceQuote> actualPriceQuotes = priceService.fetchPrices(nonExistingFlightSegments);

        //Assert
        Assert.assertTrue("List of price quotes should be empty", actualPriceQuotes.isEmpty());
    }
}
