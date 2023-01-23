package com.example.jokesapif95107;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginRegisterActivity extends AppCompatActivity {

    private EditText usernameField;
    private EditText passwordField;
    private UsersDBHelpers usersDBHelpers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        usersDBHelpers = new UsersDBHelpers(this);
        usernameField = findViewById(R.id.loginName);
        passwordField = findViewById(R.id.loginPass);
        Button loginButton = findViewById(R.id.loginBtn);
        Button registerButton = findViewById(R.id.registerBtn);

        // LOGIN LOGIC
        loginButton.setOnClickListener(view -> {
            String user = usernameField.getText().toString().trim();
            String pass = passwordField.getText().toString().trim();

            areFieldsEmpty(user, pass);

            if (doesUserExists(user, pass)) {
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
                usernameField.getText().clear();
                passwordField.getText().clear();
                Intent intent = new Intent(this, LoggedIn.class);
                intent.putExtra(Consts.CURRENT_USER_NAME, user);
                startActivity(intent);
            }

            if (!doesUserExists(user, pass)) {
                Toast.makeText(this, "Wrong credentials!", Toast.LENGTH_SHORT).show();
            }

        });

        // REGISTER LOGIC
        registerButton.setOnClickListener(view -> {
            String user = usernameField.getText().toString().trim();
            String pass = passwordField.getText().toString().trim();

            areFieldsEmpty(user, pass);

            if (doesUserExists(user, pass)) {
                Toast.makeText(this, "User already exists!", Toast.LENGTH_SHORT).show();
                usernameField.getText().clear();
                passwordField.getText().clear();
            }

            if (!doesUserExists(user, pass)) {
                registerUser(user, pass);
                usernameField.getText().clear();
                passwordField.getText().clear();
            }

        });
    }

    private boolean doesUserExists(String username, String password) {
        int id = usersDBHelpers.getCurrentPasswordAndUser(username, password);
        System.out.println(id);
        return id != -1;
    }

    private void areFieldsEmpty(String user, String pass) {
        if (user.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Fill all fields!", Toast.LENGTH_SHORT).show();
        }
    }

    private void registerUser(String username, String password) {
        boolean userData = usersDBHelpers.addData(username, password);
        if (userData) {
            Toast.makeText(this, "Registered!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Not registered!", Toast.LENGTH_SHORT).show();
        }
    }
}