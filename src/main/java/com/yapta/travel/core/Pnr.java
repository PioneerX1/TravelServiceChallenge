package com.yapta.travel.core;

import java.util.List;

/**
 * Created by lacrookedbeat on 4/20/18.
 */
public class Pnr {

    /*
    NOTE: PNR model was originally just a String, but now includes a record locator String, and a List of Ticket objects.
     */

    private String recordLoc;
    private List<Ticket> tickets;

    public Pnr(String recordLoc, List<Ticket> tickets) {
        this.recordLoc = recordLoc;
        this.tickets = tickets;
    }

    public String getRecordLoc() {
        return recordLoc;
    }

    public void setRecordLoc(String recordLoc) {
        this.recordLoc = recordLoc;
    }

    public List<Ticket> getTickets() { return tickets; }

    @Override
    public boolean equals(Object otherPnr) {
        if(!(otherPnr instanceof Pnr)) {
            return false;
        } else {
            Pnr newPnr = (Pnr) otherPnr;
            return this.getRecordLoc().equals(newPnr.getRecordLoc()) &&
                    this.getTickets().size() == newPnr.getTickets().size();     // just checking size for now
        }
    }

    @Override
    public String toString() {
        String pnrTicketOutput = "";
        for(Ticket ticket : this.tickets) {
            pnrTicketOutput += "        AS " + ticket.toString() + "\n";
        }

        return "RECLOC: " + this.recordLoc + "\n"
                + "   FLIGHTS: \n"
                + pnrTicketOutput;
    }
}
