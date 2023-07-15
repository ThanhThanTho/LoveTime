package com.vn.thanhnx.countingtimetogether.MemoryActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.vn.thanhnx.countingtimetogether.MainActivity;
import com.vn.thanhnx.countingtimetogether.Model.MemoryPic;
import com.vn.thanhnx.countingtimetogether.MyDBHandler;
import com.vn.thanhnx.countingtimetogether.R;

public class MemoryDetailActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PERMISSION = 1;
    private static final int REQUEST_CODE_PIC_MEMORY = 2;
    private TextView textViewID;
    private ImageView memoryPic;
    private MyDBHandler myDBHandler;
    private EditText status;
    private Button buttonAddMemory;
    private Button cancelButton;
    private Uri newPicUri;

    private void bindingView(){
        textViewID = findViewById(R.id.textViewPicID);
        memoryPic = findViewById(R.id.imageMemory);
        status = findViewById(R.id.status);
        myDBHandler = new MyDBHandler(this);
        buttonAddMemory = findViewById(R.id.buttonAddMemory);
        cancelButton = findViewById(R.id.buttonCancel);
    }

    private void bindingAction(){
        memoryPic.setOnClickListener(this::loadMemoryPic);
        buttonAddMemory.setOnClickListener(this::addMemoryButtonClick);
        cancelButton.setOnClickListener(this::cancelButtonClick);
    }

    private void cancelButtonClick(View view) {
        finish();
    }

    private void addMemoryButtonClick(View view) {
        SharedPreferences sharedPreferences = MainActivity.sharedPreferences;
        String currentRelationshipName = sharedPreferences.getString("currentRelationship", "");
        int currentRelaID = myDBHandler.getRelaByName(currentRelationshipName).getRelationshipID();
        MemoryPic memoryPicInsert = new MemoryPic(0, newPicUri, status.getText().toString());
        myDBHandler.insertMemory(currentRelaID, memoryPicInsert);
        Toast.makeText(this, "Thêm kỉ niệm thành công", Toast.LENGTH_SHORT).show();
        finish();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void loadMemoryPic(View view) {
    }

    private void onRceiveIntent(){
        Intent i = getIntent();
        int PicID = Integer.valueOf(i.getStringExtra("picID"));
        if (PicID == -1){
            buttonAddMemory.setEnabled(false);
            memoryPic.setImageResource(R.drawable.add);
            memoryPic.setOnClickListener(this::onClickMemoryPic);
        }
        else{
            buttonAddMemory.setVisibility(View.GONE);
            MemoryPic currentMemoryPic = myDBHandler.getMemoryPicByID(PicID);
            memoryPic.setImageURI(currentMemoryPic.getPicture());
            status.setText(currentMemoryPic.getStatus());
            textViewID.setText(String.valueOf(PicID));
        }
    }

    private void onClickMemoryPic(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            // Permission not granted, request it from the user
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION);
        }
        else {
            ImagePicker.with(this)
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .compress(1024)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .start(REQUEST_CODE_PIC_MEMORY);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PIC_MEMORY && resultCode == RESULT_OK) {
            Uri photo = data.getData();
            this.newPicUri = photo;
            memoryPic.setImageURI(photo);
            buttonAddMemory.setEnabled(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_detail);
        bindingView();
        bindingAction();
        onRceiveIntent();
    }
}