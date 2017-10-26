package alcsoft.com.autobalance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import alcsoft.com.autobalance.internal.Purchase;

/**
 * List Adapter
 *
 * This class handles the list objects in the layout purchases_layout.
 * It keeps the arraylist updated to any changes throughout use.
 *
 * Created by ALCRamirez94 on 8/16/2017.
 * Ver 1.0
 */

public class ListAdapter extends ArrayAdapter<Purchase> {
    public ListAdapter(Context context, ArrayList<Purchase> purchases){
        super(context,0,purchases);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Purchase purchase = getItem(position);
        //
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_purchases,parent,false);
        }
        // Lookup View
        // Set the purchase name
        TextView textView = (TextView) convertView.findViewById(R.id.item_purchaseName);
        textView.setText(purchase.getPname());
        // Set the amount (string)
        textView = (TextView) convertView.findViewById(R.id.item_purchaseAmount);
        textView.setText(purchase.getPAmtString());
        // Set the Purchase Date
        textView = (TextView) convertView.findViewById(R.id.item_purchaseDate);
        textView.setText(purchase.getPDate());

        return convertView;
    }
}