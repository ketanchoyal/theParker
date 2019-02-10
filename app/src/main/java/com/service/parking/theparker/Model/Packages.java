package com.service.parking.theparker.Model;

public class Packages {

    private Object package_name;
    private Object cars_selected;
    private Object bikes_selected;
    private Object price;
    private Object status;
    private Object id;


    public Packages(Object package_name, Object cars_selected, Object bikes_selected, Object price, Object status, Object id) {

        this.package_name = package_name;
        this.cars_selected = cars_selected;
        this.bikes_selected = bikes_selected;
        this.price = price;
        this.status = status;
        this.id = id;
    }

    public Packages() {
    }

    public String getPackage_name() {
        return package_name.toString();
    }

    public void setPackage_name(Object package_name) {
        this.package_name = package_name;
    }

    public String getCars_selected() {
        return cars_selected.toString();
    }

    public void setCars_selected(Object cars_selected) {
        this.cars_selected = cars_selected;
    }

    public String getBikes_selected() {
        return bikes_selected.toString();
    }

    public void setBikes_selected(Object bikes_selected) {
        this.bikes_selected = bikes_selected;
    }

    public String  getPrice() {
        return price.toString();
    }

    public void setPrice(Object price) {
        this.price = price;
    }

    public String getStatus() {
        return status.toString();
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public String getId() {
        return id.toString();
    }

    public void setId(Object id) {
        this.id = id;
    }
}
