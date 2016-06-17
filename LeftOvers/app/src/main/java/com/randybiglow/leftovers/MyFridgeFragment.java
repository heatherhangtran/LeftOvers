package com.randybiglow.leftovers;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by RandyBiglow on 6/13/16.
 */

public class MyFridgeFragment extends Fragment {

    static FridgeCursorAdapter cursorAdapter;
    private View fridgeFragmentView;
    private LocalDBHelper helper;
    private ListView listView;
    static Cursor cursor;
    static TextView textView;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        helper = LocalDBHelper.getInstance(getActivity());
        Cursor cursor = helper.getIngredients();
        cursorAdapter = new FridgeCursorAdapter(getActivity(), cursor);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fridgeFragmentView = inflater.inflate(R.layout.fragment_my_fridge, container, false);
        if (cursorAdapter == null) {
            helper = LocalDBHelper.getInstance(getActivity());
            cursor = helper.getIngredients();
            cursorAdapter = new FridgeCursorAdapter(getActivity(), cursor);
            listView = (ListView) fridgeFragmentView.findViewById(R.id.listView);
            listView.setAdapter(cursorAdapter);
            cursorAdapter.notifyDataSetChanged();

        } else {
            helper = LocalDBHelper.getInstance(getActivity());
            cursor = helper.getIngredients();
            cursorAdapter.swapCursor(cursor);
            listView = (ListView) fridgeFragmentView.findViewById(R.id.listView);
            listView.setAdapter(cursorAdapter);
            cursorAdapter.notifyDataSetChanged();
        }
        return fridgeFragmentView;
    }

    public class FridgeCursorAdapter extends CursorAdapter {


        public FridgeCursorAdapter(Context context, Cursor cursor) {
            super(context, cursor, 0);

        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false);
        }

        @Override
        public void bindView(View view, Context context, final Cursor cursor) {
            textView = (TextView) view.findViewById(R.id.food_item);
            String item = cursor.getString(cursor.getColumnIndexOrThrow(LocalDBHelper.COL_NAME));
            textView.setText(item);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View localView, int position, long id) {
                    Intent detailsIntent = new Intent(getContext(), DetailsActivity.class);
                    cursor.moveToPosition(position);
                    detailsIntent.putExtra("id", cursor.getInt(cursor.getColumnIndex(helper.COL_ID)));
                    startActivity(detailsIntent);
                }
            });

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    cursor.moveToPosition(position);
                    helper.deleteIngredient(id);

                    return true;
                }
            });
        }
    }
}