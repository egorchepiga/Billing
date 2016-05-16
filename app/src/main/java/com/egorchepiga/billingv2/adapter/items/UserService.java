package com.egorchepiga.billingv2.adapter.items;

public class UserService {
    public String name,offtime,cost,ontime;

    public UserService(String name, String cost, String ontime, String offtime) {
        this.name = name;
        this.cost = cost;
        this.ontime = ontime;
        this.offtime = offtime;
    }
}