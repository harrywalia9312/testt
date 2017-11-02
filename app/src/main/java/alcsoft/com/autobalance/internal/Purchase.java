package alcsoft.com.autobalance.internal;

/**
 *  Purchase Object
 *
 * This object holds the purchase name (String), the purchase date (String), and the purchase amount (Float)
 * Accessors return the values and their appropriate datatypes with the exception of the purchase amount, it returns
 * a float and a string value.
 *
 * Created by ALCRamirez94 on 8/16/2017. Revised 11/2/2017
 * Ver 2.0
 */

class Purchase {
    // Declared Private Variables
    private String PurchaseName;
    private String PurchaseDate;
    private float PurchaseAmt;

    // Constructor
    public Purchase(String name, String date, float amt){
        PurchaseName = name;
        PurchaseDate = date;
        PurchaseAmt = amt;
    }

    // Mutators
    // Sets the PurchaseName to parameter
    public void setPurchaseName(String name){
        PurchaseName = name;
    }

    // Sets the PurchaseAmt to parameter
    public void setPurchaseAmt(Float amt){
        PurchaseAmt = amt;
    }

    // Sets the PurchaseDate to Parameter
    public void setPurchaseDate(String date){
        PurchaseDate = date;
    }

    // Accessors
    // Returns the String PurchaseName
    public String getPurchaseName(){
        return PurchaseName;
    }

    // Returns the String PurchaseDate
    public String getPurchaseDate(){
        return PurchaseDate;
    }

    // Returns the Float PurchaseAmt
    public float getPurchaseAmt(){
        return PurchaseAmt;
    }
}
