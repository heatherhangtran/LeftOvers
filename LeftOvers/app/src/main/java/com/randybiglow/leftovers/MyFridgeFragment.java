package com.randybiglow.leftovers;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by RandyBiglow on 6/13/16.
 */

public class MyFridgeFragment extends Fragment{

    private FridgeCursorAdapter cursorAdapter;
    private View fridgeFragmentView;
    private LocalDBHelper helper;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        helper =LocalDBHelper.getInstance(getActivity());
        Cursor cursor = helper.getIngredients();
        cursorAdapter = new FridgeCursorAdapter(getActivity(), cursor);



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fridgeFragmentView = inflater.inflate(R.layout.fragment_my_fridge, container, false);
        return fridgeFragmentView;

    }

    public class FridgeCursorAdapter extends CursorAdapter{


        public FridgeCursorAdapter(Context context, Cursor cursor){
            super(context, cursor, 0);

        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView textView = (TextView)view.findViewById(R.id.food_item);
            String item = cursor.getString(cursor.getColumnIndexOrThrow(LocalDBHelper.COL_NAME));
            textView.setText(item);
            ListView listView = (ListView)fridgeFragmentView.findViewById(R.id.listView);

            listView.setAdapter(cursorAdapter);
        }


    }


}
