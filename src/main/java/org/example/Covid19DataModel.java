package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.crypto.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Covid19DataModel {
    private int positive;
    private int negative;
    private int death;


    public int getPositive() {
        return positive;
    }

    public void setPositive(int positive) {
        this.positive = positive;
    }

    public int getNegative() {
        return negative;
    }

    public void setNegative(int negative) {
        this.negative = negative;
    }

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death = death;
    }
}
