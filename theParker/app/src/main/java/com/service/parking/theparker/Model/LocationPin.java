package com.service.parking.theparker.Model;

import java.util.HashMap;
import java.util.Map;

public class LocationPin {

    private String by;
    private String description;
    private String price;
    private String type;
    private String visibility;
    private String numberofspot;
    private String pinkey;
    private Map<String,Boolean> features = new HashMap<>();
    private String photos;
    private Map<String,Double> pinloc  = new HashMap<>();
    private String address;
    private String mobile;
    private String area;

    public LocationPin() { }
    public LocationPin(String by, String description, String price, String type, String visibility, String numberofspot, String pinkey, Map<String, Boolean> features, String photos, Map<String, Double> pinloc, String address, String mobile, String area) {
        this.by = by;
        this.description = description;
        this.price = price;
        this.type = type;
        this.visibility = visibility;
        this.numberofspot = numberofspot;
        this.pinkey = pinkey;
        this.features = features;
        this.photos = photos;
        this.pinloc = pinloc;
        this.address = address;
        this.mobile = mobile;
        this.area = area;
    }

    public LocationPin(String by, String description, String price, String type, String numberofspot, String pinkey, Map<String, Boolean> features, Map<String, Double> pinloc, String address, String mobile, String area) {
        this.by = by;
        this.description = description;
        this.price = price;
        this.type = type;
        this.numberofspot = numberofspot;
        this.pinkey = pinkey;
        this.features = features;
        this.pinloc = pinloc;
        this.address = address;
        this.mobile = mobile;
        this.area = area;
    }

    public LocationPin(String by, String description, String price, String type, String numberofspot, Map<String, Boolean> features, Map<String, Double> pinloc, String address, String mobile, String area) {
        this.by = by;
        this.description = description;
        this.price = price;
        this.type = type;
        this.numberofspot = numberofspot;
        this.features = features;
        this.pinloc = pinloc;
        this.address = address;
        this.mobile = mobile;
        this.area = area;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getNumberofspot() {
        return numberofspot;
    }

    public void setNumberofspot(String numberofspot) {
        this.numberofspot = numberofspot;
    }

    public String getPinkey() {
        return pinkey;
    }

    public void setPinkey(String pinkey) {
        this.pinkey = pinkey;
    }

    public Map<String, Boolean> getFeatures() {
        return features;
    }

    public void setFeatures(Map<String, Boolean> features) {
        this.features = features;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public Map<String, Double> getPinloc() {
        return pinloc;
    }

    public void setPinloc(Map<String, Double> pinloc) {
        this.pinloc = pinloc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
