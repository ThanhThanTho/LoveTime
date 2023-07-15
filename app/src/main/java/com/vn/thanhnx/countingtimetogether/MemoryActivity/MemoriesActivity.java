package com.vn.thanhnx.countingtimetogether.MemoryActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.vn.thanhnx.countingtimetogether.ChangeFontActivity.FontRecyclerAdapter;
import com.vn.thanhnx.countingtimetogether.General;
import com.vn.thanhnx.countingtimetogether.MainActivity;
import com.vn.thanhnx.countingtimetogether.Model.MemoryPic;
import com.vn.thanhnx.countingtimetogether.Model.Relationship;
import com.vn.thanhnx.countingtimetogether.MyDBHandler;
import com.vn.thanhnx.countingtimetogether.R;

import java.util.ArrayList;

public class MemoriesActivity extends AppCompatActivity {

    private ArrayList<MemoryPic> memoryPics;
    private RecyclerView memoryRecycler;
    private MyDBHandler myDBHandler;
    private TextView textViewName;

    private void getMemoryData(){
        memoryPics = new ArrayList<>();
        Uri addIcon = General.convertDrawableToUri(this, R.drawable.add);
        SharedPreferences sharedPreferences = MainActivity.sharedPreferences;
        String currentRelationshipName = sharedPreferences.getString("currentRelationship", "");
        int currentRelaID = myDBHandler.getRelaByName(currentRelationshipName).getRelationshipID();
        memoryPics = myDBHandler.getAllMemoryByRela(currentRelaID);
        memoryPics.add(0, new MemoryPic(-1, addIcon, "Nói gì đó về ảnh này nhé"));
    }

    private void bindDataToFontRecycler(){
        memoryRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        memoryRecycler.setAdapter(new MemoryRecyclerAdapter(memoryPics, this, memoryRecycler));
    }

    private void bindingView(){
        memoryRecycler = findViewById(R.id.recyclerViewMemory);
        myDBHandler = new MyDBHandler(this);
        textViewName = findViewById(R.id.textViewName);
    }

    private void bindingAction(){

    }

    private void onReceiveIntent(){
        Intent i = getIntent();
        String previewString = i.getStringExtra("previewString");
        String currentRelaName = i.getStringExtra("currentRelaName");
        //Relationship currentRela = myDBHandler.getRelaByName(currentRelaName);
        //memoryPics.addAll(myDBHandler.getAllMemoryByRela(currentRela.getRelationshipID()));
        textViewName.setText(previewString);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memories);
        bindingView();
        bindingAction();
        getMemoryData();
        onReceiveIntent();
        bindDataToFontRecycler();
    }
}