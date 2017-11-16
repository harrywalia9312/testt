package alcsoft.com.autobalance.features.shared.interfaces;

/**
 * ConfirmActionDialogListener
 * This interface allows data to be communicated between fragment and dialog.
 *
 * @author ALCRamirez94
 * @version 1.0 (11/16/2017)
 */

public interface ConfirmActionDialogListener {

    /**
     * Passes the decision value of the dialog.
     *
     * @param action the dialog yes or no decision
     */
    void onConfirmAction(boolean action);
}
