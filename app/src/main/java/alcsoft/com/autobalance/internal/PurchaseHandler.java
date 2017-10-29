package alcsoft.com.autobalance.internal;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * PurchaseHandler Object
 * This object handles the ArrayList of Purchase objects. Provides accessors and mutators functions
 * to the ArrayList. It also returns the ArrayList as a JSON object for storage use.
 *
 * Created by ALCRamirez94 on 8/16/2017. Revised 10/27/2017
 * Ver 1.1
 */

public class PurchaseHandler {
    private ArrayList<Purchase> PurchaseList;
    private float TotalPAmt;
    private int Top;

    // Default Constructor
    public PurchaseHandler(String inlist, int intop, float inpamt){
        // Checks if list exists
        if(inlist.equals("none")){
            // Initialize default values
            PurchaseList = new ArrayList<Purchase>();
            TotalPAmt = 0.00f;
            Top = 0;
        }else{
            // Loads the already existing list from storage
            // Reads the ArrayList from JSON to ArrayList
            Gson gson = new Gson();
            String json = inlist;
            Type type = new TypeToken<ArrayList<Purchase>>() {}.getType();
            // Loads the Transaction List
            PurchaseList = gson.fromJson(json,type);
            // Loads the Top Value
            Top = intop;
            // Loads the Total Purchase Amount
            TotalPAmt = inpamt;
        }
    }

    // ArrayList Mutators
    public void addPurchase(String namein, String datein, float amtin){
        // Adds the top value by 1
        Top = Top + 1;
        // Overflow check
        if(TotalPAmt+amtin <= 99999999.99f){
            // Set to maximum value.
            TotalPAmt = 9999999.99f;
        }else {
            // Adds the total float value
            TotalPAmt = TotalPAmt + amtin;
        }

        // Creates a new purchase object
        Purchase purchase = new Purchase(namein,datein,amtin);
        // Adds it to the top list
        PurchaseList.add(0,purchase);
    }

    // Reset entire list
    public void resetList(){
        PurchaseList.clear();
        Top = 0;
        TotalPAmt = 0.00f;
    }

    public void removeLastTransaction(){
        // Underflow Check
        if(Top == 0){
            // Underflow, Do Nothing
        }else{
            // Subtract the Top value by 1
            Top = Top - 1;
            // Pulls the Object into a temp object
            Purchase purchase = PurchaseList.get(0);
            // Subtracts the purchase amt from the total
            TotalPAmt = TotalPAmt - purchase.getPAmt();
            // Removes the purchase object from the ArrayList
            PurchaseList.remove(0);
        }
    }

    // ArrayList Accessors
    public String getPurchaseListforStorage(){
        Gson gson = new Gson();
        String json = gson.toJson(PurchaseList);
        return json;
    }

    public ArrayList<Purchase> getPurchaseListReference(){
        return PurchaseList;
    }

    // TotalPAmt Accessors
    public float getTotalPAmt(){
        return TotalPAmt;
    }

    public String getTotalPAmtString(){
        return String.format(java.util.Locale.US,"%.2f",TotalPAmt);
    }

    // Top Accessor
    public int getTop(){
        return Top;
    }
}
