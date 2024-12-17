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
    private int difficulty;  // 0: Easy, 1: Medium, 2: Hard
    private int timeInMinutes;

    public Recipe(String id, String name, String description, String imageUrl,
                  String[] ingredients, String[] steps, String cuisine, boolean isFavorite,
                  int difficulty, int timeInMinutes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.ingredients = ingredients;
        this.steps = steps;
        this.cuisine = cuisine;
        this.isFavorite = isFavorite;
        this.difficulty = difficulty;
        this.timeInMinutes = timeInMinutes;
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

    public int getDifficulty() {
        return difficulty;
    }

    public int getTimeInMinutes() {
        return timeInMinutes;
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

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setTimeInMinutes(int timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
    }

    // Helper method to get difficulty text
    public String getDifficultyText() {
        switch(difficulty) {
            case 0: return "Easy";
            case 1: return "Medium";
            case 2: return "Hard";
            default: return "Unknown";
        }
    }

    public String getDisplayTime() {
        int minutes = this.timeInMinutes;
        if (minutes >= 60) {
            int hours = minutes / 60;
            int remainingMinutes = minutes % 60;
            if (remainingMinutes == 0) {
                return hours + "h";
            } else {
                return hours + "h " + remainingMinutes + "min";
            }
        } else {
            return minutes + "min";
        }
    }
}