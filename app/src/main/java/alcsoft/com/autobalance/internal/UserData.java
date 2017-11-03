package alcsoft.com.autobalance.internal;

import com.google.gson.Gson;

/**
 * UserData Object
 * This object models the User defined values for a
 * @author ALCRamirez94
 * @version 2.0
 * Created on 8/16/2017, Revised on : 11/3/2017
 * This object stores the user defined values
 *  Monthly Income value (Float), Monthly Expenses value (Float), Monthly Net Income (Float)
 */

public class UserData {

    /**
     * The User's Monthly Income
     */
    private float UserMonthlyIncome;
    /**
     * The User's Monthly Expenses
     */
    private float UserMonthlyExpenses;
    /**
     * The User's Monthly Net Income
     */
    private float UserMonthlyNetIncome;

    /**
     * Constructs the object based on the parameter given. Uses default values if
     * it is equal to "none".
     * @param userData  a string value that is JSON Formatted (Default: "none")
     */
    public UserData(String userData){
        // Checks if stored data exists
        if(userData.equals("none")){
            // Use default values 0 and sets the FirstTimeUser flag to true
            this.UserMonthlyIncome = 0.00f;
            this.UserMonthlyExpenses = 0.00f;
            this.UserMonthlyNetIncome = 0.00f;
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
    // Mutators
    private void updateUserMoNetIncome(){
        // Updates the user income
        UserMonthlyNetIncome = UserMonthlyIncome - UserMonthlyExpenses;
    }

    /**
     * Sets the User Monthly Income and calls updateUserMoNetIncome() to update the new values.
     * @param income  the user's monthly income as a float value
     */
    public void setUserMonthlyIncome(float income){
        UserMonthlyIncome = income;
        updateUserMoNetIncome();
    }

    /**
     * Sets the User Monthly Expenses and calls updateUserMoNetIncome() to update the new values.
     * @param expenses  the user's monthly expenses as a float value
     */
    public void setUserMonthlyExpenses(float expenses){
        UserMonthlyExpenses = expenses;
        updateUserMoNetIncome();
    }

    /**
     * Gets the float value UserMonthlyIncome
     * @return  UserMonthlyIncome  the user's saved monthly expenses as a float value
     */
    // Accessors
    public float getUserMonthlyIncome(){
        return UserMonthlyIncome;
    }

    /**
     * Gets the float value UserMonthlyExpenses
     * @return  UserMonthlyExpenses  the user's saved monthly expenses as a float value
     */
    public float getUserMonthlyExpenses(){
        return UserMonthlyExpenses;
    }

    /**
     * Gets the float value UserMonthlyNetIncome
     * @return  UserMonthlyNetIncome  the user's saved monthly net income as a float value
     */
    public float getUserMonthlyNetIncome(){
        return UserMonthlyNetIncome;
    }

    /**
     * Gets the boolean value by comparing UserMonthlyIncome and UserMonthlyExpenses to 0.00f
     * @return  boolean  a flag used to represent the user's current status
     */
    public boolean isFirstTimeUser(){
        return UserMonthlyIncome == 0.00f && UserMonthlyExpenses == 0.00f;
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
