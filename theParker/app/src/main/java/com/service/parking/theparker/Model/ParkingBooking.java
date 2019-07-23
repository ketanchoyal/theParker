package com.service.parking.theparker.Model;

public class ParkingBooking {
    private String parkingId;
    private String transactionId;
    private String timestamp;
    private String by;
    private String spotHost;
    private String slotNo;
    private String parkingArea;

    public ParkingBooking() {
    }

    public ParkingBooking(String parkingId, String transactionId, String timestamp, String by, String spotHost, String slotNo, String parkingArea) {
        this.parkingId = parkingId;
        this.transactionId = transactionId;
        this.timestamp = timestamp;
        this.by = by;
        this.spotHost = spotHost;
        this.slotNo = slotNo;
        this.parkingArea = parkingArea;
    }

    public String getParkingId() {
        return parkingId;
    }

    public void setParkingId(String parkingId) {
        this.parkingId = parkingId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getSpotHost() {
        return spotHost;
    }

    public void setSpotHost(String spotHost) {
        this.spotHost = spotHost;
    }

    public String getSlotNo() {
        return slotNo;
    }

    public void setSlotNo(String slotNo) {
        this.slotNo = slotNo;
    }

    public String getParkingArea() {
        return parkingArea;
    }

    public void setParkingArea(String parkingArea) {
        this.parkingArea = parkingArea;
    }
}
