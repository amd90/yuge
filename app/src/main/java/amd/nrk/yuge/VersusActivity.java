package amd.nrk.yuge;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import amd.nrk.yuge.util.Preferences;

public class VersusActivity extends AppCompatActivity {

    private ImageView ivYuge1, ivYuge2;
    private int counter1= 0, counter2 = 0;
    private TextView tvYugeCounter1, tvYugeCounter2;
    private Switch switch1, switch2;
    private TextView tvTimer1, tvTimer2;
    private TextView tvResult1, tvResult2;
    private boolean gameActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_versus);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
        ivYuge1 = (ImageView)findViewById(R.id.ivYuge1);
        ivYuge1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameActive) yuge1();
            }
        });
        ivYuge2 = (ImageView)findViewById(R.id.ivYuge2);
        ivYuge2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameActive) yuge2();
            }
        });
        tvYugeCounter1 = (TextView)findViewById(R.id.tvYugeCounter1);
        tvYugeCounter2 = (TextView)findViewById(R.id.tvYugeCounter2);
        switch1 = (Switch)findViewById(R.id.swReady1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b && switch2.isChecked()) startCountDown();
            }
        });
        switch2 = (Switch)findViewById(R.id.swReady2);
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b && switch1.isChecked()) startCountDown();
            }
        });

        tvTimer1 = (TextView)findViewById(R.id.tvTimer1);
        tvTimer2 = (TextView)findViewById(R.id.tvTimer2);

        tvResult1 = (TextView)findViewById(R.id.tvResult1);
        tvResult2 = (TextView)findViewById(R.id.tvResult2);
        tvResult1.setVisibility(View.INVISIBLE);
        tvResult2.setVisibility(View.INVISIBLE);
    }

    private void startCountDown(){
        new CountDownTimer(3000, 500){
            @Override
            public void onTick(long l) {
                long timeLeft = l/1000;
                tvResult1.setVisibility(View.VISIBLE);
                tvResult2.setVisibility(View.VISIBLE);

                tvResult1.setText(String.valueOf(timeLeft + 1));
                tvResult2.setText(String.valueOf(timeLeft + 1));
            }

            @Override
            public void onFinish() {

                tvResult1.setVisibility(View.INVISIBLE);
                tvResult2.setVisibility(View.INVISIBLE);
                startGame();
            }
        }.start();
    }

    private void startGame() {
        //Toast.makeText(this, "START", Toast.LENGTH_SHORT).show();
        gameActive = true;
        counter1 = 0;
        tvYugeCounter1.setText(counter1 + " Yuges");
        counter2 = 0;
        tvYugeCounter2.setText(counter2 + " Yuges");
        switch1.setEnabled(false);
        switch2.setEnabled(false);

        new CountDownTimer(10000, 500) {

            public void onTick(long millisUntilFinished) {
                long timeLeft = millisUntilFinished / 1000;
                tvTimer1.setText("Time Left -> 0:"+(timeLeft + 1));
                tvTimer2.setText("Time Left -> 0:"+(timeLeft + 1));
            }

            public void onFinish() {
                tvTimer1.setText("Time Left -> 0:00");
                tvTimer2.setText("Time Left -> 0:00");

                tvResult1.setVisibility(View.VISIBLE);
                tvResult2.setVisibility(View.VISIBLE);

                if (counter1 > counter2){
                    tvResult1.setText("You Win!");
                    tvResult2.setText("You Lose!");
                }else if (counter2 > counter1) {
                    tvResult1.setText("You Lose!");
                    tvResult2.setText("You Win!");
                }else {
                    tvResult1.setText("It's a tie!");
                    tvResult2.setText("It's a tie!");
                }

                switch1.setChecked(false);
                switch2.setChecked(false);
                switch1.setEnabled(true);
                switch2.setEnabled(true);
                gameActive = false;
            }
        }.start();

    }


    private void yuge1(){
        MediaPlayer mPlayer = MediaPlayer.create(VersusActivity.this, R.raw.yuge);
        mPlayer.start();
        counter1++;
        tvYugeCounter1.setText(counter1 + " Yuges");
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mPlayer) {
                mPlayer.release();
            }
        });


    }
    private void yuge2(){
        MediaPlayer mPlayer = MediaPlayer.create(VersusActivity.this, R.raw.yuge);
        mPlayer.start();
        counter2++;
        tvYugeCounter2.setText(counter2 + " Yuges");
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mPlayer) {
                mPlayer.release();
            }
        });


    }
}
