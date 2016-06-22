package com.randybiglow.leftovers;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MyFridgeFragment extends Fragment {

    static FridgeCursorAdapter cursorAdapter;
    private View fridgeFragmentView;
    private LocalDBHelper helper;
    private ListView listView;
    static Cursor cursor;
    static TextView nameTextView, expTextView, testClickedTextView;
    private Button searchRecipeButton, barcodeScanner;
    private CheckBox checkbox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = LocalDBHelper.getInstance(getActivity());
        Cursor cursor = helper.getIngredients();
        cursorAdapter = new FridgeCursorAdapter(getActivity(), cursor);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fridgeFragmentView = inflater.inflate(R.layout.fragment_my_fridge, container, false);
        checkbox = (CheckBox) fridgeFragmentView.findViewById(R.id.checkbox);
        testClickedTextView = (TextView) fridgeFragmentView.findViewById(R.id.testTextView);
        searchRecipeButton = (Button) fridgeFragmentView.findViewById(R.id.search_recipes);
        barcodeScanner = (Button) fridgeFragmentView.findViewById(R.id.barcodeScanner);

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

        //Checks if the API Version is 21 and up to show animation.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fridgeFragmentView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    v.removeOnLayoutChangeListener(this);

                    //Calls method for animation.
                    //revealView(fridgeFragmentView);
                }
            });
        }

        searchRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add Recipe Button code here.
            }
        });

        barcodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
                intentIntegrator.initiateScan();
            }
        });
        return fridgeFragmentView;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if(intentResult != null){
            String scanContent = intentResult.getContents();
            testClickedTextView.setText("CONTENT: " + scanContent);
        } else {
            Toast.makeText(getContext(), "No scan data received!", Toast.LENGTH_SHORT).show();
        }
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
            nameTextView = (TextView) view.findViewById(R.id.food_item);
            expTextView = (TextView) view.findViewById(R.id.exp_entered);
            String item = cursor.getString(cursor.getColumnIndexOrThrow(LocalDBHelper.COL_NAME));
            String expiration = cursor.getString(cursor.getColumnIndexOrThrow(LocalDBHelper.COL_EXP));
            nameTextView.setText(item);
            if (expiration.matches("")) {
                expTextView.setText("No expiration date");
            } else {
                expTextView.setText("Exp: " + expiration);
            }

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

                    helper.deleteIngredient(id);
                    Cursor cursor = helper.getIngredients();
                    cursorAdapter.swapCursor(cursor);
                    cursorAdapter.notifyDataSetChanged();
                    return true;
                }
            });
        }
    }
}