package com.egorchepiga.billingv2.POJO;

import java.util.List;

/**
 * Created by George on 16.03.2016.
 */

public class ServicesArray {

    private List<UserService> services;
    public ServicesArray(){};

    public List<UserService> getServices() {
        return services;
    }

    public void setServices(List<UserService> services) {
        this.services = services;
    }
}
