package com.randybiglow.leftovers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements RecipeCallback {
    static long time;
    private PagerAdapter adapter;

    private Uri imageUri;
    String mCurrentPhotoPath;
    private static int TAKE_PICTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater titleInflator = LayoutInflater.from(this);
        View v = titleInflator.inflate(R.layout.titleview, null);
        TextView titleText = ((TextView) v.findViewById(R.id.title));
        titleText.setText(this.getTitle());
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fledgling-sb.ttf");
        titleText.setTypeface(typeface);

        this.getSupportActionBar().setCustomView(v);
        setContentView(R.layout.activity_main);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewIngredient();
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("My Fridge"));
        tabLayout.addTab(tabLayout.newTab().setText("Recipes"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount()) {
        };
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public void addNewIngredient() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        builder.setView(dialogView);
        final EditText nameField = (EditText) dialogView.findViewById(R.id.nameET);
        final EditText expField = (EditText) dialogView.findViewById(R.id.expET);
        final Button cameraButton = (Button) dialogView.findViewById(R.id.camera_button);

        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) {
                        clean = clean + getString(R.string.t_watcher).substring(clean.length());
                    } else {
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int mon = Integer.parseInt(clean.substring(0, 2));
                        int day = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        if (mon > 12) mon = 12;
                        if (mon < 1) mon = 1;


                        cal.set(Calendar.MONTH, mon - 1);
                        year = (year < 2016) ? 2016 : (year > 2050) ? 2050 : year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                        if (day < 1) day = 1;
                        clean = String.format("%02d%02d%02d", mon, day, year);
                    }
                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    expField.setText(current);
                    expField.setSelection(sel < current.length() ? sel : current.length());

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            private String current = "";
            private Calendar cal = Calendar.getInstance();
        };
        expField.addTextChangedListener(tw);


        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });


        builder.setMessage(R.string.dialog_addnew).setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DateFormat dateFormat = new SimpleDateFormat(getString(R.string.date_format));
                Date date = new Date();

                String name = nameField.getText().toString();
                String exp = expField.getText().toString();
//                Blob blob = cameraButton.get
                System.out.println(dateFormat.format(date));
                LocalDBHelper helper = LocalDBHelper.getInstance(MainActivity.this);
                try {
                    if ((dateFormat.parse(exp).getTime() - date.getTime()) < 0)
                        exp = dateFormat.format(date);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String imagePath = imageUri != null ? imageUri.toString() : null;

                long id = helper.addItem(name, exp, dateFormat.format(date), imagePath);

                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("id", id);
                imageUri = null;
//                startActivity(intent);

                MyFridgeFragment.cursor = helper.getIngredients();
                MyFridgeFragment.cursorAdapter.notifyDataSetChanged();
                MyFridgeFragment.cursorAdapter.changeCursor(MyFridgeFragment.cursor);

                try {

                    Date date2 = dateFormat.parse(exp);
                    long difference = date2.getTime() - date.getTime();
                    System.out.println("Days: " + TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS));
                    time = new GregorianCalendar().getTimeInMillis() + ((24 * 60 * 60 * 1000) * (difference));
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                ExpirationReceiver.notify(MainActivity.this);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

//    private File getImageFile(Uri imageUri) {
//
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        if (storageDir == null) {
//            return null;
//        }
//        return new File(storageDir, imageUri.getLastPathSegment());
//
//    }

    //create file for photo taken by user
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, "jpg", storageDir);

        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    //start camera to take photo and save image to file name
    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;

        if (intent.resolveActivity(getPackageManager()) != null) {
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        imageUri = Uri.fromFile(photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == RESULT_CANCELED && requestCode == TAKE_PICTURE) {

            //todo delete file at imageUri
//            File file = new File(imageUri.getPath());
//            file.delete();
//            imageUri = null;

        }
    }


    @Override
    public void handleCallback(String response) {
        Fragment currentFragment = adapter.getCurrentFragment();
        if (currentFragment != null && currentFragment instanceof RecipesFragment) {
            ((RecipesFragment) currentFragment).handleCallback(response);
        }
    }
}
