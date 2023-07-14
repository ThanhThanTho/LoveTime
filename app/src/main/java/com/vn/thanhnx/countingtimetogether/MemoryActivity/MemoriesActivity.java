package com.vn.thanhnx.countingtimetogether.MemoryActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.vn.thanhnx.countingtimetogether.ChangeFontActivity.FontRecyclerAdapter;
import com.vn.thanhnx.countingtimetogether.General;
import com.vn.thanhnx.countingtimetogether.MainActivity;
import com.vn.thanhnx.countingtimetogether.Model.MemoryPic;
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
        memoryPics.add(new MemoryPic(-1, addIcon, "Bạn viết gì đi"));
    }

    private void bindDataToFontRecycler(){
        memoryRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        memoryRecycler.setAdapter(new MemoryRecyclerAdapter(memoryPics, this));
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
        textViewName.setText(previewString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memories);
        bindingView();
        bindingAction();
        getMemoryData();
        bindDataToFontRecycler();
        onReceiveIntent();
    }
}