package com.onlinelabreport.onlinelabreport;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class VideoGallery extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VideoAdapter videoAdapter;
    FloatingActionButton backBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_gallery);

        backBtn = findViewById(R.id.backHomebtn4);
        recyclerView = findViewById(R.id.recyclerView);
        initializeRecyclerView();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoGallery.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initializeRecyclerView() {
        List<VideoItem_class> videoItems = getSampleVideoItems(); // Replace with your actual video items
        videoAdapter = new VideoAdapter(this, videoItems);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(videoAdapter);
    }

    private List<VideoItem_class> getSampleVideoItems() {
        List<VideoItem_class> videoItems = new ArrayList<>();
        videoItems.add(new VideoItem_class("android.resource://" + getPackageName() + "/" + R.raw.vid1, "After adding or modifying resources."));
        videoItems.add(new VideoItem_class("android.resource://" + getPackageName() + "/" + R.raw.vid2, "Description 2"));
        // Add more video items as needed
        return videoItems;
    }
}
