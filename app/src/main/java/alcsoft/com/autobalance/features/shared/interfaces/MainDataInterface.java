package alcsoft.com.autobalance.features.shared.interfaces;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import alcsoft.com.autobalance.features.purchases.Purchase;

/**
 * MainDataInterface
 * This interface allows data to be communicated from the Options Fragment to Main Activity.
 *
 * @author ALCRamirez94
 * @version 1.0 (11/6/2017)
 */

public interface MainDataInterface {
    /**
     * Passes the value of the MonthlyIncome between fragment and activity.
     *
     * @param income the user's prompted monthly income
     */
    void onIncomeEdit(BigDecimal income);

    /**
     * Passes the value of MonthlyExpenses between fragment and activity.
     *
     * @param expenses the user's prompted monthly expenses
     */
    void onExpensesEdit(BigDecimal expenses);

    /**
     * Gets the MonthlyIncome value from UserData object.
     *
     * @return the user's monthly Income
     */
    String getCurrentIncome();

    /**
     * Gets the MonthlyExpenses value from the UserData object.
     *
     * @return the user's monthly expenses
     */
    String getCurrentExpenses();

    /**
     * Gets the user's MonthlyNetIncome from the UserData object.
     *
     * @return the user's monthly net income
     */
    String getCurrentNetIncome();

    /**
     * Gets the user's available spending amount using values from
     * the UserData and PurchaseHandler objects.
     *
     * @return the user's available spending amount
     */
    String getCurrentAmtAvail();

    /**
     * Gets the user values to add a purchase into the purchase list
     *
     * @param name the name of the purchase (can be empty)
     * @param amt  the amount of the purchase (cannot be empty)
     */
    void onPurchaseAdd(String name, BigDecimal amt);

    /**
     * Based on user input, edits a specific purchase in the purchase list
     * if value is empty, value will not be edited.
     *
     * @param position the position of the purchase on the list
     * @param date     the new date of the purchase
     * @param name     the new name of the purchase
     * @param amt      the new amount of the purchase
     */
    void onPurchaseEdit(int position, Date date, String name, BigDecimal amt);

    /**
     * Based on user input, removes a specific purchase in the purchase list.
     *
     * @param position the position of the purchase
     */
    void onPurchaseRemoveAt(int position);

    /**
     * Removes all purchases in the purchase list
     */
    void onPurchaseListReset();

    /**
     * Returns the arrayList of the purchaseList.
     *
     * @return the arrayList of the purchaseList
     */
    ArrayList<Purchase> getCurrentList();

    /**
     * Gets the value of the TotalPurchaseAmt from purchaseHandler object.
     *
     * @return the total amount of all purchases
     */
    String getCurrentPurchaseAmtTotal();

    /**
     * Gets the name, date, amount of a specific purchase from the purchase list.
     *
     * @param position the position of the purchase
     * @return the name, date, amount concatenated in a string
     */
    String getPurchaseInfoAt(int position);

    BigDecimal getRawCurrentPurchaseAmtTotal();

    BigDecimal getRawCurrentIncome();

    BigDecimal getRawCurrentNetIncome();
}