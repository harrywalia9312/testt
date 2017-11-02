package alcsoft.com.autobalance.internal;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * PurchaseHandler Object
 * This object handles the ArrayList of Purchase objects and provides methods to access and mutate it.
 * It also returns the arraylist as a reference or a formatted JSON object for storage.
 *
 * Created by ALCRamirez94 on 11/1/2017.
 * Ver 2.0
 */

public class PurchaseHandler {
    private ArrayList<Purchase> PurchaseList;
    private float TotalPurchaseAmt;
    private int Top;

    // Constructor
    public PurchaseHandler(String purchaseList, int top, float amt){
        // Checks if list exists
        if(purchaseList.equals("none")){
            // Creates an Empty list and uses Default values.
            PurchaseList = new ArrayList<>();
            TotalPurchaseAmt = 0.00f;
            Top = 0;
        }else{
            // Loads the already existing list from storage
            // Loads the GSON module to read gson strings.
            Gson gson = new Gson();
            // Defines the type of object to look for
            Type type = new TypeToken<ArrayList<Purchase>>() {}.getType();
            // Loads the Transaction List from the json string
            PurchaseList = gson.fromJson(purchaseList,type);
            // Loads the Top Value
            Top = top;
            // Loads the Total Purchase Amount
            TotalPurchaseAmt = amt;
        }
    }

    // Mutators
    public void addPurchase(String namein, String datein, float amtin){
        // Adds the top value by 1
        Top = Top + 1;
        // Overflow check
        if(TotalPurchaseAmt+amtin >= 99999999.99f){
            // Set to maximum value.
            TotalPurchaseAmt = 9999999.99f;
        }else {
            // Adds the total float value
            TotalPurchaseAmt = TotalPurchaseAmt + amtin;
        }

        // Creates a new purchase object
        Purchase purchase = new Purchase(namein,datein,amtin);
        // Adds it to the top list
        PurchaseList.add(0,purchase);
    }

    public void editPurchaseAt(int position, String name, float amt, String date){
        // Edits purchase at position, only values that are not empty gets edited.
        // Gets the purchase object from the list at position
        Purchase purchase = PurchaseList.get(position);

        // Checks if name is empty
        // Updates the name on the object
        if (!name.isEmpty()) purchase.setPurchaseName(name);

        // Checks if float is -1
        // Updates the amount on the object
        if (amt != -1.00f) purchase.setPurchaseAmt(amt);

        // Checks if date is empty
        // Updates the date on the object
        if (!date.isEmpty()) purchase.setPurchaseDate(date);

        // Replaces the object at the position with the updated information.
        PurchaseList.set(position,purchase);
    }

    public void removePurchaseAt(int position){
        // Removes one item off the list at position.
        // Updates the top value
        Top = Top - 1;
        // Pulls the object from PurchaseList at position into a temp object
        Purchase purchase = PurchaseList.get(position);
        // Updates the TotalPurchaseAmount
        TotalPurchaseAmt = TotalPurchaseAmt - purchase.getPurchaseAmt();
        // Removes it from the list.
        PurchaseList.remove(position);
    }

    public void removeAllPurchases(){
        // Removes all purchases from list and resets values to default.
        PurchaseList.clear();
        Top = 0;
        TotalPurchaseAmt = 0.00f;
    }

    public void removeLastTransaction(){
        // Underflow Check
        if (Top != 0) {
            // Subtract the Top value by 1
            Top = Top - 1;
            // Pulls the Object into a temp object
            Purchase purchase = PurchaseList.get(0);
            // Subtracts the purchase amt from the total
            TotalPurchaseAmt = TotalPurchaseAmt - purchase.getPurchaseAmt();
            // Removes the purchase object from the ArrayList
            PurchaseList.remove(0);
        }
    }

    // ArrayList Accessors
    public String getDataToSave(){
        Gson gson = new Gson();
        return gson.toJson(PurchaseList);
    }

    public ArrayList<Purchase> getPurchaseList(){
        return PurchaseList;
    }

    public float getTotalPurchaseAmt(){
        return TotalPurchaseAmt;
    }

    public int getTop(){
        return Top;
    }
}
