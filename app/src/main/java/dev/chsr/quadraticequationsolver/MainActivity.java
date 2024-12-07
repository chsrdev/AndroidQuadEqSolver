package dev.chsr.quadraticequationsolver;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText aCoefficientInput;
    EditText bCoefficientInput;
    EditText cCoefficientInput;
    Button solveButton;
    TextView rootsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aCoefficientInput = findViewById(R.id.aCoefficientInput);
        bCoefficientInput = findViewById(R.id.bCoefficientInput);
        cCoefficientInput = findViewById(R.id.cCoefficientInput);
        solveButton = findViewById(R.id.solveButton);
        rootsText = findViewById(R.id.resultText);

        solveButton.setOnClickListener(view -> {
            String strA = aCoefficientInput.getText().toString(),
                    strB = bCoefficientInput.getText().toString(),
                    strC = cCoefficientInput.getText().toString();
            double a = parseCoefficient(strA),
                    b = parseCoefficient(strB),
                    c = parseCoefficient(strC);

            if (a < 0) {
                a /= -1;
                b /= -1;
                c /= -1;
            }

            if (a == 0 && b == 0 && c == 0)
                rootsText.setText(R.string.any_x);
            else if (a == 0 && b == 0)
                rootsText.setText(R.string.no_roots);
            else if ((a == 0 && c == 0) || (b == 0 && c == 0))
                rootsText.setText("x = 0");
            else if (a == 0)
                rootsText.setText(String.format(Locale.US, "x = %f", -c / b));
            else if (b == 0) {
                double root = Math.sqrt(-c / a);
                rootsText.setText(String.format(Locale.US, "x₁ = %f\nx₂ = %f", root, -root));
            } else if (c == 0)
                rootsText.setText(String.format(Locale.US, "x = %f", -b / a));
            else {
                double d = Math.pow(b, 2) - (4 * a * c);
                if (d > 0) {
                    double x1 = (-b + Math.sqrt(d)) / (2 * a);
                    double x2 = (-b - Math.sqrt(d)) / (2 * a);
                    Log.i("sqrtD", String.format("%f %f %f %f", a,b,c,d));
                    rootsText.setText(String.format(Locale.US, "x₁ = %f\nx₂ = %f", x1, x2));
                } else if (d == 0) {
                    rootsText.setText(String.format(Locale.US, "x = %f", -b / (2*a)));
                } else {
                    rootsText.setText(R.string.no_roots);
                }
            }

            aCoefficientInput.clearFocus();
            bCoefficientInput.clearFocus();
            cCoefficientInput.clearFocus();
        });
    }

    private static double parseCoefficient(String str) {
        try {
            return (str.isBlank()) ? 0 : Double.parseDouble(str);
        } catch (Exception ex) {
            return 0;
        }
    }
}