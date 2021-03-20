package com.fahamin.transcomtest;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fahamin.transcomtest.database.DbUser_Helper;
import com.fahamin.transcomtest.model.User;

public class LoginActivity extends AppCompatActivity {

    EditText userName, userPass;
    Button loginBtn;
    DbUser_Helper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        db = new DbUser_Helper(LoginActivity.this);

        userName = findViewById(R.id.inputEmailEtdId);
        userPass = findViewById(R.id.inputPassEtdId);
        loginBtn = findViewById(R.id.btn_login);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = userName.getText().toString().trim();
                String password = userPass.getText().toString().trim();
                int id = checkUser(new User(name, password));

                if (id == -1) {
                    Toast.makeText(LoginActivity.this, "login failed ", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }
        });

    }

    public int checkUser(User user) {
        return db.checkUser(user);
    }


    public void RegisterBTN(View view) {
        startActivity(new Intent(this, RegesterActivity.class));

    }
}