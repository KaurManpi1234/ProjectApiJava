package com.example.projectapijava;

import android.annotation.SuppressLint;

import java.io.Serializable;

public class Asteriods implements Serializable {
    String id;
    String name;
    String distance;
    Double magnitude;
    String orbitalPeriod;

    public Asteriods(String id, String name, String distance, Double magnitude) {
        this.id = id;
        this.name = name;
        this.distance = distance;
        this.magnitude = magnitude;
        this.orbitalPeriod = orbitalPeriod;
    }

    public Asteriods() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @SuppressLint("DefaultLocale")
    public String getDistance() {
        return String.format("%.1f", Double.valueOf(distance));
    }




    public void setDistance(String distance) {
        this.distance = distance;
    }



    public void setMagnitude(Double magnitude) {
        this.magnitude = magnitude;
    }
    @SuppressLint("DefaultLocale")
    public String getMagnitude() {
        return String.format("%.1f", magnitude);
    }

    public String getOrbitalPeriod() {
        return orbitalPeriod;
    }

    public void setOrbitalPeriod(String orbitalPeriod) {
        this.orbitalPeriod = orbitalPeriod;
    }
}
