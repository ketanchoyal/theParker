package com.service.parking.theparker.Model;


public class ProfileModel {

    public String Email = " ";
    public String Mobile_no;
    public String Name;

    public ProfileModel(String email, String mobile_no, String name) {
        Email = email;
        Mobile_no = mobile_no;
        Name = name;
    }

    public ProfileModel(String mobile_no, String name) {
        Mobile_no = mobile_no;
        Name = name;
    }

    public ProfileModel() {
    }
}
