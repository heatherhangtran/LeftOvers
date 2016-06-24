package com.randybiglow.leftovers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class RecipesFragment extends Fragment implements RecipeCallback{

    private ImageView recipeImage;
    private TextView recipeName;
//    private recipeCursorAdapter cursorAdapter;
    private View recipesFragmentView;
    private EditText recipeEditText;
    private Button recipeButton;
    public static String img;
    public static String name;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        RecipeApiCall.getInstance((RecipeCallback)this.getActivity()).doRequest();
        recipesFragmentView = inflater.inflate(R.layout.fragment_recipes, container, false);
        recipeEditText = (EditText) recipesFragmentView.findViewById(R.id.recipeEditText);
        recipeButton = (Button) recipesFragmentView.findViewById(R.id.searchRecipe);
        RecipeApiCall.getInstance(RecipesFragment.this).doRequest();
        recipeImage = (ImageView) recipesFragmentView.findViewById(R.id.recipeImage);
        recipeName = (TextView) recipesFragmentView.findViewById(R.id.recipeName);
        recipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("<><><><>", "onClick");
                RecipeApiCall.doRequest(recipeEditText.getText().toString());
                setData(name, img);
            }
        });
        return recipesFragmentView;
    }

    @Override
    public void handleCallback(String nameRes, String imgRes) {

        img = imgRes;
        name = nameRes;


        Log.e("<><><><>", "handleCallback");
    }


//    public class recipeCursorAdapter extends CursorAdapter {
//        public recipeCursorAdapter(Context context, Cursor cursor) {
//            super(context, cursor, 0);
//        }
//
//        @Override
//        public View newView(Context context, Cursor cursor, ViewGroup parent) {
//            return LayoutInflater.from(context).inflate(R.layout.recipes_list_layout, parent, false);
//        }
//
//        @Override
//        public void bindView(View view, Context context, Cursor cursor) {
//
//
//
//
//            ListView listView = (ListView) recipesFragmentView.findViewById(R.id.listView);
//            listView.setAdapter(cursorAdapter);
//
//        }
//
//    }


    //@Override
    public void setData(String nameRes, String imgRes) {
        Log.e("<><><><>",imgRes + nameRes);


            Picasso.with(getActivity())
                    .load(imgRes)
                    .into(recipeImage);
            //testTextView.setText(response);
            recipeName.setText(nameRes);


            Log.e("<><><><>", "RecipeFragment has exception!!");

    }
}
