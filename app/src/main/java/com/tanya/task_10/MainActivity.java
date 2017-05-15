package com.tanya.task_10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("//////////");
        List<String> items = new ArrayList<>();
        for(int i = 0; i<25; i++)
            items.add(""+i);
        RecyclerView tagLayout = (RecyclerView) findViewById(R.id.recycler_view);
        tagLayout.setLayoutManager(new AwesomeLayoutManager(tagLayout));
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(MainActivity.this, items);
        tagLayout.setAdapter(adapter);


    }


}
