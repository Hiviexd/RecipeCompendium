package com.example.projet.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.projet.R;
import com.example.projet.models.Recipe;

public class RecipeStepsFragment extends Fragment {
    private Recipe recipe;

    public static RecipeStepsFragment newInstance(Recipe recipe) {
        RecipeStepsFragment fragment = new RecipeStepsFragment();
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
        View view = inflater.inflate(R.layout.fragment_recipe_steps, container, false);
        
        LinearLayout stepsList = view.findViewById(R.id.stepsList);
        String[] steps = recipe.getSteps();
        for (int i = 0; i < steps.length; i++) {
            TextView stepText = new TextView(getContext());
            stepText.setText((i + 1) + ". " + steps[i]);
            stepText.setTextSize(16);
            stepText.setPadding(0, 4, 0, 4);
            stepsList.addView(stepText);
        }
        
        return view;
    }
}