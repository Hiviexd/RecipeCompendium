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
import com.google.android.material.card.MaterialCardView;

public class RecipeIngredientsFragment extends Fragment {
    private Recipe recipe;

    public static RecipeIngredientsFragment newInstance(Recipe recipe) {
        RecipeIngredientsFragment fragment = new RecipeIngredientsFragment();
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
        View view = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);
        
        LinearLayout ingredientsList = view.findViewById(R.id.ingredientsList);
        for (String ingredient : recipe.getIngredients()) {
            MaterialCardView card = new MaterialCardView(getContext());
            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            );
            cardParams.setMargins(0, 0, 0, 16);
            card.setLayoutParams(cardParams);
            card.setRadius(8);
            card.setCardElevation(2);
            card.setContentPadding(16, 16, 16, 16);

            TextView textView = new TextView(getContext());
            textView.setText(ingredient);
            textView.setTextSize(18);
            
            card.addView(textView);
            ingredientsList.addView(card);
        }
        
        return view;
    }
}