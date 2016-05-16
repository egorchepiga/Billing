package com.egorchepiga.billingv2.POJO;

/**
 * Created by George on 16.03.2016.
 */
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    @Generated("org.jsonschema2pojo")
    public class UserService {

        @Override
        public String toString() {
            return super.toString();

        }

        @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("ontime")
    @Expose
    private String ontime;
    @SerializedName("offtime")
    @Expose
    private String offtime;
    @SerializedName("usluga_id")
    @Expose
    private String uslugaId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("period")
    @Expose
    private String period;
    @SerializedName("cost")
    @Expose
    private String cost;

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
     * The ontime
     */
    public String getOntime() {
        return ontime;
    }

    /**
     *
     * @param ontime
     * The ontime
     */
    public void setOntime(String ontime) {
        this.ontime = ontime;
    }

    /**
     *
     * @return
     * The offtime
     */
    public String getOfftime() {
        return offtime;
    }

    /**
     *
     * @param offtime
     * The offtime
     */
    public void setOfftime(String offtime) {
        this.offtime = offtime;
    }

    /**
     *
     * @return
     * The uslugaId
     */
    public String getUslugaId() {
        return uslugaId;
    }

    /**
     *
     * @param uslugaId
     * The usluga_id
     */
    public void setUslugaId(String uslugaId) {
        this.uslugaId = uslugaId;
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

}
