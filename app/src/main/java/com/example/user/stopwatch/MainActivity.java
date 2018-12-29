package com.example.user.stopwatch;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnStart,btnPause,btnLap;
    TextView txtTimer;
    Handler custonHandler=new Handler();
    LinearLayout container;

    long startTimer=0L, timeInMilliseconds=0L,timeSwapBuff=0L, updateTimer=0L;

    Runnable updateTimerThead =new Runnable() {
        @Override
        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis()-startTimer;
            updateTimer = timeSwapBuff + timeInMilliseconds;
            int secs= (int)(updateTimer%1000);
            int mins= secs/60;
            secs%=60;
            int milliseconds = (int)(updateTimer%1000);
            txtTimer.setText(""+mins+":"+String.format("%02d",secs)+ ":"
                             +String.format("%03d",milliseconds));
            custonHandler.postDelayed(this,0);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart=findViewById(R.id.btnStart);
        btnPause=findViewById(R.id.btnPause);
        btnLap=findViewById(R.id.btnLap);
        txtTimer=findViewById(R.id.timerVaule);
        container=(LinearLayout) findViewById(R.id.container);


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer = SystemClock.uptimeMillis();
                custonHandler.postDelayed(updateTimerThead,0);
            }
        });


        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSwapBuff+=timeInMilliseconds;
                custonHandler.removeCallbacks(updateTimerThead);
            }
        });

        btnLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View addView = inflater.inflate(R.layout.row,null);
                TextView txtValue=(TextView)addView.findViewById(R.id.txtContent);
                txtValue.setText(txtTimer.getText());
                container.addView(addView);
            }
        });
    }
}
