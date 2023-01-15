package com.example.metaphraseinvoice.InputActivitites.Model;

public class DezimalModel {
    String description;
    String hours;
    String minutes;
    String price;

    public DezimalModel(String description, String hours, String minutes, String price) {
        this.description = description;
        this.hours = hours;
        this.minutes = minutes;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public String getHours() {
        return hours;
    }

    public String getMinutes() {
        return minutes;
    }

    public String getPrice() {
        return price;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
