package com.example.jokesapif95107;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LoggedIn extends AppCompatActivity {

    private UsersDBHelpers usersDBHelpers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        usersDBHelpers = new UsersDBHelpers(this);

        Intent intent = getIntent();
        String username = intent.getStringExtra(Consts.CURRENT_USER_NAME);

        TextView loggedGreetings = findViewById(R.id.logged_in_greetings);
        System.out.println(username);
        loggedGreetings.setText("Welcome " + username);
        TextView loggedJokeText = findViewById(R.id.logged_in_joke_field);
        Button refreshJokeButton = findViewById(R.id.logged_in_refresh_joke);
        FetchHelper fetch = new FetchHelper("https://icanhazdadjoke.com/", loggedJokeText);
        fetch.execute();

        Button saveJokeButton = findViewById(R.id.logged_in_save_joke);
        Button viewSavedJokesButton = findViewById(R.id.logged_in_view_saved_jokes);

        refreshJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FetchHelper newFetch = new FetchHelper("https://icanhazdadjoke.com/", loggedJokeText);
                newFetch.execute();
            }
        });

        saveJokeButton.setOnClickListener(view -> {

            String jokeText = loggedJokeText.getText().toString();
            System.out.println(usersDBHelpers.getUserSavedJokes(username));
            if (usersDBHelpers.addJokeData(username, jokeText)) {
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Already saved!", Toast.LENGTH_SHORT).show();
            }

        });

        viewSavedJokesButton.setOnClickListener(view -> {
            Intent savedIntent = new Intent(this, SavedJokes.class);
            ArrayList<String> savedJokes = usersDBHelpers.getUserSavedJokes(username);
            String[] convertedJokes = savedJokes.toArray(new String[0]);
            savedIntent.putExtra("username", username);
            savedIntent.putStringArrayListExtra("saved", savedJokes);
            startActivity(savedIntent);
        });
    }
}