package com.example.belbin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class ResultActivity extends AppCompatActivity {
    DatabaseHelper db;
    private TextView textResult1, textResult2;
    private int idTest, idUser;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        db = new DatabaseHelper(this);
        Bundle args = getIntent().getExtras();
        idTest = (int) args.get("test");
        idUser = (int) args.get("user");
        Resources res = getResources();
        String[] textResults = res.getStringArray(R.array.result);
        textResult1 = (TextView) findViewById(R.id.textResult1);
        textResult2 = (TextView) findViewById(R.id.textResult2);
        Integer[] intResults = db.getTest(idTest);
        setResultText(intResults, textResults);
    }
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void setResultText(Integer[] intResults, String[] textResults) {
        Integer[][] sortResult = new Integer[8][2];
        for (int i = 0; i < 8; i++) {
            sortResult[i][0] = i;
            sortResult[i][1] = intResults[i];
        }
        Arrays.sort(sortResult, new Comparator<Integer[]>() {
            @Override
            public int compare(Integer[] int1, Integer[] int2) {
                Integer numPoint1 = int1[1];
                Integer numPoint2 = int2[1];
                return numPoint2.compareTo(numPoint1);
            }
        });

        for (int i = 0; i < 8; i++) {
            double percent = (double) (sortResult[i][1] * 100) / 70 ;
            if (i == 0) {
                textResult1.append(String.format("%.1f",percent) + "% — " + textResults[sortResult[i][0]]);
                continue;
            }
            if (sortResult[i][1] > 0) {
                textResult2.append(String.format("%.1f",percent) + "% — " + textResults[sortResult[i][0]]);
                textResult2.append("\n" + " \n");
            }
        }
    }
    public void toMain(View view) {
        Intent intent = new Intent(this, MenuTestActivity.class);
        intent.putExtra("user", idUser);
        startActivity(intent);
    }
}