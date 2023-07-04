package com.vn.thanhnx.countingtimetogether;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.dhaval2404.imagepicker.ImagePicker;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION = 1;
    private static final int REQUEST_CODE_PIC1 = 11;
    private static final int REQUEST_CODE_PIC2 = 12;
    private ImageView firstPic;

    private ImageView secondPic;

    private TextView textViewCounting;

    private void bindingView(){
        firstPic = findViewById(R.id.imageView);
        secondPic = findViewById(R.id.imageView2);
        textViewCounting = findViewById(R.id.textView);
    }

    private void bindingAction(){
        firstPic.setOnClickListener(this::firstPicClick);
        secondPic.setOnClickListener(this::secondPicClick);
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
        if (requestCode == REQUEST_CODE_PIC2 && resultCode == RESULT_OK) {
            Uri fullPhotoUri = data.getData();
            secondPic.setImageURI(fullPhotoUri);
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