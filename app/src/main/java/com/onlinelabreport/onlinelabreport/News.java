package com.onlinelabreport.onlinelabreport;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class News extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    FloatingActionButton backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        backBtn = findViewById(R.id.backHomeBtn5);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<NewsArticle> newsList = generateNews();
        newsAdapter = new NewsAdapter(newsList);
        recyclerView.setAdapter(newsAdapter);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(News.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private List<NewsArticle> generateNews() {
        List<NewsArticle> newsList = new ArrayList<>();
        newsList.add(new NewsArticle("Medical Breakthrough", "Scientists make significant progress in cancer research."));
        newsList.add(new NewsArticle("New Vaccine Developed", "A new vaccine shows promising results in clinical trials."));
        newsList.add(new NewsArticle("Health Tips", "Tips for maintaining a healthy lifestyle."));
        // Add more news articles as needed

        return newsList;
    }
}









