package amd.nrk.yuge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GuessGame extends AppCompatActivity {

    Button btnLower, btnWin, btnHigher;
    TextView tvStatus, tvNumber;

    int min = 0, max = 1000;
    int random = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_game);

        btnLower = (Button) findViewById(R.id.btnLower);
        btnWin = (Button) findViewById(R.id.btnWin);
        btnHigher = (Button) findViewById(R.id.btnHigher);

        tvNumber = (TextView) findViewById(R.id.tvNumber);
        tvStatus = (TextView) findViewById(R.id.tvStatus);

        printRandom();

        btnLower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                max = random - 1;
                printRandom();
            }
        });

        btnWin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                max = random;
                min = random;
                tvStatus.setText("You win!");
            }
        });

        btnHigher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                min = random + 1;
                printRandom();
            }
        });
    }

    private void printRandom() {
        Random rand = new Random();
        try {
            random = rand.nextInt((max - min) + 1) + min;
            tvNumber.setText(String.valueOf(random));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Number found!", Toast.LENGTH_SHORT).show();
        }
    }
    
}
