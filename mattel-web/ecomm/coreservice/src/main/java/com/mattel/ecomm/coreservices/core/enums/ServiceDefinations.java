package com.mattel.ecomm.coreservices.core.enums;

public enum ServiceDefinations {

    DOMAIN("https://mdev.americangirl.com/wcs/resources/store/10651")/* this will be taken from Config*/,
    LOGIN("/loginidentity?responseFormat=json");


    private final String endPoint;
    private ServiceDefinations(String endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public String toString() {
        return endPoint;
    }
}
