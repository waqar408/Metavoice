package com.example.metaphraseinvoice.InputActivitites.Model;

public class NormezelienModel {
    String description;
    String words;
    String price;

    public NormezelienModel(String description, String words, String price) {
        this.description = description;
        this.words = words;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public String getWords() {
        return words;
    }

    public String getPrice() {
        return price;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
