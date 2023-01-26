package com.example.belbin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuTestActivity extends AppCompatActivity {
    DatabaseHelper db;
    private int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_test);
        db = new DatabaseHelper(this);
        Bundle args = getIntent().getExtras();
        idUser = (int) args.get("user");
    }
    public void toTest(View view) {
        Intent intent = new Intent(this, TestActivity.class);
        intent.putExtra("user", idUser);
        startActivity(intent);
    }
    public void setLastResult(View view) {
        Intent intent = new Intent(this, ResultActivity.class);
        int idTest = db.getIdLastTest(idUser);
        intent.putExtra("test", idTest);
        intent.putExtra("user", idUser);
        startActivity(intent);
    }
}