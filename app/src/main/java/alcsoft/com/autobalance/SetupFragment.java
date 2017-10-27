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
                // Temporary float value
                float temp1 = 0.00f;
                float temp2 = 0.00f;
                // Trys to convert income value to float
                EditText editText = (EditText) view.findViewById(R.id.IncomeInputField);
                try{

                    temp1 += Float.valueOf(editText.getText().toString());
                }catch(Exception e){
                    // Do Nothing
                }finally{
                    editText.clearFocus();
                    editText.getText().clear();
                }

                editText = (EditText) view.findViewById(R.id.DeductionsInputField);
                try{

                    temp2 = Float.valueOf(editText.getText().toString());
                }catch(Exception e){
                    // Do Nothing
                }finally{
                    editText.clearFocus();
                    editText.getText().clear();
                }

                MainActivity.userVars.setUserValues(temp1,temp2);

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
