package alcsoft.com.autobalance.features.shared.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;

import alcsoft.com.autobalance.R;
import alcsoft.com.autobalance.features.purchases.Purchase;

/**
 * List Adapter
 * It communicates data between the arrayList and the ListView.
 * @author ALCRamirez94
 * @version 2.0 (11/3/2017)
 */

public class ListAdapter extends ArrayAdapter<Purchase> {

    public ListAdapter(Context context, ArrayList<Purchase> purchases){
        super(context,0,purchases);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Purchase purchase = getItem(position);
        //
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_purchases,parent,false);
        }
        // Lookup View
        // Set the purchase name
        TextView textView = (TextView) convertView.findViewById(R.id.item_purchaseName);
        textView.setText(purchase != null ? purchase.getPurchaseName() : null);
        // Set the amount (string)
        textView = (TextView) convertView.findViewById(R.id.item_purchaseAmount);
        String temp = String.format(java.util.Locale.US, "%.2f", purchase != null ? purchase.getPurchaseAmt() : 0);
        textView.setText(temp);
        // Set the Purchase Date
        textView = (TextView) convertView.findViewById(R.id.item_purchaseDate);
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
        assert purchase != null;
        temp = df.format(purchase.getPurchaseDate());
        textView.setText(temp);

        return convertView;
    }
}