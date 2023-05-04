package com.example.project;

public class DestinationModels {
    private String id;
    private String name;
    private String description;
    private String location;
    private String category;
    private String image;
    private float rating;

    public DestinationModels(){}
    public DestinationModels(String id, String name, String description, String location, String category, String image, float rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.category = category;
        this.image = image;
        this.rating = rating;
    }
    public float getRating(){return this.rating;}
    public void setRating(float rating){this.rating = rating;}
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
