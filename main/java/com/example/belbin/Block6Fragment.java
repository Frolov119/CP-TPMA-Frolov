package com.example.belbin;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class Block6Fragment extends Fragment {
    private final SeekBar[] massBar = new SeekBar[8];
    private final TextView[] massProgress = new TextView[8];
    private final TextView[] massState = new TextView[8];
    private TextView errorView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_block6, container, false);
        Resources res = getResources();
        String[] statements = res.getStringArray(R.array.statements6);
        massState[0] = (TextView) view.findViewById(R.id.statement1);
        massState[1] = (TextView) view.findViewById(R.id.statement2);
        massState[2] = (TextView) view.findViewById(R.id.statement3);
        massState[3] = (TextView) view.findViewById(R.id.statement4);
        massState[4] = (TextView) view.findViewById(R.id.statement5);
        massState[5] = (TextView) view.findViewById(R.id.statement6);
        massState[6] = (TextView) view.findViewById(R.id.statement7);
        massState[7] = (TextView) view.findViewById(R.id.statement8);
        for (int i = 0; i < 8; i++) {
            massState[i].setText(statements[i]);
        }
        massBar[0] = (SeekBar) view.findViewById(R.id.seekBar1);
        massBar[1] = (SeekBar) view.findViewById(R.id.seekBar2);
        massBar[2] = (SeekBar) view.findViewById(R.id.seekBar3);
        massBar[3] = (SeekBar) view.findViewById(R.id.seekBar4);
        massBar[4] = (SeekBar) view.findViewById(R.id.seekBar5);
        massBar[5] = (SeekBar) view.findViewById(R.id.seekBar6);
        massBar[6] = (SeekBar) view.findViewById(R.id.seekBar7);
        massBar[7] = (SeekBar) view.findViewById(R.id.seekBar8);
        massProgress[0] = (TextView) view.findViewById(R.id.textView1);
        massProgress[1] = (TextView) view.findViewById(R.id.textView2);
        massProgress[2] = (TextView) view.findViewById(R.id.textView3);
        massProgress[3] = (TextView) view.findViewById(R.id.textView4);
        massProgress[4] = (TextView) view.findViewById(R.id.textView5);
        massProgress[5] = (TextView) view.findViewById(R.id.textView6);
        massProgress[6] = (TextView) view.findViewById(R.id.textView7);
        massProgress[7] = (TextView) view.findViewById(R.id.textView8);
        errorView = (TextView) view.findViewById(R.id.errorView);
        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                int summBar = 0;
                for (int i = 0; i < 8; i++) {
                    summBar += massBar[i].getProgress();
                    if (seekBar == massBar[i]) {
                        massProgress[i].setText(String.valueOf(massBar[i].getProgress()));
                    }
                }
                if (summBar > 10) {
                    errorView.setText("Баллов распределено больше 10. \nУберите данное количество баллов: " + (summBar - 10));
                    ((Button)getActivity().findViewById(R.id.prevBtn)).setEnabled(false);
                    ((Button)getActivity().findViewById(R.id.nextBtn)).setEnabled(false);
                } else {
                    errorView.setText("");
                    ((Button)getActivity().findViewById(R.id.prevBtn)).setEnabled(true);
                    ((Button)getActivity().findViewById(R.id.nextBtn)).setEnabled(true);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        };
        for (int i = 0; i < 8; i++) {
            massBar[i].setOnSeekBarChangeListener(onSeekBarChangeListener);
        }
        return view;
    }
}