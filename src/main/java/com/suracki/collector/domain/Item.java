package com.suracki.collector.domain;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="item")
public class Item {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int itemId;

    @NotEmpty(message = "Item Name (eg: Card Name) is mandatory.")
    private String itemName;

    @NotEmpty(message = "Item Number (eg: Issue Number) is mandatory.")
    private int itemNumber;

    @NotEmpty(message = "Item Quantity is mandatory.")
    private int itemQuantity;

    private String itemCondition;
    private String comments;
    private String detail;

    @NotEmpty(message = "Type is mandatory.")
    private String type;

    private int storageLocation;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getItemCondition() {
        return itemCondition;
    }

    public void setItemCondition(String itemCondition) {
        this.itemCondition = itemCondition;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(int storageLocation) {
        this.storageLocation = storageLocation;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}
