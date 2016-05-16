package com.egorchepiga.billingv2.POJO;

/**
 * Created by George on 21.03.2016.
 */
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class StockService {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cost")
    @Expose
    private String cost;
    @SerializedName("period")
    @Expose
    private String period;

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
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The cost
     */
    public String getCost() {
        return cost;
    }

    /**
     *
     * @param cost
     * The cost
     */
    public void setCost(String cost) {
        this.cost = cost;
    }

    /**
     *
     * @return
     * The period
     */
    public String getPeriod() {
        return period;
    }

    /**
     *
     * @param period
     * The period
     */
    public void setPeriod(String period) {
        this.period = period;
    }

}
