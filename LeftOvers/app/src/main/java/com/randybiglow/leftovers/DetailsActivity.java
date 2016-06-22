package com.randybiglow.leftovers;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class DetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        int id = getIntent().getIntExtra("id", -1);
        LocalDBHelper dbHelper = LocalDBHelper.getInstance(DetailsActivity.this);

        Cursor detailsCursor = dbHelper.getDescriptionById(id);
        String itemName = detailsCursor.getString(detailsCursor.getColumnIndex(LocalDBHelper.COL_NAME));
        String dateAdded = getString(R.string.date_added) + " " + detailsCursor.getString(detailsCursor.getColumnIndex(LocalDBHelper.COL_ADDED));
        String expiration = getString(R.string.expiration) + " " + detailsCursor.getString(detailsCursor.getColumnIndex(LocalDBHelper.COL_EXP));


        TextView nameText = (TextView) findViewById(R.id.nameTV);
        TextView addedText = (TextView) findViewById(R.id.addedTV);
        TextView expirationText = (TextView) findViewById(R.id.expTV);
        ImageView itemImage = (ImageView) findViewById(R.id.image);


        nameText.setText(itemName);
        addedText.setText(dateAdded);
        expirationText.setText(expiration);
//        itemImage.setImageURI();
    }

    private File getImageFile(Uri imageUri) {

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (storageDir == null){
            return null;
        }
        return new File(storageDir, imageUri.getLastPathSegment());

    }


}
