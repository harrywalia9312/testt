package alcsoft.com.autobalance.features.shared.interfaces;

/**
 * AddPurchaseDialogListener
 * This interface communicates data between any fragment and dialog that calls it.
 *
 * @author ALCRamirez94
 * @version 1.0 (11/16/2017)
 */

public interface AddPurchaseDialogListener {

    /**
     * Passes the parameters for Purchase handling.
     *
     * @param name the name of the purchase
     * @param amt  the amount of the purchase
     */
    void onDialogAddPurchase(String name, String amt);

    /**
     * Passes the error level for error handling.
     *
     * @param errorLevel the type of error
     */
    void onDialogInvalidInput(int errorLevel);
}