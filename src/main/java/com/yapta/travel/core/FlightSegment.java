package com.yapta.travel.core;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class FlightSegment {

    /*
    FlightSegment model now includes Segment Status, Departure Time, Arrival Time, and Fare Ladder properties.
     */

    private final String flightNumber;
    private final String departureDate;
    private final String origin;
    private final String destination;
    private final String segmentStatus;
    private final String departureTime;
    private final String arrivalTime;
    private final String fareLadder;

    public FlightSegment(String flightNumber, String departureDate, String origin, String destination,
                        String segmentStatus, String departureTime, String arrivalTime, String fareLadder)
    {
        this.flightNumber = flightNumber;
        this.departureDate = departureDate;
        this.origin = origin;
        this.destination = destination;
        this.segmentStatus = segmentStatus;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.fareLadder = fareLadder;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getSegmentStatus() {
        return segmentStatus;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getFareLadder() {
        return fareLadder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightSegment that = (FlightSegment) o;
        return Objects.equal(getFlightNumber(), that.getFlightNumber()) &&
                Objects.equal(getDepartureDate(), that.getDepartureDate()) &&
                Objects.equal(getOrigin(), that.getOrigin()) &&
                Objects.equal(getDestination(), that.getDestination());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getFlightNumber(), getDepartureDate(), getOrigin(), getDestination());
    }

    @Override
    public String toString() {
        String fsOutput = this.flightNumber + "  " + this.departureDate + "  " + this.origin + this.destination +
                "  " + this.segmentStatus + "  " + this.departureTime + "  " + this.arrivalTime + "  " +
                this.fareLadder;
        return fsOutput;
    }
}
