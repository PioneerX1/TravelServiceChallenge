#Yapta Take Home Question: Travel Service

##Summary
Your goal here is to create a new travel service which, given the record locator for an existing PNR (see the glossary at the bottom of the README), can provide a list of lower prices in the same class of service as each of the tickets on the PNR. 

To accomplish this, you'll be integrating with a few existing web services (for the purposes of this question, the 'services' have been simplified into fake clients which return stubbed data). Each service can provide a piece of the functionality you need. Two of the services are relatively modern, and will return data to you as POJOs. Unfortunately, one of the services is decades old, and is only capable of returning text - it will be up to you to extract the data you need from this service.

##Requirements for Completion
As mentioned above, the primary requirement is to create an HTTP endpoint that takes a record locator, and returns a list of lower prices in the same class of service as the existing tickets on the PNR. Beyond that, we just want to see what your idea of good, clean code is. Good luck!

##Getting Started
At Yapta, we use IntelliJ as an IDE, which has a free community edition [here](https://www.jetbrains.com/idea/download/), but you can use whichever tools you're most comfortable with.

If you do elect to use IntelliJ, you can import the project by selecting File->Open->navigating to the pom.xml at the root of the project directory. [This](http://mikelynchgames.com/software-development/how-to-run-a-dropwizard-application-in-intellij-idea/) blog post provides instructions on how to run/debug a Dropwizard app through IntelliJ. Alternatively, you can build and run from the terminal by running `mvn package` to compile, test, and package the app, followed by `java -jar target/travel-service-1.0.0.jar server local.yml` to run it. This will start up the main endpoint on localhost:8080, and the admin endpoints on localhost:8081. To test that things are working as intended, try browsing to localhost:8080/pnrs/ABC123. You should get a block of text in response.
 
The project skeleton is implemented using Dropwizard. If you're unfamiliar with it, they provide a helpful [Getting Started guide](http://www.dropwizard.io/1.0.0/docs/getting-started.html), and pretty thorough documentation. Additionally, the /pnrs endpoint has been included as an example of how to create an HTTP endpoint using Dropwizard. Finally, there are several simple unit tests as examples - we definitely encourage you to add your own tests so you can be confident that your solution works as expected.

##Existing Services
The following 'services' already exist in the project, and can be called to get the data you need to implement your solution. These are simple stubs, which have 1 PNR loaded in them, accessible using the record locator `ABC123`. If you run the app and go to /pnrs/ABC123, you can see what the raw PNR looks like.


```
#!java

interface TicketService {

    /**
     * Returns details about the given ticket
     * @param ticketNumber The ticketNumber to look up
     */
    Ticket fetchTicket(String ticketNumber) throws TicketNotFoundException;
}

public class Ticket {

    private final Money price;
    private final String ticketNumber;
    private final ClassOfService classOfService;

...
}

interface PnrService {

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
    String fetchPnr(String recordLocator) throws PnrNotFoundException;
}

interface PriceService {

    /**
     * Provides pricing for the given list of segments
     * @param segmentsToPrice the list of flight segments to provide pricing for
     * @return Returns a list of PriceQuotes for the given trip
     */
    List<PriceQuote> fetchPrices(List<FlightSegment> segmentsToPrice);
}

public class PriceQuote {

    private final Money price;
    private final ClassOfService classOfService;
...
}

public class FlightSegment {

    private final String flightNumber;
    private final String departureDate;
    private final String origin;
    private final String destination;
...
}

```

##Rules

This is a pretty wide open question - we want you to use all the same tools and problem solving techniques you would in a real work scenario. To that end, these are the rules:

 * You are welcome to add any external dependencies you think will help you solve the problem in the best way.
 * You can add as many new packages and classes as you need. You can also make changes to the existing code, as long as the basic functionality of the 3 provided "services" are not altered.
 * Use Google, Bing, Stackoverflow, etc. as much as you need to - they're great tools for real problem solving, and we don't want you to exclude them just because it's an interview question.
 * Our goal is for this to be completable in a few hours of your time (assuming you're already familiar with our tools and Dropwizard), but you can take more time if you feel you need it to best represent your engineering abilities.We only ask that it be finished in time for your follow-up interview, which we will schedule for a couple days after your in-person interview loop.

##Glossary of Travel Terms

**PNR**: Stands for "Passenger Name Record". Generally speaking, it is the object that contains all the details of a trip - passenger information, flights, hotels, car rentals, etc. For this example, the PNR is simplified to contain a very small subset of the data on a real PNR.

**Record Locator**: This is the unique identifier for a PNR. Typically a 6 digit alphanumeric code, e.g. RT5Q9Y.

**Flight Segment**: Flights are represented as collections of flight segments, which are collectively represented by a single ticket. For example, if you had a round trip flight from Seattle to New York with a layover in Chicago on the way there, and a direct flight on the way back there would be 3 segments: SEA->ORD, ORD->JFK, JFK->SEA. These 3 segments are represented with a single ticket.


## Addendum from Mick  4/22/18
**Please note that the PNR data is no longer a String, but organized into a PNR object.
**The FlightSegment model now includes more attributes like segment status, depart time, arrival time, and fare ladder.
**The new PriceBuster service sends its data to the PBResource, and can be viewed by visiting the path with extension of "/pricebuster/{recordLocator}" -- just replace the "pnrs" part of the path with the word "pricebuster"
**As a result of these changes, I also altered and some of the pre-existing J-Unit tests, added new one.