package com.example.projet;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.projet.models.Recipe;
import android.content.res.ColorStateList;
import android.view.Gravity;
import android.graphics.drawable.GradientDrawable;

public class RecipeDetailsActivity extends AppCompatActivity {
    private Recipe recipe;
    private FloatingActionButton favoriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        // Get recipe from intent
        recipe = (Recipe) getIntent().getSerializableExtra("recipe");

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(recipe.getName());
        // Set title text color to white
        toolbar.setTitleTextColor(getColor(R.color.white));

        // Setup image
        ImageView recipeImage = findViewById(R.id.recipeImage);
        String imageName = recipe.getImageUrl().replaceAll("\\.jpg$", "");
        int imageResId = getResources().getIdentifier(
            imageName, "drawable", getPackageName());
        recipeImage.setImageResource(imageResId);

        // Setup cuisine
        TextView cuisineText = findViewById(R.id.cuisineText);
        cuisineText.setText(recipe.getCuisine());
        // change text color to orange dark
        cuisineText.setTextColor(getColor(R.color.orange_dark));
        // set text to bold
        cuisineText.setTypeface(null, Typeface.BOLD);

        // Create rounded background for cuisine text
        GradientDrawable cuisineBackground = new GradientDrawable();
        cuisineBackground.setColor(getColor(R.color.orange_light_transparent));
        cuisineBackground.setCornerRadius(24);
        cuisineText.setBackground(cuisineBackground);
        cuisineText.setPadding(24, 8, 24, 8);

        // Setup description
        TextView descriptionText = findViewById(R.id.descriptionText);
        descriptionText.setText(recipe.getDescription());

        // Setup ingredients list
        LinearLayout ingredientsList = findViewById(R.id.ingredientsList);
        for (String ingredient : recipe.getIngredients()) {
            TextView bulletPoint = new TextView(this);
            bulletPoint.setText("• " + ingredient);
            bulletPoint.setTextSize(16);
            bulletPoint.setPadding(0, 4, 0, 4);
            ingredientsList.addView(bulletPoint);
        }

        // Setup steps list
        LinearLayout stepsList = findViewById(R.id.stepsList);
        String[] steps = recipe.getSteps();
        for (int i = 0; i < steps.length; i++) {
            TextView stepText = new TextView(this);
            stepText.setText((i + 1) + ". " + steps[i]);
            stepText.setTextSize(16);
            stepText.setPadding(0, 4, 0, 4);
            stepsList.addView(stepText);
        }

        // Setup favorite button
        favoriteButton = findViewById(R.id.favoriteButton);
        updateFavoriteButton();
        favoriteButton.setOnClickListener(v -> {
            recipe.setFavorite(!recipe.isFavorite());
            updateFavoriteButton();
        });
    }

    private void updateFavoriteButton() {
        favoriteButton.setImageResource(recipe.isFavorite() ? 
            R.drawable.ic_favorite : 
            R.drawable.ic_favorite_border);
        favoriteButton.setBackgroundTintList(
            ColorStateList.valueOf(getColor(R.color.orange_primary)));
        // Add this line to make the icon white
        favoriteButton.setImageTintList(ColorStateList.valueOf(getColor(R.color.white)));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}