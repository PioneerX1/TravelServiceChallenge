package com.yapta.travel.health;

import com.codahale.metrics.health.HealthCheck;
import com.google.common.base.Strings;
import com.yapta.travel.client.IPnrService;

public class PnrHealthCheck extends HealthCheck {

    private final IPnrService pnrService;

    public PnrHealthCheck(IPnrService pnrService) {

        this.pnrService = pnrService;
    }

    @Override
    protected Result check() throws Exception {
        final String pnr = pnrService.fetchPnr("ABC123").toString();
        if (Strings.isNullOrEmpty(pnr)) {
            return Result.unhealthy("Expected PNR does not exist");
        }
        return Result.healthy();
    }
}
