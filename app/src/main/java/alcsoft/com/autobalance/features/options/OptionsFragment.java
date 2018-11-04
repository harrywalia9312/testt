package alcsoft.com.autobalance.features.options;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

import alcsoft.com.autobalance.features.shared.interfaces.MainDataInterface;
import alcsoft.com.autobalance.R;


/**
 * OptionsFragment
 * This object has the methods for the Options_Layout. It allows users to setup their Monthly Income and Expenses.
 * @author ALCRamirez94
 * @version 2.0 (11/7/2017)
 */

public class OptionsFragment extends Fragment {

    /**
     * Interface to communicate with MainActivity
     */
    private MainDataInterface mainDataInterface;
    /**
     * EditText that links to the MonthlyIncomeInputField
     */
    private EditText MonthlyIncomeInputBox;
    /**
     * EditText that links to the MonthlyExpensesInputField
     */
    private EditText MonthlyExpensesInputBox;
    /**
     * TextView that links to the MonthlyNetIncome Value
     */
    private TextView MonthlyNetIncome;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainDataInterface) {
            mainDataInterface = (MainDataInterface) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    @Override
    public void onDetach() {
        mainDataInterface = null;
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.options_layout, container, false);
        // Creates the options menu in the actionbar
        setHasOptionsMenu(true);
        // Initialization of EditTexts
        MonthlyIncomeInputBox = (EditText) view.findViewById(R.id.OF_MonthlyIncomeInputField);
        MonthlyExpensesInputBox = (EditText) view.findViewById(R.id.OF_MonthlyExpensesInputField);
        MonthlyNetIncome = (TextView) view.findViewById(R.id.OF_NetIncomeTextField);
        // Pulls the current data for Income and Expenses and Net Income
        MonthlyIncomeInputBox.setText(mainDataInterface.getCurrentIncome());
        MonthlyExpensesInputBox.setText(mainDataInterface.getCurrentExpenses());
        MonthlyNetIncome.setText(mainDataInterface.getCurrentNetIncome());


        // Listeners initialized for keyboard actions and its handlers
        MonthlyIncomeInputBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (MonthlyIncomeInputBox.getText().toString().equals(mainDataInterface.getCurrentIncome())) {
                    MonthlyIncomeInputBox.getText().clear();
                }
                return false;
            }
        });

        MonthlyExpensesInputBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (MonthlyExpensesInputBox.getText().toString().equals(mainDataInterface.getCurrentExpenses())) {
                    MonthlyExpensesInputBox.getText().clear();
                }
                return false;
            }
        });

        MonthlyIncomeInputBox.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (MonthlyIncomeInputBox.getText().toString().isEmpty()) {
                        MonthlyIncomeInputBox.setText(mainDataInterface.getCurrentIncome());
                    }
                }

            }
        });

        MonthlyExpensesInputBox.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (MonthlyExpensesInputBox.getText().toString().isEmpty()) {
                        MonthlyExpensesInputBox.setText(mainDataInterface.getCurrentExpenses());
                    }
                }
            }
        });

        // Keyboard handler on MonthlyInputField
        MonthlyIncomeInputBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    // Checks Input is empty
                    if (!MonthlyIncomeInputBox.getText().toString().isEmpty()) {
                        //Checks if Float value is valid
                        BigDecimal temp = new BigDecimal(MonthlyIncomeInputBox.getText().toString());
                        if (temp.compareTo(new BigDecimal(99999999.99)) < 0) {
                            // Saves the value
                            mainDataInterface.onIncomeEdit(temp);
                            // Refreshes TextView boxes
                            MonthlyIncomeInputBox.setText(mainDataInterface.getCurrentIncome());
                            MonthlyNetIncome.setText(mainDataInterface.getCurrentNetIncome());
                        } else {
                            // Display Error message
                            Toast.makeText(getActivity(), getString(R.string.ErrorLevel1), Toast.LENGTH_LONG).show();
                            MonthlyIncomeInputBox.setText(mainDataInterface.getCurrentIncome());
                        }
                    }
                    // Clears focus
                    MonthlyIncomeInputBox.clearFocus();
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert inputMethodManager != null;
                    inputMethodManager.hideSoftInputFromWindow(MonthlyIncomeInputBox.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }else{
                    return false;
                }
            }
        });

        // Keyboard handler on MonthlyExpensesInputField
        MonthlyExpensesInputBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    // Checks Input is empty
                    if (!MonthlyExpensesInputBox.getText().toString().isEmpty()) {
                        //Checks if Float value is valid
                        BigDecimal temp = new BigDecimal(MonthlyExpensesInputBox.getText().toString());
                        if (temp.compareTo(new BigDecimal(99999999.99)) < 0) {
                            // Saves the value
                            mainDataInterface.onExpensesEdit(temp);
                            // Refreshes TextView boxes
                            MonthlyExpensesInputBox.setText(mainDataInterface.getCurrentExpenses());
                            MonthlyNetIncome.setText(mainDataInterface.getCurrentNetIncome());
                        } else {
                            // Display Error message
                            Toast.makeText(getActivity(), getString(R.string.ErrorLevel2), Toast.LENGTH_LONG).show();
                            MonthlyExpensesInputBox.setText(mainDataInterface.getCurrentExpenses());
                        }
                    }
                    // Clears focus
                    MonthlyExpensesInputBox.clearFocus();
                    // Clears keyboard
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert inputMethodManager != null;
                    inputMethodManager.hideSoftInputFromWindow(MonthlyExpensesInputBox.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    return true;
                }else{
                    return false;
                }
            }
        });

        return view;
    }

    public void onUserDataReset() {
        mainDataInterface.onIncomeEdit(BigDecimal.ZERO);
        mainDataInterface.onExpensesEdit(BigDecimal.ZERO);
    }
}
