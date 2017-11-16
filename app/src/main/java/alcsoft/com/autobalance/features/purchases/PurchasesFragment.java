package alcsoft.com.autobalance.features.purchases;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import alcsoft.com.autobalance.features.shared.interfaces.MainDataInterface;
import alcsoft.com.autobalance.R;
import alcsoft.com.autobalance.features.shared.adapters.ListAdapter;
import alcsoft.com.autobalance.features.shared.dialogs.AddPurchaseDialog;
import alcsoft.com.autobalance.features.shared.dialogs.ConfirmActionDialog;
import alcsoft.com.autobalance.features.shared.interfaces.ConfirmActionDialogListener;
import alcsoft.com.autobalance.features.shared.interfaces.AddPurchaseDialogListener;


/**
 * PurchasesFragment
 * This class uses the purchases_layout and handles input and output Purchase Objects
 * and updates the list.
 * @author ALCRamirez94
 * @version 2.0 (11/8/2017)
 */

public class PurchasesFragment extends Fragment implements AdapterView.OnItemLongClickListener, AddPurchaseDialogListener, EditPurchaseDialogListener, ConfirmActionDialogListener {

    private MainDataInterface mainDataInterface;
    private ListAdapter listAdapter;
    private View view;
    private int purchasePosition;
    private int dialogChoice;

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.purchases_layout, container, false);
        /* Calls method to show options in action bar */
        setHasOptionsMenu(true);
        /* Sets the Transaction total amount text */
        refreshTextView();
        /*
        * ListView Initializations Begin Here
        * */
        ListView listView = (ListView) view.findViewById(R.id.PL_PurchaseList);
        listAdapter = new ListAdapter(getActivity(), mainDataInterface.getCurrentList());
        listView.setAdapter(listAdapter);
        /*
        * ListView Initializations Ends Here
        * */
        listView.setOnItemLongClickListener(this);

        return view;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> l, final View view, final int position, long id) {
        purchasePosition = position;
        EditPurchaseDialog editPurchaseDialog = new EditPurchaseDialog();
        editPurchaseDialog.setTargetFragment(this, 0);
        editPurchaseDialog.show(getActivity().getSupportFragmentManager(), "EditPurchaseDialog");
        return true;
    }


    // Creates the menu in the action bar
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Clears any menu before
        menu.clear();
        // Inflates the specific menu
        inflater.inflate(R.menu.pl_menu, menu);
    }

    // Handles the menu click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_PLSettings:
                ConfirmActionDialog confirmActionDialog = ConfirmActionDialog.newInstance(getString(R.string.PurchaseConfirmResetTitle), getString(R.string.PurchaseConfirmResetMessage));
                dialogChoice = 1;
                confirmActionDialog.setTargetFragment(this, 0);
                confirmActionDialog.show(getActivity().getSupportFragmentManager(), "ConfirmDialog");
                break;

            case R.id.action_PLAddPurchase:
                AddPurchaseDialog addPurchaseDialog = new AddPurchaseDialog();
                addPurchaseDialog.setTargetFragment(this, 0);
                addPurchaseDialog.show(getActivity().getSupportFragmentManager(), "DialogFragment");
                break;
            default:
                // Do nothing
        }
        return true;
    }

    public void onDialogAddPurchase(String name, String amt) {
        mainDataInterface.onPurchaseAdd(name, Float.valueOf(amt));
        listAdapter.notifyDataSetChanged();
        refreshTextView();

    }

    @Override
    public void onDialogInvalidInput(int errorLevel) {
        switch (errorLevel) {
            case 4:
                Toast.makeText(getActivity(), getString(R.string.ErrorLevel4), Toast.LENGTH_SHORT).show();
                break;
            case 5:
                Toast.makeText(getActivity(), getString(R.string.ErrorLevel5), Toast.LENGTH_LONG).show();
                break;
        }
    }

    public void onPurchaseEdit(String name, String date, String amt) {
        Date date1 = null;
        if (!date.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy", Locale.getDefault());
            try {
                date1 = dateFormat.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (!amt.isEmpty()) {
            if (Float.valueOf(amt) < 9999999.99f) {
                mainDataInterface.onPurchaseEdit(purchasePosition, date1, name, Float.valueOf(amt));
            } else {
                Toast.makeText(getActivity(), R.string.ErrorLevel6, Toast.LENGTH_LONG).show();
                mainDataInterface.onPurchaseEdit(purchasePosition, date1, name, -1.00f);
            }
        }
        mainDataInterface.onPurchaseEdit(purchasePosition, date1, name, -1.00f);
        listAdapter.notifyDataSetChanged();
        refreshTextView();
    }

    public void onPurchaseRemoval() {
        dialogChoice = 2;
        ConfirmActionDialog confirmActionDialog = ConfirmActionDialog.newInstance(getString(R.string.PurchaseConfirmDeleteTitle), getString(R.string.PurchaseConfirmDeleteMessage));
        confirmActionDialog.setTargetFragment(this, 0);
        confirmActionDialog.show(getActivity().getSupportFragmentManager(), "ConfirmDeleteDialog");
    }


    private void refreshTextView() {
        TextView textView = (TextView) view.findViewById(R.id.PL_TotalPurchaseAmtText);
        String temp = "$ " + mainDataInterface.getCurrentPurchaseAmtTotal();
        textView.setText(temp);
    }

    @Override
    public void onConfirmAction(boolean action) {
        switch (dialogChoice) {
            case 1:
                // Reset Purchase List
                if (action) {
                    //Reset the List
                    mainDataInterface.onPurchaseListReset();
                    listAdapter.notifyDataSetChanged();
                    refreshTextView();
                    Toast.makeText(getActivity(), getString(R.string.PurchaseListResetSuccess), Toast.LENGTH_SHORT).show();
                } else {
                    // Don't reset the list
                    Toast.makeText(getActivity(), getString(R.string.PurchaseCancelAction), Toast.LENGTH_SHORT).show();
                }
                break;

            case 2:
                // Delete purchase
                if (action) {
                    mainDataInterface.onPurchaseRemoveAt(purchasePosition);
                    listAdapter.notifyDataSetChanged();
                    refreshTextView();
                    Toast.makeText(getActivity(), getString(R.string.PurchaseRemoveActionSuccess), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), getString(R.string.PurchaseCancelAction), Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
}