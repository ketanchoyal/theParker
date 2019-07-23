package com.service.parking.theparker_adminapp;

public class PackageModel {

    Object package_name;
    Object cars_selected;
    Object bikes_selected;
    Object price;
    Object status;
    Object id;


    public PackageModel(Object package_name, Object cars_selected, Object bikes_selected, Object price, Object status, Object id) {

        this.package_name = package_name;
        this.cars_selected = cars_selected;
        this.bikes_selected = bikes_selected;
        this.price = price;
        this.status = status;
        this.id = id;
    }

    public PackageModel() {
    }
}
