package com.example.projet.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.projet.R;
import com.example.projet.models.Recipe;

public class RecipeOverviewFragment extends Fragment {
    private Recipe recipe;

    public static RecipeOverviewFragment newInstance(Recipe recipe) {
        RecipeOverviewFragment fragment = new RecipeOverviewFragment();
        Bundle args = new Bundle();
        args.putSerializable("recipe", recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipe = (Recipe) getArguments().getSerializable("recipe");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_overview, container, false);
        
        TextView cuisineText = view.findViewById(R.id.cuisineText);
        TextView descriptionText = view.findViewById(R.id.descriptionText);
        TextView difficultyText = view.findViewById(R.id.difficultyText);
        TextView timeText = view.findViewById(R.id.timeText);
        ImageView difficultyIcon = view.findViewById(R.id.difficultyIcon);
        ImageView timeIcon = view.findViewById(R.id.timeIcon);
        
        descriptionText.setText(recipe.getDescription());
        cuisineText.setText(recipe.getCuisine());
        difficultyText.setText(recipe.getDifficultyText());
        timeText.setText(recipe.getDisplayTime());
        
        // Set difficulty icon
        switch(recipe.getDifficulty()) {
            case 0: // Easy
                difficultyIcon.setImageResource(R.drawable.ic_check_circle);
                break;
            case 1: // Medium
                difficultyIcon.setImageResource(R.drawable.ic_question_circle);
                break;
            case 2: // Hard
                difficultyIcon.setImageResource(R.drawable.ic_warning_circle);
                break;
        }
        
        // Set time icon
        int minutes = recipe.getTimeInMinutes();
        if (minutes < 60) {
            timeIcon.setImageResource(R.drawable.ic_timer_green);
        } else if (minutes < 120) {
            timeIcon.setImageResource(R.drawable.ic_timer_yellow);
        } else {
            timeIcon.setImageResource(R.drawable.ic_timer_red);
        }
        
        return view;
    }
}