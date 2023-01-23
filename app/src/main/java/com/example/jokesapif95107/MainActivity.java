package com.example.jokesapif95107;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView jokeText = findViewById(R.id.mainActivityJoke);
        FetchHelper fetch = new FetchHelper("https://icanhazdadjoke.com/", jokeText);
        fetch.execute();

        Button login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginRegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}