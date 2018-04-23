package com.yapta.travel.resources;

/**
 * Created by lacrookedbeat on 4/22/18.
 */

import com.yapta.travel.client.*;
import com.yapta.travel.core.Pnr;
import com.yapta.travel.core.PriceQuote;
import com.yapta.travel.core.Ticket;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/pricebuster/{recordLocator}")
@Produces(MediaType.TEXT_PLAIN)
public class PBResource {

    private final IPriceBusterService priceBusterService;
    private final ITicketService ticketService;
    private final IPnrService pnrService;

    public PBResource(IPriceBusterService priceBusterService, ITicketService ticketService, IPnrService pnrService) {
        this.priceBusterService = priceBusterService;
        this.ticketService = ticketService;
        this.pnrService = pnrService;
    }

    @GET
    public String fetchPnr(@PathParam("recordLocator") String recordLocator) {

        // first, find the PNR, so you can retrieve its tickets
        Pnr currentPnr;
        try {
            currentPnr = pnrService.fetchPnr(recordLocator);
        } catch (PnrNotFoundException e) {
            return "PNR NOT FOUND";
        }

        // second, find the tickets associated with that PNR, then verify those tickets with TicketService
        List<Ticket> ticketList = currentPnr.getTickets();
        String masterOutput = "PRICEBUSTER FINDS PNR# " + currentPnr.getRecordLoc() + "\n\n";
        for(Ticket expectedTicket : ticketList) {
            // verify with TicketService
            try {
                Ticket validTicket = ticketService.fetchTicket(expectedTicket.getTicketNumber());
                masterOutput += "   ---Cheaper Fares for Ticket# " + expectedTicket.getTicketNumber() + "\n";

                // third, find the cheaper price quotes for each verified ticket
                try {
                    List<PriceQuote> priceQuoteList = priceBusterService.fetchCheaperQuotes(validTicket);
                    for(PriceQuote pq : priceQuoteList) {
                        masterOutput += "       " + pq.getClassOfService() + "  " + pq.getPrice() + "\n";
                    }
                    masterOutput += "\n";
                } catch (PriceQuoteNotFoundException e) {
                    masterOutput += "       No lower prices found, savings complete!" + "\n";
                }

            } catch (TicketNotFoundException e) {
                masterOutput += "   ---CANNOT FIND OR VERIFY AUTHENTICITY OF TICKET# " + expectedTicket.getTicketNumber() + "\n";
            }
        }
        return masterOutput;
    }
}
