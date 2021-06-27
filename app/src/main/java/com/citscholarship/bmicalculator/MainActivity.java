package com.citscholarship.bmicalculator;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText weight, height;
    Button calculate;
    TextView appTitle, yourBMI, bmiTV, typeTV;
    double weightInKG, heightInCM, bmi;
    String regexForNumber = "^[\\d\\.]+$";
    TableLayout statusTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        weight = findViewById(R.id.weight);
        height = findViewById(R.id.height);
        calculate = findViewById(R.id.calculate);
        appTitle = findViewById(R.id.appTitle);
        yourBMI = findViewById(R.id.yourBMI);
        bmiTV = findViewById(R.id.bmiTV);
        typeTV = findViewById(R.id.typeTV);
        statusTable = findViewById(R.id.statusTable);

        appTitle.setShadowLayer(5, 0, 3, Color.parseColor("#000000"));
        calculate.setShadowLayer(5, 0, 3, Color.parseColor("#333333"));

        weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (weight.getText().toString().matches(regexForNumber)) {
                    weight.setTextColor(Color.parseColor("#ffffff"));
                } else {
                    weight.setTextColor(Color.parseColor("#ff0000"));
                }
            }
        });

        height.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (height.getText().toString().matches(regexForNumber)) {
                    height.setTextColor(Color.parseColor("#ffffff"));
                } else {
                    height.setTextColor(Color.parseColor("#ff0000"));
                }
            }
        });

        height.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    if (weight.getText().toString().matches(regexForNumber) && (height.getText().toString().matches(regexForNumber))) {
                        weightInKG = Double.parseDouble(weight.getText().toString());
                        heightInCM = Double.parseDouble(height.getText().toString());
                    } else if (TextUtils.isEmpty(weight.getText().toString())) {
                        Toast.makeText(MainActivity.this,
                                "Please enter weight!",
                                Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(height.getText().toString())) {
                        Toast.makeText(MainActivity.this,
                                "Please enter height!",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this,
                                "Please enter numbers only!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(weight.getText().toString())) {
                    Toast.makeText(MainActivity.this,
                            "Please enter weight!",
                            Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(height.getText().toString())) {
                    Toast.makeText(MainActivity.this,
                            "Please enter height!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (weight.getText().toString().matches(regexForNumber) && (height.getText().toString().matches(regexForNumber))) {
                        weightInKG = Double.parseDouble(weight.getText().toString());
                        heightInCM = Double.parseDouble(height.getText().toString());
                        double heightInMeter = heightInCM / 100;
                        bmi = weightInKG / (heightInMeter * heightInMeter);
                        String bmiString = new DecimalFormat("##.##").format(bmi);
                        yourBMI.setText("YOUR BMI IS");
                        bmiTV.setText(bmiString);
                        typeTV.setShadowLayer(5, 0, 2, Color.parseColor("#000000"));
                        typeTV.setText(getType(bmi));
                    } else {
                        Toast.makeText(MainActivity.this,
                                "Please enter numbers only!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public String getType(double bmi) {
        String condition = "";
        if (bmi < 18.50) {
            condition += "Underweight!";
            typeTV.setTextColor(Color.parseColor("#FF2424"));
        } else if (bmi >= 18.50 && bmi <= 24.99) {
            condition += "Healthy, Normal Weight!";
            typeTV.setTextColor(Color.parseColor("#73FF00"));
        } else if (bmi >= 25.00 && bmi <= 29.99) {
            condition += "Overweight!";
            typeTV.setTextColor(Color.parseColor("#FF630C"));
        } else if (bmi >= 30.00) {
            condition += "Obese!";
            typeTV.setTextColor(Color.parseColor("#FF2424"));
        } else {
            condition = "Please try again!";
        }

        return condition;
    }
}