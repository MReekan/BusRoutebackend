package com.example.busroutebackend;

public class Route {

    private String routeId;
    private String routeNumber;


    public Route(){

    }

    public Route(String routeId, String routeNumber) {
        this.routeId = routeId;
        this.routeNumber = routeNumber;
    }

    public String getRouteId() {
        return routeId;
    }

    public String getRouteNumber() {
        return routeNumber;
    }
}
