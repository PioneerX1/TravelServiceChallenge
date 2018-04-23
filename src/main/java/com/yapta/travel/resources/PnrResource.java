package com.yapta.travel.resources;

import com.google.common.collect.ImmutableMap;
import com.yapta.travel.client.IPnrService;
import com.yapta.travel.client.IPriceBusterService;
import com.yapta.travel.client.PnrNotFoundException;
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

@Path("/pnrs/{recordLocator}")
@Produces(MediaType.TEXT_PLAIN)
public class PnrResource {

    private final IPnrService pnrService;
    private Pnr currentPnr;

    public PnrResource(IPnrService pnrService) {
        this.pnrService = pnrService;
    }

    @GET
    public String fetchPnr(@PathParam("recordLocator")String recordLocator) {
        try {
            currentPnr = pnrService.fetchPnr(recordLocator);
            return currentPnr.toString();
        } catch (PnrNotFoundException e) {
            return "PNR NOT FOUND";
        }
    }
}
