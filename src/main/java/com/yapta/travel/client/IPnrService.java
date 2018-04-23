package com.yapta.travel.client;

import com.yapta.travel.core.Pnr;

public interface IPnrService {

    /**
     * Returns the raw PNR associated with the given record locator
     * @param recordLocator the record locator of the PNR to fetch
     * @return example:
     *      RECLOC: ABC123
     *      FLIGHTS:
     *      1. AS 0277850344766
     *          a. 487K 10OCT SEALAX HK1   250P  535P /DCAS*HJQTEX /E*
     *          b. 486T 18OCT LAXSEA HK1   230P  513P /DCAS*HJQTEX /E*l
     * The "FLIGHTS" section has a list of ticket numbers, which each have a list of flight segments on the ticket. In the
     * example above, ticket number 0277850344766 is a direct, round-trip flight from SEA to LAX and back. The format of each segment is:
     * {flightNumber} {departureDate} {originDestination} {segmentStatus}   {departureTime} {arrivalTime}   /{fareLadder} /E*
     */
    Pnr fetchPnr(String recordLocator) throws PnrNotFoundException;
}
