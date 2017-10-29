package alcsoft.com.autobalance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * SetupFragment
 *
 * This object has the methods for the Options_Layout. It allows users
 * to setup values to be used later in the program.
 *
 * Created by ALCRamirez94 on 8/16/2017. Revised 10/29/2017
 * Ver 1.2
 */

public class SetupFragment extends Fragment{

    View view;
    private EditText IncomeEdit;
    private EditText DeductionEdit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.options_layout, container, false);
        // Loads the current saved options
        updateSettings();

        IncomeEdit = (EditText) view.findViewById(R.id.IncomeInputField);
        DeductionEdit = (EditText) view.findViewById(R.id.DeductionsInputField);
        IncomeEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    getEditTextOps();
                    return true;

                }else{
                    return false;
                }
            }
        });

        DeductionEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    getEditTextOps();
                    return true;
                }else{
                    return false;
                }
            }
        });


        return view;
    }

    private void getEditTextOps() {
                // Calls method to hide keyboard after click.
                MainActivity.hidekeyboard(this.getContext());
                // Temp variable
                float temp1 = 0.00f;
                // Float overflow boolean variables
                boolean incomechk = false;
                boolean deductionschk = false;
                // Checks if Field is empty
                if(IncomeEdit.getText().toString().isEmpty()){
                    // Do Nothing
                }else{
                    // Checks if number is out of bounds
                    if(Float.valueOf(IncomeEdit.getText().toString())>= 99999999.99f){
                        // Handles exception... literally nobody has that much money coming in unless you're a drug lord.
                        incomechk = true;
                    }else{
                        // Passes user income to temp variable as float
                        temp1 = Float.valueOf(IncomeEdit.getText().toString());
                        MainActivity.userVars.setUserIncome(temp1);
                    }
                }
                // Clears editText focus and value
                IncomeEdit.clearFocus();
                IncomeEdit.getText().clear();

                // Checks if field is empty
                if(DeductionEdit.getText().toString().isEmpty()){
                    // Do Nothing
                }else{
                    // Checks if number is out of bounds
                    if(Float.valueOf(DeductionEdit.getText().toString())>= 99999999.99f){
                        // Handles exception... literally nobody has that much bills... er.. I mean personally if you have that much bills to pay, what are  you a country?
                        deductionschk = true;
                    }else{
                        // Passes user deduction to temp variable as float
                        temp1 = Float.valueOf(DeductionEdit.getText().toString());
                        MainActivity.userVars.setUserDeductions(temp1);
                    }
                }
                // Clers edittext focus and value
                DeductionEdit.clearFocus();
                DeductionEdit.getText().clear();
                // Refreshes the options
                updateSettings();
                // Outputs appropriate response message using toast
                Toast toast;
                if(incomechk == true & deductionschk == true){
                    toast = Toast.makeText(getActivity(),"I'm not going to calculate that. Please use realistic values.",Toast.LENGTH_LONG);
                    toast.show();
                }else if(incomechk == true & deductionschk == false){
                    toast = Toast.makeText(getActivity(),"Quit lying, nobody makes that kind of money. Options Saved Successfully",Toast.LENGTH_LONG);
                    toast.show();
                }else if(incomechk == false & deductionschk == true){
                    toast = Toast.makeText(getActivity(),"I don't think you'll ever have any money to spend if you pay THAT MUCH each month. Options Saved Successfully.",Toast.LENGTH_LONG);
                    toast.show();
                }else{
                    toast = Toast.makeText(getActivity(), "Options Saved Successfully.", Toast.LENGTH_SHORT);
                    toast.show();
                }
    }

    private void updateSettings(){
        TextView textView = (TextView) view.findViewById(R.id.IncomeTextField);
        textView.setText(MainActivity.userVars.getUserIncomeString());
        textView = (TextView) view.findViewById(R.id.DeductionTextField);
        textView.setText(MainActivity.userVars.getUserDeductionsString());
        textView = (TextView) view.findViewById(R.id.NetAmountTextField);
        textView.setText(MainActivity.userVars.getUserNetIncomeString());
    }
}
