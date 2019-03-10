package com.service.parking.theparker.Model;


public class UserProfile {

    public String Email = " ";
    public String Mobile_no;
    public String Name;
    public String Total_spots;
    public String Spots_used;
    public String Balance;
    public String Earnings;

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

    public UserProfile(String email, String mobile_no, String name, String total_spots, String spots_used, String balance, String earnings) {
        Email = email;
        Mobile_no = mobile_no;
        Name = name;
        Total_spots = total_spots;
        Spots_used = spots_used;
        Balance = balance;
        Earnings = earnings;
    }
}
