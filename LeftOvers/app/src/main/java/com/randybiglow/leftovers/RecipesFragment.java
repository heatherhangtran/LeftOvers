package com.randybiglow.leftovers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class RecipesFragment extends Fragment {

    ImageView recipeImage;
    TextView testTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        TextView test = (TextView) container.findViewById(R.id.recipes);
//        return inflater.inflate(R.layout.fragment_recipes, container, false);
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        recipeImage = (ImageView) view.findViewById(R.id.recipeImage);
        testTextView = (TextView) view.findViewById(R.id.testTextView);
        RecipeApiCall.getInstance((RecipeCallback)this.getActivity()).doRequest();

        return view;
    }

    public void handleCallback(String response) {
        Picasso.with(getContext())
                .load(response)
//                .fit()
//                .centerCrop()
                .into(recipeImage);
    }
}
