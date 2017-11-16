package alcsoft.com.autobalance.features.purchases;

/**
 * EditPurchaseDialogListener
 * This interface allows data communication between PurchasesFragment and EditPurchaseDialog
 *
 * @author ALCRamirez94
 * @version 1.0 (11/16/2017)
 */

public interface EditPurchaseDialogListener {
    /**
     * Calls the method to edit a specific purchase
     *
     * @param name the new purchase name
     * @param Date the new purchase date
     * @param amt  the new purchase amount
     */
    void onPurchaseEdit(String name, String Date, String amt);

    /**
     * Calls the method to remove a specific purchase
     */
    void onPurchaseRemoval();
}