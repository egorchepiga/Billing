package com.egorchepiga.billingv2.POJO;

/**
 * Created by George on 23.03.2016.
 */

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Type {

    @SerializedName("type")
    @Expose
    private String type;

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

}