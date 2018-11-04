package alcsoft.com.autobalance.features.purchases;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Purchase Object
 * This object models the Purchase information of a Purchase.
 * @author ALCRamirez94
 * @version 3.0 (11/2/2017)
 * @since 1.0
 */

public class Purchase {
    /**
     * The name of the purchase
     */
    private String PurchaseName;
    /**
     * The date of the purchase
     */
    private Date PurchaseDate;
    /**
     * The amount of the purchase
     */
    private BigDecimal PurchaseAmt;

    /**
     * Constructs the object using the parameters to define it.
     * @param name  the name of the purchase
     * @param date  the date of the purchase
     * @param amt  the amount of the purchase
     */
    public Purchase(String name, Date date, BigDecimal amt) {
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
    void setPurchaseAmt(BigDecimal amt){
        PurchaseAmt = amt;
    }

    /**
     * Sets the PurchaseDate to the defined parameter.
     * @param date  the new date of the purchase
     */
    void setPurchaseDate(Date date) {
        PurchaseDate = date;
    }

    /**
     * Gets the Purchase Name
     * @return PurchaseName  the name of the purchase
     */
    public String getPurchaseName() {
        return PurchaseName;
    }

    /**
     * Gets the Purchase Date
     * @return PurchaseDate  the date of the purchase
     */
    public Date getPurchaseDate() {
        return PurchaseDate;
    }

    /**
     * Gets the Purchase Amount
     * @return PurchaseAmt  the amount of the purchase
     */
    public BigDecimal getPurchaseAmt() {
        return PurchaseAmt;
    }
}
