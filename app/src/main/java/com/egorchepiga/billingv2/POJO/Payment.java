package com.egorchepiga.billingv2.POJO;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Payment {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sum")
    @Expose
    private String sum;
    @SerializedName("abonent_id")
    @Expose
    private String abonentId;
    @SerializedName("time")
    @Expose
    private String time;

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
     * The sum
     */
    public String getSum() {
        return sum;
    }

    /**
     *
     * @param sum
     * The sum
     */
    public void setSum(String sum) {
        this.sum = sum;
    }

    /**
     *
     * @return
     * The abonentId
     */
    public String getAbonentId() {
        return abonentId;
    }

    /**
     *
     * @param abonentId
     * The abonent_id
     */
    public void setAbonentId(String abonentId) {
        this.abonentId = abonentId;
    }

    /**
     *
     * @return
     * The time
     */
    public String getTime() {
        return time;
    }

    /**
     *
     * @param time
     * The time
     */
    public void setTime(String time) {
        this.time = time;
    }

}