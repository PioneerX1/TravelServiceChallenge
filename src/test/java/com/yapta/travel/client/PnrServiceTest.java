package com.yapta.travel.client;

import com.yapta.travel.core.ClassOfService;
import com.yapta.travel.core.FlightSegment;
import com.yapta.travel.core.Pnr;
import com.yapta.travel.core.Ticket;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

public class PnrServiceTest {

    /*
    First test was refactored since a PNR model was created and Ticket model now includes List of FlightSegments
     */

    IPnrService pnrService = new PnrService();

    @Test
    public void fetchPnrReturnsExpectedPnrObjectGivenExistingRecordLocator() throws PnrNotFoundException {
        //Arrange
        String recordLocator = "ABC123";
        List<FlightSegment> flightSegments1 = new ArrayList<>();
        FlightSegment fs1 = new FlightSegment("487K", "10OCT", "SEA", "LAX", "HK1", "250P", "535P", "DCAS*HJQTEX");
        flightSegments1.add(fs1);
        FlightSegment fs2 = new FlightSegment("486T", "18OCT", "LAX", "SEA", "HK1", "230P", "513P", "DCAS*HJQTEX");
        flightSegments1.add(fs2);
        List<FlightSegment> flightSegments2 = new ArrayList<>();
        FlightSegment fs3 = new FlightSegment("223H", "12OCT", "SFO", "CHI", "HK1", "1150A", "615P", "DCAS*HJQTEX");
        FlightSegment fs4 = new FlightSegment("231G", "17OCT", "CHI", "SFO", "HK1", "920A", "1130A", "DCAS*HJQTEX");
        flightSegments2.add(fs3);
        flightSegments2.add(fs4);
        List<Ticket> tickets = new ArrayList<>();
        Ticket ticket1 = new Ticket(Money.of(CurrencyUnit.USD, 2500.00), "0277850344766", ClassOfService.BUSINESS, flightSegments1);
        tickets.add(ticket1);
        Ticket ticket2 = new Ticket(Money.of(CurrencyUnit.USD, 700.00), "0347850344755", ClassOfService.ECONOMY, flightSegments2);
        tickets.add(ticket2);
        Pnr expectedPnr = new Pnr(recordLocator, tickets);

        //Act
        Pnr actualPnr = pnrService.fetchPnr(recordLocator);

        //Assert
        Assert.assertEquals(expectedPnr, actualPnr);
    }

    @Test(expected=PnrNotFoundException.class)
    public void fetchPnrThrowsPnrNotFoundExceptionGivenNonExistingRecordLocator() throws PnrNotFoundException {
        //Arrange
        String recordLocator = "NOT123";

        //Act
        pnrService.fetchPnr(recordLocator);
    }


}
