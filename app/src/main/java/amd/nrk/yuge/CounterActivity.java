package amd.nrk.yuge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CounterActivity extends AppCompatActivity {

    TextView tvNumber;
    Button btnAdd;
    Button btnRemove;
    EditText etQty;

    Button btnGuess;

    private int number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        tvNumber = (TextView)findViewById(R.id.tvNumber);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnRemove = (Button)findViewById(R.id.btnRemove);
        btnGuess = (Button)findViewById(R.id.btnGuess);

        etQty = (EditText)findViewById(R.id.etQty);

        tvNumber.setText("0");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etQty.getText().toString().isEmpty()){
                    number++;
                }else {
                    int customNumber = Integer.parseInt(etQty.getText().toString().trim());
                    number = number + customNumber;
                    etQty.setText("");
                }
                tvNumber.setText(String.valueOf(number));

            }
        });


        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etQty.getText().toString().isEmpty()){
                    number--;
                }else {
                    int customNumber = Integer.parseInt(etQty.getText().toString().trim());
                    number = number - customNumber;
                    etQty.setText("");
                }
                tvNumber.setText(String.valueOf(number));
            }
        });


        btnGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CounterActivity.this, GuessGame.class));
            }
        });


    }

}
