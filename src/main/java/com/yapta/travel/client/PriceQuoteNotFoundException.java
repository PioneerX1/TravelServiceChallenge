package com.yapta.travel.client;

/**
 * Created by lacrookedbeat on 4/22/18.
 */
public class PriceQuoteNotFoundException extends Exception {

    /*
    Exception is thrown if there are not price quotes found within same class of service that are cheaper
     */

    public PriceQuoteNotFoundException() {
        super();
    }
}