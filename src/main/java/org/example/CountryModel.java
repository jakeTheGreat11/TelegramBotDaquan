package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryModel {
    private String name;
    private String alpha3Code;
    private String capital;
    private int population;
    private List<String> borders;

    public String getName() {
        return name;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }
    public String getCapital() {
        return capital;
    }



    public List<String> getBorders() {
        return borders;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlpha3Code(String alpha3Code) {
        this.alpha3Code = alpha3Code;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setBorders(List<String> borders) {
        this.borders = borders;
    }
}
