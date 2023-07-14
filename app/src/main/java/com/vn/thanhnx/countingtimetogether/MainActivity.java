package com.vn.thanhnx.countingtimetogether;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.vn.thanhnx.countingtimetogether.ChangeFontActivity.FontSelectActivity;
import com.vn.thanhnx.countingtimetogether.MemoryActivity.MemoriesActivity;
import com.vn.thanhnx.countingtimetogether.Model.Font;
import com.vn.thanhnx.countingtimetogether.Model.MemoryPic;
import com.vn.thanhnx.countingtimetogether.Model.Relationship;
import com.vn.thanhnx.countingtimetogether.Model.Theme;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION = 1;
    private static final int REQUEST_CODE_PIC1 = 11;
    private static final int REQUEST_CODE_PIC2 = 12;
    private static final int REQUEST_CODE_OPEN_MEMORIES = 13;

    private static final int REQUEST_CODE_OPEN_FONT_CHANGE = 10;
    private ImageView firstPic;

    private ImageView secondPic;
    private Button changeFont;

    private TextView firstPersonName;

    private TextView secondPersonName;

    private TextView textViewCounting;
    private TextView relaName;
    private MyDBHandler myDBHandler;
    private TextView startDate;
    private TextView textViewTime;
    public static ArrayList<Font> fontList;
    private Button memoryButton;
    public static SharedPreferences sharedPreferences;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private void bindingView(){
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        firstPic = findViewById(R.id.firstPic);
        secondPic = findViewById(R.id.secondPic);
        textViewCounting = findViewById(R.id.textViewTime);
        changeFont = findViewById(R.id.buttonChangeFont);
        firstPersonName = findViewById(R.id.firstPersonName);
        secondPersonName = findViewById(R.id.secondPersonName);
        relaName = findViewById(R.id.relaName);
        myDBHandler = new MyDBHandler(this);
        startDate = findViewById(R.id.startDate);
        textViewTime = findViewById(R.id.textViewTime);
        memoryButton = findViewById(R.id.memoryButton);
        sharedPreferences = getApplicationContext().getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
    }

    private void bindingAction(){
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firstPic.setOnClickListener(this::firstPicClick);
        secondPic.setOnClickListener(this::secondPicClick);
        changeFont.setOnClickListener(this::changeFont);
        firstPersonName.setOnClickListener(this::onFirstNameClick);
        secondPersonName.setOnClickListener(this::onSecondNameClick);
        startDate.setOnClickListener(this::dateClick);
        relaName.setOnClickListener(this::relaNameClick);
        memoryButton.setOnClickListener(this::memoryClick);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void memoryClick(View view) {
        Intent i = new Intent(this, MemoriesActivity.class);
        String previewString = firstPersonName.getText()+" & "+secondPersonName.getText();
        i.putExtra("previewString", previewString);
        startActivityForResult(i, REQUEST_CODE_OPEN_MEMORIES);
    }

    private void relaNameClick(View view) {
        General.editTextView(relaName, MainActivity.this,
                "RelationshipName", relaName.getText().toString(), myDBHandler);
    }

    private void dateClick(View view) {
        // Create a Calendar instance to get the current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a DatePickerDialog and set the initial date
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                MainActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Create a Calendar instance and set the selected date
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, month, dayOfMonth);

                        // Create a SimpleDateFormat to format the date
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        String formattedDate = dateFormat.format(selectedDate.getTime());

                        // Update the TextView with the selected date
                        startDate.setText(formattedDate);
                        myDBHandler.updateRelationship(relaName.getText().toString(), "StartDate", formattedDate);
                        textViewTime.setText(countingLoveTime(formattedDate));
                    }
                },
                year, month, day);

        // Set the maximum date to today's date
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        // Show the DatePickerDialog
        datePickerDialog.show();
    }

    private void onSecondNameClick(View view) {
        General.editTextView(secondPersonName, MainActivity.this,
                "SecondPersonName", relaName.getText().toString(), myDBHandler);
    }

    private void onFirstNameClick(View view) {
        General.editTextView(firstPersonName, MainActivity.this,
                "FirstPersonName", relaName.getText().toString(), myDBHandler);
    }

    //move to change font activity
    private void changeFont(View view) {
        Intent i = new Intent(this, FontSelectActivity.class);
        String previewString = firstPersonName.getText()+" & "+secondPersonName.getText();
        i.putExtra("previewString", previewString);
        startActivityForResult(i, REQUEST_CODE_OPEN_FONT_CHANGE);
    }

    public void getFontData() {
        fontList = new ArrayList<>();
        fontList.add(new Font("AlexBrush-Regular", Typeface.createFromAsset(getAssets(),
                "AlexBrush-Regular.ttf")));
        fontList.add(new Font("Allura-Regular", Typeface.createFromAsset(getAssets(),
                "Allura-Regular.ttf")));
        fontList.add(new Font("Beaurivage-Regular", Typeface.createFromAsset(getAssets(),
                "BeauRivage-Regular.ttf")));
        fontList.add(new Font("BlackOpsOne-Regular", Typeface.createFromAsset(getAssets(),
                "BlackOpsOne-Regular.ttf")));
        fontList.add(new Font("BungeeShade-Regular", Typeface.createFromAsset(getAssets(),
                "BungeeShade-Regular.ttf")));
        fontList.add(new Font("ComforterBrush-Regular", Typeface.createFromAsset(getAssets(),
                "ComforterBrush-Regular.ttf")));
        fontList.add(new Font("DancingScript-VariableFont", Typeface.createFromAsset(getAssets(),
                "DancingScript-VariableFont_wght.ttf")));
        fontList.add(new Font("DenkOne-Regular", Typeface.createFromAsset(getAssets(),
                "DenkOne-Regular.ttf")));
        fontList.add(new Font("GreatVibes-Regular", Typeface.createFromAsset(getAssets(),
                "GreatVibes-Regular.ttf")));
        fontList.add(new Font("GrenzeGotisch_VariableFont", Typeface.createFromAsset(getAssets(),
                "GrenzeGotisch-VariableFont_wght.ttf")));
        fontList.add(new Font("Mansalva-Regular", Typeface.createFromAsset(getAssets(),
                "Mansalva-Regular.ttf")));
        fontList.add(new Font("MeowScript-Regular", Typeface.createFromAsset(getAssets(),
                "MeowScript-Regular.ttf")));
        fontList.add(new Font("Pacifico-Regular", Typeface.createFromAsset(getAssets(),
                "Pacifico-Regular.ttf")));
        fontList.add(new Font("QwitcherGrypen-Bold", Typeface.createFromAsset(getAssets(),
                "QwitcherGrypen-Bold.ttf")));
        fontList.add(new Font("Roboto-Regular", Typeface.createFromAsset(getAssets(),
                "Roboto-Regular.ttf")));
        fontList.add(new Font("Srisakdi-Bold", Typeface.createFromAsset(getAssets(),
                "Srisakdi-Bold.ttf")));
        fontList.add(new Font("Srisakdi-Regular", Typeface.createFromAsset(getAssets(),
                "Srisakdi-Regular.ttf")));
        fontList.add(new Font("VT323-Regular", Typeface.createFromAsset(getAssets(),
                "VT323-Regular.ttf")));
    }

    private void loadPersonImage(ImageView imageView){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
            // Permission not granted, request it from the user
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION);
        }
        else {
            // Permission already granted, open the image list
            openImagePicker(imageView);
        }
    }

    //open image picker to pick image for imageview
    private void openImagePicker(ImageView imageView) {
        if (imageView.equals(firstPic)){
            ImagePicker.with(this)
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .compress(1024)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .start(REQUEST_CODE_PIC1);
        } else if (imageView.equals(secondPic)) {
            ImagePicker.with(this)
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .compress(1024)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .start(REQUEST_CODE_PIC2);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PIC1 && resultCode == RESULT_OK) {
            Uri fullPhotoUri = data.getData();
            firstPic.setImageURI(fullPhotoUri);
            myDBHandler.updateRelationship(relaName.getText().toString(), "FirstPic", fullPhotoUri.toString());
        }
        else if (requestCode == REQUEST_CODE_PIC2 && resultCode == RESULT_OK) {
            Uri fullPhotoUri = data.getData();
            secondPic.setImageURI(fullPhotoUri);
            myDBHandler.updateRelationship(relaName.getText().toString(), "SecondPic", fullPhotoUri.toString());
        }
        else if(requestCode == REQUEST_CODE_OPEN_FONT_CHANGE && resultCode == 1){
            //set font type for all related textview
            String nameFont = data.getStringExtra("fontFamily");
            myDBHandler.updateRelationship(relaName.getText().toString(), "FontType", nameFont);
            Typeface font = General.getFont(nameFont);
            if (font != null){
                setFontType(font);
            }
        }
    }

    private void setFontType(Typeface font){
        firstPersonName.setTypeface(font);
        secondPersonName.setTypeface(font);
        textViewCounting.setTypeface(font);
        relaName.setTypeface(font);
        startDate.setTypeface(font);
    }

    private void setTextColor(String color){
        firstPersonName.setTextColor(Color.parseColor(color));
        secondPersonName.setTextColor(Color.parseColor(color));
        textViewTime.setTextColor(Color.parseColor(color));
        startDate.setTextColor(Color.parseColor(color));
        relaName.setTextColor(Color.parseColor(color));
    }

    private void secondPicClick(View view) {
        loadPersonImage(secondPic);
    }

    private void firstPicClick(View view) {
        loadPersonImage(firstPic);
    }

    private void loadCurrentRelationship(){
        boolean keyExists = sharedPreferences.contains("currentRelationship");
        if (!keyExists){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("currentRelationship", "FirstLove");
            editor.commit();
        }
        String currentRelationshipName = sharedPreferences.getString("currentRelationship", "");
        Relationship currentRelationship = myDBHandler.getRelaByName(currentRelationshipName);
        Theme currentTheme = myDBHandler.getThemeByID(currentRelationship.getThemeID());
        Typeface currentFont = General.getFont(currentRelationship.getFontType());
        relaName.setText(currentRelationship.getRelationshipName());//correct
        startDate.setText(currentRelationship.getStartDate());//correct
        firstPic.setImageURI(currentRelationship.getFirstPic());//correct
        secondPic.setImageURI(currentRelationship.getSecondPic());//correct
        firstPersonName.setText(currentRelationship.getFirstPersonName());//correct
        secondPersonName.setText(currentRelationship.getSecondPersonName());//correct
        setFontType(currentFont);//correct
        try {
            InputStream inputStream = getContentResolver().openInputStream(currentTheme.getBackground());
            Drawable drawable = Drawable.createFromStream(inputStream, currentTheme.getBackground().toString());
            getWindow().setBackgroundDrawable(drawable);//correct
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        setTextColor(currentTheme.getTextColor());//correct
        textViewTime.setText(countingLoveTime(currentRelationship.getStartDate()));
    }

    private String countingLoveTime(String dateString){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date givenDate = dateFormat.parse(dateString);

            Date currentTime = Calendar.getInstance().getTime();

            long timeDifference = currentTime.getTime() - givenDate.getTime();
            long days = TimeUnit.MILLISECONDS.toDays(timeDifference);

            return days + " ngày";
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    private void loadDefaultData(){
        //add default theme
        Uri backGround = General.convertDrawableToUri(this, R.drawable.lovebackground);
        String textColor = "#000000";
        myDBHandler.insertTheme(new Theme(backGround, textColor));
        //add default relationship:
        Relationship defaultRela = new Relationship();
        defaultRela.setRelationshipName("FirstLove");
        defaultRela.setThemeID(1);
        defaultRela.setFirstPersonName("FirstPersonName");
        Uri firstPicUri = General.convertDrawableToUri(this, R.drawable.male_avt);
        defaultRela.setFirstPic(firstPicUri);
        defaultRela.setSecondPersonName("SecondPersonName");
        defaultRela.setSecondPic(General.convertDrawableToUri(this, R.drawable.female_avt));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Month is zero-based
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        defaultRela.setStartDate(day+"/"+month+"/"+year);
        defaultRela.setFontType("Roboto-Regular");
        myDBHandler.insertRelationship(defaultRela);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindingView();
        bindingAction();
        getFontData();
        if (myDBHandler.getRecordCount("Relationship")==0
            && myDBHandler.getRecordCount("Theme")==0){
            loadDefaultData();
        }
        loadCurrentRelationship();
    }


}