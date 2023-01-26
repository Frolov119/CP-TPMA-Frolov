package com.example.belbin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Currency;

public class RegisterActivity extends AppCompatActivity {
    DatabaseHelper db;
    private EditText login, pass, confPass;
    Button btnRegist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = new DatabaseHelper(this);
        login = (EditText) findViewById(R.id.login);
        pass = (EditText) findViewById(R.id.pass);
        confPass = (EditText) findViewById(R.id.confPass);
        btnRegist = (Button) findViewById(R.id.btnRegist);
    }
    public void toMenuTest(View view) {
        String strLogin = login.getText().toString();
        String strPass = pass.getText().toString();
        String strConf = confPass.getText().toString();
        if (strLogin.equals("") || strPass.equals("") || strConf.equals("")) {
            Toast.makeText(getApplicationContext(), "Одно или несколько полей пусты", Toast.LENGTH_SHORT).show();
        } else {
            if (strPass.equals(strConf)) {
                boolean checkLogin = db.checkLogin(strLogin);
                if (checkLogin) {
                    boolean insertUser = db.insertInUser(strLogin, strPass, 2);
                    if (insertUser) {
                        Toast.makeText(getApplicationContext(), "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, MenuTestActivity.class);
                        int idUser = db.getIdUser(strLogin, strPass);
                        intent.putExtra("user", idUser);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Логин уже существует", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Пароли не совпадают", Toast.LENGTH_SHORT).show();
            }
        }
    }
}