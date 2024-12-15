package com.example.projet;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.EditText;
import android.text.Editable;
import android.text.TextWatcher;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.projet.models.Recipe;
import com.example.projet.util.RecipeReader;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;
import android.widget.FrameLayout;
import android.view.Gravity;
import android.graphics.Color;
import android.graphics.Typeface;
import java.util.stream.Collectors;
import java.util.List;
import android.graphics.drawable.GradientDrawable;
import android.widget.ImageView;

public class RecipeListActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private LinearLayout recipeContainer;
    private ActionBarDrawerToggle drawerToggle;
    private String loggedInUsername;
    private boolean showingFavorites = false;
    private List<Recipe> allRecipes;
    private EditText searchBar;
    private List<Recipe> filteredRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        loggedInUsername = getIntent().getStringExtra("username");
        
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        drawerLayout = findViewById(R.id.drawer_layout);
        recipeContainer = findViewById(R.id.recipeContainer);

        // Add search bar
        LinearLayout searchContainer = new LinearLayout(this);
        LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        containerParams.setMargins(16, 16, 16, 48);
        searchContainer.setLayoutParams(containerParams);
        searchContainer.setOrientation(LinearLayout.HORIZONTAL);
        searchContainer.setGravity(Gravity.CENTER_VERTICAL);
        searchContainer.setBackgroundResource(android.R.color.darker_gray);
        searchContainer.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F5F5F5")));
        int cornerRadius = 24;
        searchContainer.setBackground(new GradientDrawable() {
            {
                setColor(Color.parseColor("#F5F5F5"));
                setCornerRadius(cornerRadius);
            }
        });
        searchContainer.setPadding(24, 0, 24, 0);

        searchBar = new EditText(this);
        searchBar.setLayoutParams(new LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1f
        ));
        searchBar.setHint("Search recipes...");
        searchBar.setBackgroundResource(android.R.color.transparent);
        searchBar.setTextColor(getColor(android.R.color.black));
        searchBar.setHintTextColor(getColor(android.R.color.darker_gray));

        ImageView searchIcon = new ImageView(this);
        LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(
            64, 64
        );
        searchIcon.setLayoutParams(iconParams);
        searchIcon.setImageResource(R.drawable.ic_search);
        searchIcon.setColorFilter(getColor(android.R.color.darker_gray));
        searchIcon.setPadding(8, 8, 8, 8);

        searchContainer.addView(searchBar);
        searchContainer.addView(searchIcon);

        // Add search container as first child of recipeContainer
        recipeContainer.addView(searchContainer, 0);

        // Add text change listener for search
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                filterRecipes(s.toString());
            }
        });

        setupDrawerToggle();
        displayRecipes();
        setupNavigationDrawer();
    }

    private void filterRecipes(String query) {
        if (allRecipes == null) return;

        // Convert query to lowercase for case-insensitive search
        String finalQuery = query.toLowerCase().trim();

        filteredRecipes = allRecipes.stream()
            .filter(recipe -> 
                (!showingFavorites || recipe.isFavorite()) &&
                (finalQuery.isEmpty() ||
                 recipe.getName().toLowerCase().contains(finalQuery) ||
                 recipe.getDescription().toLowerCase().contains(finalQuery) ||
                 recipe.getCuisine().toLowerCase().contains(finalQuery)))
            .collect(Collectors.toList());

        // Clear and rebuild recipe list
        // Remove all views except search bar
        int childCount = recipeContainer.getChildCount();
        if (childCount > 1) {
            recipeContainer.removeViews(1, childCount - 1);
        }

        // Show empty state if no results
        if (filteredRecipes.isEmpty()) {
            TextView emptyView = new TextView(this);
            emptyView.setText(showingFavorites ? 
                "No favorite recipes match your search" : 
                "No recipes match your search");
            emptyView.setGravity(Gravity.CENTER);
            emptyView.setPadding(32, 32, 32, 32);
            recipeContainer.addView(emptyView);
            return;
        }

        // Display filtered recipes
        for (Recipe recipe : filteredRecipes) {
            MaterialCardView card = new MaterialCardView(this);
            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            );
            cardParams.setMargins(16, 8, 16, 8);
            card.setLayoutParams(cardParams);
            card.setRadius(16);
            card.setCardElevation(4);

            // Clicking on card opens details activity
            card.setOnClickListener(v -> {
                Intent intent = new Intent(this, RecipeDetailsActivity.class);
                intent.putExtra("recipe", recipe);
                startActivity(intent);
            });

            // Create main content container with horizontal orientation
            LinearLayout content = new LinearLayout(this);
            content.setOrientation(LinearLayout.HORIZONTAL);
            content.setPadding(24, 24, 24, 24);
            content.setGravity(Gravity.CENTER_VERTICAL);

            // Image view for recipe image
            ImageView recipeImage = new ImageView(this);
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                120, 120
            );
            recipeImage.setLayoutParams(imageParams);
            recipeImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

            // Get image name without extension from imageUrl
            String imageName = recipe.getImageUrl().replaceAll("\\.jpg$", "");

            // Load image from drawable using the filename
            int imageResId = getResources().getIdentifier(
                imageName,  // "carbonara" from "carbonara.jpg"
                "drawable", 
                getPackageName()
            );
            recipeImage.setImageResource(imageResId);
            
            // Text container
            LinearLayout textContainer = new LinearLayout(this);
            textContainer.setOrientation(LinearLayout.VERTICAL);
            textContainer.setLayoutParams(new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1
            ));
            LinearLayout.LayoutParams textContainerParams = 
                (LinearLayout.LayoutParams) textContainer.getLayoutParams();
            textContainerParams.setMarginStart(16);
            textContainer.setLayoutParams(textContainerParams);
            
            TextView titleView = new TextView(this);
            titleView.setText(recipe.getName());
            titleView.setTextSize(18);
            titleView.setTypeface(null, Typeface.BOLD);
            
            TextView descView = new TextView(this);
            descView.setText(recipe.getDescription());
            descView.setPadding(0, 8, 0, 0);
            
            // Create container for favorite button to center it
            FrameLayout buttonContainer = new FrameLayout(this);
            FrameLayout.LayoutParams buttonContainerParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            );
            buttonContainer.setLayoutParams(buttonContainerParams);
            
            // Favorite button
            MaterialButton favButton = new MaterialButton(this);
            FrameLayout.LayoutParams buttonParams = new FrameLayout.LayoutParams(
                64, 64
            );
            buttonParams.gravity = Gravity.CENTER;
            favButton.setLayoutParams(buttonParams);

            // Styles
            favButton.setIconTint(ColorStateList.valueOf(Color.WHITE));
            favButton.setIcon(getDrawable(recipe.isFavorite() ? 
                R.drawable.ic_favorite : 
                R.drawable.ic_favorite_border));
            favButton.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.orange_primary)));
            favButton.setIconGravity(MaterialButton.ICON_GRAVITY_TEXT_START);
            favButton.setInsetTop(0);
            favButton.setInsetBottom(0);
            favButton.setCornerRadius(32);
            favButton.setIconSize(32);
            favButton.setIconPadding(0);
            
            favButton.setOnClickListener(v -> {
                recipe.setFavorite(!recipe.isFavorite());
                favButton.setIcon(getDrawable(recipe.isFavorite() ? 
                    R.drawable.ic_favorite : 
                    R.drawable.ic_favorite_border));

                // If we're in favorites view and unfavoriting, remove the card
                if (showingFavorites && !recipe.isFavorite()) {
                    recipeContainer.removeView(card);
                    // Show empty message if no favorites left
                    if (recipeContainer.getChildCount() == 0) {
                        TextView emptyView = new TextView(this);
                        emptyView.setText("No favorite recipes yet");
                        emptyView.setGravity(Gravity.CENTER);
                        emptyView.setPadding(32, 32, 32, 32);
                        recipeContainer.addView(emptyView);
                    }
                }
            });
            
            // Add views to their containers
            buttonContainer.addView(favButton);
            textContainer.addView(titleView);
            textContainer.addView(descView);
            content.addView(recipeImage);
            content.addView(textContainer);
            content.addView(buttonContainer);
            card.addView(content);
            recipeContainer.addView(card);
        }
    }

    private void displayRecipes() {
        try {
            if (allRecipes == null) {
                allRecipes = RecipeReader.readRecipesFromJson(this);
            }
            
            // Initialize filtered recipes
            filterRecipes(searchBar != null ? searchBar.getText().toString() : "");
            
        } catch (Exception e) {
            TextView errorView = new TextView(this);
            errorView.setText(e.getMessage());
            errorView.setPadding(32, 32, 32, 32);
            recipeContainer.addView(errorView);
            e.printStackTrace();
        }
    }

    private void setupDrawerToggle() {
        drawerToggle = new ActionBarDrawerToggle(
            this, 
            drawerLayout,
            R.string.nav_drawer_open,
            R.string.nav_drawer_close
        );
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupNavigationDrawer() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView usernameText = headerView.findViewById(R.id.nav_header_username);
        usernameText.setText("Welcome back, " + loggedInUsername);

        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_logout) {
                Intent intent = new Intent(RecipeListActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.nav_favorites) {
                showingFavorites = true;
                // Clear search bar text
                searchBar.setText("");
                // Remove all views except search bar
                int childCount = recipeContainer.getChildCount();
                if (childCount > 1) {
                    recipeContainer.removeViews(1, childCount - 1);
                }
                displayRecipes();
                drawerLayout.closeDrawers();
                getSupportActionBar().setTitle("Favorite Recipes");
                return true;
            } else if (item.getItemId() == R.id.nav_home) {
                showingFavorites = false;
                // Clear search bar text
                searchBar.setText("");
                // Remove all views except search bar
                int childCount = recipeContainer.getChildCount();
                if (childCount > 1) {
                    recipeContainer.removeViews(1, childCount - 1);
                }
                displayRecipes();
                drawerLayout.closeDrawers();
                getSupportActionBar().setTitle("All Recipes");
                return true;
            }
            return false;
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(findViewById(R.id.nav_view))) {
            drawerLayout.closeDrawer(findViewById(R.id.nav_view));
        } else {
            super.onBackPressed();
        }
    }
}