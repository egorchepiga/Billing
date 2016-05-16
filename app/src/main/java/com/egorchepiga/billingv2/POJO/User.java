package com.egorchepiga.billingv2.POJO;

/**
 * Created by George on 16.03.2016.
 */
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class User {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("balance")
    @Expose
    private String balance;

    @SerializedName("telephone")
    @Expose
    private String telephone;

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     * The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     * The password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     * The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     * The balance
     */
    public String getBalance() {
        return balance;
    }

    /**
     *
     * @param balance
     * The balance
     */
    public void setBalance(String balance) {
        this.balance = balance;
    }
}
