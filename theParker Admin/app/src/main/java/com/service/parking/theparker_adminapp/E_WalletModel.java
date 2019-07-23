package com.service.parking.theparker_adminapp;

public class E_WalletModel {

    String card_wallet_date;
    String card_wallet_rate;
    String card_wallet_package_name;

    public E_WalletModel() {
    }

    public E_WalletModel(String card_wallet_date, String card_wallet_rate, String card_wallet_package_name) {
        this.card_wallet_date = card_wallet_date;
        this.card_wallet_rate = card_wallet_rate;
        this.card_wallet_package_name = card_wallet_package_name;
    }

    public String getCard_wallet_date() {
        return card_wallet_date;
    }

    public void setCard_wallet_date(String card_wallet_date) {
        this.card_wallet_date = card_wallet_date;
    }

    public String getCard_wallet_rate() {
        return card_wallet_rate;
    }

    public void setCard_wallet_rate(String card_wallet_rate) {
        this.card_wallet_rate = card_wallet_rate;
    }

    public String getCard_wallet_package_name() {
        return card_wallet_package_name;
    }

    public void setCard_wallet_package_name(String card_wallet_package_name) {
        this.card_wallet_package_name = card_wallet_package_name;
    }
}
