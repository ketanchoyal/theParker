package com.service.parking.theparker.Model;


public class UserProfile {

    public String Email = " ";
    public String Mobile_no;
    public String Name;

    public UserProfile(String email, String mobile_no, String name) {
        Email = email;
        Mobile_no = mobile_no;
        Name = name;
    }

    public UserProfile(String mobile_no, String name) {
        Mobile_no = mobile_no;
        Name = name;
    }

    public UserProfile() {
    }
}
