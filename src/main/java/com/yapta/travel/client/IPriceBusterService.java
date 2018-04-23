package com.yapta.travel.client;

import com.yapta.travel.core.Pnr;
import com.yapta.travel.core.PriceQuote;
import com.yapta.travel.core.Ticket;

import java.util.List;

/**
 * Created by lacrookedbeat on 4/19/18.
 */
public interface IPriceBusterService {
    /*
    Returns a list of cheaper price quotes that have the same class of service, or throws the exception
     */

    List<PriceQuote> fetchCheaperQuotes(Ticket ticket) throws PriceQuoteNotFoundException;
}
