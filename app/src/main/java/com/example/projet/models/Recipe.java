// app/src/main/java/com/example/projet/model/Recipe.java
package com.example.projet.models;

import java.io.Serializable;

public class Recipe implements Serializable {
    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private String[] ingredients;
    private String[] steps;
    private String cuisine;
    private boolean isFavorite;

    public Recipe(String id, String name, String description, String imageUrl,
                  String[] ingredients, String[] steps, String cuisine, boolean isFavorite) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.ingredients = ingredients;
        this.steps = steps;
        this.cuisine = cuisine;
        this.isFavorite = isFavorite;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public String[] getSteps() {
        return steps;
    }

    public String getCuisine() {
        return cuisine;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(String[] steps) {
        this.steps = steps;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public void setFavorite(boolean favorite) {
        this.isFavorite = favorite;
    }
}