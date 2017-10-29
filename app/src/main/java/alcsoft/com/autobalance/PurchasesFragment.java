package alcsoft.com.autobalance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * PurchasesFragment
 *
 * This class uses the purchases_layout and handles input and output Purchase Objects
 * and updates the list.
 *
 * Created by ALCRamirez94 on 8/16/2017. Revised 10/29/2017
 * Ver 1.2
 */

public class PurchasesFragment extends Fragment implements View.OnClickListener {

    private ListView listView;
    private ListAdapter listAdapter;
    private EditText PurchaseNameEdit;
    private EditText PurchaseAmtEdit;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.purchases_layout,container,false);
        // Calls the initialization method
        initializeStatus();

        final Button resetPurchaseListButton = (Button) view.findViewById(R.id.PL_ResetListButton);
        resetPurchaseListButton.setOnClickListener(this);
        final Button removeLastTransaction = (Button) view.findViewById(R.id.PL_RemoveLastPurchaseButton);
        removeLastTransaction.setOnClickListener(this);
        PurchaseNameEdit = (EditText) view.findViewById(R.id.PL_PurchaseNameInputField);
        PurchaseAmtEdit = (EditText) view.findViewById(R.id.PL_PurchaseAmountInputField);

        // Sets OnActionListeners for Input Fields
        PurchaseNameEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_NEXT){
                    PurchaseAmtEdit.requestFocus();
                    return true;
                }
                return false;
            }
        });

        PurchaseAmtEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    performInputOperations();
                    return true;
                }
                    return false;
            }
        });

        return view;
    }

    public void onResume(){
        super.onResume();
        // On resume fragment
        initializeStatus();
    }

    private void initializeStatus(){
        // Sets the Transaction total amount text
        TextView textView = (TextView) view.findViewById(R.id.PL_PurchaseTotalAmt);
        textView.setText("$ "+MainActivity.purchaseHandler.getTotalPAmtString());
        // Initializes the listView.
        listView = (ListView) view.findViewById(R.id.PL_PurchaseList);
        listAdapter = new ListAdapter(getActivity(),MainActivity.purchaseHandler.getPurchaseListReference());
        listView.setAdapter(listAdapter);
    }

    private void updateStatus(){
        MainActivity.updateSpendingAmt();
        TextView textView = (TextView)view.findViewById(R.id.PL_PurchaseTotalAmt);
        textView.setText("$ "+MainActivity.purchaseHandler.getTotalPAmtString());
    }

    private void performInputOperations(){
        // Calls method to close keyboard.
        MainActivity.hidekeyboard(this.getContext());
        // Checks if the input field is empty
        if(PurchaseAmtEdit.getText().toString().isEmpty()){
            // Returns a message that field is empty
            Toast.makeText(getActivity(),"Please enter a purchase amount.",Toast.LENGTH_SHORT).show();
            // Clears edit text and focus of AmtField
            PurchaseAmtEdit.getText().clear();
            PurchaseAmtEdit.clearFocus();
            // Clears edit text and focus of NameField
            PurchaseAmtEdit.getText().clear();
            PurchaseAmtEdit.clearFocus();
        }else{
            // Checks if number is inbounds
            if(Float.valueOf(PurchaseAmtEdit.getText().toString())>= 99999999.99f){
                // Handles the exception... seriously you cant afford it.
                Toast.makeText(getActivity(),"You and I both know you can't afford this...",Toast.LENGTH_LONG).show();
                // Clears edit text and focus.
                PurchaseAmtEdit.getText().clear();
                PurchaseAmtEdit.clearFocus();
            }else{
                // Proceeds normally.
                String temp;
                String date;
                float tempfl;

                tempfl = Float.valueOf(PurchaseAmtEdit.getText().toString());
                PurchaseAmtEdit.clearFocus();
                PurchaseAmtEdit.getText().clear();

                temp = PurchaseNameEdit.getText().toString();
                // Gets the current date and saves it to a string
                Calendar c = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy");
                date = simpleDateFormat.format(c.getTime());
                // Sends to list
                MainActivity.purchaseHandler.addPurchase(temp,date,tempfl);
                PurchaseNameEdit.clearFocus();
                PurchaseNameEdit.getText().clear();
                listAdapter.notifyDataSetChanged();
                updateStatus();
                Toast.makeText(getActivity(),"Purchase Added!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.PL_RemoveLastPurchaseButton:
                MainActivity.purchaseHandler.removeLastTransaction();
                listAdapter.notifyDataSetChanged();
                updateStatus();
                break;

            case R.id.PL_ResetListButton:
                MainActivity.purchaseHandler.resetList();
                listAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity(),"List was reset!",Toast.LENGTH_SHORT).show();
                updateStatus();
                break;
        }
    }
}
