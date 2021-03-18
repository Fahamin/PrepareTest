package com.fahamin.transcomtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fahamin.transcomtest.database.DbUser_Helper;
import com.fahamin.transcomtest.model.User;

public class RegesterActivity extends AppCompatActivity {

    DbUser_Helper dbUser_helper;
    EditText userNameEDT;
    EditText userPassEdt;
    String name,pass;
    Button regBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regester);

        userNameEDT = findViewById(R.id.inputEmailEtdId);
        userPassEdt = findViewById(R.id.inputPassEtdId);
        regBtn = findViewById(R.id.btn_login);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = userNameEDT.getText().toString().trim();
                pass = userPassEdt.getText().toString().trim();

                dbUser_helper = new DbUser_Helper(RegesterActivity.this);
                dbUser_helper.addUser(new User(name,pass));


                startActivity(new Intent(RegesterActivity.this,MainActivity.class));

            }
        });
    }

}