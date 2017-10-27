package alcsoft.com.autobalance.internal;

import com.google.gson.Gson;

/**
 * UserVars Object
 * This object stores the user defined Income value (Float), Deductions value (Float)
 * and keeps the updated Net Income (Float)
 *
 * Created by ALCRamirez94 on 8/16/2017 Revised: 10/27/2017
 * Ver 1.1
 */

public class UserVars {
    private float UserIncome;
    private float UserDeductions;
    private float UserNetIncome;

    public UserVars(String userin){
        // Checks if stored data exists
        if(userin.equals("none")){
            // Use default values
            UserIncome = 0.00f;
            UserDeductions = 0.00f;
            UserNetIncome = 0.00f;
        }else{
            // Loads the value from storage
            Gson gson = new Gson();
            UserVars temp = gson.fromJson(userin,UserVars.class);
            UserIncome = temp.getUserIncome();
            UserDeductions = temp.getUserDeductions();
            UserNetIncome = temp.getUserNetIncome();
        }
    }

    // UserIncome and UserDeduction Mutator
    public void setUserValues(float inincome, float indeductions){
        // Sets the UserIncome and UserDeduction value
        UserIncome = inincome;
        UserDeductions = indeductions;
        // Updates the NetIncome
        UserNetIncome = UserIncome - UserDeductions;
    }

    // UserIncome Accessors
    public float getUserIncome(){
        return UserIncome;
    }

    public String getUserIncomeString(){
        return String.format(java.util.Locale.US,"%.2f",UserIncome);
    }

    // UserDeductions Accessors
    public float getUserDeductions(){
        return UserDeductions;
    }

    public String getUserDeductionsString(){
        return String.format(java.util.Locale.US,"%.2f",UserDeductions);
    }

    // UserNetIncome Accessors
    public float getUserNetIncome(){
        return UserNetIncome;
    }

    public String getUserNetIncomeString(){
        return String.format(java.util.Locale.US,"%.2f",UserNetIncome);
    }

    // Object Accessor
    public String getUserVarsForStorage(){
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }
}
