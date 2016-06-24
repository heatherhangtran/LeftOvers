package com.randybiglow.leftovers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class RecipesFragment extends Fragment implements RecipeCallback {

    private ImageView recipeImage;
    private TextView recipeName, recipeUrl;
    //    private recipeCursorAdapter cursorAdapter;
    private View recipesFragmentView;
    private EditText recipeEditText;
    private Button recipeButton, linkButton;
    public static String img, name, url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        RecipeApiCall.getInstance((RecipeCallback)this.getActivity()).doRequest();
        recipesFragmentView = inflater.inflate(R.layout.fragment_recipes, container, false);
        recipeEditText = (EditText) recipesFragmentView.findViewById(R.id.recipeEditText);
        recipeButton = (Button) recipesFragmentView.findViewById(R.id.searchRecipe);
        RecipeApiCall.getInstance(RecipesFragment.this).doRequest();
        recipeImage = (ImageView) recipesFragmentView.findViewById(R.id.recipeImage);
//        linkButton = (Button) recipesFragmentView.findViewById(R.id.linkButton);
//        linkButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), WebView.class);
//                startActivity(intent);
//            }
//        });
        recipeName = (TextView) recipesFragmentView.findViewById(R.id.recipeName);
        recipeUrl = (TextView) recipesFragmentView.findViewById(R.id.recipeUrl);
        recipeName.setImeOptions(EditorInfo.IME_ACTION_DONE);
        recipeName.setSingleLine();
        recipeUrl.setMovementMethod(LinkMovementMethod.getInstance());
        recipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("<><><><>", "onClick");
                RecipeApiCall.doRequest(recipeEditText.getText().toString());
                setData(name, img, url);
            }
        });
        return recipesFragmentView;
    }

    @Override
    public void handleCallback(String nameRes, String imgRes, String urlRes) {
        img = imgRes;
        name = nameRes;
        url = urlRes;
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
//            ListView listView = (ListView) recipesFragmentView.findViewById(R.id.listView);
//            listView.setAdapter(cursorAdapter);
//
//        }
//
//    }

    public void setData(String nameRes, String imgRes, String urlRes) {
        Picasso.with(getActivity())
                .load(imgRes)
                .into(recipeImage);
        recipeName.setText(nameRes);
        recipeUrl.setText(urlRes);


    }
}
