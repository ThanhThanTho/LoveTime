package com.vn.thanhnx.countingtimetogether;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.vn.thanhnx.countingtimetogether.Model.Font;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION = 1;
    private static final int REQUEST_CODE_PIC1 = 11;
    private static final int REQUEST_CODE_PIC2 = 12;

    private static final int REQUEST_CODE_OPEN_FONT_CHANGE = 10;
    private ImageView firstPic;

    private ImageView secondPic;
    private Button changeFont;

    private TextView firstPersonName;

    private TextView secondPersonName;

    private TextView textViewCounting;

    private void bindingView(){
        firstPic = findViewById(R.id.imageView);
        secondPic = findViewById(R.id.imageView2);
        textViewCounting = findViewById(R.id.textViewTime);
        changeFont = findViewById(R.id.buttonChangeFont);
        firstPersonName = findViewById(R.id.firstPersonName);
        secondPersonName = findViewById(R.id.secondPersonName);
    }

    private void bindingAction(){
        firstPic.setOnClickListener(this::firstPicClick);
        secondPic.setOnClickListener(this::secondPicClick);
        changeFont.setOnClickListener(this::changeFont);
        firstPersonName.setOnClickListener(this::onFirstNameClick);
        secondPersonName.setOnClickListener(this::onSecondNameClick);
    }

    private void onSecondNameClick(View view) {
        General.editTextView(secondPersonName, MainActivity.this);
    }

    private void onFirstNameClick(View view) {
        General.editTextView(firstPersonName, MainActivity.this);
    }

    //move to change font activity
    private void changeFont(View view) {
        Intent i = new Intent(this, FontSelectActivity.class);
        String previewString = firstPersonName.getText()+" & "+secondPersonName.getText();
        i.putExtra("previewString", previewString);
        startActivityForResult(i, REQUEST_CODE_OPEN_FONT_CHANGE);
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

    //open imagepicker to pick image for imageview
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
        }
        else if (requestCode == REQUEST_CODE_PIC2 && resultCode == RESULT_OK) {
            Uri fullPhotoUri = data.getData();
            secondPic.setImageURI(fullPhotoUri);
        }
        else if(requestCode == REQUEST_CODE_OPEN_FONT_CHANGE && resultCode == 1){
            String nameFont = data.getStringExtra("fontFamily");
            ArrayList<Font> fontArrayList = FontSelectActivity.getFontList();
            for(int i = 0; i<fontArrayList.size(); i++){
                if (fontArrayList.get(i).getFontName().equals(nameFont)){
                    firstPersonName.setTypeface(fontArrayList.get(i).getFontType());
                    secondPersonName.setTypeface(fontArrayList.get(i).getFontType());
                    textViewCounting.setTypeface(fontArrayList.get(i).getFontType());
                    break;
                }
            }

        }
    }

    private void secondPicClick(View view) {
        loadPersonImage(secondPic);
    }

    private void firstPicClick(View view) {
        loadPersonImage(firstPic);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindingView();
        bindingAction();
    }
}