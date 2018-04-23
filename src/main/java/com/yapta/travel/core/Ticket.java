package com.yapta.travel.core;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.joda.money.Money;

import java.util.List;

public class Ticket {

    /*
    NOTE: Ticket model now includes a List of Flight Segments and must be instantiated with this.
     */

    private final Money price;
    private final String ticketNumber;
    private final ClassOfService classOfService;
    private final List<FlightSegment> flightSegments;

    public Ticket(Money price, String ticketNumber, ClassOfService classOfService, List<FlightSegment> flightSegments) {

        this.price = price;
        this.ticketNumber = ticketNumber;
        this.classOfService = classOfService;
        this.flightSegments = flightSegments;
    }

    public Money getPrice() {
        return price;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public ClassOfService getClassOfService() {
        return classOfService;
    }

    public List<FlightSegment> getFlightSegments() { return flightSegments; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equal(getPrice(), ticket.getPrice()) &&
                Objects.equal(getTicketNumber(), ticket.getTicketNumber()) &&
                getClassOfService() == ticket.getClassOfService();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPrice(), getTicketNumber(), getClassOfService());
    }

    @Override
    public String toString() {
        String ticketOutput = this.ticketNumber + "    " + this.price + "    " + this.classOfService + "\n";
        if (this.flightSegments.size() != 0) {
            for(FlightSegment fs : this.flightSegments) {
                ticketOutput += "           " + fs.toString() + "\n";
            }
        }
        return ticketOutput;
    }
}
