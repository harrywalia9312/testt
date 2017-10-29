package alcsoft.com.autobalance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * SetupFragment
 *
 * This object has the methods for the Options_Layout. It allows users
 * to setup values to be used later in the program.
 *
 * Created by ALCRamirez94 on 8/16/2017. Revised 10/27/2017
 * Ver 1.1
 */

public class SetupFragment extends Fragment implements View.OnClickListener {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.options_layout, container, false);
        // Loads the current saved options
        updateSettings();

        // Save Button
        final Button button = (Button) view.findViewById(R.id.SaveOptionsButton);
        button.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.SaveOptionsButton:
                // Calls method to hide keyboard after click.
                MainActivity.hidekeyboard(this.getContext());
                // Temp variable
                float temp1 = 0.00f;
                // Assigns editText to IncomeInputField
                EditText editText = (EditText) view.findViewById(R.id.IncomeInputField);
                // Checks if Field is empty
                if(editText.getText().toString().isEmpty()){
                    // Do Nothing
                }else{
                    // Passes user income to temp variable as float
                    temp1 = Float.valueOf(editText.getText().toString());
                    MainActivity.userVars.setUserIncome(temp1);
                    // Clears editText focus and value
                    editText.clearFocus();
                    editText.getText().clear();
                }
                // Assign the editText to the Deductions input field
                editText = (EditText) view.findViewById(R.id.DeductionsInputField);
                // Checks if field is empty
                if(editText.getText().toString().isEmpty()){
                    // Do Nothing
                }else{
                    // Passes user deduction to temp variable as float
                    temp1 = Float.valueOf(editText.getText().toString());
                    MainActivity.userVars.setUserDeductions(temp1);
                    // Clers edittext focus and value
                    editText.clearFocus();
                    editText.getText().clear();
                }
                // Refreshes the options
                updateSettings();
                // Outputs success message
                Toast.makeText(getActivity(),"Options Saved Successfully!",Toast.LENGTH_SHORT).show();
                break;
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
