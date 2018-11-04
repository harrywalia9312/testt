package alcsoft.com.autobalance.features.purchases;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * PurchaseHandler Object
 * This object handles the arrayList of Purchase objects and provides methods to access and modify it.
 * It also returns the arrayList as a reference or a formatted JSON object for storage.
 * @author ALCRamirez94
 * @version 2.0 (11/1/2017)
 */

public class PurchaseHandler {
    /**
     * ArrayList to store Purchase Objects
     */
    private final ArrayList<Purchase> PurchaseList;
    /**
     * The total accumulation of all PurchaseAmt from Purchase Objects stored in the list
     */
    private BigDecimal TotalPurchaseAmt;
    /**
     * The index value of the top item on the arrayList.
     */
    private int Top;

    /**
     * Constructs the object based on the parameter given, uses default values if
     * purchaseList is equal to "none"
     * @param purchaseList  the saved list as a JSON format, (Default: "none")
     * @param top  the saved Top value
     * @param amt  the saved TotalPurchaseAmt
     */
    public PurchaseHandler(String purchaseList, int top, BigDecimal amt){
        // Checks if list exists
        if(purchaseList.equals("none")){
            // Creates an Empty list and uses Default values.
            PurchaseList = new ArrayList<>();
            TotalPurchaseAmt = BigDecimal.ZERO;
            TotalPurchaseAmt = this.TotalPurchaseAmt.setScale(2, RoundingMode.HALF_EVEN);
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

    /**
     * Adds a Purchase object to the list using the parameters as information. Adds
     * the purchaseAmt to the total and updates the new top value.
     * @param name  the name of the purchase
     * @param date  the date of the purchase
     * @param amt  the amount of the purchase
     */
    public void addPurchase(String name, Date date, BigDecimal amt) {
        // Adds the top value by 1
        Top = Top + 1;
        // Overflow check
        if(TotalPurchaseAmt.add(amt).compareTo(new BigDecimal(99999999.99)) > 0 ){
            // Set to maximum value.
            TotalPurchaseAmt = new BigDecimal(9999999.99);
        }else {
            // Adds the total float value
            TotalPurchaseAmt = TotalPurchaseAmt.add(amt);
        }

        // Creates a new purchase object
        Purchase purchase = new Purchase(name,date,amt);
        // Adds it to the top list
        PurchaseList.add(0,purchase);
        sortList();
    }

    /**
     * Gets the Purchase object at a specified position on the list, edits ONLY the
     * non-empty values passed as the parameter.
     * @param position  the index value of the targeted object for modification
     * @param date  the new purchase date for the object
     * @param name  the new purchase name for the object
     * @param amt   the new purchase amount for the object
     */
    public void editPurchaseAt(int position, Date date, String name, BigDecimal amt) {
        // Edits purchase at position, only values that are not empty gets edited.
        // Gets the purchase object from the list at position
        Purchase purchase = PurchaseList.get(position);

        // Checks if name is empty
        // Updates the name on the object
        if (!name.isEmpty()) purchase.setPurchaseName(name);

        // Checks if float is -1
        // Updates the amount on the object
        if (amt.compareTo(new BigDecimal(-1.00)) == 0) {
            TotalPurchaseAmt = TotalPurchaseAmt.subtract(purchase.getPurchaseAmt());
            purchase.setPurchaseAmt(amt);
            TotalPurchaseAmt = TotalPurchaseAmt.add(amt);
        }

        // Checks if date is empty
        // Updates the date on the object
        if (date != null) purchase.setPurchaseDate(date);

        // Replaces the object at the position with the updated information.
        PurchaseList.set(position,purchase);
        sortList();
    }

    /**
     * Removes the Purchase object from the specified position from the list
     * and updates the top and net income values.
     * @param position  the index value of the targeted object for deletion
     */
    public void removePurchaseAt(int position){
        // Removes one item off the list at position.
        // Updates the top value
        Top = Top - 1;
        // Pulls the object from PurchaseList at position into a temp object
        Purchase purchase = PurchaseList.get(position);
        // Updates the TotalPurchaseAmount
        TotalPurchaseAmt = TotalPurchaseAmt.subtract(purchase.getPurchaseAmt());
        // Removes it from the list.
        PurchaseList.remove(position);
        sortList();
    }

    /**
     * Removes ALL Purchase objects from the lists and sets the values as if
     * the list was empty.
     */
    public void removeAllPurchases(){
        // Removes all purchases from list and resets values to default.
        PurchaseList.clear();
        Top = 0;
        TotalPurchaseAmt = BigDecimal.ZERO;
        TotalPurchaseAmt = TotalPurchaseAmt.setScale(2,RoundingMode.HALF_EVEN);
    }

    /**
     * Gets the ArrayList as a JSON formatted string
     * @return json  a json formatted string of the arrayList
     */
    public String getDataToSave(){
        Gson gson = new Gson();
        return gson.toJson(PurchaseList);
    }

    /**
     * Gets the Purchase Date, Name, and Amt from a specified purchase from PurchaseList
     *
     * @param position the position of the item in the arrayList
     * @return temp  the purchase date, name, amt
     */
    public Purchase getPurchaseInfoAt(int position) {
        return PurchaseList.get(position);
    }

    /**
     * Gets the ArrayList.
     * @return PurchaseList  the arrayList that stores the Purchase Objects
     */
    public ArrayList<Purchase> getPurchaseList(){
        return PurchaseList;
    }

    /**
     * Sorts the ArrayList from most recent date
     */
    private void sortList() {
        Collections.sort(PurchaseList, new Comparator<Purchase>() {
            @Override
            public int compare(Purchase o1, Purchase o2) {
                return o2.getPurchaseDate().compareTo(o1.getPurchaseDate());
            }
        });
    }

    /**
     * Gets the TotalPurchaseAmt value
     * @return TotalPurchaseAmt  the accumulation of all Purchase amounts on the arrayList
     */
    public BigDecimal getTotalPurchaseAmt(){
        return TotalPurchaseAmt;
    }

    /**
     * Gets the top value.
     * @return Top  the index value of the top of the arrayList
     */
    public int getTop(){
        return Top;
    }

}
