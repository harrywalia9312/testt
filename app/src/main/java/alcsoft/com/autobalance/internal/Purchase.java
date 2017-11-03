package alcsoft.com.autobalance.internal;

/**
 *  Purchase Object
 * This object models the Purchase information of a Purchase.
 * @author ALCRamirez94
 * @version 2.0 (11/2/2017)
 */

class Purchase {
    /**
     * The name of the purchase
     */
    private String PurchaseName;
    /**
     * The date of the purchase
     */
    private String PurchaseDate;
    /**
     * The amount of the purchase
     */
    private float PurchaseAmt;

    /**
     * Constructs the object using the parameters to define it.
     * @param name  the name of the purchase
     * @param date  the date of the purchase
     * @param amt  the amount of the purchase
     */
    public Purchase(String name, String date, float amt){
        this.PurchaseName = name;
        this.PurchaseDate = date;
        this.PurchaseAmt = amt;
    }


    /**
     * Sets the PurchaseName to the defined parameter.
     * @param name  the new name of the purchase
     */
    void setPurchaseName(String name){
        PurchaseName = name;
    }

    /**
     * Sets the PurchaseAmount to the defined parameter.
     * @param amt  the new amount of the purchase
     */
    void setPurchaseAmt(Float amt){
        PurchaseAmt = amt;
    }

    /**
     * Sets the PurchaseDate to the defined parameter.
     * @param date  the new date of the purchase
     */
    void setPurchaseDate(String date){
        PurchaseDate = date;
    }

    /**
     * Gets the Purchase Name
     * @return PurchaseName  the name of the purchase
     */
    String getPurchaseName(){
        return PurchaseName;
    }

    /**
     * Gets the Purchase Date
     * @return PurchaseDate  the date of the purchase
     */
    String getPurchaseDate(){
        return PurchaseDate;
    }

    /**
     * Gets the Purchase Amount
     * @return PurchaseAmt  the amount of the purchase
     */
    float getPurchaseAmt(){
        return PurchaseAmt;
    }
}
