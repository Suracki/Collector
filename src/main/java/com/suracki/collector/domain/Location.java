package com.suracki.collector.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="location")
public class Location {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int locationId;

    @NotEmpty(message = "Storage Name is mandatory")
    private String storageName;

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }
}
