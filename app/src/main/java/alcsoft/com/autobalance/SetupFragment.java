package alcsoft.com.autobalance;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
 * Ver 1.3
 */

public class SetupFragment extends Fragment{

    View view;
    private EditText IncomeEdit;
    private EditText DeductionEdit;
    private DialogInterface.OnClickListener dialogListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.options_layout, container, false);
        // Creates the options menu in actionbar
        setHasOptionsMenu(true);
        // Loads the current saved options
        updateSettings();

        // Initializes EditTexts for later use.
        IncomeEdit = (EditText) view.findViewById(R.id.IncomeInputField);
        DeductionEdit = (EditText) view.findViewById(R.id.DeductionsInputField);

        // Listeners intialized for keyboard actions and handlers
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

        // DialogListener listens for user response and handles them here.
        dialogListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Checks user's response.
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        // Resets values to 0
                        MainActivity.userVars.setUserDeductions(0.00f);
                        MainActivity.userVars.setUserIncome(0.00f);
                        // Refreshes the textView for current numbers.
                        updateSettings();
                        // Shows success dialog
                        Toast.makeText(getActivity(),"Values Reset!",Toast.LENGTH_SHORT).show();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        // Shows Dialog. No Changes made.
                        Toast.makeText(getActivity(), "Nothing was Changed", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        return view;
    }

    // Creates and inflates the menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        // Clears any menu
        menu.clear();
        // Sets the menu to specific one and inflates it.
        inflater.inflate(R.menu.optionslayout_menu,menu);
    }

    // Handles Menu clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            // If menu item is selected.
            case R.id.action_OPSettings:
                // Builds alert dialog and shows it.
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Reset Values");
                builder.setMessage("This will use 0 as the default income and deduction values. Are you sure?");
                builder.setPositiveButton("Yes", dialogListener);
                builder.setNegativeButton("No",dialogListener);
                builder.show();
                break;
            default:
                // Do nothing
        }
        return true;
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
                if(incomechk & deductionschk){
                    toast = Toast.makeText(getActivity(),"I'm not going to calculate that. Please use realistic values.",Toast.LENGTH_LONG);
                    toast.show();
                }else if(incomechk & !deductionschk){
                    toast = Toast.makeText(getActivity(),"Quit lying, nobody makes that kind of money.",Toast.LENGTH_LONG);
                    toast.show();
                }else if(!incomechk & deductionschk){
                    toast = Toast.makeText(getActivity(),"I don't think you'll ever have any money to spend if you pay THAT MUCH each month.",Toast.LENGTH_LONG);
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
