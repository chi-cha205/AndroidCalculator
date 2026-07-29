package jp.ac.sus.t124042.tablelayouttest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private TextView textView4;

    private double result = 0.0;
    private String operator = "";
    private boolean isNewInput = true;
    private double lastValue = 0.0;
    private boolean lastEqual = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        textView4 = findViewById(R.id.textView4);

        textView.setText("0");
        textView4.setText("");
    }

    public void onClick(View view) {

        Button button = (Button) view;
        String str = button.getText().toString();

        textView4.setText(str);

        // AC
        if (str.equals("AC")) {
            result = 0.0;
            operator = "";
            lastValue = 0.0;
            isNewInput = true;
            lastEqual = false;

            textView.setText("0");
            textView4.setText("");
            return;
        }

        if (str.equals("%")) {
            double value = Double.parseDouble(textView.getText().toString());
            value = value / 100.0;
            textView.setText(formatNumber(value));
            return;
        }

        // +/-
        if (str.equals("+/-")) {
            double value =
                    Double.parseDouble(textView.getText().toString());

            value = value * -1;

            textView.setText(formatNumber(value));
            return;
        }

        // %
        if (str.equals("%")) {
            double value =
                    Double.parseDouble(textView.getText().toString());

            value = value / 100.0;

            textView.setText(formatNumber(value));
            return;
        }

        // 演算子
        if (str.equals("+")
                || str.equals("-")
                || str.equals("×")
                || str.equals("÷")) {

            result =
                    Double.parseDouble(textView.getText().toString());

            operator = str;
            isNewInput = true;
            lastEqual = false;
            return;
        }

        // =
        if (str.equals("=")) {

            double value;

            if (lastEqual) {
                value = lastValue;
            } else {
                value =
                        Double.parseDouble(
                                textView.getText().toString());
                lastValue = value;
            }

            switch (operator) {

                case "+":
                    result += value;
                    break;

                case "-":
                    result -= value;
                    break;

                case "×":
                    result *= value;
                    break;

                case "÷":

                    if (value == 0) {
                        textView.setText("Error");
                        isNewInput = true;
                        return;
                    }

                    result /= value;
                    break;
            }

            textView.setText(formatNumber(result));

            isNewInput = true;
            lastEqual = true;

            return;
        }

        // 数字入力

        String current = textView.getText().toString();

        if (current.equals("Error")) {
            current = "0";
        }

        // 小数点重複防止
        if (str.equals(".") && current.contains(".")) {
            return;
        }

        if (isNewInput || current.equals("0")) {

            if (str.equals(".")) {
                textView.setText("0.");
            } else {
                textView.setText(str);
            }

            isNewInput = false;

        } else {

            textView.setText(current + str);
        }

        lastEqual = false;
    }

    private String formatNumber(double value) {

        String result =
                String.format("%.10f", value);

        result = result.replaceAll("0+$", "");
        result = result.replaceAll("\\.$", "");

        return result;
    }
}