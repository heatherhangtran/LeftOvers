package com.randybiglow.leftovers;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class RecipesFragment extends Fragment {

    ImageView recipeImage;
    TextView recipeName;
    private recipeCursorAdapter cursorAdapter;
    private View recipesFragmentView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

//        Cursor cursor = helper.getIngredients();
//        cursorAdapter = new recipeCursorAdapter(getActivity(), cursor);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RecipeApiCall.getInstance((RecipeCallback)this.getActivity()).doRequest();
        recipesFragmentView = inflater.inflate(R.layout.fragment_recipes, container, false);
        Log.e("Fragment View", "This shows the Recipes Fragment");
        return recipesFragmentView;
    }

    public class recipeCursorAdapter extends CursorAdapter{

        public recipeCursorAdapter(Context context, Cursor cursor) {
            super(context, cursor, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.recipes_list_layout, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            recipeImage = (ImageView)view.findViewById(R.id.recipeImage);

            recipeName = (TextView)view.findViewById(R.id.recipeName);

            ListView listView = (ListView)recipesFragmentView.findViewById(R.id.listView);
            listView.setAdapter(cursorAdapter);

        }

    }

    public void handleCallback(String response) {
        Picasso.with(getContext())
                .load(response)
//                .fit()
//                .centerCrop()
                .into(recipeImage);
        testTextView.setText(response);
    }


}
