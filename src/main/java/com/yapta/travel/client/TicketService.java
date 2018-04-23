package com.yapta.travel.client;

import com.google.common.collect.ImmutableMap;
import com.yapta.travel.core.ClassOfService;
import com.yapta.travel.core.FlightSegment;
import com.yapta.travel.core.Ticket;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TicketService implements ITicketService {

    /*
    NOTE: The Ticket model has been changed to include an ArrayList of Flight Segments now, so all Ticket objects
        must be instantiated with this.
     */

    private static FlightSegment fs1 = new FlightSegment("487K", "10OCT", "SEA", "LAX", "HK1", "250P", "535P", "DCAS*HJQTEX");
    private static FlightSegment fs2 = new FlightSegment("486T", "18OCT", "LAX", "SEA", "HK1", "230P", "513P", "DCAS*HJQTEX");
    private static FlightSegment fs3 = new FlightSegment("223H", "12OCT", "SFO", "CHI", "HK1", "1150A", "615P", "DCAS*HJQTEX");
    private static FlightSegment fs4 = new FlightSegment("231G", "17OCT", "CHI", "SFO", "HK1", "920A", "1130A", "DCAS*HJQTEX");

    private static List<FlightSegment> flightSegments1 = new ArrayList<FlightSegment>(Arrays.asList(fs1, fs2));
    private static List<FlightSegment> flightSegments2 = new ArrayList<FlightSegment>(Arrays.asList(fs3, fs4));


    private static final ImmutableMap<String, Ticket> ticketsByTicketNumber = ImmutableMap.<String,Ticket>builder()
            .put("0277850344766", new Ticket(Money.of(CurrencyUnit.USD, 2500.00), "0277850344766", ClassOfService.BUSINESS, flightSegments1))
            .put("0347850344755", new Ticket(Money.of(CurrencyUnit.USD, 700.00), "0347850344755", ClassOfService.ECONOMY, flightSegments2))
            .build();

    @Override
    public Ticket fetchTicket(String ticketNumber) throws TicketNotFoundException {
        return Optional.ofNullable(ticketsByTicketNumber.get(ticketNumber)).orElseThrow(TicketNotFoundException::new);
    }
}
