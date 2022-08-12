package com.suracki.collector.security;

public class SessionDetails {

    Integer lastPageNumber;
    String lastViewedType;
    String lastViewedLocation;

    public Integer getLastPageNumber() {
        return lastPageNumber;
    }

    public void setLastPageNumber(Integer lastPageNumber) {
        this.lastPageNumber = lastPageNumber;
    }

    public String getLastViewedType() {
        return lastViewedType;
    }

    public void setLastViewedType(String lastViewedType) {
        this.lastViewedType = lastViewedType;
    }

    public String getLastViewedLocation() {
        return lastViewedLocation;
    }

    public void setLastViewedLocation(String lastViewedLocation) {
        this.lastViewedLocation = lastViewedLocation;
    }
}
