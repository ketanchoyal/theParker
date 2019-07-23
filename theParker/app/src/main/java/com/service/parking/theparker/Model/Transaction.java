package com.service.parking.theparker.Model;

public class Transaction {

    private String amount;
    private String by;
    private String forr;
    private String id;
    private String of;
    private String timeStamp;
    private String transaction_key;

    public Transaction() {
    }

    public Transaction(String amount, String by, String forr, String id, String of, String timeStamp) {
        this.amount = amount;
        this.by = by;
        this.forr = forr;
        this.id = id;
        this.of = of;
        this.timeStamp = timeStamp;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getForr() {
        return forr;
    }

    public void setForr(String forr) {
        this.forr = forr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOf() {
        return of;
    }

    public void setOf(String of) {
        this.of = of;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTransaction_key() {
        return transaction_key;
    }

    public void setTransaction_key(String transaction_key) {
        this.transaction_key = transaction_key;
    }
}
