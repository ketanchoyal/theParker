package com.service.parking.theparker.Services;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.service.parking.theparker.Controller.Activity.ParkingPinDetailActivity;
import com.service.parking.theparker.Controller.Adapters.MySpotsAdapter;
import com.service.parking.theparker.Controller.Adapters.PackageAdapter;
import com.service.parking.theparker.Controller.Adapters.SlotsAdapter;
import com.service.parking.theparker.Model.LocationPin;
import com.service.parking.theparker.Model.Packages;
import com.service.parking.theparker.Model.ParkingBooking;
import com.service.parking.theparker.Model.Transaction;
import com.service.parking.theparker.Model.UserProfile;
import com.service.parking.theparker.Utils.LocationConstants;
import com.service.parking.theparker.Utils.PackageConstants;
import com.service.parking.theparker.Utils.ParkingBookingConstants;
import com.service.parking.theparker.Utils.TransactionConstants;
import com.service.parking.theparker.View.SnackbarWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

//import retrofit2.Retrofit;

public class NetworkServices {
    static private DatabaseReference REF = FirebaseDatabase.getInstance().getReference();

    public static UserProfile userProfile;

    public static class ProfileData {

        static DatabaseReference mProfileReference = REF.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Profile");

        public static void updateData(String Name, String email, Context con) {

            final Map<String,Object> UserdataMap = new HashMap<>();
            UserdataMap.put("Name",Name);
            UserdataMap.put("Email",email.trim());

            mProfileReference.updateChildren(UserdataMap).addOnCompleteListener(v -> {

                if (v.isSuccessful()) {
                    SnackbarWrapper.make(con, "Sucessfully Updated Profile");
                }
                else {
                    SnackbarWrapper.make(con, "Please try again!");
                }
            });

        }

        public static void setData(Object name_et, Object email_et, Object mobile_et) {

            mProfileReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                    NetworkServices.userProfile = userProfile;

                    if(name_et != null) {
                        if(name_et instanceof EditText) {
                            EditText name = (EditText) name_et;
                            name.setText(userProfile.Name);
                        }
                        if(name_et instanceof TextView) {
                            TextView name = (TextView) name_et;
                            name.setText(userProfile.Name);
                        }
                    }


                    if(email_et != null) {
                        if(email_et instanceof EditText) {
                            EditText email = (EditText) email_et;
                            email.setText(userProfile.Email);
                        }
                        if(email_et instanceof TextView) {
                            TextView email = (TextView) email_et;
                            email.setText(userProfile.Email);
                        }
                    }

                    if(mobile_et != null) {
                        if(mobile_et instanceof EditText) {
                            EditText Mobile_no = (EditText) mobile_et;
                            Mobile_no.setText(userProfile.Mobile_no);
                        }
                        if(mobile_et instanceof TextView) {
                            TextView Mobile_no = (TextView) mobile_et;
                            Mobile_no.setText(userProfile.Mobile_no);
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        public static void getProfileData() {

            mProfileReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                    NetworkServices.userProfile = userProfile;

                    Log.d("XYZ ABC",NetworkServices.userProfile.Email + " " + NetworkServices.userProfile.Mobile_no + " " + NetworkServices.userProfile.Name);
                    Log.d("XYZ ABC",NetworkServices.userProfile.Total_spots + " " + NetworkServices.userProfile.Balance + " " + NetworkServices.userProfile.Earnings);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        public static void getProfileDataById(String id,TextView nameview) {
            DatabaseReference userRef = REF.child("Users").child(id).child("Profile");

            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    UserProfile user = dataSnapshot.getValue(UserProfile.class);
                    nameview.setText(user.Name);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    public static class PackagesData {
        static DatabaseReference mPackageRef = REF.child("packages");

        public static void getPackages(List<Packages> list, PackageAdapter adapter) {
            mPackageRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    Map<String, Object> data = (Map<String, Object>) dataSnapshot.getValue();

                    Log.d("ID : ",dataSnapshot.getKey());

                    Packages cm = new Packages(data.get(PackageConstants.FB_package_name),data.get(PackageConstants.FB_no_of_cars),data.get(PackageConstants.FB_no_of_bike),
                            data.get(PackageConstants.FB_package_price),data.get(PackageConstants.FB_package_status),dataSnapshot.getKey());
                    list.add(cm);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
                {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot)
                {
//                    Toast.makeText(getApplicationContext(),""+dataSnapshot,Toast.LENGTH_LONG).show();

                    String keyID =dataSnapshot.getKey();
                    for(int i = 0;i<=list.size()-1;i++)
                    {
                        Packages object = list.get(i);
                        if(object.getId().equals(keyID))
                        {
                            list.remove(object);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public static class ParkingPin {
        static DatabaseReference mGlobalLocationPinRef = REF.child("GlobalPins");
        static DatabaseReference mUserLocationPinRef = REF.child("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("MyLocationPins");
        public static ArrayList<String> parkingAreas = new ArrayList<>();
        public static Map<String,LocationPin> globalPins = new HashMap<>();

        public static void setLocationPin(LocationPin locationPin) {

            String area = locationPin.getArea();

            String by = FirebaseAuth.getInstance().getCurrentUser().getUid();

            Map<String,Object> locationpin = new HashMap<>();
            locationpin.put(LocationConstants.by,by);
            locationpin.put(LocationConstants.price,locationPin.getPrice());
            locationpin.put(LocationConstants.visibility,locationPin.getVisibility());
            locationpin.put(LocationConstants.mobile,locationPin.getMobile());
            locationpin.put(LocationConstants.pinloc,locationPin.getPinloc());
            locationpin.put(LocationConstants.address,locationPin.getAddress());
            locationpin.put(LocationConstants.features,locationPin.getFeatures());
            locationpin.put(LocationConstants.type,locationPin.getType());
            locationpin.put(LocationConstants.numberofspot,locationPin.getNumberofspot());
            locationpin.put(LocationConstants.description,locationPin.getDescription());
            locationpin.put(LocationConstants.area,area);

            String pinkey = mGlobalLocationPinRef.push().getKey();

            mGlobalLocationPinRef.child(area).child(pinkey).setValue(locationpin, (databaseError, databaseReference) -> {
                if (databaseError == null) {
                    mUserLocationPinRef.child(pinkey).setValue(locationpin);

                    int Spots_used = Integer.parseInt(userProfile.Spots_used) + Integer.parseInt(locationPin.getNumberofspot());
                    Map<String,Object> spotsUpdate = new HashMap<>();
                    spotsUpdate.put("Spots_used",""+Spots_used);
                    ProfileData.mProfileReference.updateChildren(spotsUpdate);

                }
            });
        }

        public static void getMyLocationPins(List<LocationPin> locationPinList, MySpotsAdapter mySpotsAdapter) {
            mUserLocationPinRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                    Map<String, Object> pinsSnapshot = (Map<String, Object>) dataSnapshot.getValue();

                    LocationPin pin = dataSnapshot.getValue(LocationPin.class);

//                    Log.d("ID : ",dataSnapshot.getKey());
//
//                    String by = pinsSnapshot.get(LocationConstants.by).toString();
//                    String description = pinsSnapshot.get(LocationConstants.description).toString();
//                    String price = pinsSnapshot.get(LocationConstants.price).toString();
//                    String type = pinsSnapshot.get(LocationConstants.type).toString();
////                    String visibility = pinsSnapshot.get(LocationConstants.visibility).toString();
//                    String numberofspot = pinsSnapshot.get(LocationConstants.numberofspot).toString();
                    String pinkey = dataSnapshot.getKey();
//                    Map<String,Boolean> features = (Map<String, Boolean>) pinsSnapshot.get(LocationConstants.features);
////                    String photos;
//                    Map<String,Double> pinloc  = (Map<String, Double>) pinsSnapshot.get(LocationConstants.pinloc);
//                    String address = pinsSnapshot.get(LocationConstants.address).toString();
//                    String mobile = pinsSnapshot.get(LocationConstants.mobile).toString();
//                    String area = pinsSnapshot.get(LocationConstants.area).toString();
//
//                    Log.d("RANDOM :",by +" "+ description + " "+ price +" "+ type +" "+ features.toString() +" "+ pinloc.toString());
//
//                    LocationPin pin = new LocationPin(by,description,price,type,numberofspot,pinkey,features,pinloc,address,mobile,area);
                    pin.setPinkey(pinkey);
                    locationPinList.add(pin);
                    mySpotsAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {    }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {    }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {  }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }
            });
        }

        public static void getParkingAreas(GoogleMap googleMap) {

            if(!parkingAreas.isEmpty()) {
                for(String area : parkingAreas) {
                    getParkingsOfArea(area,googleMap,false);
                }
            }

            mGlobalLocationPinRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    if (parkingAreas.isEmpty()) {
                        parkingAreas.add(dataSnapshot.getKey());
                        Log.d("RANDOM AREA :",parkingAreas.toString());
                        getParkingsOfArea(dataSnapshot.getKey(),googleMap,false);
                    } else if (!parkingAreas.contains(dataSnapshot.getKey())) {
                        parkingAreas.add(dataSnapshot.getKey());
                        getParkingsOfArea(dataSnapshot.getKey(),googleMap,false);
                        Log.d("RANDOM AREA :",parkingAreas.toString());
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        //Get the Parking Spots from the database of the passed Area
        public static void getParkingsOfArea(String area, GoogleMap googleMap,Boolean fromFragment) {
            if(fromFragment) {
                googleMap.clear();
            }
            if(area == "All") {
                googleMap.clear();
                getParkingAreas(googleMap);
            }
            mGlobalLocationPinRef.child(area).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                    Map<String, Object> pinsSnapshot = (Map<String, Object>) dataSnapshot.getValue();

                    LocationPin pin = dataSnapshot.getValue(LocationPin.class);

//                    Log.d("ID : ",dataSnapshot.getKey());
//
//                    String by = pinsSnapshot.get(LocationConstants.by).toString();
//                    String description = pinsSnapshot.get(LocationConstants.description).toString();
//                    String price = pinsSnapshot.get(LocationConstants.price).toString();
//                    String type = pinsSnapshot.get(LocationConstants.type).toString();
////                    String visibility = pinsSnapshot.get(LocationConstants.visibility).toString();
//                    String numberofspot = pinsSnapshot.get(LocationConstants.numberofspot).toString();
                    String pinkey = dataSnapshot.getKey();
//                    Map<String,Boolean> features = (Map<String, Boolean>) pinsSnapshot.get(LocationConstants.features);
////                    String photos;
//                    Map<String,Double> pinloc  = (Map<String, Double>) pinsSnapshot.get(LocationConstants.pinloc);
//                    String address = pinsSnapshot.get(LocationConstants.address).toString();
//                    String mobile = pinsSnapshot.get(LocationConstants.mobile).toString();
//                    String area = pinsSnapshot.get(LocationConstants.area).toString();
//
//                    Log.d("RANDOM :",by +" "+ description + " "+ price +" "+ type +" "+ features.toString() +" "+ pinloc.toString());
//
//                    LocationPin pin = new LocationPin(by,description,price,type,numberofspot,pinkey,features,pinloc,address,mobile,area);
//                    globalPins.put(pinkey,pin);
                    pin.setPinkey(pinkey);
                    LatLng latLng = new LatLng(pin.getPinloc().get("lat"), pin.getPinloc().get("long"));
                    googleMap.addMarker(new MarkerOptions().position(latLng).title("₹"+pin.getPrice()+"/4 Hour")).setTag(pin);

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {    }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {    }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {  }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }
            });
        }
    }

    public static class TransactionData {
        static DatabaseReference mGlobalTransactions = REF.child("GlobalTransaction").child("Transactions");
        static DatabaseReference mGlobalBalance = REF.child("GlobalBalance");
        static DatabaseReference mUserTransactions = REF.child("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("Transaction");

        public static void doTransaction(Transaction transaction, Object package_or_booking_or_addbalance) {
            Map<String,Object> transactionMap = new HashMap<>();

            Map<String, String> timeStamp = ServerValue.TIMESTAMP;

            transactionMap.put(TransactionConstants.amount,transaction.getAmount());
            transactionMap.put(TransactionConstants.by,transaction.getBy());
            transactionMap.put(TransactionConstants.forr,transaction.getForr());
            transactionMap.put(TransactionConstants.id,transaction.getId());
            transactionMap.put(TransactionConstants.of,transaction.getOf());
            transactionMap.put(TransactionConstants.timeStamp, timeStamp);

            String pinkey = mGlobalTransactions.push().getKey();

            mGlobalTransactions.child(pinkey).setValue(transactionMap,(databaseError, databaseReference) -> {
                if (databaseError == null) {
                    if (transaction.getForr() == "Admin") {
                        //add balance to globalbalance and deduct from loged in user here

                        //update the global balance here(use of cloud functions)
                        mGlobalBalance.child("Balance").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String globalBalance = dataSnapshot.getValue(String.class);
                                int newGlobalBalance = Integer.parseInt(globalBalance) + Integer.parseInt(transaction.getAmount());
                                Map<String,Object> balanceUpdate = new HashMap<>();
                                balanceUpdate.put("Balance",""+newGlobalBalance);

                                mGlobalBalance.updateChildren(balanceUpdate);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) { }
                        });
                        mGlobalBalance.child("Transactions").child(pinkey).setValue(pinkey);

                        Packages packageToBuy = (Packages) package_or_booking_or_addbalance;

                        //Updated the balance and totalSpots for logged in user
                        int newbalance = Integer.parseInt(userProfile.Balance) - Integer.parseInt(transaction.getAmount());
                        int newTotal_spots = Integer.parseInt(userProfile.Total_spots) + Integer.parseInt(packageToBuy.getCars_selected());
                        Map<String,Object> balanceUpdate = new HashMap<>();
                        balanceUpdate.put("Balance",""+newbalance);
                        balanceUpdate.put("Total_spots",""+newTotal_spots);
                        ProfileData.mProfileReference.updateChildren(balanceUpdate);

                        mUserTransactions.child(pinkey).setValue(pinkey);
                    } else if (transaction.getForr() == FirebaseAuth.getInstance().getCurrentUser().getUid()) {
                        //Add balance in loged in user here
                        //of = "Add Balance"
                        //id = "Add Balance"
                        int newbalance = Integer.parseInt(userProfile.Balance) + Integer.parseInt(transaction.getAmount());
                        Map<String,Object> balanceUpdate = new HashMap<>();
                        balanceUpdate.put("Balance",""+newbalance);
                        ProfileData.mProfileReference.updateChildren(balanceUpdate);

                        mUserTransactions.child(pinkey).setValue(pinkey);
                    } else {
                        //of = "Parking"
                        //id = "parkingid"
                        //Add or Subtract balance/earning from both the users here

                        //logged in user
                        ParkingBooking parkingBooking = (ParkingBooking) package_or_booking_or_addbalance;
                        int newbalance = Integer.parseInt(userProfile.Balance) - Integer.parseInt(transaction.getAmount());
                        Map<String,Object> balanceUpdate = new HashMap<>();
                        balanceUpdate.put("Balance",""+newbalance);
                        ProfileData.mProfileReference.updateChildren(balanceUpdate);

                        //for spot holder earnings
                        DatabaseReference spotHolder = REF.child("Users").child(transaction.getForr());

                        spotHolder.child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                UserProfile spotHolderProfile = dataSnapshot.getValue(UserProfile.class);
                                int newEarnings = Integer.parseInt(spotHolderProfile.Earnings) + Integer.parseInt(transaction.getAmount());
                                Map<String,Object> earningUpdate = new HashMap<>();
                                earningUpdate.put("Earnings",""+newEarnings);
                                spotHolder.child("Profile").updateChildren(earningUpdate).addOnCompleteListener(task -> {
                                    parkingBooking.setTransactionId(pinkey);
                                    Booking.bookParkingSpot(parkingBooking);
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
//
                        spotHolder.child("Transaction").child(pinkey).setValue(pinkey);
                        mUserTransactions.child(pinkey).setValue(pinkey);
                    }
                }
            });

        }
    }

    public static class Booking {
        static DatabaseReference mGlobalLocationPinRef = REF.child("GlobalPins");
        static DatabaseReference mGlobalBookings = REF.child("GlobalBookings");
        static DatabaseReference mUserParkingBookingsRef = REF.child("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("MyParkingBookings");

        public static void getSlotData(LocationPin locationPin, int year, int monthOfYear, int dayOfMonth, List<Map<String,Object>> slotsDataList, SlotsAdapter slotsAdapter) {
            mGlobalLocationPinRef.child(locationPin.getArea()).child(locationPin.getPinkey()).child("booking").child(year + "").child("" + (monthOfYear)).child(dayOfMonth + "")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChildren()) {
                                //Adapter code for booking slots display
                                slotsAdapter.notifyDataSetChanged();

                                for(int i=1;i<=6;i++) {
                                    String slotname = "slot"+i;
                                    Map<String,Object> slot = (Map<String,Object>)dataSnapshot.child(slotname).getValue();
                                    slotsDataList.add(slot);
                                    slotsAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Booking.setDefaultValues(locationPin, year, monthOfYear, dayOfMonth);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
        }

        private static void setDefaultValues(LocationPin locationPin, int year, int monthOfYear, int dayOfMonth) {
            int time=0;

            Map<String,Object> mains=new HashMap<>();

            for(int i=1;i<=6;i++)
            {
                String st=time+" to "+(time+4);

                time=time+4;
                Map<String,String> s=new HashMap<>();
                s.put("capacity",locationPin.getNumberofspot());
                s.put("booked","0");
                s.put("slot",st);
                mains.put("slot"+i,s);
            }
            mGlobalLocationPinRef.child(locationPin.getArea()).child(locationPin.getPinkey()).child("booking").child(year + "").child("" + (monthOfYear)).child(dayOfMonth + "").setValue(mains).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });
        }

        private static void bookParkingSpot(ParkingBooking parkingBooking) {
            Map<String, Object> bookingMap = new HashMap<>();

            bookingMap.put(ParkingBookingConstants.by, parkingBooking.getBy());
            bookingMap.put(ParkingBookingConstants.parkingArea, parkingBooking.getParkingArea());
            bookingMap.put(ParkingBookingConstants.parkingId, parkingBooking.getParkingId());
            bookingMap.put(ParkingBookingConstants.slotNo, parkingBooking.getSlotNo());
            bookingMap.put(ParkingBookingConstants.spotHost, parkingBooking.getSpotHost());
            bookingMap.put(ParkingBookingConstants.timestamp, ServerValue.TIMESTAMP);
            bookingMap.put(ParkingBookingConstants.transactionId, parkingBooking.getTransactionId());

            NetworkServices.logg(parkingBooking.getBy());
            logg(parkingBooking.getParkingArea());
            logg(parkingBooking.getParkingId());
            logg(parkingBooking.getSlotNo());
            logg(parkingBooking.getSpotHost());
            logg(parkingBooking.getTransactionId());

            String key = REF.child("GlobalBookings").push().getKey();

            Map<String, Object> spotBookedUpdate = new HashMap<>();
            spotBookedUpdate.put("booked", "" + ParkingPinDetailActivity.noOfSlotsToBeBooked);

            mGlobalLocationPinRef.child(parkingBooking.getParkingArea() + "/" + parkingBooking.getParkingId() + "/booking" + "/" + ParkingPinDetailActivity.Year + "/"
                    + ParkingPinDetailActivity.monthOfYear + "/" + ParkingPinDetailActivity.dayOfMonth + "/" + parkingBooking.getSlotNo()).updateChildren(spotBookedUpdate)
                    .addOnCompleteListener(task -> mGlobalBookings.child(key).setValue(bookingMap, (databaseError, databaseReference) -> {
                        if (databaseError == null) {
                            mUserParkingBookingsRef.child(key).setValue(key);
                        } else {
                            //remove transaction and add that balance back
                        }
                    }));
            }
        }

    public static void logg(String msg) {
        Log.d("PQR",msg);
    }

}
