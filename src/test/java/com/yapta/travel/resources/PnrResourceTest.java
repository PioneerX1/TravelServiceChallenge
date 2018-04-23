package com.yapta.travel.resources;

import com.yapta.travel.client.*;
import com.yapta.travel.core.ClassOfService;
import com.yapta.travel.core.FlightSegment;
import com.yapta.travel.core.Pnr;
import com.yapta.travel.core.Ticket;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class PnrResourceTest {

    /*
    PNR Resource Test was modified since the PNR is now returned as a PNR object instead of String
     */

    public static final IPnrService mockPnrService = mock(PnrService.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new PnrResource(mockPnrService))
            .build();

    @After
    public void tearDown() {
        reset(mockPnrService);
    }

    @Test
    public void testGetPnrReturnsExpectedPnrGivenExistingPnr() throws PnrNotFoundException {
        //Arrange
        String recordLocator = "ABC123";
        List<FlightSegment> flightSegments1 = new ArrayList<>();
        FlightSegment fs1 = new FlightSegment("487K", "10OCT", "SEA", "LAX", "HK1", "250P", "535P", "DCAS*HJQTEX");
        flightSegments1.add(fs1);
        FlightSegment fs2 = new FlightSegment("486T", "18OCT", "LAX", "SEA", "HK1", "230P", "513P", "DCAS*HJQTEX");
        flightSegments1.add(fs2);
        List<FlightSegment> flightSegments2 = new ArrayList<>();
        FlightSegment fs3 = new FlightSegment("223H", "12OCT", "SFO", "CHI", "HK1", "1150A", "615P", "DCAS*HJQTEX");
        FlightSegment fs4 = new FlightSegment("231G", "17OCT", "CHI", "SFO", "HK1", "920A", "1130A", "DCAS*HJQTEX");
        flightSegments2.add(fs3);
        flightSegments2.add(fs4);
        List<Ticket> tickets = new ArrayList<>();
        Ticket ticket1 = new Ticket(Money.of(CurrencyUnit.USD, 2500.00), "0277850344766", ClassOfService.BUSINESS, flightSegments1);
        tickets.add(ticket1);
        Ticket ticket2 = new Ticket(Money.of(CurrencyUnit.USD, 700.00), "0347850344755", ClassOfService.ECONOMY, flightSegments2);
        tickets.add(ticket2);
        Pnr expectedPnr = new Pnr(recordLocator, tickets);

        when(mockPnrService.fetchPnr(recordLocator)).thenReturn(expectedPnr);

        //Act
        String actualPnr = resources.client().target("/pnrs/" + recordLocator).request().get(String.class);

        //Assert
        Assert.assertEquals(expectedPnr.toString(), actualPnr);
        verify(mockPnrService).fetchPnr(recordLocator);
    }

    @Test
    public void testGetPnrReturnsNotFoundMessageGivenNonExistingPnr() throws PnrNotFoundException {

        //Arrange
        when(mockPnrService.fetchPnr("NONPNR")).thenThrow(new PnrNotFoundException());
        String expectedResponse = "PNR NOT FOUND";

        //Act
        String actualResponse = resources.client().target("/pnrs/NONPNR").request().get(String.class);

        //Assert
        Assert.assertEquals(expectedResponse, actualResponse);
    }

}
