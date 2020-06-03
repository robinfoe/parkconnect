package org.park.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document
public class Location {

    @Getter @Setter
    private Object locationNumber;

    @Getter @Setter
    private Object locationName;

    @Getter @Setter
    private Object address;

    @Getter @Setter
    private Object city;

    @Getter @Setter
    private Object state;

    @Getter @Setter
    private Object zipCode;

    @Getter @Setter
    private Object phoneNumber;

    @Getter @Setter
    private Object faxNumber;

    @Getter @Setter
    private Object latitude;

    @Getter @Setter
    private Object longitude;

    @Getter @Setter
    private Object location;

    @Override
    public String toString()
    {
        return "Parks{" +
                "locationNumber=" + locationNumber +
                ", locationName=" + locationName +
                ", address=" + address +
                ", city=" + city +
                ", state=" + state +
                ", zipCode=" + zipCode +
                ", phoneNumber=" + phoneNumber +
                ", faxNumber=" + faxNumber +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", location=" + location +
                '}';
    }
    
}
