package com.example.projet.util;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import com.example.projet.models.Recipe;

public class RecipeReader {
    public static List<Recipe> readRecipesFromJson(Context context) {
        List<Recipe> recipes = new ArrayList<>();
        try {
            // Load JSON file from assets folder
            InputStream is = context.getAssets().open("recipes.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            
            // Convert to string
            String json = new String(buffer, StandardCharsets.UTF_8);
            
            // Parse JSON
            JSONObject obj = new JSONObject(json);
            JSONArray recipesArray = obj.getJSONArray("recipes");
            
            // Convert each JSON object to Recipe
            for (int i = 0; i < recipesArray.length(); i++) {
                JSONObject recipeObj = recipesArray.getJSONObject(i);
                
                // Get ingredients array
                JSONArray ingredientsArr = recipeObj.getJSONArray("ingredients");
                String[] ingredients = new String[ingredientsArr.length()];
                for (int j = 0; j < ingredientsArr.length(); j++) {
                    ingredients[j] = ingredientsArr.getString(j);
                }
                
                // Get steps array
                JSONArray stepsArr = recipeObj.getJSONArray("steps");
                String[] steps = new String[stepsArr.length()];
                for (int j = 0; j < stepsArr.length(); j++) {
                    steps[j] = stepsArr.getString(j);
                }
                
                // Create Recipe object
                Recipe recipe = new Recipe(
                    recipeObj.getString("id"),
                    recipeObj.getString("name"),
                    recipeObj.getString("description"),
                    recipeObj.getString("imageUrl"),
                    ingredients,
                    steps,
                    recipeObj.getString("cuisine"),
                    recipeObj.getBoolean("isFavorite")

                );
                
                recipes.add(recipe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipes;
    }
}