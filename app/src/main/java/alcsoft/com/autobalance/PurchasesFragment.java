package alcsoft.com.autobalance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by ALCRamirez94 on 8/16/2017. Revised 10/27/2017
 * Ver 1.1
 */

public class PurchasesFragment extends Fragment implements View.OnClickListener {

    private ListView listView;
    private ListAdapter listAdapter;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.purchases_layout,container,false);
        // Calls the initialization method
        initializeStatus();

        final Button addPurchaseButton = (Button) view.findViewById(R.id.PL_AddPurchaseToList);
        addPurchaseButton.setOnClickListener(this);
        final Button resetPurchaseListButton = (Button) view.findViewById(R.id.PL_ResetListButton);
        resetPurchaseListButton.setOnClickListener(this);
        final Button removeLastTransaction = (Button) view.findViewById(R.id.PL_RemoveLastPurchaseButton);
        removeLastTransaction.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.PL_AddPurchaseToList:
                // Calls method to close keyboard.
                MainActivity.hidekeyboard(this.getContext());

                EditText editText = (EditText) view.findViewById(R.id.PL_PurchaseAmountInputField);
                System.out.print("Before IF");
                System.out.print(editText.getText().toString().isEmpty());
                if(editText.getText().toString().isEmpty()){

                    Toast.makeText(getActivity(),"Please Enter the purchase amount.",Toast.LENGTH_SHORT).show();

                }else{
                    String temp;
                    String date;
                    float tempfl;

                    tempfl = Float.valueOf(editText.getText().toString());
                    editText.clearFocus();
                    editText.getText().clear();

                    // Gets the name of the purchase list item
                    editText = (EditText) view.findViewById(R.id.PL_PurchaseNameInputField);
                    temp = editText.getText().toString();
                    // Gets the current date and saves it to a string
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy");
                    date = simpleDateFormat.format(c.getTime());
                    // Sends to list
                    MainActivity.purchaseHandler.addPurchase(temp,date,tempfl);
                    editText.clearFocus();
                    editText.getText().clear();
                    listAdapter.notifyDataSetChanged();
                    updateStatus();
                    Toast.makeText(getActivity(),"Purchase Added!",Toast.LENGTH_SHORT).show();

                }

                break;
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
