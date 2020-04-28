package android.example.calculator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.commons.lang3.StringUtils;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "CalculatorActivity";

    private String calculationclause;
    private double firstNumber;
    private Double secondNumber;
    private TextView clauseView;
    private TextView resultView;
    private Calculator.Operator operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clauseView = findViewById(R.id.clause);
        resultView = findViewById(R.id.result);
    }

    private String getViewText(View view) {
        Button b = (Button) view;
        return b.getText().toString();
    }

    private String setCalculationClause(String textToAdd) {
        if (calculationclause != null) {
            calculationclause = textToAdd;
        } else if (calculationclause != null) {
            calculationclause = calculationclause + textToAdd;
        } else {
            calculationclause = textToAdd;
        }
        return calculationclause;
    }

    public void compute(View view) {
        if (!isCalculationClauseEmpty()) {
            if (operator != null) {
                int firstNumberIndexEnd = calculationclause.indexOf(operator.getOperator());
                if (firstNumberIndexEnd != -1) {
                    firstNumber = Double.valueOf(calculationclause.substring(0, firstNumberIndexEnd));
                }
                String valueAfterOperator = calculationclause
                        .substring(calculationclause.lastIndexOf(operator.getOperator()) + 1);
                if (!valueAfterOperator.isEmpty()) {
                    secondNumber = Double.valueOf(valueAfterOperator);
                    double result = 0;
                    final Calculator calculator = new Calculator();
                    try {
                        switch (operator) {
                            case DIV:
                                result = calculator.div(firstNumber, secondNumber);
                                break;
                            case ADD:
                                result = calculator.add(firstNumber, secondNumber);
                                break;
                            case SUB:
                                result = calculator.sub(firstNumber, secondNumber);
                                break;
                            case MUL:
                                result = calculator.mul(firstNumber, secondNumber);
                                break;
                            default:
                                Log.e("Calculation", "Invalid argument");
                        }
                    } catch (NumberFormatException e) {
                        Log.e(TAG, "NumberFormatException");
                        clauseView.setText(getString(R.string.computationError));
                        return;
                    }
                    resultView.setText(String.valueOf(result));
                    operator = null;
                }
            }
        } else {
            Log.i("Equals", "No given operator or calculation clause is empty");
        }
    }

    public void onNumber(View view) {
        String text;
        if (calculationclause == null || operator == null) {
            text = getViewText(view);
        } else {
            text = calculationclause + getViewText(view);
        }
        clauseView.setText(text);
        calculationclause = text;
    }

    public void onDiv(View view) {
        operator = Calculator.Operator.DIV;
        clauseView.setText(setCalculationClause(operator.getOperator()));
    }

    public void onAdd(View view) {
        operator = Calculator.Operator.ADD;
        clauseView.setText(setCalculationClause(operator.getOperator()));
    }

    public void onSubtract(View view) {
        operator = Calculator.Operator.SUB;
        clauseView.setText(setCalculationClause(operator.getOperator()));
    }

    public void onMultiply(View view) {
        operator = Calculator.Operator.MUL;
        clauseView.setText(setCalculationClause(operator.getOperator()));
    }

    public void onErase(View view) {
        String str = clauseView.getText().toString();
        String result = removeLastCharacter(str);
        clauseView.setText(result);
        calculationclause = result;
    }

    public void onCe(View view) {
        clauseView.setText("");
        resultView.setText("");
        operator = null;
        calculationclause = "";
    }

    public void onPeriod(View view) {
        String addPeriod = calculationclause + getViewText(view);
        clauseView.setText(addPeriod);
        calculationclause = addPeriod;
    }

    private String removeLastCharacter(final String string) {
        return StringUtils.substring(string, 0, string.length() - 1);
    }

    private boolean isCalculationClauseEmpty() {
        if (calculationclause != null) {
            return true;
        } else
            return false;
    }
}

