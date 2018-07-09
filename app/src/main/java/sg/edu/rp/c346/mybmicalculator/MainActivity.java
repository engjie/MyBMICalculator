package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText etWeight;
    EditText etHeight;
    Button btnCalculate;
    Button btnReset;
    TextView tvLastDate;
    TextView tvLastBMI;
    TextView tvStatus;


    @Override
    protected void onResume() {
        super.onResume();

        //Step 2a: Obtain an instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //Step 2b: Retrieve the key "greeting" from the SharedPreferences object
        String msgDate = prefs.getString("tvDate", getString(R.string.lastDate));
        String msgBMI = prefs.getString("tvBMI", getString(R.string.lastBMI));
        String msgStatus = prefs.getString("tvStatus",null);
        String weight = prefs.getString("etWeight", null);
        String height = prefs.getString("etHeight", null);

        etWeight.setText(weight);
        etHeight.setText(height);
        tvLastDate.setText(msgDate);
        tvLastBMI.setText(String.valueOf(msgBMI));
        tvStatus.setText(msgStatus);

    }

    @Override
    protected void onPause() {
        super.onPause();

        //Step 1a: Obtain an instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //Step 1b: Obtain an instance of the SharedPreference Editor for update later
        SharedPreferences.Editor prefEdit = prefs.edit();

        //Step 1c: Add the key-value pair
        prefEdit.putString("tvDate",tvLastDate.getText().toString());
        prefEdit.putString("tvBMI",tvLastBMI.getText().toString());
        prefEdit.putString("etWeight", null);
        prefEdit.putString("etHeight", null);
        prefEdit.putString("tvStatus",tvStatus.getText().toString());



        //Step 1d: Call commit() method to save the changes into the SharedPreferences
        prefEdit.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalculate = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);
        tvLastDate = findViewById(R.id.textViewLastDate);
        tvLastBMI = findViewById(R.id.textViewLastBMI);
        tvStatus = findViewById(R.id.textViewStatus);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                tvLastDate.setText(getString(R.string.lastDate)+datetime);

                String weight = etWeight.getText().toString();
                String height = etHeight.getText().toString();

                int intWeight = Integer.parseInt(weight);
                float intHeight = Float.parseFloat(height);

                float calculate = intWeight/(intHeight*intHeight);


                tvLastBMI.setText(getString(R.string.lastBMI)+String.valueOf(calculate));

                if(calculate<18.5){
                    tvStatus.setText("You are underweight");
                }
                else if(18.5<calculate && calculate<24.9){
                    tvStatus.setText("Your BMI is normal");
                }

                else  if(25<calculate && calculate<29.9){
                    tvStatus.setText("You are overweight");
                }

                else  if (calculate>30){
                    tvStatus.setText("You are obese");
                }



            }


        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etWeight.setText(null);
                etHeight.setText(null);
                tvLastDate.setText(getString(R.string.lastDate));
                tvLastBMI.setText(getString(R.string.lastBMI));
                tvStatus.setText(null);
            }
        });


    }


}







