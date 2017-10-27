package alcsoft.com.autobalance.internal;

/**
 *  Purchase Object
 *
 * This object holds the purchase name (String), the purchase date (String), and the purchase amount (Float)
 * Accessors return the values and their appropriate datatypes with the exception of the purchase amount, it returns
 * a float and a string value.
 *
 * Created by ALCRamirez94 on 8/16/2017.
 * Ver 1.0
 */

public class Purchase {
    private String PName;
    private String PDate;
    private float PAmt;

    public Purchase(String namein, String datein, float amtin){
        PName = namein;
        PDate = datein;
        PAmt = amtin;
    }

    public String getPname(){
        return PName;
    }

    public String getPDate(){
        return PDate;
    }

    public String getPAmtString(){
        return String.format(java.util.Locale.US,"%.2f",PAmt);
    }
    public float getPAmt(){
        return PAmt;
    }
}
