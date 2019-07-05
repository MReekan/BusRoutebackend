package com.example.busroutebackend;

public class TravellingPath {

    String travellingId;
    String locationOne;
    String locationTwo;
    String ticketFees;
    String travellingDistance;


    public TravellingPath() {

    }

    public TravellingPath(String travellingId, String locationOne, String locationTwo, String travellingDistance,String ticketFees) {
        this.travellingId = travellingId;
        this.locationOne = locationOne;
        this.locationTwo = locationTwo;
        this.travellingDistance = travellingDistance;
        this.ticketFees = ticketFees;
    }

    public String getTravellingId() {
        return travellingId;
    }

    public String getLocationOne() {
        return locationOne;
    }

    public String getLocationTwo() {
        return locationTwo;
    }

    public String getTicketFees() {
        return ticketFees;
    }

    public String getTravellingDistance() {
        return travellingDistance;
    }


//    public TravellingPath(String travellingId,String ticketFees) {
//        this.travellingId = travellingId;
//        this.ticketFees = ticketFees;
//    }
}
