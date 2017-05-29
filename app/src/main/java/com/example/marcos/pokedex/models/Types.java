package com.example.marcos.pokedex.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Karol on 24/05/2017.
 */

public class Types {

    @SerializedName("slot")
    @Expose
    private Integer slot;
    @SerializedName("type")
    @Expose
    private Dato type;

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public Dato getType() {
        return type;
    }

    public void setType(Dato type) {
        this.type = type;
    }

}