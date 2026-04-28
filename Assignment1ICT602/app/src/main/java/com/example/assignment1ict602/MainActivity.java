package com.example.assignment1ict602;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //declaration
    EditText editTextUnits;
    EditText editTextRebate;
    TextView textViewTcharges, textViewFcost ;
    Button btnCalculate, btnClear;

    private DecimalFormat decimalFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set Toolbar as ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorText));


        // Initialize the decimalFormat object
        decimalFormat = new DecimalFormat("#.00");

        //set variable with ui components
        btnCalculate = findViewById(R.id.btnCalculate);
        btnClear = findViewById(R.id.btnClear);
        editTextUnits = findViewById(R.id.editTextUnits);
        editTextRebate = findViewById(R.id.editTextRebate);
        textViewTcharges = findViewById(R.id.textViewTcharges);
        textViewFcost= findViewById(R.id.textViewFcost);


        //listener (action)
        btnCalculate.setOnClickListener(this);
        btnClear.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btnCalculate) {
            try {
                String variable = editTextUnits.getText().toString();
                String variable2 = editTextRebate.getText().toString();

                int unit = Integer.parseInt(variable);
                double rebate = Double.parseDouble(variable2) / 100;

                double first200 = 0.218;
                double next100 = 0.334;
                double next300 = 0.516;
                double moreThan700 = 0.546;

                double charges = calculateCharges(unit, first200, next100, next300, moreThan700);

                double finalCost = calculateFinalCost(charges, rebate);

                textViewFcost.setText("Final Cost: RM " + decimalFormat.format(finalCost));


                // Calculate and set the total usage and total charges

                double totalCharges = calculateTotalCharges(unit, first200, next100, next300, moreThan700);
                double totalRebate = totalCharges * rebate;
                double totalCost = totalCharges - totalRebate;

                textViewTcharges.setText("Total Charges: RM " + decimalFormat.format(totalCost));



            } catch (NumberFormatException nfe) {
                showToast("Please Enter a Valid Number");
            } catch (Exception exp) {
                showToast("Unknown Exception");
                Log.d("Exception", exp.getMessage());
            }
        } else if (v.getId() == R.id.btnClear) {
            // Clear button logic
            editTextUnits.setText("");
            editTextRebate.setText("");
            textViewTcharges.setText("Total Charges");
            textViewFcost.setText("Final Cost");
            showToast("Clear Clicked");
        }

    }

    private double calculateCharges(int unit, double first200, double next100, double next300, double moreThan700) {
        double charges = 0.0;

        if (unit <= 200) {
            charges = unit * first200;
        } else if (unit <= 300) {
            charges = (200 * first200) + ((unit - 200) * next100);
        } else if (unit <= 600) {
            charges = (200 * first200) + (100 * next100) + ((unit - 300) * next300);
        } else if (unit > 600) {
            charges = (200 * first200) + (100 * next100) + (300 * next300) + ((unit - 600) * moreThan700);
        }
        // Limiting charges to two decimal places
        return Double.parseDouble(decimalFormat.format(charges));
    }

    private double calculateFinalCost(double charges, double rebate) {
        double finalCost = charges - (charges * rebate);

        // Limiting final cost to two decimal places
        return Double.parseDouble(decimalFormat.format(finalCost));
    }

    private double calculateTotalCharges(int unit, double first200, double next100, double next300, double moreThan700) {
        double charges;

        if (unit <= 200) {
            charges = unit * first200;
        } else if (unit <= 300) {
            charges = (200 * first200) + ((unit - 200) * next100);
        } else if (unit <= 600) {
            charges = (200 * first200) + (100 * next100) + ((unit - 300) * next300);
        } else {
            charges = (200 * first200) + (100 * next100) + (300 * next300) + ((unit - 600) * moreThan700);
        }

        // Limiting charges to two decimal places
        return Double.parseDouble(decimalFormat.format(charges));
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

