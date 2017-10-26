package alcsoft.com.autobalance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * MainFragment
 *
 * This class uses the layout main_layout and refreshes the value when user views the
 * layout.
 *
 * Created by ALCRamirez94 on 8/16/2017.
 * Ver 1.0
 */

public class MainFragment extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_layout, container, false);
        // Calls the Method updateStatus
        updateStatus();
        return view;
    }

    public void updateStatus(){
        // Updates the spending amount to current value
        MainActivity.updateSpendingAmt();
        // Sets the textviews to match the values
        TextView textView = (TextView) view.findViewById(R.id.Main_SpendingAmount);
        textView.setText("$ "+MainActivity.AvailSpendingAmt);

        textView = (TextView) view.findViewById(R.id.Main_PurchaseTotalAmt);
        textView.setText("$ "+MainActivity.purchaseHandler.getTotalPAmtString());
    }
}
