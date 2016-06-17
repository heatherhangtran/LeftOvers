package com.randybiglow.leftovers;

import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class DetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        int id = getIntent().getIntExtra("id", -1);
        LocalDBHelper dbHelper = LocalDBHelper.getInstance(DetailsActivity.this);

        Cursor detailsCursor = dbHelper.getDescriptionById(id);
        String itemName = detailsCursor.getString(detailsCursor.getColumnIndex(LocalDBHelper.COL_NAME));
        String dateAdded = detailsCursor.getString(detailsCursor.getColumnIndex(LocalDBHelper.COL_ADDED));
        String expiration = detailsCursor.getString(detailsCursor.getColumnIndex(LocalDBHelper.COL_EXP));

        TextView nameText = (TextView)findViewById(R.id.nameTV);
        TextView addedText = (TextView)findViewById(R.id.addedTV);
        TextView expirationText = (TextView)findViewById(R.id.expTV);

        nameText.setText(itemName);
        addedText.setText(dateAdded);
        expirationText.setText(expiration);
    }

}
