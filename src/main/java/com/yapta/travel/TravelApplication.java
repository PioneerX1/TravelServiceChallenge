package com.yapta.travel;

import com.yapta.travel.client.*;
import com.yapta.travel.core.PriceQuote;
import com.yapta.travel.health.PnrHealthCheck;
import com.yapta.travel.resources.PBResource;
import com.yapta.travel.resources.PnrResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.List;

public class TravelApplication extends Application<TravelConfiguration> {

    public static void main(String[] args) throws Exception {
        new TravelApplication().run(args);
    }

    @Override
    public String getName() {
        return "travel-application";
    }

    @Override
    public void initialize(Bootstrap<TravelConfiguration> bootstrap) {

    }

    @Override
    public void run(TravelConfiguration travelConfiguration, Environment environment) throws Exception {

        final IPnrService pnrService = new PnrService();
        final IPriceBusterService priceBusterService = new PriceBusterService();
        final ITicketService ticketService = new TicketService();

        final PnrResource pnrResource = new PnrResource(pnrService);
        environment.jersey().register(pnrResource);

        // NEW HTTP ENDPOINT: go to path("/pricebuster/{recordLocator}") to see this
        final PBResource pbResource = new PBResource(priceBusterService, ticketService, pnrService);
        environment.jersey().register(pbResource);

        final PnrHealthCheck healthCheck = new PnrHealthCheck(pnrService);
        environment.healthChecks().register("pnr", healthCheck);
    }
}
