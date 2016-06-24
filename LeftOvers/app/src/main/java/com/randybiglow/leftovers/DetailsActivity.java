package com.randybiglow.leftovers;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends Activity {


    ImageView itemImage;
    Uri imageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        itemImage = (ImageView) findViewById(R.id.image);
        Animation fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        itemImage.startAnimation(fade_in);

        int id = getIntent().getIntExtra("id", -1);
        setDetails(id);
    }

    private void setImage(){
        ContentResolver cr = getContentResolver();
        Bitmap bitmap;

        try {
            bitmap = MediaStore.Images.Media.getBitmap(cr, imageUri);
            itemImage.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDetails(long id){
        LocalDBHelper dbHelper = LocalDBHelper.getInstance(DetailsActivity.this);

        Cursor detailsCursor = dbHelper.getDescriptionById(id);
        String itemName = detailsCursor.getString(detailsCursor.getColumnIndex(LocalDBHelper.COL_NAME));
        String dateAdded = getString(R.string.date_added) + " " + detailsCursor.getString(detailsCursor.getColumnIndex(LocalDBHelper.COL_ADDED));
        String expiration = getString(R.string.expiration) + " " + detailsCursor.getString(detailsCursor.getColumnIndex(LocalDBHelper.COL_EXP));
        String imagePath = detailsCursor.getString(detailsCursor.getColumnIndex(LocalDBHelper.COL_PHOTO));
        if(imagePath != null) imageUri = Uri.parse(imagePath);

        TextView nameText = (TextView) findViewById(R.id.nameTV);
        TextView addedText = (TextView) findViewById(R.id.addedTV);
        TextView expirationText = (TextView) findViewById(R.id.expTV);

        nameText.setText(itemName);
        addedText.setText(dateAdded);
        expirationText.setText(expiration);
        if(imageUri != null) setImage();

        //adds transition animation
        nameText.setTransitionName("transition");

    }



}
