package com.example.jokesapif95107;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;


public class SavedJokes extends AppCompatActivity {
    ListView simpleListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_jokes);
        UsersDBHelpers usersDBHelpers = new UsersDBHelpers(this);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("username");
        ArrayList<String> savedJokes = usersDBHelpers.getUserSavedJokes(userName);
        String[] arraySavedJokes = savedJokes.toArray(new String[0]);






        simpleListView = (ListView) findViewById(R.id.simpleListView);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                R.layout.item_view, R.id.itemTextView, arraySavedJokes);
        simpleListView.setAdapter(arrayAdapter);

    }
}