package com.example.belbin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AuthActivity extends AppCompatActivity {
    DatabaseHelper db;
    private EditText login, pass;
    Button btnAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        db = new DatabaseHelper(this);
        login = (EditText) findViewById(R.id.loginAuth);
        pass = (EditText) findViewById(R.id.passAuth);
        btnAuth = (Button) findViewById(R.id.btnAuth);
    }
    public void toMenuTest(View view) {
        String strLogin = login.getText().toString();
        String strPass = pass.getText().toString();
        if (strLogin.equals("") || strPass.equals("")) {
            Toast.makeText(getApplicationContext(), "Одно или несколько полей пусты", Toast.LENGTH_SHORT).show();
        } else {
            boolean chkLoginPass = db.chkLoginPass(strLogin, strPass);
            if (chkLoginPass) {
                Toast.makeText(getApplicationContext(), "Авторизация прошла успешно", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MenuTestActivity.class);
                int idUser = db.getIdUser(strLogin, strPass);
                intent.putExtra("user", idUser);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
            }
        }
    }
}