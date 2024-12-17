package com.example.projet;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.projet.fragments.RecipeIngredientsFragment;
import com.example.projet.fragments.RecipeOverviewFragment;
import com.example.projet.fragments.RecipeStepsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.projet.models.Recipe;
import android.content.res.ColorStateList;
import android.view.Gravity;
import android.graphics.drawable.GradientDrawable;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class RecipeDetailsActivity extends AppCompatActivity {
    private Recipe recipe;
    private FloatingActionButton favoriteButton;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;

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
        toolbar.setTitleTextColor(getColor(R.color.white));

        // Setup image
        ImageView recipeImage = findViewById(R.id.recipeImage);
        String imageName = recipe.getImageUrl().replaceAll("\\.jpg$", "");
        int imageResId = getResources().getIdentifier(
            imageName, "drawable", getPackageName());
        recipeImage.setImageResource(imageResId);

        // Setup ViewPager and TabLayout
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        // Create adapter
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // Link TabLayout with ViewPager
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager,
            (tab, position) -> {
                /*switch (position) {
                    case 0:
                        tab.setText("Overview");
                        break;
                    case 1:
                        tab.setText("Ingredients");
                        break;
                    case 2:
                        tab.setText("Steps");
                        break;
                }*/
            }
        );
        tabLayoutMediator.attach();

        // After setting up TabLayoutMediator
        viewPager.setPageTransformer((page, position) -> {
            // Fade animation
            float normalizedPosition = Math.abs(Math.abs(position) - 1);
            page.setAlpha(normalizedPosition);
        });

        // After setting up TabLayoutMediator
        viewPager.setUserInputEnabled(true);  // Enable swiping
        viewPager.setOffscreenPageLimit(2);   // Keep neighboring pages in memory
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // Animate to selected tab
                tabLayout.setScrollPosition(position, 0f, true);
            }
        });

        // Set smooth scroll
        viewPager.setCurrentItem(0, true);

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

    // ViewPager adapter class
    private class ViewPagerAdapter extends FragmentStateAdapter {
        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return RecipeOverviewFragment.newInstance(recipe);
                case 1:
                    return RecipeIngredientsFragment.newInstance(recipe);
                case 2:
                    return RecipeStepsFragment.newInstance(recipe);
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}