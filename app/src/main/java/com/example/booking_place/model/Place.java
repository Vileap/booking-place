package com.example.booking_place.model;

import java.util.List;

public class Place {
    private String name;
    private List<String> images;
    private String price;

    public  Place() {}
    public Place(String pName, List<String> pImages, String pPrice) {
        this.images = pImages;
        this.price = pPrice;
        this.name = pName;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getImages() {
        return images;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}
