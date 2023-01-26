package com.example.belbin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import java.util.Arrays;

public class TestActivity extends AppCompatActivity {
    DatabaseHelper db;
    private Button prevBtn, nextBtn, resultBtn;
    private final Fragment[] fragments = new Fragment[7];
    private int index = 0;
    private int currentIndex;
    private int[][] arrProgress = new int[8][7];
    private Integer[] massResult = new Integer[8];
    private int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        db = new DatabaseHelper(this);
        Bundle args = getIntent().getExtras();
        idUser = (int) args.get("user");
        prevBtn = (Button) findViewById(R.id.prevBtn);
        nextBtn = (Button) findViewById(R.id.nextBtn);
        resultBtn = (Button) findViewById(R.id.resultBtn);
        fragments[0] = new Block1Fragment();
        fragments[1] = new Block2Fragment();
        fragments[2] = new Block3Fragment();
        fragments[3] = new Block4Fragment();
        fragments[4] = new Block5Fragment();
        fragments[5] = new Block6Fragment();
        fragments[6] = new Block7Fragment();

        setNewFragment("left");
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = index;
                saveProgress();
                setNewFragment("left");
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = index;
                saveProgress();
                setNewFragment("right");
            }
        });
    }
    // отправка результатов
    public void toResult(View view) {
        currentIndex = index;
        saveProgress();
        boolean insertTest = db.insertInTest(idUser, massResult);
        if (insertTest) {
            Intent intent = new Intent(this, ResultActivity.class);
            int idTest = db.getIdLastTest(idUser);
            intent.putExtra("test", idTest);
            intent.putExtra("user", idUser);
            startActivity(intent);
        }
    }
    // сохранение текущих баллов
    private void saveProgress() {
        View frag = ((FrameLayout) findViewById(R.id.frame_layout)).getChildAt(0);
        arrProgress[0][currentIndex] = ((SeekBar) frag.findViewById(R.id.seekBar1)).getProgress();
        arrProgress[1][currentIndex] = ((SeekBar) frag.findViewById(R.id.seekBar2)).getProgress();
        arrProgress[2][currentIndex] = ((SeekBar) frag.findViewById(R.id.seekBar3)).getProgress();
        arrProgress[3][currentIndex] = ((SeekBar) frag.findViewById(R.id.seekBar4)).getProgress();
        arrProgress[4][currentIndex] = ((SeekBar) frag.findViewById(R.id.seekBar5)).getProgress();
        arrProgress[5][currentIndex] = ((SeekBar) frag.findViewById(R.id.seekBar6)).getProgress();
        arrProgress[6][currentIndex] = ((SeekBar) frag.findViewById(R.id.seekBar7)).getProgress();
        arrProgress[7][currentIndex] = ((SeekBar) frag.findViewById(R.id.seekBar8)).getProgress();
        for (int i = 0; i < 8; i++) {
            massResult[i] = 0;
            int summ = 0;
            for (int j = 0; j < 7; j++) {
                summ += arrProgress[i][j];
            }
            massResult[i] = summ;
        }
    }
    // вывод фрагмента
    private void setNewFragment(String str) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (str == "left") {
            if (index != 0) {
                prevBtn.setEnabled(true);
                nextBtn.setEnabled(true);
                index--;
            }
        }
        if (str == "right") {
            if (index != 6) {
                prevBtn.setEnabled(true);
                nextBtn.setEnabled(true);
                index++;
            }
        }
        if (index == 0) {
            prevBtn.setEnabled(false);
        }
        if (index == 6) {
            nextBtn.setEnabled(false);
        }
        ft.replace(R.id.frame_layout, fragments[index]);
        ft.addToBackStack(null);
        ft.commit();
    }
}