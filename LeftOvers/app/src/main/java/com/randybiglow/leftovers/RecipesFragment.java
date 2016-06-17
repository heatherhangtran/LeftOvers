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

public class RecipesFragment extends Fragment {

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
//        TextView test = (TextView) container.findViewById(R.id.recipes);
//        return inflater.inflate(R.layout.fragment_recipes, container, false);
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
            ImageView imageView = (ImageView)view.findViewById(R.id.recipe_picture);


            TextView textView = (TextView)view.findViewById(R.id.recipe_name);

            ListView listView = (ListView)recipesFragmentView.findViewById(R.id.listView);
            listView.setAdapter(cursorAdapter);

        }
    }
}
