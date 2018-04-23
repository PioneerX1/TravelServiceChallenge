package com.yapta.travel.client;

import com.yapta.travel.core.ClassOfService;
import com.yapta.travel.core.FlightSegment;
import com.yapta.travel.core.Ticket;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TicketServiceTest {

    private ITicketService ticketService = new TicketService();

    @Test
    public void fetchTicketShouldReturnExpectedTicketGivenExistingTicketNumber() throws TicketNotFoundException {
        //Arrange
        List<FlightSegment> flightSegments = new ArrayList<>();
        FlightSegment fs1 = new FlightSegment("487K", "10OCT", "SEA", "LAX", "HK1", "250P", "535P", "DCAS*HJQTEX");
        flightSegments.add(fs1);
        Ticket expectedTicket = new Ticket(Money.of(CurrencyUnit.USD, 2500.00), "0277850344766", ClassOfService.BUSINESS, flightSegments);

        //Act
        String ticketNumber = "0277850344766";
        Ticket actualTicket = ticketService.fetchTicket(ticketNumber);

        //Assert
        Assert.assertEquals(expectedTicket, actualTicket);
    }

    @Test(expected = TicketNotFoundException.class)
    public void fetchTicketShouldThrowTicketNotFoundExceptionGivenNonExistingTicketNumber() throws TicketNotFoundException {
        //Arrange

        //Act
        ticketService.fetchTicket("0000000000000");

        //Assert
    }
}
