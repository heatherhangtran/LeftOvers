package com.randybiglow.leftovers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RecipesFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        TextView test = (TextView) container.findViewById(R.id.recipes);
//        return inflater.inflate(R.layout.fragment_recipes, container, false);
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        Log.e("Fragment View", "This shows the Recipes Fragment");
        return view;
    }
}
