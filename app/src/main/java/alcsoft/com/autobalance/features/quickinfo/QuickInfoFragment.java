package alcsoft.com.autobalance.features.quickinfo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import java.math.BigDecimal;

import alcsoft.com.autobalance.features.shared.dialogs.AddPurchaseDialog;
import alcsoft.com.autobalance.features.shared.interfaces.AddPurchaseDialogListener;
import alcsoft.com.autobalance.features.shared.interfaces.MainDataInterface;
import alcsoft.com.autobalance.R;
import alcsoft.com.autobalance.features.shared.adapters.ListAdapter;
import alcsoft.com.autobalance.features.purchases.PurchasesFragment;


/**
 * QuickInfoFragment
 * This fragment displays quick stats on the user's current amount to spend.
 * @author ALCRamirez94
 * @version 2.0 (11/7/2017)
 */

public class QuickInfoFragment extends Fragment implements AdapterView.OnItemClickListener, AddPurchaseDialogListener {

    /**
     * Interface to communicate with MainActivity
     */
    private MainDataInterface mainDataInterface;
    private ListAdapter listAdapter;
    private View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainDataInterface) {
            mainDataInterface = (MainDataInterface) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.layout_quickinfo, container, false);
        // Updates the spending amount to current value
        refreshTextView();

        ListView listView = (ListView) view.findViewById(R.id.QF_RecentPurchasesList);
        listAdapter = new ListAdapter(getActivity(), mainDataInterface.getCurrentList());
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Clears any menu before
        menu.clear();
        // Inflates the specific menu
        inflater.inflate(R.menu.qf_menu, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_QFAddPurchase:
                AddPurchaseDialog addPurchaseDialog = AddPurchaseDialog.newInstance(getString(R.string.QuickAddPurchaseDialogTitle));
                addPurchaseDialog.setTargetFragment(this, 0);
                addPurchaseDialog.show(getActivity().getSupportFragmentManager(), "DialogFragment");
                break;
            default:
                // Do nothing
        }
        return true;
    }

    private void refreshTextView() {
        // Updates the spending amount to current value
        String temp;
        // Sets the TextViews to match the values
        TextView spendingAmtText = (TextView) view.findViewById(R.id.QF_SpendingAmountAvailText);
        temp = mainDataInterface.getCurrentAmtAvail();
        spendingAmtText.setText(temp);

        if (mainDataInterface.getRawCurrentIncome().equals(new BigDecimal(0.00))) {
            float compare = Float.valueOf(mainDataInterface.getRawCurrentPurchaseAmtTotal().divide(mainDataInterface.getRawCurrentNetIncome()).multiply(new BigDecimal(100.00)).toString());
            System.out.println(compare);

            if (compare >= 100) {
                spendingAmtText.setTextColor(getResources().getColor(R.color.colorNoFunds));
            }
            if (75 <= compare && compare < 100) {
                spendingAmtText.setTextColor(getResources().getColor(R.color.colorLowFunds));
            }
            if (50 <= compare && compare < 75) {
                spendingAmtText.setTextColor(getResources().getColor(R.color.colorMediumLowFunds));
            }
            if (25 <= compare && compare < 50) {
                spendingAmtText.setTextColor(getResources().getColor(R.color.colorMediumHiFunds));
            }
            if (5 < compare && compare < 25) {
                spendingAmtText.setTextColor(getResources().getColor(R.color.colorHighLowFunds));
            }
            if (0 <= compare && compare <= 5) {
                spendingAmtText.setTextColor(getResources().getColor(R.color.colorHighFullFunds));
            }
        } else {
            spendingAmtText.setTextColor(getResources().getColor(R.color.colorNoFunds));
        }


        TextView totalPurchaseAmtText = (TextView) view.findViewById(R.id.QF_PurchaseTotalAmtText);
        temp = mainDataInterface.getCurrentPurchaseAmtTotal();
        totalPurchaseAmtText.setText(temp);
    }

    @Override
    public void onDetach() {
        mainDataInterface = null;
        super.onDetach();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        FragmentManager fragmentManager = super.getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new PurchasesFragment()).commit();
    }

    @Override
    public void onDialogAddPurchase(String name, String amt) {
        mainDataInterface.onPurchaseAdd(name, new BigDecimal(amt));
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
}
