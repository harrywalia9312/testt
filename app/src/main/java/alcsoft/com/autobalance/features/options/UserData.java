package alcsoft.com.autobalance.features.options;

import com.google.gson.Gson;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * UserData Object
 * This object models the user's monthly income, expenses and net income information.
 * @author ALCRamirez94
 * @version 2.0 (11/3/2017)
 */

public class UserData {

    /**
     * The User's Monthly Income
     */
    private BigDecimal UserMonthlyIncome;
    /**
     * The User's Monthly Expenses
     */
    private BigDecimal UserMonthlyExpenses;
    /**
     * The User's Monthly Net Income
     */
    private BigDecimal UserMonthlyNetIncome;

    /**
     * Constructs the object based on the parameter given. Uses default values if
     * it is equal to "none".
     * @param userData  a string value that is JSON Formatted (Default: "none")
     */
    public UserData(String userData){
        // Checks if stored data exists
        if(userData.equals("none")){
            // Use default values 0 and sets the rounding to HALF_EVEN with 2 decimal places
            this.UserMonthlyIncome = BigDecimal.ZERO;
            this.UserMonthlyIncome = this.UserMonthlyIncome.setScale(2,RoundingMode.HALF_EVEN);
            this.UserMonthlyExpenses = BigDecimal.ZERO;
            this.UserMonthlyExpenses = this.UserMonthlyExpenses.setScale(2,RoundingMode.HALF_EVEN);
            this.UserMonthlyNetIncome = BigDecimal.ZERO;
            this.UserMonthlyNetIncome = this.UserMonthlyNetIncome.setScale(2,RoundingMode.HALF_EVEN);
        }else{
            // Loads the value from storage
            Gson gson = new Gson();
            // Uses a temp object to store parameter
            UserData temp = gson.fromJson(userData,UserData.class);
            // Sets the value to the current object.
            this.UserMonthlyIncome = temp.getUserMonthlyIncome();
            this.UserMonthlyExpenses = temp.getUserMonthlyExpenses();
            this.UserMonthlyNetIncome = temp.getUserMonthlyNetIncome();
        }
    }

    /**
     *  Updates the UserMonthlyNetIncome using the subtraction of UserMonthlyIncome and UserMonthlyExpenses.
     */
    private void updateUserMoNetIncome(){
        UserMonthlyNetIncome = UserMonthlyIncome.subtract(UserMonthlyExpenses);
    }

    /**
     * Sets the User Monthly Income and calls updateUserMoNetIncome() to update the new values.
     * @param income  the user's monthly income as a float value
     */
    public void setUserMonthlyIncome(BigDecimal income){
        UserMonthlyIncome = income;
        updateUserMoNetIncome();
    }

    /**
     * Sets the User Monthly Expenses and calls updateUserMoNetIncome() to update the new values.
     * @param expenses  the user's monthly expenses as a float value
     */
    public void setUserMonthlyExpenses(BigDecimal expenses){
        UserMonthlyExpenses = expenses;
        updateUserMoNetIncome();
    }

    /**
     * Gets the float value UserMonthlyIncome
     * @return  UserMonthlyIncome  the user's saved monthly expenses as a float value
     */
    // Accessors
    public BigDecimal getUserMonthlyIncome(){
        return UserMonthlyIncome;
    }

    /**
     * Gets the float value UserMonthlyExpenses
     * @return  UserMonthlyExpenses  the user's saved monthly expenses as a float value
     */
    public BigDecimal getUserMonthlyExpenses(){
        return UserMonthlyExpenses;
    }

    /**
     * Gets the float value UserMonthlyNetIncome
     * @return  UserMonthlyNetIncome  the user's saved monthly net income as a float value
     */
    public BigDecimal getUserMonthlyNetIncome(){
        return UserMonthlyNetIncome;
    }

    /**
     * Gets the boolean value by comparing UserMonthlyIncome and UserMonthlyExpenses to 0.00f
     * @return  boolean  a flag used to represent the user's current status
     */
    public boolean isFirstTimeUser(){
        return UserMonthlyIncome.equals(BigDecimal.ZERO) && UserMonthlyExpenses.equals(BigDecimal.ZERO);
    }

    /**
     * Gets this object as a JSON String
     * @return  json  a JSON formatted string of the object
     */
    public String getDataToSave(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

